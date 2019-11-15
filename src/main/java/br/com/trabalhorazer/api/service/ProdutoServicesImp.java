package br.com.trabalhorazer.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trabalhorazer.api.excepetion.ServicesExceptions;
import br.com.trabalhorazer.api.model.Produto;
import br.com.trabalhorazer.api.repository.ProdutoRepository;

@Service
public class ProdutoServicesImp  implements Services<Produto>{
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Produto salvar(Produto produto) {
		try {
			return produtoRepository.save(produto);
		} catch (Exception e) {
			throw new ServicesExceptions("Problemas ao persistir Produto");
		}
	}

	@Override
	public List<Produto> listar() {
		List<Produto> produtos = new ArrayList<Produto>();
		Iterable<Produto>  busca = produtoRepository.findAll();
		busca.forEach(p -> produtos.add(p));
		if(produtos.isEmpty())
			throw new ServicesExceptions("Nenhum produto cadastrado!");
		return produtos;
	}

	@Override
	public Produto buscar(Long id) {
		try {
			Optional<Produto> busca = produtoRepository.findById(id);
			return busca.get();
		} catch (Exception e) {
			throw new ServicesExceptions("Produto não encontrado!");
		}
	}

	@Override
	public Produto atualizar(Produto produto) {
		try {
			return produtoRepository.save(produto);
		} catch (Exception e) {
			throw new ServicesExceptions("Problema ao atualizar Produto!");
		}
	}

	@Override
	public void excluir(Produto produto) {
		try {
			produtoRepository.delete(produto);
		} catch (Exception e) {
			throw new ServicesExceptions("Problema ao excluir Produto!");
		}
		
	}
	
}
