package br.com.vidal.projeto_mv_xpto.exception;

public class ErroServidorException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ErroServidorException(){
    super("Ocorreu um problema no servidor. Tente novamente mais tarde.");
  }
  
}
