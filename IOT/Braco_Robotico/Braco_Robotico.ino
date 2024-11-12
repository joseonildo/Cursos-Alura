#include <VarSpeedServo.h>

// Angulo inicial dos servos
#define ANGULO_INICIAL_BASE 90
#define ANGULO_INICIAL_BRACO1 60
#define ANGULO_INICIAL_BRACO2 60
#define ANGULO_INICIAL_GARRA 75

// --- Mapeamento dos Servos ---
VarSpeedServo motorBase, motorBraco1, motorBraco2, motorGarra;

// --- Variáveis auxiliares dos Servo Motores ---
int anguloBase, anguloBraco1, anguloBraco2, anguloGarra;

// --- Mapeamento dos Joysticks ---
#define joy1X A1
#define joy1Y A0
#define botao1 A2
#define joy2X A3
#define joy2Y A4
#define botao2 A5

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
  leBotoes();  
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
  pinMode (botao1, INPUT_PULLUP);
  pinMode (botao2, INPUT_PULLUP);
}

// --- Inicializar angulo dos Servo Motores ---
void inicializarAnguloServos() {
  // --- Angulo Inicial Servo Motores ---
  anguloBase = ANGULO_INICIAL_BASE;
  anguloBraco1 = ANGULO_INICIAL_BRACO1;
  anguloBraco2 = ANGULO_INICIAL_BRACO2;
  anguloGarra = ANGULO_INICIAL_GARRA;
  motorBase.slowmove(anguloBase, 30);
  motorBase.wait();
  motorBraco1.slowmove(anguloBraco1, 30);
  motorBraco1.wait();
  motorBraco2.slowmove(anguloBraco2, 30);
  motorBraco2.wait();
  motorGarra.write(anguloGarra);
}

// --- Leitura dos Botões ---
void leBotoes() {
  int leituraBotao1 = !digitalRead(botao1);
  int leituraBotao2 = !digitalRead(botao2);
  if (leituraBotao1 || leituraBotao2) inicializarAnguloServos();
}

// --- Mover Base ---
void moverBase(){
  int leitura = analogRead(joy1X);
  leitura = map(leitura, 0, 1023, 0, 180);  
  if (leitura < 70) {
    if (--anguloBase < 0) anguloBase = 0;    
    movimentaMotores(1);
  }    
  if (leitura > 110) {
    if (++anguloBase > 180) anguloBase = 180;    
    movimentaMotores(1);
  }  
}

// --- Mover Braço 1 ---
void moverBraco1() {
  int leitura = analogRead(joy1Y);
  leitura = map(leitura, 0, 1023, 0, 180);  
  if (leitura < 70) {
    if (++anguloBraco1 > 170) anguloBraco1 = 170;    
    movimentaMotores(2);
  }    
  if (leitura > 110) {
    if (--anguloBraco1 < 10) anguloBraco1 = 10;
    if (anguloBraco1 < 30 && anguloBraco2 < 100) ++anguloBraco2;     
    movimentaMotores(2);
  }  
}

// --- Mover Braço 2 ---
void moverBraco2() {
  int leitura = analogRead(joy2X);
  leitura = map(leitura, 0, 1023, 0, 180);  
  if (leitura < 70) {
    if (--anguloBraco2 < 30) anguloBraco2 = 30;
    if (anguloBraco2 < 90 && anguloBraco1 < 70) ++anguloBraco1;    
    movimentaMotores(3);
  }    
  if (leitura > 110) {
    if (++anguloBraco2 > 175) anguloBraco2 = 175;    
    movimentaMotores(3);
  }   
}

// --- Mover Garra ---
void moverGarra() {
  int leitura = analogRead(joy2Y);
  leitura = map(leitura, 0, 1023, 0, 180);  
  if (leitura < 70) {
    if (++anguloGarra > 128) anguloGarra = 128;    
    movimentaMotores(4);
  }    
  if (leitura > 110) {
    if (--anguloGarra < 75) anguloGarra = 75;
    movimentaMotores(4);
  }  
}

void movimentaMotores(int motor) {
  if (motor == 1) motorBase.write(anguloBase);
  else if (motor == 2) motorBraco1.write(anguloBraco1);
  else if (motor == 3) motorBraco2.write(anguloBraco2);
  else if (motor == 4) motorGarra.write(anguloGarra);
}