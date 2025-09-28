package com.smarvel.springboot.backend.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarvel.springboot.backend.entities.Supermercado;
import com.smarvel.springboot.backend.repository.SupermercadoRepository;
import com.smarvel.springboot.backend.service.ISupermercadoService;

@Service
public class SupermercadoServiceImpl implements ISupermercadoService{
	
	@Autowired
	SupermercadoRepository supermercadoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Supermercado> mostrarSupermercados() {
		
		return supermercadoRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Supermercado> searchSupermercado(String search) {
		
		return supermercadoRepository.findByNombreContainingIgnoreCaseOrCiudadContainingIgnoreCase(search, search);
	}

	@Override
	@Transactional(readOnly = true)
	public Supermercado buscarSupermercadoPorId(Long id) {
		
		return supermercadoRepository.findById(id).orElseThrow(null);
	}

	@Override
	@Transactional
	public Supermercado guardarSupermercado(Supermercado supermercado) {
		
		return supermercadoRepository.save(supermercado);
	}

	@Override
	@Transactional
	public void borrarSupermercado(Long id) {
		supermercadoRepository.deleteById(id);
		
	}

}
