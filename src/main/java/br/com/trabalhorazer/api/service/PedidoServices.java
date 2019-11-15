package br.com.trabalhorazer.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trabalhorazer.api.excepetion.ServicesExceptions;
import br.com.trabalhorazer.api.model.Cliente;
import br.com.trabalhorazer.api.model.Pedido;
import br.com.trabalhorazer.api.repository.PedidoRepository;

@Service
public class PedidoServices {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	public Pedido salvar(Pedido pedido) {
		try {
			return pedidoRepository.save(pedido);
		} catch (ServicesExceptions e) {
			throw new ServicesExceptions("Problemas ao salvar o Pedido!");
		}
	}
	
	public List<Pedido> listarPedidosPorCpf(Cliente cliente){
		try {
			List<Pedido> lista = pedidoRepository.findByCpf(cliente.getCpf());
			return lista;
		} catch (Exception e) {
			throw new ServicesExceptions("Nenhum pedido encontrado!");
		}
	}
	
}
