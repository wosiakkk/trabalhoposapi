package br.com.trabalhorazer.api.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.trabalhorazer.api.excepetion.ServicesExceptions;
import br.com.trabalhorazer.api.model.Cliente;
import br.com.trabalhorazer.api.response.Response;
import br.com.trabalhorazer.api.service.ClienteServicesImp;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteServicesImp clienteService;
	@Autowired
	private Response<Cliente> response;
	@Autowired
	private Response<List<Cliente>> responseList;
	
	
	@PostMapping(value = "/", produces = "application/json")
	private ResponseEntity<Response<Cliente>> salvar(@Valid @RequestBody Cliente novoCliente, BindingResult results){
		if(results.hasErrors()) { //validação
			results.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		novoCliente = clienteService.salvar(novoCliente);
		response.setData(novoCliente);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoCliente.getId())
				.toUri();
		return ResponseEntity.created(location).body(response);
	}
	
	@GetMapping(value = "/", produces = "application/json")
	private ResponseEntity<Response<List<Cliente>>> listarClientes(){
		List<Cliente> clientes = new ArrayList<>();
		try {
			clientes = clienteService.listar();
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			responseList.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(responseList);
		}
		responseList.setData(clientes);
		return ResponseEntity.ok().body(responseList);
		
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Response<Cliente>> atualizar(@Valid @PathVariable(value = "id")Long id,
											  @RequestBody Cliente editado, BindingResult results){
		if(results.hasErrors()) {
			results.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Cliente	busca;
		try {
			 busca = clienteService.buscar(id);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			response.setData(new Cliente());
			response.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		editado.setId(busca.getId());
		editado = clienteService.atualizar(editado);
		response.setData(editado);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Response<Cliente>> pesquisar(@PathVariable(value = "id")Long id){
		try {
			Cliente	busca = clienteService.buscar(id);
			response.setData(busca);
			return ResponseEntity.ok().body(response);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			response.setData(new Cliente());
			response.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<Response<List<Cliente>>> deletar(@PathVariable(value = "id")Long id){
		Cliente buscar;
		try {
			buscar = clienteService.buscar(id);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			responseList.setData(clienteService.listar());
			responseList.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseList);
		}
		try {
			clienteService.excluir(buscar);
			responseList.setData(clienteService.listar());
			return ResponseEntity.ok().body(responseList);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			responseList.setData(clienteService.listar());
			responseList.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseList);	
		}
	}
	
}
