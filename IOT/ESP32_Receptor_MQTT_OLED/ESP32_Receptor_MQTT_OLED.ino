// --- CONFIGURAÇÃO WIFI ---
#include <WiFi.h>
const char* ssid = "Alura-Wifi";
const char* password = "123456";
WiFiClient esp32Client;

// --- CONFIGURAÇÃO MQTT CLIENT ---
#include <PubSubClient.h>
PubSubClient client(esp32Client);
const char* mqtt_server = "192.168.0.1";
const char* mqtt_id = "ESP32-MQTT";
const char* mqtt_user = "alura";
const char* mqtt_pass = "123456";
const char* mqtt_topico_sensor_temperatura = "ESP32/sensorTemperatura";
const char* mqtt_topico_sensor_humidade = "ESP32/sensorHumidade";
const char* mqtt_topico_sensor_presenca = "ESP32/sensorPresenca";
const char* mqtt_topico_sensor_sinal = "ESP32/nivelSinal";
const char* mqtt_topico_ativacao_manual = "ESP32/ativacaoManual";
String estadoDaLuz = "";
int temperatura;
int humidade;
int nivelSinalMqtt = -100;
boolean ativacaoManual = false;

// --- PARAMETROS DO DISPLAY OLED ---
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
Adafruit_SSD1306 display(128, 64, &Wire, -1);
long milissegundos = millis();

// ----------------------- CONFIGURAÇÕES INICIAIS ------------------
void setup() {
  Serial.begin(115200);
  configurarDisplay(); 
  conectarWifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(monitoraTopicos);
}

// --------------------------LOOP PRINCIPAL ------------------------
void loop() {
  if (WiFi.status() != WL_CONNECTED) conectarWifi();
  if (!client.connected()) conectarMQTT();
  client.loop();          
  mostrarLeiturasDisplay();
  delay(500);  
}

// ----------------------- FUNÇÕES AUXILIARES ----------------------

// --- CONFIGURAR DISPLAY OLED ---
void configurarDisplay() {
  display.begin(SSD1306_SWITCHCAPVCC, 0x3D);  
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
    client.subscribe(mqtt_topico_sensor_temperatura);
    client.subscribe(mqtt_topico_sensor_humidade);
    client.subscribe(mqtt_topico_sensor_presenca);
    client.subscribe(mqtt_topico_sensor_sinal);
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

// --- MONITORA OS TOPICOS INSCRITOS ---
void monitoraTopicos(char* topic, byte* payload, unsigned int length) {
  if (strcmp(topic,mqtt_topico_sensor_temperatura) == 0) {
    //Serial.print("Temperatura recebida: ");
    String recebida;
    for (int i=0;i<length;i++) {
      recebida += (char)payload[i];
    }
    //Serial.println(recebida);
    temperatura = recebida.toInt();
  }
  if (strcmp(topic,mqtt_topico_sensor_humidade) == 0) {
    //Serial.print("Humidade recebida: ");
    String recebida;
    for (int i=0;i<length;i++) {
      recebida += (char)payload[i];
    }
    //Serial.println(recebida);
    humidade = recebida.toInt();
  }
  if (strcmp(topic,mqtt_topico_sensor_presenca) == 0) {
    //Serial.print("Estado do sensor: ");
    String recebida;
    for (int i=0;i<length;i++) {
      recebida += (char)payload[i];
    }
    //Serial.println(recebida);
    estadoDaLuz = recebida;
  }
  if (strcmp(topic,mqtt_topico_sensor_sinal) == 0) {
    //Serial.print("Sinal recebido: ");
    String recebida;
    for (int i=0;i<length;i++) {
      recebida += (char)payload[i];
    }
    //Serial.println(recebida);
    nivelSinalMqtt = recebida.toInt();
  }
  if (strcmp(topic,mqtt_topico_ativacao_manual) == 0) {
    if ((char)payload[0] == '1') {
      ativacaoManual = true;
      Serial.println("Ativação da luz manual - Sensor de presença desligado");
      estadoDaLuz = "ON";
    } else {
      ativacaoManual = false;
      Serial.println("Desativação da luz manual - Sensor de presença ligado");
    }
  }
}

// --- EXIBE TEMPERATURA E UMIDADE NO DISPLAY ---
void mostrarLeiturasDisplay() {   
  display.clearDisplay(); 
  display.setTextSize(1);
  display.setCursor(0,0);   display.print("Temperatura");
  display.setCursor(71,0);  display.print("Humidade");
  display.setTextSize(4);  
  display.setCursor(0,17);  display.print(temperatura);
  display.setTextSize(2);   display.print("o");
  display.setTextSize(4);
  display.setCursor(68,17); display.print(humidade);
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