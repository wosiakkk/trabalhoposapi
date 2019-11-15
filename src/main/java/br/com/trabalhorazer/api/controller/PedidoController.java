package br.com.trabalhorazer.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trabalhorazer.api.dto.PedidoDTO;
import br.com.trabalhorazer.api.model.Cliente;
import br.com.trabalhorazer.api.model.ItemDoPedido;
import br.com.trabalhorazer.api.model.Pedido;
import br.com.trabalhorazer.api.model.Produto;
import br.com.trabalhorazer.api.repository.ClienteRepository;
import br.com.trabalhorazer.api.repository.PedidoRepository;
import br.com.trabalhorazer.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	@PostMapping(value = "/", produces = "application/json")
	private ResponseEntity<Pedido> salvar(@RequestBody List<PedidoDTO> listaItensPedido){
		Cliente cliente = null;
		Pedido pedido = new Pedido();
		List<ItemDoPedido> itens = new ArrayList<>();
		for(PedidoDTO item : listaItensPedido) {
			if(cliente == null) {
				cliente = clienteRepository.findByCpf(item.getCpfCliente());
				pedido.setCliente(cliente);
				Calendar c = Calendar.getInstance();
				Date data = c.getTime();
				pedido.setData(data);
				pedido = pedidoRepository.save(pedido);
			}
			
			Optional<Produto> produtoOptional =  produtoRepository.findById(Long.parseLong(item.getIdProduto()));
			Long quantidade = Long.parseLong(item.getQuantidade());
			ItemDoPedido itemNovo = new ItemDoPedido(quantidade,pedido,produtoOptional.get());
			itens.add(itemNovo);
		}
		pedido.setItens(itens);
		pedido = pedidoRepository.save(pedido);
		return new ResponseEntity<Pedido>(pedido, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/listar", produces = "application/json")
	private ResponseEntity<List<Pedido>> listarPedidos(@RequestBody Cliente cpf){
		List<Pedido> lista = new ArrayList<>();
		lista = pedidoRepository.findByCpf(cpf.getCpf());
		if(lista.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		return new ResponseEntity<List<Pedido>>(lista, HttpStatus.OK);
	}

}
