let amigos = [];

function adicionar() {
    amigo = document.getElementById('nome-amigo');
    if (amigo.value == '') {
        alert("Faltou digitar o nome do amigo");
        foco();
        return;
    }

    let ano = verificarAnoBissexto(amigo.value);
    alert(ano);

    if (amigos.map(a => a.toLowerCase()).indexOf(amigo.value.toLowerCase()) !== -1) {
        alert("Amigo já adicionado, adicione outro");
        foco();
        return;
    }

    amigos.push(amigo.value);
    listaAmigos = document.getElementById('lista-amigos')
    if (listaAmigos.textContent == '') {
        listaAmigos.textContent = amigo.value;
    } else {
        listaAmigos.textContent = listaAmigos.textContent + ", " + amigo.value;
    }
    foco();

}

function sortear() {
    if (amigos.length < 3) {
        alert("Adicione pelo menos 3 amigos para poder sortear");
        foco();
        return;
    }

    let listaSorteio = document.getElementById('lista-sorteio');
    listaSorteio.textContent = '';
    embaralha(amigos);
    for (let i = 0; i < amigos.length; i++) {
        if (i == amigos.length - 1) {
            listaSorteio.innerHTML += amigos[i] + ' --> ' + amigos[0] + "<br>";
        } else {
            listaSorteio.innerHTML += amigos[i] + ' --> ' + amigos[i + 1] + "<br>";
        }
    }
}

function reiniciar() {
    amigos = [];
    document.getElementById('nome-amigo').value = '';
    document.getElementById('lista-amigos').textContent = '';
    document.getElementById('lista-sorteio').textContent = '';
}

var inputs = document.getElementsByTagName('input');
for (var i = 0; i < inputs.length; i++) {
    var input = inputs[i];
    input.addEventListener('focus', function () {
        var place = this.getAttribute('placeholder');
        this.setAttribute('placeholder', '');
        var blur = function () {
            this.setAttribute('placeholder', place);
        }
        this.addEventListener('blur', blur);
    });
}

function embaralha(lista) {

    for (let indice = lista.length; indice; indice--) {

        const indiceAleatorio = Math.floor(Math.random() * indice);

        // atribuição via destructuring
        [lista[indice - 1], lista[indiceAleatorio]] =
            [lista[indiceAleatorio], lista[indice - 1]];
    }
}

function foco() {
    amigo.value = '';
    amigo.focus();
}

/* let amigos = [];


function adicionar() {
    let amigo = document.getElementById('nome-amigo');
    let lista = document.getElementById('lista-amigos');


    amigos.push(amigo.value);


    if (lista.textContent == '') {
        lista.textContent = amigo.value;
    } else {
        lista.textContent = lista.textContent + ', ' + amigo.value;
    }


    amigo.value = '';


    atualizarLista();
    atualizarSorteio();
}


function sortear() {
    embaralhar(amigos);


    let sorteio = document.getElementById('lista-sorteio');
    for (let i = 0; i < amigos.length; i++) {
        if (i == amigos.length - 1) {
            sorteio.innerHTML = sorteio.innerHTML + amigos[i] +' --> ' +amigos[0] + '<br/>';
        } else {
            sorteio.innerHTML = sorteio.innerHTML + amigos[i] +' --> ' +amigos[i + 1] + '<br/>';
        }
    }
}


function excluirAmigo(index) {
    amigos.splice(index, 1);
    atualizarLista();
    atualizarSorteio();
}


function embaralhar(lista) {
    for (let indice = lista.length; indice; indice--) {
        const indiceAleatorio = Math.floor(Math.random() * indice);
        [lista[indice - 1], lista[indiceAleatorio]] = [lista[indiceAleatorio], lista[indice - 1]];
    }
}


function atualizarSorteio() {
    let sorteio = document.getElementById('lista-sorteio');
    sorteio.innerHTML = '';
}


function atualizarLista() {
    let lista = document.getElementById('lista-amigos');
    lista.innerHTML = '';


    for (let i = 0; i < amigos.length; i++) {
        // Cria um elemento de parágrafo para cada amigo
        let paragrafo = document.createElement('p');
        paragrafo.textContent = amigos[i];
       
        // Adiciona um evento de clique para excluir o amigo
        paragrafo.addEventListener('click', function() {
            excluirAmigo(i);
        });


        // Adiciona o parágrafo à lista
        lista.appendChild(paragrafo);
    }
}


function reiniciar() {
    amigos = [];
    document.getElementById('lista-amigos').innerHTML = '';
    document.getElementById('lista-sorteio').innerHTML = '';
} */

/* // Criando um array
let minhaLista = [];
minhaLista.push(1, 2, 3, 4, 5, 6);
console.log("Adicionando elementos:", minhaLista);

// Criando uma nova variável array
let outrosNumeros = [4, 5, 6];
console.log("Adicionando elementos:", outrosNumeros);

// Concatenando arrays
let novaLista = minhaLista.concat(outrosNumeros);
console.log("Juntando Arrays:", novaLista);

// Removendo o último elemento
novaLista.pop();
console.log("Desafio 4:", novaLista);

function embaralharArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
  }
  
// Embaralhando novaLista
novaLista = embaralharArray(novaLista);
console.log("Embaralhando a Lista:", novaLista);


// Função para remover duplicatas de um array
function removerDuplicatas(array) {
    return [...new Set(array)];
}
  
// Testando a função com novaLista
let novaListaSemDuplicatas = removerDuplicatas(novaLista);
console.log("Remover duplicatas:", novaListaSemDuplicatas); */