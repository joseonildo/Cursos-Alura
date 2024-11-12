// --- CONFIGURAÇÃO WIFI ---
#include <WiFi.h>
const char* ssid = "Alura-Wifi";
const char* password = "123456";
WiFiClient esp32Client;

// --- CONFIGURAÇÃO MQTT CLIENT ---
#include <PubSubClient.h>
PubSubClient client(esp32Client);
const char* mqtt_server = "192.168.0.1";
const char* mqtt_id = "ESP32-BLE";
const char* mqtt_user = "alura";
const char* mqtt_pass = "123456";
const char* mqtt_topico_sensor_temperatura = "ESP32/sensorTemperatura";
const char* mqtt_topico_sensor_umidade = "ESP32/sensorHumidade";
const char* mqtt_topico_sensor_presenca = "ESP32/sensorPresenca";
const char* mqtt_topico_sensor_sinal = "ESP32/nivelSinal";
const char* mqtt_topico_ativacao_manual = "ESP32/ativacaoManual";
boolean ativacaoManual = false;

// --- PARAMETROS DO DISPLAY OLED ---
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
Adafruit_SSD1306 display(128, 64, &Wire, -1);
long milissegundos = millis();

// --- PARAMETROS DO SENSOR DHT ---
#include "DHT.h"
#define DHTPIN 23
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
int temperaturaMedida = 0;
int umidadeMedida = 0;
int temperaturaAnterior = 0;
int umidadeAnterior = 0;

// --- PARAMETROS DO BLUETOOTH LE ---
#include <BLEDevice.h>
#include <BLEScan.h>
#include <BLEAdvertisedDevice.h>
int scanTime = 5;  //In seconds

// --- PARÂMETROS DOS DISPOSITIVOS BLE BUSCADO ---
int sinalRequerido = -65;
String nomeDispositivoBuscado = "Relogio RS4";
String macDispositivoBuscado = "**:**:**:**:**:**";
String estadoDaLuz = "";
String estadoDaLuzAnterior = "";
int dispositivoPresente = 0;
int nivelSinalMedido = -100;
int nivelSinalMqtt = -100;
int nivelSinalMqttAnterior = -100;
int contadorSinal = 0;
BLEScan *pBLEScan;

// ----------------------- CONFIGURAÇÕES INICIAIS ------------------
void setup() {
  Serial.begin(115200);
  configurarDisplay(); 
  conectarWifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(monitoraTopico);
  dht.begin(); 
  BLEDevice::init("");  
}

// --------------------------LOOP PRINCIPAL ------------------------
void loop() {  
  if (WiFi.status() != WL_CONNECTED) conectarWifi();
  if (!client.connected()) conectarMQTT();
  leSensorDHT();
  client.loop();          
  if (doisSegundos()) {
    realizaScanBLE();
  }  
  publicarLeiturasMQTT();
  mostrarLeiturasDisplay();
}

// ----------------------- FUNÇÕES AUXILIARES ----------------------

// --- CONFIGURAR DISPLAY OLED ---
void configurarDisplay() {
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);  
  display.setTextColor(WHITE);
  display.display();  
  delay(500);
  display.clearDisplay();  
}

// --- CONECTAR A REDE WIFI ---
void conectarWifi() {
  display.setTextSize(1);
  display.setCursor(0,0);
  display.print("Iniciando Wifi");
  Serial.print("Iniciando conexão Wifi");  
  display.display();    
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    display.print(".");
    Serial.print(".");
    display.display();
    if (display.getCursorX() >= 126 && display.getCursorY() >= 56) {
      display.clearDisplay();
      display.setCursor(0,0);
      display.print("Iniciando Wifi");
      display.display();
    }
    delay(500);   
  }
  display.println();
  display.println("Wifi connectada!");
  Serial.println(" connectada!");  
  display.display();
  delay(1000);
}

// --- CONECTAR AO SERVIDOR MQTT ---
void conectarMQTT() {   
  display.print("Iniciando MQTT");
  Serial.print("Iniciando conexão MQTT");  
  display.display();
  while (!client.connected()) {   
    client.connect(mqtt_id,mqtt_user,mqtt_pass);
    client.subscribe(mqtt_topico_ativacao_manual);
    display.print(".");
    Serial.print(".");
    display.display();
    if (display.getCursorX() >= 126 && display.getCursorY() >= 56) {
        display.clearDisplay();
        display.setCursor(0,0);
        display.print("Iniciando MQTT");
        display.display();
    }
    delay(500);     
  }
  display.println();
  display.println("MQTT conectado!");
  Serial.println(" conectado!");
  display.display();
  delay(1000);    
}

// --- MONITORA A TOPICO  ESP32/ativacaoManual ---
void monitoraTopico(char* mqtt_topico_ativacao_manual, byte* payload, unsigned int length) {
  if ((char)payload[0] == '1') {
    ativacaoManual = true;
    Serial.println("Ativação da luz manual - Sensor de presença desligado");
    estadoDaLuz = "ON";
  }  else {
    ativacaoManual = false;
    Serial.println("Desativação da luz manual - Sensor de presença ligado");
  }
}

// --- LÊ E TESTAR O SENSOR DHT ---
void leSensorDHT() {
  if (isnan(dht.readHumidity()) || isnan(dht.readTemperature())) {
    display.print("Falha no sensor DHT");
    Serial.print("Falha na conexão com o sensor DHT");
    display.display();
    while (isnan(dht.readHumidity()) || isnan(dht.readTemperature())) {
      display.print(".");
      Serial.print(".");
      display.display();
      if (display.getCursorX() >= 126 && display.getCursorY() >= 56) {
        display.clearDisplay();
        display.setCursor(0,0);
        display.print("Falha no sensor DHT");
        display.display();
      }    
      delay(500);
    }
    display.println();
    display.print("Sensor reconectado!");
    Serial.println(" reconectado!");
    display.display();
    delay(1000);         
  }
  umidadeMedida = dht.readHumidity();  
  temperaturaMedida = dht.readTemperature(); 
  display.clearDisplay();
}

// --- FAZ O TRATAMENTO DOS DISPOSITIVOS BLE ESCANEADOS ---
class MyAdvertisedDeviceCallbacks : public BLEAdvertisedDeviceCallbacks {  
  void onResult(BLEAdvertisedDevice advertisedDevice) {
    String dispositivoEscaneado = advertisedDevice.getAddress().toString().c_str();   
    if (macDispositivoBuscado == dispositivoEscaneado) {
      nivelSinalMedido = advertisedDevice.getRSSI();      
      nivelSinalMqtt = nivelSinalMedido;
      Serial.printf("%s, Sinal: %i > %i ?",nomeDispositivoBuscado,nivelSinalMedido,sinalRequerido); 
      if (nivelSinalMedido >= sinalRequerido) { 
        Serial.println(" SIM -> DISPOSITIVO DENTRO DA FAIXA!");        
        dispositivoPresente = 3;
      } else { 
        Serial.println(" NÂO -> DISPOSITIVO FORA DA FAIXA!");      
      }
      pBLEScan->stop(); // Parar a varredura caso encontre o dispositivo procurado
    }
  }  
};

// --- INICIA ESCANEANDO DISPOSITIVOS BLE ---
void realizaScanBLE() {
  if (!ativacaoManual) {     
    pBLEScan = BLEDevice::getScan();  //create new scan
    pBLEScan->setAdvertisedDeviceCallbacks(new MyAdvertisedDeviceCallbacks());
    pBLEScan->setActiveScan(true);  //active scan uses more power, but get results faster  
    pBLEScan->setInterval(100);
    pBLEScan->setWindow(99);  // less or equal setInterval value
    BLEScanResults *foundDevices = pBLEScan->start(scanTime, false); 
    if (dispositivoPresente == 3 && nivelSinalMedido >= sinalRequerido) {      
      estadoDaLuz = "ON";      
    } else {
      if (--dispositivoPresente <= 0) {      
        estadoDaLuz = "OFF";
        dispositivoPresente = 0;
      }
      if (nivelSinalMedido == -100) {
        Serial.println("DISPOSITIVO SEM SINAL!");
        nivelSinalMqtt = -100;
      }
    }
    pBLEScan->clearResults();  // limpa memória
    nivelSinalMedido = -100;
  }
}

// --- PUBLICA (MQTT) TEMPERATURA, UMIDADE E PRESENÇA ---
void publicarLeiturasMQTT(){
  if (temperaturaMedida != temperaturaAnterior) {
    temperaturaAnterior = temperaturaMedida;
    client.publish(mqtt_topico_sensor_temperatura, String(temperaturaMedida).c_str(), true);     
  }
  if (umidadeMedida != umidadeAnterior) {
    umidadeAnterior = umidadeMedida;
    client.publish(mqtt_topico_sensor_umidade, String(umidadeMedida).c_str(), true);    
  }
  if (nivelSinalMqtt != nivelSinalMqttAnterior) { 
    contadorSinal++;   
    client.publish(mqtt_topico_sensor_sinal, String(nivelSinalMqtt).c_str(), true);
    nivelSinalMqttAnterior = nivelSinalMqtt;       
  } 
  if (estadoDaLuz != estadoDaLuzAnterior) {
    estadoDaLuzAnterior = estadoDaLuz; 
    client.publish(mqtt_topico_sensor_presenca, estadoDaLuz.c_str(), true);    
  } 
}

// --- EXIBE TEMPERATURA E UMIDADE NO DISPLAY ---
void mostrarLeiturasDisplay() {     
  display.clearDisplay(); 
  display.setTextSize(1);
  display.setCursor(0,0);   display.print("Temperatura");
  display.setCursor(71,0);  display.print("Humidade");
  display.setTextSize(4);  
  display.setCursor(0,17);  display.print(temperaturaMedida);
  display.setTextSize(2);   display.print("o");
  display.setTextSize(4);
  display.setCursor(68,17); display.print(umidadeMedida);
  display.setTextSize(2);   display.print("%");
  display.setTextSize(1);  
  if (ativacaoManual) {
    display.setCursor(0,56);  display.print("MANUAL");
  } else {    
    display.setCursor(15,56);  display.print(estadoDaLuz);
  }
  display.setCursor(50,56);  display.print("Sinal: ");
  display.print(nivelSinalMqtt);  display.print("dB");
  display.display();
  display.clearDisplay();   
}

// --- CONTA DOIS SEGUNDOS E RETORNA TRUE ---
boolean doisSegundos() {
  if (millis() > milissegundos + 2000) {
    milissegundos = millis();
    return true;
  }
  return false;
}