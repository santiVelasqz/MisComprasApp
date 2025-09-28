package com.smarvel.springboot.backend.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarvel.springboot.backend.entities.Marca;
import com.smarvel.springboot.backend.repository.MarcaRepository;
import com.smarvel.springboot.backend.repository.ProductoRepository;
import com.smarvel.springboot.backend.service.IMarcaService;

@Service
public class MarcaServiceImpl implements IMarcaService{
	
	@Autowired
	MarcaRepository marcaRepository;
	
	@Autowired
	ProductoRepository productoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Marca> mostrarMarcas() {
		
		return marcaRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Marca> searchMarca(String nombre) {
		return marcaRepository.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public Marca buscarMarcaPorId(Long id) {
		
		return marcaRepository.findById(id).orElseThrow(null);
	}

	@Override
	@Transactional
	public Marca guardarMarca(Marca marca) {
		
		return marcaRepository.save(marca);
	}

	@Override
	@Transactional
	public void borrarMarca(Long id) {
		marcaRepository.deleteById(id);
		
	}
	
	@Transactional(readOnly = true)
	public boolean tieneProductosAsociados(Long marcaId) {
	    return productoRepository.existsByMarcaId(marcaId);
	}

	
	

}
