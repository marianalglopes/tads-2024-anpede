package com.anpede.resources;

import java.net.URI;
import java.util.List;

import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anpede.dto.AssociadoDTO;
import com.anpede.services.AssociadoService;

@RestController
@RequestMapping(value = "/associados")
public class AssociadoResource {
	
	@Autowired
	private AssociadoService service;
	
	@GetMapping //Outro 'get', mas com mapeamento diferente
	public ResponseEntity<List<AssociadoDTO>> findAll(){
		List<AssociadoDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	//localhost:8080/associados/1
	@GetMapping(value = "/{id}")
	public ResponseEntity<AssociadoDTO> findById(@PathVariable Long id){
		AssociadoDTO dto = service.findById(id);		
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping //Método que mapeia o 'post' e resolve o problema
	public ResponseEntity<AssociadoDTO> insert(@RequestBody AssociadoDTO dto) {
		//Vai pegar o que está dentro do HTTP (@RequestBody) e colocar em 'dto'
		dto = service.insert(dto); //Vai receber o mesmo valor gravado no banco. Arquitetura Java abstraído pelo 'RestController'
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(null);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AssociadoDTO> update(@PathVariable Long id, @RequestBody AssociadoDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}




