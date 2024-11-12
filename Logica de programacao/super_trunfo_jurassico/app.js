//console.clear();

var cartas = [{nome:"Blue",
               imagem: "img/blue.png",
               atributos: {Ataque: 10, Defesa: 6, Grito: 5}},
             {nome:"Echo",
              imagem: "img/echo.png",
              atributos: {Ataque: 5, Defesa: 5, Grito: 5}}, 
             {nome:"Delta",
              imagem: "img/delta.png",
              atributos: {Ataque: 4, Defesa: 4, Grito: 7}},
             {nome:"Tiranossauro Rex",
              imagem: "img/t-rex.png",
              atributos: {Ataque: 7, Defesa: 8, Grito: 10}},
             {nome:"Carnotauro",
              imagem: "img/carnotauro.png",
              atributos: {Ataque: 7, Defesa: 8, Grito: 6}},
             {nome:"Indominus Rex",
              imagem: "img/indominus.png",
              atributos: {Ataque: 10, Defesa: 7, Grito: 3}},
             {nome:"Indoraptor",
              imagem: "img/indoraptor.png",
              atributos: {Ataque: 9, Defesa: 4, Grito: 6}}];
var cartaMaquina, cartaJogador, atributoMaquina, atributoJogador;
var resultado = document.getElementById("resultado");
var divCartaMaquina = document.getElementById("carta-maquina");
var divCartaJogador = document.getElementById("carta-jogador");
var moldura = '<img src="img/card-super-trunfo.png" style=" width: inherit; height: inherit; position: absolute;">';
var tagHTML = "<div id='opcoes' class= 'carta-status'>";

function sortearCarta() {
  //console.clear();
  resultado.innerHTML = "";
  divCartaMaquina.innerHTML = moldura;
  divCartaMaquina.style.backgroundImage = "";
  var numeroCarta = parseInt(Math.random() * 7);
  cartaMaquina = cartas[numeroCarta]; 
  
  var numeroAtributoMaquina = parseInt(Math.random() * 3);
  if (numeroAtributoMaquina == 0) {atributoMaquina = "Ataque"}
  if (numeroAtributoMaquina == 1) {atributoMaquina = "Defesa"}
  if (numeroAtributoMaquina == 2) {atributoMaquina = "Grito"}
  
  var numeroCarta = parseInt(Math.random() * 7);
  cartaJogador = cartas[numeroCarta]; 
  
  exibirCartaJogador();
            
  document.getElementById("btnSortear").disabled = true;
  document.getElementById("btnJogar").disabled = false;
}

function exibirCartaJogador(){
  var nome = "<p class='carta-subtitle'>" + cartaJogador.nome + "</p>";  
  var radioOpcoes = "";
  for (var atributoJ in cartaJogador.atributos){
    radioOpcoes += "<input type='radio' name='atributoJ' value='" + atributoJ + "'>" + atributoJ + "<br>";
  }
  divCartaJogador.style.backgroundImage = "url(" + cartaJogador.imagem + ")";
  divCartaJogador.innerHTML = moldura + nome + tagHTML + radioOpcoes + "</div>";
}

function exibirCartaMaquina(){
  var nome = "<p class='carta-subtitle'>" + cartaMaquina.nome + "</p>";  
  var radioOpcoes = "";
  for (var atributoM in cartaMaquina.atributos){
    radioOpcoes += "<input type='radio' name='atributoM' value='" + atributoM + "'>" + atributoM + " " + cartaMaquina.atributos[atributoM] + "<br>";
  }
  divCartaMaquina.style.backgroundImage = "url(" + cartaMaquina.imagem + ")";
  divCartaMaquina.innerHTML = moldura + nome + tagHTML + radioOpcoes + "</div>";
} 

function jogar(){
  atributoJogador = obtemAtributoJogador();     
  if (atributoJogador == undefined) {
    document.getElementById("btnSortear").disabled = true;
    document.getElementById("btnJogar").disabled = false;
    resultado.innerHTML = "<p class='resultado-final'>ERRO!! Poder não selecionado</p>";
    delay = 1000; setTimeout(function() {resultado.innerHTML = "";}, delay);
  } else {    
    document.getElementById("btnSortear").disabled = false;
    document.getElementById("btnJogar").disabled = true;
    
    exibirCartaMaquina();
    marcaAtributoMaquina();
    exibirCartaJogadorCompleta(); 
    
    var valorCartaMaquina = cartaMaquina.atributos[atributoMaquina];
    var valorCartaJogador = cartaJogador.atributos[atributoJogador];    
        
    if (valorCartaMaquina > valorCartaJogador){resultado.innerHTML = "<p class='resultado-final'>Maquina ganhou!!</p>"
    } else if (valorCartaMaquina < valorCartaJogador){resultado.innerHTML = "<p class='resultado-final'>Parabens!! Você ganhou!!</p>"
    } else {resultado.innerHTML = "<p class='resultado-final'>Empate!!</p>"}
  }
  console.log("Carta Jogador: " + cartaJogador.nome + " - " + atributoJogador + ": " + valorCartaJogador);
  console.log("Carta Maquina: " + cartaMaquina.nome + " - " + atributoMaquina + ": " + valorCartaMaquina);  
}

function obtemAtributoJogador(){
   var radioAtributos = document.getElementsByName("atributoJ");
   for (var i = 0; i < radioAtributos.length; i++) {
     if (radioAtributos[i].checked == true) {
       return radioAtributos[i].value;
     }
   }
}

function marcaAtributoMaquina(){
   var radioAtributos = document.getElementsByName("atributoM");
   for (var i = 0; i < radioAtributos.length; i++) {
     if (radioAtributos[i].value == atributoMaquina) {
       radioAtributos[i].checked = true;
     }
   }
 }

function exibirCartaJogadorCompleta(){
  var nome = "<p class='carta-subtitle'>" + cartaJogador.nome + "</p>";
  var radioOpcoes = "";
  for (var atributoJ in cartaJogador.atributos){
    radioOpcoes += "<input type='radio' name='atributoJ' value='" + atributoJ + "'>" + atributoJ + " " + cartaJogador.atributos[atributoJ] + "<br>";
  }
  divCartaJogador.style.backgroundImage = "url(" + cartaJogador.imagem + ")";
  divCartaJogador.innerHTML = moldura + nome + tagHTML + radioOpcoes + "</div>";
  var radioAtributos = document.getElementsByName("atributoJ");
  for (var i = 0; i < radioAtributos.length; i++) {
    if (radioAtributos[i].value == atributoJogador) {
       radioAtributos[i].checked = true;
     }
   }
}
