package br.com.vidal.projeto_mv_xpto.view.model.relatorios;

public class ValorPorQuantidadeMovimentacao {

  public double valorPorQuantidademovimentacao(Integer qtdMovimentacao){
   
    if (qtdMovimentacao <= 10) {
      return 1.00;

    } else if ((qtdMovimentacao > 10) && (qtdMovimentacao <= 20)){
      return 0.75;

    } else {
      return 0.50;
    }

  }  
}
