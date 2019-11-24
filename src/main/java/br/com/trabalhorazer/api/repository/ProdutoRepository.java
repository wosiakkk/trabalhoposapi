package br.com.trabalhorazer.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.trabalhorazer.api.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long>{

}
