let somarCarrinho=0;
let listaCarrinho = document.getElementById('lista-produtos');
let valorCarrinho = document.getElementById('valor-total');

function adicionar() {
    //recuperar valores de nome do produto, quantidade e valor
    let produto = document.getElementById('produto').value;
    let nomeProduto = produto.split('-')[0];
    let precoProduto = produto.split('R$')[1];
    let qtdProduto = document.getElementById('quantidade').value;    
    
    //calcular o preço, o subtotal
    let precoTotalProduto = qtdProduto * precoProduto;    
    
    //adicionar no carrinho    
    listaCarrinho.innerHTML += `<section class="carrinho__produtos__produto">
        <span class="texto-azul">${qtdProduto}x</span> ${nomeProduto} 
        <span class="texto-azul">R$${precoTotalProduto},00</span>
        </section>`;

    //atualizar o valor total 
    somarCarrinho += precoTotalProduto;    
    valorCarrinho.textContent = `R$${somarCarrinho},00`;
    document.getElementById('quantidade').value = 1;   
}

function limpar() {
    somarCarrinho = 0;
    listaCarrinho.innerHTML = '';    
    valorCarrinho.textContent = `R$${somarCarrinho},00`;    
}

const duasSentencas = "Criar o que não existe ainda deve ser a pretensão de todo sujeito que está vivo; A tarefa mais importante de uma pessoa que vem ao mundo é criar algo"
const frasesSeparadas = duasSentencas.split(';');

console.log(frasesSeparadas);

const numerosSeparados = "10,20,30,40,50";
const arrayNumeros = numerosSeparados.split(',');
console.log(arrayNumeros);