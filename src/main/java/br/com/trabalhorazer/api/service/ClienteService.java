package br.com.trabalhorazer.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trabalhorazer.api.model.Cliente;
import br.com.trabalhorazer.api.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public List<Cliente> listar(){
		List<Cliente> clientes = new ArrayList<Cliente>();
		Iterable<Cliente> retornoBusca = clienteRepository.findAll();
		retornoBusca.forEach(c -> clientes.add(c));
		return clientes;
	}
	
	public Cliente atualizar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
}
