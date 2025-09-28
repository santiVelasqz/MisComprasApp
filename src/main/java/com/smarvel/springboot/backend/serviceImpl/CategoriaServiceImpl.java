package com.smarvel.springboot.backend.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarvel.springboot.backend.entities.Categoria;
import com.smarvel.springboot.backend.repository.CategoriaRepository;
import com.smarvel.springboot.backend.service.ICategoriaService;

@Service
public class CategoriaServiceImpl implements ICategoriaService{
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> mostrarCategorias() {
		
		return categoriaRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> searchCategoria(String nombre) {
			
		return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria buscarCategoriaPorId(Long id) {
		
		return categoriaRepository.findById(id).orElseThrow(null);
	}

	@Override
	@Transactional
	public Categoria guardarCategoria(Categoria categoria) {
		
		return categoriaRepository.save(categoria);
	}

	@Override
	@Transactional
	public void borrarCategoria(Long id) {
		categoriaRepository.deleteById(id);
		
	}

}
