package br.com.trabalhorazer.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.trabalhorazer.api.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	
	@Query(value = "select c from Cliente c where c.cpf = ?1")
	Cliente findByCpf(String cpf);
	
}
