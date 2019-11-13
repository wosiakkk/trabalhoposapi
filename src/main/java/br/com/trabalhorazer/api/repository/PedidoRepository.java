package br.com.trabalhorazer.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.trabalhorazer.api.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{
	
	@Query(value = "select p from Pedido p where p.cliente.cpf = ?1")
	List<Pedido> findByCpf(String cpf);
}
