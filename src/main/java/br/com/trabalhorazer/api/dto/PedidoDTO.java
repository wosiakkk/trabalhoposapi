package br.com.trabalhorazer.api.dto;

import java.io.Serializable;

public class PedidoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cpfCliente;
	
	private String idProduto;
	
	private String quantidade;

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	
}
