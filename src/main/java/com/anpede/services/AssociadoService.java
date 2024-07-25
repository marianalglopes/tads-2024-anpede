package com.anpede.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anpede.dto.AssociadoDTO;
import com.anpede.entities.Associado;
import com.anpede.repositories.AssociadoRepository;
import com.anpede.services.exceptions.EntityNotFoundException;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repository;

	@Transactional(readOnly = true)
	public List<AssociadoDTO> findAll() {
		// Lista utilizando Lambdas com retorno direto
		List<Associado> list = repository.findAll();
		return list.stream().map(a -> new AssociadoDTO(a)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AssociadoDTO findById(Long id) {
		Optional<Associado> obj = repository.findById(id); //Arrow-function que joga a mensagem da exceção
		Associado a = obj.orElseThrow(() -> new EntityNotFoundException("O registro não foi localizado na base de dados"));

		return new AssociadoDTO(a);
	}

	public AssociadoDTO insert(AssociadoDTO dto) {
		//Converter o 'dto' em um 'Associado' padrão, pois o repository só aceita esse tipo
		Associado entity = new Associado(); //'Get' em cada elemento 'dto' em elementos da entidade
		entity.setNome(dto.getNome());
		entity.setCPF(dto.getCPF());
		entity.setDataNascimento(dto.getDataNascimento());
		entity.setTelefone(dto.getTelefone());
		entity.setEmail(dto.getEmail());
		entity.setEndereco(dto.getEndereco());
		
		entity = repository.save(entity); //Retroalimenta a 'entity'. Ao dar o 'save', ele gera o id no banco
		return new AssociadoDTO(entity);
	}
	
	@Transactional //A transação só vai "morrer" no fim.
	public AssociadoDTO update(Long id, AssociadoDTO dto) {
		try {
		Associado entity = repository.getReferenceById(id); //Pego a variável da memória que já está no pc.
		
		entity.setNome(dto.getNome());
		entity.setCPF(dto.getCPF());
		entity.setDataNascimento(dto.getDataNascimento());
		entity.setTelefone(dto.getTelefone());
		entity.setEmail(dto.getEmail());
		entity.setEndereco(dto.getEndereco());
		
		entity = repository.save(entity); //Retroalimenta a 'entity'. Ao dar o 'save', ele gera o id no banco
		return new AssociadoDTO(entity);
		
		} catch(jakarta.persistence.EntityNotFoundException e) {
			throw new EntityNotFoundException("O recurso com o ID " +id+ " não foi localizado.");
		}
	}
	//Implementar tratamento de exceções para a deleção de registros
	public void delete(Long id) {
		repository.deleteById(id);
		
	}
	
}
