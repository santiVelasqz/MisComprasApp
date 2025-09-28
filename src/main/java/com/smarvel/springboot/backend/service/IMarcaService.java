package com.smarvel.springboot.backend.service;

import java.util.List;


import com.smarvel.springboot.backend.entities.Marca;

public interface IMarcaService {
	
	public List<Marca> mostrarMarcas();
	
	public List<Marca> searchMarca(String nombre);
	
	public Marca buscarMarcaPorId(Long id);
	
	public Marca guardarMarca(Marca marca);
	
	public void borrarMarca(Long id);
	
	public boolean tieneProductosAsociados(Long marcaId);

}
