let numeroSecreto, numerosSecretosJaSorteados = [], numerosJaChutados = [];             // Variáveis globais
let tentativas='', menorNumero, maiorNumero, ativaFala=0;                               // Variáveis globais
let listaDificuldade = document.querySelector('#dificuldade');                          // Define a lista de dificuldade
let botaoOK =  document.querySelector('#selecionarDificuldade');                        // Define botão OK da lista de dificuldade
let botaoChutar =  document.querySelector('#chutar');                                   // Define botão Chutar 
let botaoNovoJogo =  document.querySelector('#novoJogo');                               // Define botão Novo Jogo 
let campoChute =  document.querySelector('#campoChute');                                // Define o campo de entrada de dados
document.getElementsByTagName("i")[0].addEventListener("click",                         // Define função do botão de ativar fala
  function(){
    if(ativaFala==0){      
        ativaFala++;
        document.querySelector("i").style.opacity="1";
        falarTexto('Fala ativada');        
    }else{      
        ativaFala--;     
        document.querySelector("i").style.opacity=".5";
        responsiveVoice.speak("Fala desativada", 'Brazilian Portuguese Female', {rate:1.2});
    }
  }
);

function atualizaTela(ok, chutar, novoJogo){                                            // Função para atualizar os textos e botões na tela    
    exibirTexto('p1', 'Chutes restantes: ' + tentativas);                               // Exibe o paragrafo p1
    exibirTexto('p2', 'Chutados: ' + numerosJaChutados);                                // Exibe o paragrafo p2 
    botaoOK.disabled = ok;                                                              // Bloqueia/desbloqueia o botão OK com parametro ok
    listaDificuldade.disabled = ok;                                                     // Bloqueia/desbloqueia a lista com parametro ok
    botaoChutar.disabled = chutar;                                                      // Bloqueia/desbloqueia o botão chutar com parametro chutar
    campoChute.disabled = chutar;                                                       // Bloqueia/desbloqueia o campo chute com parametro chutar
    botaoNovoJogo.disabled = novoJogo;                                                  // Bloqueia/desbloqueia o botão Novo Jogo com parametro novoJogo
    campoChute.focus();                                                                 // Coloca o ponteiro no campo chute
}

function exibirTexto(tag, texto) {                                                      // Função para exibir texto nas tags html
    let campo = document.querySelector(tag);
    campo.innerHTML = texto;    
}

function falarTexto(texto){                                                             // Função para falar no jogo
    if (ativaFala){                                                                     // Verifica se a fala está ativada
        responsiveVoice.speak(texto, 'Brazilian Portuguese Female', {rate:1.2});        //Define os parâmetros da fala
    }
}

function novoJogo() {                                                                   // Função inicia o jogo ou um Novo Jogo
    maiorNumero = 100;                                                                  // Define a quantidade maxima de números para 100
    menorNumero = 0;
    tentativas = '';                                                                    // Define a quantidade minima de números para 0
    numeroSecreto = sortearNumeroSecreto();                                             // Sorteia e armazena o número secreto
    limparCampo();                                                                      // Esvazia o campo chute para digitação
    numerosJaChutados = [];                                                             // Esvazia os números já chutados (se houver)   
    campoChute.placeholder = '^ Escolha a dificuldade acima ^';                         // Exibe orientação inicial (ecolher a dificuldade) 
    falarTexto('Escolha a dificuldade e clique em OK');                                 // Fala para escolher a dificuldade do jogo e clicar em OK
    atualizaTela(false,true,true);                                                      // Chama a função para atualizar os textos e botões na tela
}

function selecionarDificuldade(){                                                       // Função para selecionar o nível de Dificuldade:
    let dificuldade = listaDificuldade.value;
    if (dificuldade == "facil") {                                                       // Se escolhido na lista a opção Dácil...
        tentativas = 20;                                                                // Define tentativas para 20
        falarTexto('Fácil, 20 chutes');                                                 
    } else if (dificuldade == "media") {                                                // Se escolhido na lista a opção Media...
        tentativas = 10;                                                                // Define tentativas para 10
        falarTexto('Media, 10 chutes');                                                 
    } else if (dificuldade == "dificil") {                                              // Se escolhido na lista a opção Dificil...
        tentativas = 5;                                                                 // Define tentativas para 5
        falarTexto('Dificil, 5 chutes');                                                
    } else {
        alert("Opção inválida");                                                        // Se nada escolhido exibe "Opção inválida" (backup)
    }
    atualizaTela(true,false,true);                                                      // Chama a função para atualizar os textos e botões na tela
    campoChute.placeholder = 'Digite aqui um número entre '+menorNumero+' e '+maiorNumero;          // Exibe orientação para digitar um número
    falarTexto('Agora tente adivinhar o número secreto');                               // Fala para escolher a dificuldade do jogo e clicar em OK    
}

function sortearNumeroSecreto(){                                                        // Função para sortear o número secreto
    let numeroSorteado = Math.round(Math.random() * (maiorNumero + 1));                 // Sorteia um número
    if (numerosSecretosJaSorteados.length > 99) {numerosSecretosJaSorteados = [];}      // Apaga a lista de números ja sorteados se maior que 99;                                    
    if (numerosSecretosJaSorteados.includes(numeroSorteado)) {                          // Testa o número sorteado
        return sortearNumeroSecreto();                                                  // Se já sorteado então retorna e sorteia novamente
    } else {                                                                            // Se já sorteado volta ao inicio, Se não...
        numerosSecretosJaSorteados.push(numeroSorteado);                                // Armazena o número na lista de sorteados
        return numeroSorteado;                                                          // Devolve o número sorteado                                          
    }                                                               
}

function atrasoTempo(){                                                                 // Função para atrazar o tempo antes de limpar o campo chute
    delay = 1000; setTimeout(function() {limparCampo();}, delay);
}

function limparCampo() {                                                                // Função para limpar o número chutado do campo chute    
    campoChute.value = "";
}

function verificaJaChutado(chute){                                                      // Função para verificar se o número já foi chutado
    let jachutado = false;
    for (let i=0;i<numerosJaChutados.length;i++) {
        if (chute == numerosJaChutados[i]) {
            jachutado = true;                                                           // Se já chutado marca como positivo
        }
    }    
    return jachutado;                                                                   // Devolve se já chutado ou não
}

function exibeJaChutado(chute){                                                         // Função para exibir se o número já foi chutado    
    let mensagemJachutado = 'Número '+chute+' já chutado! Tente outro!'           // Amazena mensagem já chutado
    campoChute.placeholder = mensagemJachutado;                                         // Exibe que digitou um número já chutado 
    campoChute.value = mensagemJachutado;                                               // Exibe que digitou um número já chutado
    falarTexto('Esse número já foi chutado! Tente outro!');                                               
    atrasoTempo();                                                                      // Aguarda 1s e limpa o campo para nova digitação
    campoChute.focus();                                                                 // Coloca o ponteiro no campo chute  
}

function exibeForaDaFaixa(chute){                                                       // Função para exibir se o número está fora da faixa
    let mensagemForaDaFaixa = 'Digite um número entre '+menorNumero+' e '+maiorNumero+'!';    // Amazena mensagem fora da faixa
    campoChute.placeholder = mensagemForaDaFaixa;                                       // Exibe que digitou um número fora da faixa
    campoChute.value = mensagemForaDaFaixa;                                             // Exibe que digitou um número fora da faixa
    falarTexto('Número inválido! Tente outro!');                                            
    atrasoTempo();                                                                      // Aguarda 1s e limpa o campo para nova digitação
    campoChute.focus();                                                                 // Coloca o ponteiro no campo chute
}

function exibeAcerto(chute){                                                            // Função para exibir que acertou o número secreto
    numerosJaChutados.push(chute);                                                      // Acrescenta o número chutado na lista
    campoChute.value = 'Parabéns! Você acertou! Número "'+numeroSecreto+'"!';           // Exibir que Acertou o número secreto! Exibe o Número!
    atualizaTela(true,true,false);                                                      // Chama a função para atualizar os textos e botões na tela
    exibirTexto('p1', 'Tentativas utilizadas: ' + numerosJaChutados.length);            // Informa a quantidade de tentativas utilizadas.
    falarTexto('Parabéns! Você acertou o número secreto! Quer jogar novamente?');
}

function exibeErro(chute) {                                                             // Função para exibir que errou o chute
    numerosJaChutados.push(chute);                                                      // Acrescenta o número chutado na lista
    tentativas--;                                                                       // Diminui o número de tentativas
    if (!tentativas) {                                                                  // Se esgotou as tentativas então encerra o jogo 
        campoChute.value = 'Você errou! O número era: "'+numeroSecreto+'"!';
        falarTexto('Você errou! Acabou o jogo! O número era '+numeroSecreto+'! Jogar novamente?');
        atualizaTela(true,true,false);                                                  // Chama a função para atualizar os textos e botões na tela
    } else {                                                                            // Se não estotou as tentativas...
        let maiorMenor;                                                  
        if (numeroSecreto > chute) {                                                    // Se o número sorteado for menor que o chute
            menorNumero = chute;                                              
            maiorMenor = "maior";     
        } else {                                                                        // Se o número sorteado for menor que o chute
            maiorNumero = chute;
            maiorMenor = "menor";
        }
        let mensagemErrouONumero = 'Você errou! O número é '+maiorMenor+'!'        // Amazena mensagem
        falarTexto("Você errou! O número e" + maiorMenor);
        campoChute.placeholder = mensagemErrouONumero;                                  // Exibir que errou e informa se o número é maior ou menor
        campoChute.value = mensagemErrouONumero;                                        // Exibir que errou e informa se o número é maior ou menor
        atrasoTempo();                                                                  // Aguarda 1s e limpa o campo para nova digitação
        atualizaTela(true,false,true);                                                  // Chama a função para atualizar os textos e botões na tela
    }
}

function chutar() {                                                                     // Função principal (Botão Chutar)
    let chute = parseInt(campoChute.value);                                             // Lê e armazena o input digitado (chute) 
    campoChute.value = "";                                                              // Esvazia o campo input 
    if (chute < menorNumero || chute > maiorNumero || isNaN(chute)) {                               // Testa se o chute é válido
        exibeForaDaFaixa(chute);                                                        // Exibe que o chute é inválido (fora da faixa)
    } else if (verificaJaChutado(chute)) {                                              // Verifica se o número já foi chutado
        exibeJaChutado(chute);                                                          // Exibe que o número já foi chutado
    } else if (numeroSecreto == chute) {                                                // Verifica se acertou o chute
        exibeAcerto(chute);                                                             // Exibe que acertou o número secreto e encerra o jogo
    } else {                                                                            // Se não acertou o chute...
        exibeErro(chute);                                                               // Exibe que errou o chute.    
    }
}

falarTexto('Bem vindo ao Número Secreto');                                                 // Dá as boas vindas ao jogo em voz
novoJogo();                                                                             // Chama a função e Inicia o jogo
