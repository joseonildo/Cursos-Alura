let qtdAlugados=1, qtdJaAlugados=1;
console.log("Quantidade de jogos alugados: "+qtdAlugados);
console.log("Quantidade de jogos já alugados: "+qtdJaAlugados);
function alterarStatus(id) {
    let gameClicado = document.getElementById(`game-${id}`);
    let imagem = gameClicado.querySelector('.dashboard__item__img');
    let botao = gameClicado.querySelector('.dashboard__item__button'); 
    let nomeJogo = gameClicado.querySelector('.dashboard__item__name').textContent;   
   
    if (imagem.classList.contains('dashboard__item__img--rented')) {        
        if(confirm(`Tem certeza que deseja devolver o Game ${nomeJogo}?`)){
            imagem.classList.remove('dashboard__item__img--rented');
            botao.textContent = 'Alugar';
            botao.classList.remove('dashboard__item__button--return');
            qtdAlugados--;
        }               
    } else {
        if(confirm(`O aluguel do Game ${nomeJogo} custará R$10,00! Confirma?`)){
            imagem.classList.add('dashboard__item__img--rented');
            botao.textContent = 'Devolver';
            botao.classList.add('dashboard__item__button--return');
            qtdAlugados++;
            qtdJaAlugados++;            
        }        
    }
    console.log("Quantidade de jogos alugados: "+qtdAlugados);
    console.log("Quantidade de jogos já alugados: "+qtdJaAlugados);    
}
