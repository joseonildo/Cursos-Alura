#include <Wire.h>

#define BUZZER 13                         // Define o Buzzer na porta digital 13
#define BOTAO_VERDE A0                    // Define o botão do led verde na porta analógica A0
#define BOTAO_AMARELO A1                  // Define o botão do led amarelo na porta analógica A1
#define BOTAO_VERMELHO A2                 // Define o botão do led vermelho na porta analógica A2
#define BOTAO_AZUL A3                     // Define o botão do led azul na porta analógica A3
#define BOTAO_JOGAR A4                    // Define o botão Jogar novamente / Parar na porta analógica A4

#define LED_VERDE 2                       // Define o led verde na porta digital 2
#define LED_AMARELO 3                     // Define o led amarelo na porta digital 3
#define LED_VERMELHO 4                    // Define o led vermelho na porta digital 4
#define LED_AZUL 5                        // Define o led azul na porta digital 5
#define LED_JOGAR 6                       // Define o Led para indicar jogo rodando

#define MEIO_SEGUNDO 500                  // Define o MEIO_SEGUNDO como 500ms para o delay() 
#define UM_SEGUNDO 1000                   // Define o UM_SEGUNDO como 1000ms para o delay()
#define INDEFINIDO -1                     // Define o INDEFINIDO como -1 

#define dificuldade1 68                   // Define os leds acesos para a dificuldade 1;
#define dificuldade2 76                   // Define os leds acesos para a dificuldade 2;
#define dificuldade3 92                   // Define os leds acesos para a dificuldade 3;
#define dificuldade4 124                  // Define os leds acesos para a dificuldade 4;

#define bipNormal 50                      // Define o tempo do bip normal;
#define bipSucesso 200                    // Define o tempo do bip do sucesso;
#define bipFracasso 200                   // Define o tempo do bip do fracasso;

boolean tocaSons = true;                  // Variável para tocar som na finalização do jogo;
boolean ligaDesliga = true;               // Variável para sinalizar jogo ligado/desligado;
boolean ledJogoRodando = true;            // Variável para sinalizar led do jogo rodando ou não;

int contadorMultiUso = 0;                 // Contador para vários usos no jogo;
int sequencia = 4;                        // Variável do tamanho da sequencia inicial
int sequenciaLeds[8];                     // Array para comportar a sequencia de leds

int semente = 0;                          // Variavel para receber a leitura do pino A5 e usar no Random;
int nivelAtual = 1;                       // Variavel para receber o nível atual do jogo;
int rodada = 0;                           // Variavel para das rodadas;
int ledsRespondidos = 0;                  // Variavel dos leds respondidos;
int dificuldade = 0;                      // Variavel para a dificuldade do jogo -> 0 = Solicita escolher;

long milis = millis();                    // Variavel para contagem de tempo determinado na função e piscar os leds sequenciais;
long millisMeioSegundo = millis();        // Variavel para contagem de tempo de meio segundo e piscar os leds sequenciais;
long millisUmSegundo = millis();          // Variavel para contagem de tempo de um segundo e escrever Serial.print();

enum Estados {                                      // Estados enumerados das fases do jogo;
  ESCOLHA_DIFICULDADE,
  SUBIU_DE_NIVEL,
  PRONTO_PARA_PROXIMA_RODADA,
  JOGADOR_RESPONDENDO,
  JOGO_FINALIZADO_COM_SUCESSO,
  JOGO_FINALIZADO_SEM_SUCESSO
};

void setup() {                                      // Configuração inicial geral
  Serial.begin(9600);                               // Define a velocidade da serial
  iniciaPortas();                                   // Configuração das portas
  delay(100);                                       // Delay para estabilização das portas
  iniciaJogo();                                     // Configuração do jogo
  tocaSomInicio();                                  // Toca o som do inicio do jogo;                                                               
  milis = millis();                                 // Carrega o tempo inicial do jogo
}

void iniciaPortas() {                               // Configuração das portas dos leds e botões
  pinMode(BOTAO_VERDE, INPUT_PULLUP);
  pinMode(BOTAO_AMARELO, INPUT_PULLUP);
  pinMode(BOTAO_VERMELHO, INPUT_PULLUP);
  pinMode(BOTAO_AZUL, INPUT_PULLUP);
  pinMode(BOTAO_JOGAR, INPUT_PULLUP);
  pinMode(A5, INPUT);

  pinMode(LED_VERDE, OUTPUT);
  pinMode(LED_AMARELO, OUTPUT);
  pinMode(LED_VERMELHO, OUTPUT);
  pinMode(LED_AZUL, OUTPUT);
  pinMode(LED_JOGAR, OUTPUT);
}

void iniciaJogo() {                                 // Configuração inicial do jogo
  rodada = 0;                                       // Zera as rodadas;                              
  ledsRespondidos = 0;                              // Zera os leds respondidos;
  semente = analogRead(A5);                         // Faz leitura flutuante do pino A5 (nada conectado);
  contadorMultiUso = 0;                             // Zera o contador MultiUso;
  tocaSons = true;                                  // Ativa o som de finalização do jogo;     
  randomSeed(semente);                              // Define o randomSeed() pela leitura do pino A5;
  Serial.print("Jogo nº: ");                        // Imprime a leitura como jogo;
  Serial.println(semente);                          // Imprime a leitura como jogo;
  for (int i = 0; i < sequencia; i++) {             // Chama a função e monta a sequencia de leds;
    sequenciaLeds[i] = sorteiaCor();
  }
}

int sorteiaCor() {                                  // Função sorteiaCor(), devolve o led sorteado;
  return random(LED_VERDE, LED_AZUL + 1);
}

void loop() {                                       // Loop principal, Programa principal;                       
  novoJogo();                                       // Se variavel ligaDesliga = true ativa o jogo;
  if (ligaDesliga) {
    switch (estadoAtual()) {                        // Chama a função e verifica o estado atual do jogo;
    case ESCOLHA_DIFICULDADE:                       // Caso dificuldade = 0;
      if (umSegundo()) {
        Serial.println("Escolha a dificuldade!");
      }
      escolhaDificuldade();                         // Chama a função para escolher a dificuldade;
      break;
    case SUBIU_DE_NIVEL:                            // Se subiu de nível:
      Serial.println("Subindo de nível!");
      subirDeNivel();                               // Chama a função para aumentar a dificuldade;
      break;
    case PRONTO_PARA_PROXIMA_RODADA:                // Rodada de leds OK então:
      Serial.println("Preparando nova rodada!");
      preparaNovaRodada();                          // Chama a função para preparar uma nova rodada;
      break;
    case JOGADOR_RESPONDENDO:                       // Se o jogador ainda não respondeu:
      if (umSegundo()) {
        Serial.println("Aguardando resposta do jogador...");
      }
      processaRespostaJogador();                    // Fica checando se algum botão foi pressionado;
      break;
    case JOGO_FINALIZADO_COM_SUCESSO:               // Caso o jogador respondeu tudo corretamente:
       if (umSegundo()) {
        Serial.println("Jogo finalizado com sucesso!");
      } 
      jogoFinalizadoComSucesso();                   // Chama a função para encerrar o jogo e piscar a sequencia de sucesso;
      break;
    case JOGO_FINALIZADO_SEM_SUCESSO:               // Caso o jogador errar qualquer botão do led:
      jogoFinalizadoSemSucesso();                   // Chama a função para encerrar o jogo e piscar a sequencia de falha;
      break;
    }
  }
  delay(1);                                         // Atrazo de 1ms para sincronizar os timers e contagem de millis();
}

int estadoAtual() {                                 // Função para verificar o estado atual do jogo;
  if (dificuldade == 0) {                           // Se dificuldade = 0...
    return ESCOLHA_DIFICULDADE;                     // Retorna para escolher a dificuldade;
  } else if (rodada <= sequencia) {                 // Senão, então verifica as rodadas e sequencias validas; 
    if (ledsRespondidos == rodada) {                // Se todos os leds foram respondidos...
      return PRONTO_PARA_PROXIMA_RODADA;            // Retorna para iniciar a proxima rodada;
    } else {                                        // Se todos os leds não foram respondidos então..
      return JOGADOR_RESPONDENDO;                   // Retorna para aguardar a resposta de cada um;
    }
  } else if (rodada == sequencia + 1 && nivelAtual < dificuldade) {  //verifica rodadas, sequencias e nivel atual
    return SUBIU_DE_NIVEL;                          // Se atendidos então retorna para aumentar o nível do jogo 
  } else if (rodada == sequencia + 1) {             // Se não então verifica se acabaram as rodadas então...
    return JOGO_FINALIZADO_COM_SUCESSO;             // Retorna para encerrar o jogo com sucesso;
  } else {                                          // Se não acabaram as rodadas ou houve erro então...
    return JOGO_FINALIZADO_SEM_SUCESSO;             // Retorna para encerrar o jogo sem sucesso;
  }
}

void escolhaDificuldade() {                         // Função para escolher a dificuldade do jogo;
  int resposta = verificaBotao();                   // Chama função e checa se algum botão foi pressionado;
  if (resposta == LED_VERDE) {                      // Se pressionado o botão 2 (Led Verde);
    dificuldade = 1;                                // Seta a dificuldade para 1;
    Serial.println("Dificuldade escolhida: 1");     
    piscaLedDificuldade(dificuldade1);              // Pisca apenas o led verde indicando dificuldade 1 - Inicio do jogo;
  } else if (resposta == LED_AMARELO) {             // Se pressionado o botão 3 (Led Amarelo);
    dificuldade = 2;                                // Seta a dificuldade para 2;
    Serial.println("Dificuldade escolhida: 2");
    piscaLedDificuldade(dificuldade2);              // Pisca o led Verde e Amarelo indicando dificuldade 2;
    piscaLedDificuldade(dificuldade1);              // Depois pisca apenas o led verde indicando iniciar o jogo na dificuldade 1;
    Serial.println("Dificuldade: 1");
  } else if (resposta == LED_VERMELHO) {            // Se pressionado o botão 4 (Led Vermelho);
    dificuldade = 3;                                // Seta a dificuldade para 3;
    Serial.println("Dificuldade escolhida: 3");
    piscaLedDificuldade(dificuldade3);              // Pisca o led Verde, Amarelo e vermelho indicando dificuldade 3;
    piscaLedDificuldade(dificuldade1);              // Depois pisca apenas o led verde indicando iniciar o jogo na dificuldade 1;
    Serial.println("Dificuldade: 1");
  } else if (resposta == LED_AZUL) {                // Se pressionado o botão 5 (Led Azul);
    dificuldade = 4;                                // Seta a dificuldade para 4;
    Serial.println("Dificuldade escolhida: 4");
    piscaLedDificuldade(dificuldade4);              // Pisca o led Verde, Amarelo, Vermelho e Azul indicando dificuldade 4;
    piscaLedDificuldade(dificuldade1);              // Depois pisca apenas o led verde indicando iniciar o jogo na dificuldade 1;
    Serial.println("Dificuldade: 1");
  } else if (resposta == INDEFINIDO) {              // Se nada pressionado retorna vazio e reinicia o ciclo;
    aguardaEscolhaDificuldade();                   // Pisca uma sequencia de leds até ser feita a escolha da dificuldade;
  }
}

void subirDeNivel() {                               // Função para subir de nível e aumentar a dificuldade;
  nivelAtual++;                                     // incrementa +1 ao nivelAtual e aumenta a dificuldade;
  if (nivelAtual == 2) {                            // Se o nível subiu para 2 então...
    piscaLedDificuldade(dificuldade2);                        // Pisca o led Verde e Amarelo indicando que subiu a dificuldade para 2;
    Serial.println("Dificuldade: 2");
  } else if (nivelAtual == 3) {                     // Se o nível subiu para 3 então...
    piscaLedDificuldade(dificuldade3);                        // Pisca o led Verde, Amarelo e vermelho indicando que subiu para 3;
    Serial.println("Dificuldade: 3");
  } else if (nivelAtual == 4) {                     // Se o nível subiu para 3 então...
    piscaLedDificuldade(dificuldade4);                        // Pisca o led Verde, Amarelo, vermelho e Azul indicando que subiu para 4;
    Serial.println("Dificuldade: 4");
  }
  sequencia++;                                      // Aumenta o tamalho da sequencia;
  rodada = 0;                                       // Zera a rodada atual;
  ledsRespondidos = 0;                              // Zera os leds respondidos;
  iniciaJogo();                                     // Chama o jogo inicial e sorteia os leds novamente;
}

void preparaNovaRodada() {                          // Prepara nova rodada.
  ledsRespondidos = 0;                              // Zera os leds respondidos.
  if (rodada++ < sequencia) {                       // Acrescenta nova rodada e verifica se ainda menor que sequencia
    piscaLedRodada();                               // Pisca os respectivos leds da sequencia.
  }
}

void processaRespostaJogador() {                    // Função para processar a resposta do jogador baseado no botão pressionado;
  int resposta = verificaBotao();                   // Chama a função para verificar o botão e amazena na variável o resultado;  
  if (resposta == sequenciaLeds[ledsRespondidos]) { // Se recebido um botão pressionado e o botão está correto então...
    ledsRespondidos++;                              // Acrescenta que o led foi respondido corretamente.
    Serial.println("Resposta correta!");
  } else if (resposta == INDEFINIDO) {              // Se recebido -1 então nada ainda respondido então...
    return;                                         // Retorna vazio e reinicia a verificação;
  } else {                                          // Se recebido um botão pressionado e o botão está errado então...
    rodada = sequencia + 2;                         // Marca que errou igualando rodada = sequencia + 2 e encerrando o jogo.
    Serial.println("Resposta errada!");
    tocaSomFracasso(resposta);                      // Toca som de fracasso e pisca o led do erro por 3x;
    imprimeSequencia(sequenciaLeds[ledsRespondidos]);   // Imprime a sequencia correta dos leds;
  }
}

int verificaBotao() {                               // Função para verificar se algum botão foi pressionado;           
  if (!digitalRead(BOTAO_VERDE)) {                  // Se pressionado o botão do Led verde então...
    return piscaLed(LED_VERDE, MEIO_SEGUNDO, 0);    // Chama a função para piscar o led verde e retorna o botão pressionado
  }

  if (!digitalRead(BOTAO_AMARELO)) {                // Se pressionado o botão do Led amarelo então...
    return piscaLed(LED_AMARELO, MEIO_SEGUNDO, 0);  // Chama a função para piscar o led amarelo e retorna o botão pressionado
  }

  if (!digitalRead(BOTAO_VERMELHO)) {               // Se pressionado o botão do Led vermelho então...
    return piscaLed(LED_VERMELHO, MEIO_SEGUNDO, 0); // Chama a função para piscar o led Vermelho e retorna o botão pressionado
  }

  if (!digitalRead(BOTAO_AZUL)) {                   // Se pressionado o botão do Led azul então...
    return piscaLed(LED_AZUL, MEIO_SEGUNDO, 0);     // Chama a função para piscar o led azul e retorna o botão pressionado
  }

  return INDEFINIDO;                                //se nada foi pressionado então retorna -1;
}

int piscaLed(int led, int tempo1, int tempo2) {     // Função para atender a piscada do respectivo led solicitado;
  tocaSom(2000, bipNormal);                         // Chama a função tocaSom para tocar um bip por led piscado;
  digitalWrite(led, HIGH);                          // Acende o respectivo led solicitado;
  delay(tempo1);                                    // Atrazo de tempo1 recebido;  
  digitalWrite(led, LOW);                           // Apaga o respectivo led solicitado;
  delay(tempo2);                                    // Atrazo de tempo2 recebido; 
  return led;                                       // Retorna o led que foi piscado;
} 

void piscaLedRodada() {                             // Função para solicitar a piscada da rodada de leds solicitados;
  delay(MEIO_SEGUNDO);                              // Atrazo de meio segundo;
  for (int i = 0; i < rodada; i++) {                // Pisca a quantidade de leds conforme o tamanho da "rodada" atual
    piscaLed(sequenciaLeds[i], MEIO_SEGUNDO, MEIO_SEGUNDO);   // Chama a função piscaLed para piscar do respectivo led solicitado;
  }
}

void piscaLedDificuldade(int dificuldade) {         // Função para piscar a sequencia de leds pela dificuldade escolhida;
  PORTD = 64;                                       // Apaga todos os leds ligados nas portas digitais (PORTD);
  delay(250);                                       // Atrazo de 250ms para sincronismo;
  for (int i = 0; i < 3; i++) {                     // Repete 3x as piscadas e bips;
    PORTD = dificuldade;                            // Acende os leds solicitados;
    if (dificuldade == dificuldade1) {              // Se a sequencia de leds recebida for a 4 então;
      tocaSom(2000, bipNormal);                     // Chama a função tocaSom para tocar os bips na frequencia 2000;
    } else if (dificuldade == dificuldade2) {       // Se a sequencia de leds recebida for a 12 então;
      tocaSom(2200, bipNormal);                     // Chama a função tocaSom para tocar os bips na frequencia 2200;
    } else if (dificuldade == dificuldade3) {       // Se a sequencia de leds recebida for a 28 então;
      tocaSom(2400, bipNormal);                     // Chama a função tocaSom para tocar os bips na frequencia 2400;
    } else if (dificuldade == dificuldade4) {       // Se a sequencia de leds recebida for a 60 então;
      tocaSom(2500, bipNormal);                     // Chama a função tocaSom para tocar os bips na frequencia 2500;
    }
    delay(250);                                     // Atrazo de 250ms;
    PORTD = 64;                                     // Apaga todos os leds ligados nas portas digitais (PORTD);
    delay(250);                                     // Atrazo de 250ms;  
  }
}

void aguardaEscolhaDificuldade() {                  // Função para piscar uma sequencia de leds                                                    
  if (millis() > milis && millis() < milis + 250) { // enquanto aguarda a escolhida da dificuldade;                
    PORTD = dificuldade1;
  } else if (millis() > milis + 500 && millis() < milis + 750) {
    PORTD = dificuldade2;
  } else if (millis() > milis + 1000 && millis() < milis + 1250) {
    PORTD = dificuldade3;
  } else if (millis() > milis + 1500 && millis() < milis + 1750) {
    PORTD = dificuldade4;
  } else if (millis() > milis + 2000 && millis() < milis + 2250) {
    PORTD = dificuldade3;
  } else if (millis() > milis + 2500 && millis() < milis + 2750) {
    PORTD = dificuldade2;
  } else if (millis() > milis + 3000 && millis() < milis + 3250) {
    PORTD = dificuldade1;
  } else if (millis() > milis + 3500 && millis() < milis + 3750) {
    PORTD = 64;
  } else if (millis() > milis + 4000) {
    milis = millis();
  }
}

void jogoFinalizadoComSucesso() {                   // Função para piscar uma sequencia de leds
  ledJogoRodando = false;                           // quando o Jogo finalizado com sucesso!      
  if (millis() > milis && millis() < milis + 150) { 
    PORTD = 4;  
    if (tocaSons) {tocaSom(2000, bipSucesso);}
  } else if (millis() > milis + 150 && millis() < milis + 300) {
    PORTD = 8;
    if (tocaSons) {tocaSom(2200, bipSucesso);}
  } else if (millis() > milis + 300 && millis() < milis + 450) {
    PORTD = 16;
    if (tocaSons) {tocaSom(2400, bipSucesso);}
  } else if (millis() > milis + 450 && millis() < milis + 600) {
    PORTD = 32;
    if (tocaSons) {tocaSom(2500, bipSucesso);}
  } else if (millis() > milis + 600 && millis() < milis + 750) {
    PORTD = 16;
  } else if (millis() > milis + 750 && millis() < milis + 900) {
    PORTD = 8;
  } else if (millis() > milis + 900) {
    milis = millis();
  }
  if (tocaSons) {                                   // Se a variável tocaSons estiver válida...
    if (contadorMultiUso++ > 1400) {                // Se o contador chegar a 1400 então para de tocar!
    tocaSons = false;                               // Invalida a variável parando de tocar o som!
    }
  }
}

void jogoFinalizadoSemSucesso() {                   // Função para piscar uma sequencia de leds  
  ledJogoRodando = false;                           // para o jogo finalizado sem sucesso!
  if (millis() > millisMeioSegundo + 250) {               
    millisMeioSegundo = millis();
    if (digitalRead(LED_VERDE)) {
      PORTD = 0;
    } else {
      PORTD = 60;
    }  
  }
}

void novoJogo() {                                   // Inicia / Encerra partida / desliga jogo;
  digitalWrite(LED_JOGAR, ledJogoRodando);          // liga ou desliga o led baseado no status do ledJogoRodando;
  if (!digitalRead(BOTAO_JOGAR)) {                  // Se o botão Jogar for pressionado então...
    int conta = 0;                                  // Variavel para contagem de tempo do botão pressionado  
    while (!digitalRead(BOTAO_JOGAR)) {             // Se o botão continuar pressionado...
      delay(10);                                    // Atrazo de 10ms para contagem de 10 em 10;
      if (conta++ > 100){                           // Inicia a contagem e se contagem chegou em 100...
        ligaDesliga = false; 
        ledJogoRodando = false;                       // Interrompe o loop do jogo;
        PORTD = 0;                                  // Apaga todos os leds;
        Serial.println("Jogo foi desligado!");      // Informa que o jogo foi desligado;
        delay(1000);                                // Atrazo de 1seg para sincronismo;
        return;                                     // Interrompe o fluxo da função;
        } 
      }
    if (ledJogoRodando) {                           // Se o jogo já está ativo então...
      ledJogoRodando = false;                       // Seta a variável para desligar o led do jogo rodando;                       
      digitalWrite(LED_JOGAR, ledJogoRodando);      // Desliga o led do jogo rodando;
      dificuldade = 1;                              // Seta a dificuldade para 1 para interromper o loop de escolha de dificuldade                             
      rodada = sequencia + 2;                       // Coloca o jogo no modo de encerrado sem sucesso - Interrompido;
    } else {                                        // Se o jogo não está ativo então...
      ligaDesliga = true;                           // Ativa o loop do jogo;
      ledJogoRodando = true;                        // Seta a variável para ligar o led do jogo rodando;
      dificuldade = 0;                              // Seta a dificuldade para 0 para chamar a escolha de dificuldade;
      nivelAtual = 1;                               // Seta o nivelAtual para o nível inicial;
      sequencia = 4;                                // Seta a sequencia para nível inicial;
      PORTD = 0;                                    // Apaga os leds para um reset inicial;
      digitalWrite(LED_JOGAR, ledJogoRodando);      // Liga o led do jogo rodando;
      iniciaJogo();                                 // Chama a função de inicio do jogo e refaz o sorteio!
      tocaSomInicio();                                // Toca o som do inicio do jogo;
    }
  }
}

void tocaSom(int frequencia, int tempo) {           // Função para tocar um som baseado na frequencia e tempo fornecidos;
  tone(BUZZER, frequencia, tempo);
}
  
void tocaSomInicio() {                              // Função para tocar o som do inicio do jogo;
  PORTD = 68; tocaSom(2000, bipNormal); delay(150);  
  PORTD = 76; tocaSom(2200, bipNormal); delay(150); 
  PORTD = 92; tocaSom(2400, bipNormal); delay(150);  
  PORTD = 124; tocaSom(2500, bipNormal); delay(150);
}

void tocaSomFracasso(int led) {                     // Função para tocar som de fracasso e pisca o led do erro por 3x;
  for (int i=0; i<3; i++) {                           
    tocaSom(250, 200);     
    digitalWrite(led, HIGH);
    delay(200);
    digitalWrite(led, LOW);
    delay(150);                                   
  }
}

boolean umSegundo() {                               // Função para retornar true para o solicitante 
  if (millis() > millisUmSegundo + 1000) {          // a cada um segundo evitando a parada do jogo com delays;
    millisUmSegundo = millis();                     // Melhora consideravelmente a respostas dos botões.
    return true;
  } else {
    return false;
  }
}

void imprimeSequencia(int pinoLed) {                // No caso de erro então Imprime na serial: mensagens, led correto, a sequencia correta!
  Serial.println();
  Serial.print("Resposta errada! O correto era o led ");
  Serial.println(converteCorLeds(sequenciaLeds[ledsRespondidos]));
  Serial.println("Você perdeu! Você errou a sequencia!");
  Serial.print("Sequencia correta: ");
  for (int i=0; i<sequencia; i++) {
    Serial.print(converteCorLeds(sequenciaLeds[i]));
    Serial.print(", ");
  }
  Serial.println();
  Serial.println("Jogo finalizado com falha!");
}

String converteCorLeds(int pinoLed) {               // Função para devolver a Cor do led baseado no pino fornecido;
  if (pinoLed == LED_VERDE) {                       // Se pino do Led verde então...
    return "VERDE";                                 // devolve a cor VERDE;
  } else if (pinoLed == LED_AMARELO) {              // Se pino do Led amarelo então...
    return "AMARELO";                               // devolve a cor AMARELO;
  } else if (pinoLed == LED_VERMELHO) {             // Se pino do Led vermelho então...
    return "VERMELHO";                              // devolve a cor VERMELHO;
  } else if (pinoLed == LED_AZUL) {                 // Se pino do Led azul então...
    return "AZUL";                                  // devolve a cor AZUL;
  }
}