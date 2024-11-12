#include <VarSpeedServo.h>

// --- Mapeamento dos Servos ---
VarSpeedServo motorBase, motorBraco1, motorBraco2, motorGarra;

// --- Angulo Inicial Servo Motores ---
int auxBase = 90;
int auxBraco1 = 90;
int auxBraco2 = 90;
int auxGarra = 90;

// --- Mapeamento dos Joysticks ---
#define joy1X A1
#define joy1Y A0
#define joy2X A3
#define joy2Y A4

// --- Inicialização ---
void setup() {
  Serial.begin(115200);
  mapearPinosServos();
  mapearPinosJoys();
  delay(500);
  inicializarAnguloServos();
  delay(100);  
}

// --- Loop Infinito ---
void loop() { 
  moverBase();
  moverBraco1();
  moverBraco2();
  moverGarra();  
  delay(15);
}  //loop

// --- Funções Auxiliares ---

// --- Mapear pinos dos Servo Motores ---
void mapearPinosServos() {
  motorBase.attach(2);
  motorBraco1.attach(3);
  motorBraco2.attach(4);
  motorGarra.attach(5);
}

// --- Definir pinos dos Joysticks como INPUT---
void mapearPinosJoys() {
  pinMode (joy1X, INPUT);
  pinMode (joy1Y, INPUT);
  pinMode (joy2X, INPUT);
  pinMode (joy2Y, INPUT);
}

// --- Inicializar angulo dos Servo Motores ---
void inicializarAnguloServos() {
  motorBase.write(auxBase);
  motorBraco1.write(auxBraco1);
  motorBraco2.write(auxBraco2);
  motorGarra.write(auxGarra);
}

// --- Mover Base ---
void moverBase(){
  int pos = analogRead(joy1X);
  pos = map(pos, 0, 1023, 0, 180);
  if(pos > 110){
    auxBase+=1;
    motorBase.write(auxBase,50);
  }else if(pos < 70){
    auxBase-=1;
    motorBase.write(auxBase,50);   
  }
  //Serial.print(pos);
  //Serial.print(" - ");  
  //Serial.println(auxBase);
}

// --- Mover Braço 1 ---
void moverBraco1() {
  int pos = analogRead(joy1Y);
  pos = map(pos, 0, 1023, 180, 0);
  if(pos > 110){
    auxBraco1+=1;
    motorBraco1.write(auxBraco1);
  }else if(pos < 70){
    auxBraco1-=1;
    motorBraco1.write(auxBraco1);   
  }
  //Serial.print(pos);
  //Serial.print(" - ");
  //Serial.println(auxBraco1);
}

// --- Mover Braço 2 ---
void moverBraco2() {
  int pos = analogRead(joy2X);
  pos = map(pos, 0, 1023, 180, 0);
   if(pos > 110){
    auxBraco2+=1;
    motorBraco2.write(auxBraco2);
  }else if(pos < 70){
    auxBraco2-=1;
    motorBraco2.write(auxBraco2);   
  }
  //Serial.print(pos);
  //Serial.print(" - ");
  //Serial.println(auxBraco2);
}

// --- Mover Garra ---
void moverGarra() {
  int pos = analogRead(joy2Y);
  pos = map(pos, 0, 1023, 180, 0);
   if(pos > 110){
    auxGarra+=1;
    motorGarra.write(auxGarra);
  }else if(pos < 70){
    auxGarra-=1;
    motorGarra.write(auxGarra);   
  }
  //Serial.print(pos);
  //Serial.print(" - ");
  //Serial.println(auxGarra);
}
