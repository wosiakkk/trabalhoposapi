package br.com.trabalhorazer.api.service;

import java.util.List;

public interface Services<T> {

	public T salvar(T entidade);
	
	public List<T> listar();
	
	public T buscar(Long id);
	
	public T atualizar(T entidade);
	
	public void excluir(T entidade);
	
	
}
