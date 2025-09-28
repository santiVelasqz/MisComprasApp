package com.smarvel.springboot.backend.service;

import java.util.List;

import com.smarvel.springboot.backend.entities.Producto;

public interface IProductoService {
	
	public List<Producto> mostrarProductos();
	
	public List<Producto> searchProducto(String nombre);
	
	public Producto buscarProductoPorId(Long id);
	
	public Producto guardarProducto(Producto producto);
	
	public void borrarProducto(Long id);
	public boolean existeProductoEnSupermercado(Long supermercadoId);

}
