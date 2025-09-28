package com.smarvel.springboot.backend.service;

import java.time.LocalDate;
import java.util.List;

import com.smarvel.springboot.backend.dto.ProductoSupermercadoConComprasDTO;
import com.smarvel.springboot.backend.dto.ProductoSupermercadoDTO;
import com.smarvel.springboot.backend.entities.ProductoSupermercado;

public interface IProductoSupermercadoService {

	public List<ProductoSupermercado> mostrarProductoSupermercados();
	
	//public List<ProductoSupermercado> searchProductoSupermercado(String nombre);
	
	//public List<ProductoSupermercado> productosPorSupermercado(Long supermercadoId);
	
	public ProductoSupermercado buscarProductoSupermercadoPorId(Long id);
	
	public List<ProductoSupermercadoDTO> productosPorSupermercado(Long supermercadoId);
	
	public ProductoSupermercado guardarProductoSupermercado(ProductoSupermercado productoSupermercado);
	
	public void borrarProductoSupermercado(Long id);
	
	public ProductoSupermercado buscarPorProductoYSupermercado(Long productoId, Long supermercadoId);
	
	//public ProductoSupermercado buscarPrecioActual(Long productoId, Long supermercadoId);
	
//	public List<ProductoSupermercado> buscarTodosPorProductoYSupermercado(Long productoId, Long supermercadoId);
//	
	//public List<ProductoSupermercado> searchPorNombreYSuper(Long superId, String nombre);
	public List<ProductoSupermercadoDTO> searchPorNombreYSuper(Long superId, String nombre);
	public List<ProductoSupermercado> searchProductoSupermercado(String nombre);
	public List<ProductoSupermercadoConComprasDTO> searchPorNombreYSuperConCompras(Long supermercadoId, String nombre);
	public List<ProductoSupermercadoConComprasDTO> productosPorSupermercadoConCompras(Long supermercadoId);
	
	//ProductoSupermercado buscarPorProductoSupermercadoPrecio(Long productoId, Long supermercadoId, Double precio);

}
