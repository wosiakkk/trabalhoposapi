package br.com.trabalhorazer.api.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

import br.com.trabalhorazer.api.model.Cliente;
import br.com.trabalhorazer.api.repository.ClienteRepository;
import br.com.trabalhorazer.api.response.Response;
import br.com.trabalhorazer.api.service.ClienteService;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping(value = "/", produces = "application/json")
	private ResponseEntity<Response<Cliente>> salvar(@Valid @RequestBody Cliente novoCliente, BindingResult results){
		Response<Cliente> response = new Response<Cliente>();
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
	
	@PutMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Cliente> atualizar(@PathVariable(value = "id")Long id,
											  @RequestBody Cliente editado){
		
		try {
			Optional<Cliente> busca = clienteRepository.findById(id);
			if(busca.get()!=null) {
				editado.setId(busca.get().getId());
				try {
					editado = clienteRepository.save(editado);
					return new ResponseEntity<Cliente>(editado, HttpStatus.CREATED);
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
				}
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Cliente> pesquisar(@PathVariable(value = "id")Long id){
		
		try {
			Optional<Cliente> pesquisado = clienteRepository.findById(id);
			if(pesquisado.get()!=null) {
				return new ResponseEntity<Cliente>(pesquisado.get(), HttpStatus.OK);
			}else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<Cliente> deletar(@PathVariable(value = "id")Long id){
		try {
			Optional<Cliente> busca = clienteRepository.findById(id);
			if(busca.get()!=null) {
				try {
					clienteRepository.delete(busca.get());
					return ResponseEntity.status(HttpStatus.OK).body(null);
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
				}
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping(value = "/", produces = "application/json")
	private ResponseEntity<List<Cliente>> listarClientes(){
		List<Cliente> clientes = new ArrayList<>();
		try {
			Iterable<Cliente> lista = clienteRepository.findAll();
			lista.forEach(c -> clientes.add(c));
			return new ResponseEntity<List<Cliente>>(clientes,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
