package br.com.trabalhorazer.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trabalhorazer.api.excepetion.ServicesExceptions;
import br.com.trabalhorazer.api.model.Cliente;
import br.com.trabalhorazer.api.repository.ClienteRepository;

@Service
public class ClienteServicesImp implements Services<Cliente>{

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public Cliente salvar(Cliente cliente) throws ServicesExceptions {
		try {
			return clienteRepository.save(cliente);
		} catch (Exception e) {
			throw new ServicesExceptions("Problemas ao persistir Cliente");
		}
	}
	
	@Override
	public List<Cliente> listar() throws ServicesExceptions{
		List<Cliente> clientes = new ArrayList<Cliente>();
		Iterable<Cliente> retornoBusca = clienteRepository.findAll();
		retornoBusca.forEach(c -> clientes.add(c));
		if(clientes.isEmpty())
			throw new ServicesExceptions("Nenhum cliente cadastrado!");
		return clientes;
	}
	
	@Override
	public Cliente buscar(Long id) throws ServicesExceptions{
		Optional<Cliente> buscado;
		try {
			buscado = clienteRepository.findById(id);
			return buscado.get();
		} catch (Exception e) {
			throw new ServicesExceptions("Cliente não encontrado!");
		}
	}
	
	@Override
	public Cliente atualizar(Cliente cliente) throws ServicesExceptions {
		try {
			return clienteRepository.save(cliente);
		} catch (Exception e) {
			throw new ServicesExceptions("Problemas ao atualziar o Cliente");
		}
	}
	
	@Override
	public void excluir(Cliente cliente) throws ServicesExceptions {
		try {
			clienteRepository.delete(cliente);
		} catch (Exception e) {
			throw new ServicesExceptions("Problemas ao excluir o Cliente");
		}
	}
	
	public Cliente buscarPorCpf(String cpf) {
		Cliente busca;
		try {
			busca =  clienteRepository.findByCpf(cpf);
			return busca;
		} catch (ServicesExceptions e) {
			throw new ServicesExceptions("Cliente não encontrado");
		}
	}
	
}
