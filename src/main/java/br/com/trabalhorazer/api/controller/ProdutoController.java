package br.com.trabalhorazer.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trabalhorazer.api.model.Produto;
import br.com.trabalhorazer.api.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping(value = "/", produces = "application/json")
	private ResponseEntity<Produto> salvar(@RequestBody Produto novoProduto) {
		try {
			novoProduto = produtoRepository.save(novoProduto);
			return new ResponseEntity<Produto>(novoProduto, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Produto> atualizar(@PathVariable(value = "id")Long id,
											  @RequestBody Produto produtoAtualizado){
		try {
			Optional<Produto> busca = produtoRepository.findById(id);
			if(busca!=null) {
				produtoAtualizado.setId(busca.get().getId());
				produtoAtualizado = produtoRepository.save(produtoAtualizado);
				return new ResponseEntity<Produto>(produtoAtualizado, HttpStatus.CREATED);
			}else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<Produto> deletar(@PathVariable(value = "id")Long id){
		try {
			Optional<Produto> busca = produtoRepository.findById(id);
			if(busca!=null) {
				try {
					produtoRepository.delete(busca.get());
					return ResponseEntity.ok().build();
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
				}
			}else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	private ResponseEntity<Produto> pesquisar(@PathVariable(value = "id")Long id){
		try {
			Optional<Produto> busca = produtoRepository.findById(id);
			if(busca!=null) {
				return new ResponseEntity<Produto>(busca.get(), HttpStatus.OK);
			}else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping(value = "/" , produces = "application/json")
	private ResponseEntity<List<Produto>> listar(){
		try {
			List<Produto> lista = new ArrayList<>();
			Iterable<Produto> busca = produtoRepository.findAll();
			busca.forEach(produto -> lista.add(produto));
			if(lista.size()>0)
				return new ResponseEntity<List<Produto>>(lista, HttpStatus.OK);
			else 
				return ResponseEntity.noContent().build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
