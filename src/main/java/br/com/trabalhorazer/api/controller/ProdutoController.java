package br.com.trabalhorazer.api.controller;

import java.net.URI;
import java.util.List;
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

import br.com.trabalhorazer.api.excepetion.ServicesExceptions;
import br.com.trabalhorazer.api.model.Produto;
import br.com.trabalhorazer.api.repository.ProdutoRepository;
import br.com.trabalhorazer.api.response.Response;
import br.com.trabalhorazer.api.service.ProdutoServicesImp;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ProdutoServicesImp produtoService;
	@Autowired
	private Response<Produto> response;
	@Autowired
	private Response<List<Produto>> responseList;
	
	
	@PostMapping(value = "/", produces = "application/json")
	private ResponseEntity<Response<Produto>> salvar(@Valid @RequestBody Produto novoProduto, BindingResult results) {
		if(results.hasErrors()) {
			response.setData(novoProduto);
			results.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		novoProduto = produtoService.salvar(novoProduto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoProduto.getId())
				.toUri();
		response.setData(novoProduto);
		return ResponseEntity.created(location).body(response);
	}
	
	@GetMapping(value = "/" , produces = "application/json")
	private ResponseEntity<Response<List<Produto>>> listar(){
		try {
			List<Produto> lista = produtoService.listar();
			responseList.setData(lista);
			return ResponseEntity.ok().body(responseList);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			responseList.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(responseList);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Response<Produto>> atualizar(@Valid @PathVariable(value = "id")Long id,
											  @RequestBody Produto editado, BindingResult results){
		if(results.hasErrors()) {
			response.setData(editado);
			results.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Produto busca;
		try {
			busca = produtoService.buscar(id);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			response.setData(new Produto());
			response.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		editado.setId(busca.getId());
		editado = produtoService.salvar(editado);
		response.setData(editado);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Response<Produto>> pesquisar(@PathVariable(value = "id")Long id){
		try {
			Produto busca = produtoService.buscar(id);
			response.setData(busca);
			return ResponseEntity.ok().body(response);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			response.setData(new Produto());
			response.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<Response<List<Produto>>> deletar(@PathVariable(value = "id")Long id){
		Produto busca;
		try {
			busca = produtoService.buscar(id);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			responseList.setData(produtoService.listar());
			responseList.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseList);
		}
		try {
			produtoService.excluir(busca);
			responseList.setData(produtoService.listar());
			return ResponseEntity.ok().body(responseList);
		} catch (ServicesExceptions e) {
			e.printStackTrace();
			responseList.setData(produtoService.listar());
			responseList.getErros().add(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseList);
		}
	}
	
}
