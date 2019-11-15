package br.com.trabalhorazer.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import br.com.trabalhorazer.api.response.Response;
import br.com.trabalhorazer.api.service.ClienteServicesImp;
import br.com.trabalhorazer.api.service.PedidoServices;
import br.com.trabalhorazer.api.service.ProdutoServicesImp;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteServicesImp clienteServices;
	@Autowired
	private ProdutoServicesImp produtoServices;
	@Autowired
	private PedidoServices pedidoServices;
	@Autowired
	private Response<Pedido> response;
	@Autowired
	private Response<List<Pedido>> responseList;
	
	@PostMapping(value = "/", produces = "application/json")
	private ResponseEntity<Response<Pedido>> salvar(@RequestBody List<PedidoDTO> listaItensPedido){
		Cliente cliente = null;
		Pedido pedido = new Pedido();
		List<ItemDoPedido> itens = new ArrayList<>();
		for(PedidoDTO item : listaItensPedido) {
			if(cliente == null) {
				cliente = clienteServices.buscarPorCpf(item.getCpfCliente());
				pedido.setCliente(cliente);
				Calendar c = Calendar.getInstance();
				Date data = c.getTime();
				pedido.setData(data);
				pedido = pedidoServices.salvar(pedido);
			}
			Produto produtoItem = produtoServices.buscar(Long.parseLong(item.getIdProduto()));
			Long quantidade = Long.parseLong(item.getQuantidade());
			ItemDoPedido itemNovo = new ItemDoPedido(quantidade,pedido,produtoItem);
			itens.add(itemNovo);
		}
		pedido.setItens(itens);
		pedido = pedidoServices.salvar(pedido);
		response.setData(pedido);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/listar", produces = "application/json")
	private ResponseEntity<Response<List<Pedido>>> listarPedidos(@RequestBody Cliente cliente){
		List<Pedido> lista = new ArrayList<>();
		try {
			lista = pedidoServices.listarPedidosPorCpf(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			responseList.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(responseList);
		}
		responseList.setData(lista);
		return ResponseEntity.ok().body(responseList);
	}

}
