package br.com.trabalhorazer.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import br.com.trabalhorazer.api.model.Cliente;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class TrabalhorazerapiApplicationTests {
	

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int porta;
	
	private String getUrlPadrao() {
		return "http://localhost:"+porta;
	}

	@Test
	  public void contextLoads() {
		}
	
	private static Long id;
	
	@Test
	public void testeCadastroCliente() {
		Cliente cliente = new Cliente();
		cliente.setCpf("000.000.000-16");
		cliente.setNome("Teste unitario");
		cliente.setSobrenome("Sobrenome Teste");
		ResponseEntity<Cliente> postResponse = restTemplate.postForEntity(getUrlPadrao() + "/clientes/",cliente, Cliente.class);
		Assert.notNull(postResponse.getBody(), postResponse.getStatusCode().toString());
		Assert.doesNotContain(postResponse.getStatusCode().toString(), "500", "CPF duplicado");
		id = postResponse.getBody().getId();
	}
	
	@Test
	public void testeBuscaCliente() {
		ResponseEntity<Cliente> getResponse = restTemplate.getForEntity(getUrlPadrao() +"/clientes/"+id , Cliente.class);
		Assert.notNull(getResponse.getBody(), getResponse.getStatusCode().toString());
		Assert.doesNotContain(getResponse.getStatusCode().toString(), "404", "Cliente não encontrado");
	}
	
	@Test
	public void testeBuscaTodosClientes() {
		ResponseEntity<Cliente> getResponse = restTemplate.getForEntity(getUrlPadrao() +"/clientes/", Cliente.class);
		Assert.notNull(getResponse.getBody(), getResponse.getStatusCode().toString());
	}
	
	
	
}
