function sortear() {
    let qtd = document.getElementById("quantidade").value;
    let de = document.getElementById("de").value;
    let ate = document.getElementById("ate").value;
    let resultado = document.getElementById("paragrafo");

    if (verificaCampos(qtd, de, ate)) {
        return;
    }

    let sorteados = [];
    for (let i = 0; i < qtd; i++) {

        let repetido = 1;
        let contador = 0;
        while (repetido) {
            let sorteado = obterNumeroAleatorio(de, ate);
            if (sorteados.includes(sorteado)) {
                console.log("Repetido: " + sorteado);
            } else {
                sorteados.push(sorteado);
                console.log("Sorteado: " + sorteado);
                repetido = 0;
            }
            if (++contador > (qtd*5)) {
                alert("Erro no sorteio! \nLoop infinito detectado")
                return;
            }
        }
    }
    sorteados.sort(function (a, b) { return a - b; });
    console.log("Sorteados: " + sorteados);
    
    resultado.textContent = `Números sorteados: ${sorteados}`;

    alterarStatusBotao();
}

function obterNumeroAleatorio(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function verificaCampos(qtd, de, ate) {
    if (qtd == "") {
        alert("Favor informar a quantidade de números a ser sorteado");
        return 1;
    } else if (de == "") {
        alert('Favor informar o campo "Do número"');
        return 1;
    } else if (ate == "") {
        alert('Favor informar o campo "Até o número"');
        return 1;
    } else if (qtd > (ate - de + 1)) {
        alert("Intervalo de números insuficiente para o sorteio");
        console.log(qtd + " > " + ((ate - de) + 1) + " --> Sim")
        return 1;
    } else {return 0}
}

function reiniciar() {
    document.getElementById("quantidade").value = '';
    document.getElementById("de").value = '';
    document.getElementById("ate").value = '';
    document.getElementById("paragrafo").textContent = "Números sorteados:  nenhum";

    alterarStatusBotao();
}

function alterarStatusBotao() {    
    let botao = document.getElementById("btn-reiniciar");    
    
    if (botao.classList.contains('container__botao-desabilitado')) {
        botao.classList.remove('container__botao-desabilitado');
        botao.classList.add('container__botao');
        botao.disabled = false;
    } else {
        botao.classList.remove('container__botao');
        botao.classList.add('container__botao-desabilitado'); 
        botao.disabled = true;       
    }
}