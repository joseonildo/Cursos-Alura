// --- CONFIGURAÇÃO WIFI e NTP ---
#include <ESP8266WiFi.h>
#include <NTPClient.h>
#include <WiFiUdp.h>
const char* ssid = "********";            // SSID WIFI
const char* password = "********";        // Senha WIFI
WiFiClient espClient;
WiFiUDP ntpUDP;                           // Serviço NTP para obter hora online
NTPClient timeClient(ntpUDP);             // Servidor NTP para obter hora online

// --- CONFIGURAÇÃO MQTT CLIENT ---
#include <PubSubClient.h>
PubSubClient client(espClient);
const char* mqtt_server = "192.168.0.123"; // Endereço Servidor MQTT Local
const char* clientID = "ESP8266-DHT22";    // ID Sensor MQTT
const char* clientUser = "****";           // Usuário servidor MQTT
const char* clientPass = "********";       // Senha servidor MQTT
const char* topicoTemperatura = "dht22/temperatura";
const char* topicoHumidade = "dht22/humidade";

// --- PARAMETROS DO DISPLAY OLED ---
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
Adafruit_SSD1306 display(128, 64, &Wire, -1);
long milissegundos = millis();
int horas = 0, minutos = 0, segundos = 0, conta = 0;
int inverte = false;
String mensagem;

// --- PARAMETROS DO SENSOR DHT ---
#include "DHT.h"
#define DHTPIN 2
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
int humidade;
int temperatura;

// --- CONFIGURAÇÕES INICIAIS ---
void setup() {  
  Serial.begin(115200);
  Wire.begin(12, 14);               // Define Pinos I2C do Display OLED embutido
  configurarDisplay();
  conectarWifi();
  configurarHora();                 // Obtem hora online para exibir no Display OLED
  client.setServer(mqtt_server, 1883);
  dht.begin();
}  

// --- LOOP PRINCIPAL ---
void loop() {  
  if (umSegundo()) {    
    if (WiFi.status() != WL_CONNECTED) conectarWifi();
    if (!client.connected()) conectarMQTT();
    if (leSensorDHT()) return;
    publicarTemperaturaUnidadeMQTT();
    exibeTemperaturaHumidadeOLED();
  }
}

// --- FUNÇÕES AUXILIARES ---
// --- CONFIGURAR DISPLAY OLED---
void configurarDisplay() {
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);  
  display.setTextColor(WHITE);
  display.display();  
  delay(1000);
  display.clearDisplay();  
}

// --- CONECTAR A REDE WIFI ---
void conectarWifi() {
  delay(10);  
  display.setTextSize(1);
  display.setCursor(0,0);
  display.print("Conectando a Wifi");
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    display.print(".");
    display.display();
    if (display.getCursorX() >= 126 && display.getCursorY() >= 56) {
      display.clearDisplay();
      display.setCursor(0,0);
      display.print("Conectando a Wifi");
      display.display();
      delay(1000);
    }
  }
  display.println("");
  display.print(ssid);
  display.println(" connectada!");  
  display.println("");
  display.display();
  delay(1000);
}

// --- AJUSTAR HORA ONLINE COM SERVIDOR NTP---
void configurarHora() {
  timeClient.begin();
  timeClient.setTimeOffset(-10800); // Ajusta o fuso horario para o horario do Brasil.
  timeClient.update();

  horas = timeClient.getHours();
  minutos = timeClient.getMinutes();
  segundos = timeClient.getSeconds();
}

// --- CONECTAR AO SERVIDOR MQTT ---
void conectarMQTT() {
  display.setTextSize(1);
  display.print("Conectando ao MQTT");   
  while (!client.connected()) {
    display.print(".");
    display.display();   
    client.connect(clientID,clientUser,clientPass);
    if (display.getCursorX() >= 126 && display.getCursorY() >= 56) {
      display.clearDisplay();
      display.setCursor(0,0);
      display.print("Conectando ao MQTT");
      display.display();
    }
    delay(1000);     
  }
  display.println();
  display.println("Conectado com sucesso");
  display.display();
  delay(1000);
  display.println();
  display.print("Iniciando sensor DHT");
  display.display();
  delay(1000); 
}

// --- LÊ E TESTAR O SENSOR DHT ---
boolean leSensorDHT() {
  humidade = dht.readHumidity();  
  temperatura = dht.readTemperature();
  if (isnan(dht.readHumidity()) || isnan(dht.readTemperature())) {
    exibeErro();
    return true;
  } 
  return false;
}

// --- EXIBE FALHA DO SENSOR DHT ---
void exibeErro() {
    mensagem = "Falha sensor DHT!";
    humidade = 0;
    temperatura = 0;
    publicarTemperaturaUnidadeMQTT();
    if (inverte) mostrarMensagemNoDisplay(mensagem,temperatura);
    else mostrarMensagemNoDisplay(mensagem,humidade);    
}

// --- PUBLICA (MQTT) TEMPERATURA E UMIDADE ---
void publicarTemperaturaUnidadeMQTT(){
  client.publish(topicoTemperatura, String(temperatura).c_str(), true);
  client.publish(topicoHumidade, String(humidade).c_str(), true);
}

// --- RECEBE DADOS E EXIBE NO DISPLAY ---
void exibeTemperaturaHumidadeOLED() {
  if (inverte) {                         
    mostrarMensagemNoDisplay("Temperatura:",temperatura);    
  } else {      
    mostrarMensagemNoDisplay("Humidade:",humidade);     
  } 
}

// --- EXIBE TEMPERATURA E UMIDADE NO DISPLAY ---
void mostrarMensagemNoDisplay(String texto1, int medicao) {   
  display.clearDisplay();
  display.setTextSize(1);
  display.setCursor(20,0); 
  display.print(texto1);
  display.setTextSize(4);
  display.setCursor(25,17);  
  display.print(medicao);  
  if (texto1 == "Temperatura:") {
    display.setTextSize(1);
    display.print("o");
    display.setTextSize(2);
    display.print("C");
  }else{ 
    display.setTextSize(2);
    display.print("%");    
  }
  mostrarRelogioNoDisplay(); 
}

// --- EXIBE A HORA NO DISPLAY ---
void mostrarRelogioNoDisplay() {  
    display.setTextSize(1);
    display.setCursor(25,56);    
    display.print(horas);
    if (minutos < 10)  display.print(":0");
    else display.print(":");
    display.print(minutos);
    if (segundos < 10)  display.print(":0");
    else display.print(":");
    display.print(segundos);  
    display.display();  
}

boolean umSegundo() {
  if (millis() > milissegundos + 1000) {
    milissegundos = millis();
    if (++conta == 5) {inverte = !inverte; conta = 0;}
    if (++segundos > 59) {
      segundos = 0; 
      if (++minutos > 59) {
        minutos = 0;
        configurarHora(); // Ajusta online o relogio a cada hora.
        if (++horas > 23) {        
          horas = 0;
        }
      }      
    }  
    return true;
  }
  return false;
}