package com.smarvel.springboot.backend.service;

import java.util.List;

import com.smarvel.springboot.backend.entities.Categoria;

public interface ICategoriaService {
	
	public List<Categoria> mostrarCategorias();
	
	public List<Categoria> searchCategoria(String nombre);
	
	public Categoria buscarCategoriaPorId(Long id);
	
	public Categoria guardarCategoria(Categoria categoria);
	
	public void borrarCategoria(Long id);

}
