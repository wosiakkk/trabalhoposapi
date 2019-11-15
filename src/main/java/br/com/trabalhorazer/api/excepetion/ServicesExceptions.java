package br.com.trabalhorazer.api.excepetion;

public class ServicesExceptions extends RuntimeException{

	private static final long serialVersionUID = 1L;

	
	public ServicesExceptions(String mensagem) {
		super(mensagem);
	}
	
	public ServicesExceptions(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
