let qtdIngressosCadeiraInferior = 400;
let qtdIngressosCadeiraSuperior = 200;
let qtdIngressosPista = 100;

function comprarIngresso() {
    let ingressoSelecionado = document.getElementById("tipo-ingresso").value;
    let quantidadeDigitada = document.getElementById("qtd").value; 

    // let desconto = calcularDescontoTotal(quantidadeDigitada);
    // let valorTotal = quantidadeDigitada*50;    
    // let valorFinal = valorTotal - desconto;
    // alert("Jogos comprados: " + quantidadeDigitada + 
    //         "\nValor total: R$" + valorTotal +
    //         ",00\nDesconto: R$" + desconto +
    //         ",00\nValor final: R$"+ valorFinal + ",00");

    
    if (isNaN(quantidadeDigitada) || quantidadeDigitada <= 0) {
        alert("Ops, faltou digitar a quantidade de ingressos!")
    }else if (ingressoSelecionado == "pista") {
        compraIngressoPista(quantidadeDigitada);        
    } else if (ingressoSelecionado == "superior") {
        compraIngressoCadeiraSuperior(quantidadeDigitada);
    } else if (ingressoSelecionado == "inferior") {
        compraIngressoCadeiraInferior(quantidadeDigitada);    
    }
    document.getElementById("qtd").value = '';
    document.getElementById("qtd").focus();
}

function compraIngressoPista(qtd) {
    if (qtd <= qtdIngressosPista) {
        qtdIngressosPista -= qtd;        
        atualizaQtdIngressos();
        alert(`Compra de ${qtd} ingressos realizada com sucesso!`);
    } else {
        if (qtdIngressosPista == 0) {
            alert(`Desculpe, mas acabaram os ingressos para a pista!`);
        } else {
        alert(`Só temos ${qtdIngressosPista} ingressos disponíveis para a pista!`);
        }
    }
}

function compraIngressoCadeiraSuperior(qtd) {
    if (qtd <= qtdIngressosCadeiraSuperior) {
        qtdIngressosCadeiraSuperior -= qtd;        
        atualizaQtdIngressos();
        alert(`Compra de ${qtd} ingressos realizada com sucesso!`);
    } else {
        if (qtdIngressosCadeiraSuperior == 0) {
            alert(`Desculpe, mas acabaram os ingressos para as cadeiras superiores!!`);
        } else {
        alert(`Só temos ${qtdIngressosCadeiraSuperior} ingressos disponíveis para as cadeiras superiores!`);
        }
    }
}

function compraIngressoCadeiraInferior(qtd) {    
    if (qtd <= qtdIngressosCadeiraInferior) {
        qtdIngressosCadeiraInferior -= qtd;        
        atualizaQtdIngressos();
        alert(`Compra de ${qtd} ingressos realizada com sucesso!`);
    } else {
        if (qtdIngressosCadeiraInferior == 0) {
            alert(`Desculpe, mas acabaram os ingressos para as cadeiras inferiores!`);
        } else {
        alert(`Só temos ${qtdIngressosCadeiraInferior} ingressos disponíveis para as cadeiras inferiores!`);
        }
    }
}

function atualizaQtdIngressos(){
    document.getElementById("qtd-pista").textContent = qtdIngressosPista;
    document.getElementById("qtd-superior").textContent = qtdIngressosCadeiraSuperior;
    document.getElementById("qtd-inferior").textContent = qtdIngressosCadeiraInferior;
}


// function calcularDescontoTotal(quantidadeDeJogos) {
//     let desconto = 0;
//     let i = 0;

//     while (i < quantidadeDeJogos) {        
//         if (quantidadeDeJogos >= 10) {
//             desconto += 0.2 * 50;
//         } else if (quantidadeDeJogos >= 5) {
//             desconto += 0.1 * 50;
//         }
//         i++;        
//     }
//     return desconto;
// }