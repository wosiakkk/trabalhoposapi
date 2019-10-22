package br.com.trabalhorazer.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.trabalhorazer.api.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
