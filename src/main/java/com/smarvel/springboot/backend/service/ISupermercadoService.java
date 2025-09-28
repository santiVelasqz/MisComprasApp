package com.smarvel.springboot.backend.service;

import java.util.List;

import com.smarvel.springboot.backend.entities.Supermercado;

public interface ISupermercadoService {
	
public List<Supermercado> mostrarSupermercados();
	
	public Supermercado buscarSupermercadoPorId(Long id);
	
	public List<Supermercado> searchSupermercado(String nombre);
	
	public Supermercado guardarSupermercado(Supermercado supermercado);
	
	public void borrarSupermercado(Long id);

}
