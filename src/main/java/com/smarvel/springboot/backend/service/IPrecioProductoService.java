package com.smarvel.springboot.backend.service;

import java.time.LocalDate;
import java.util.List;

import com.smarvel.springboot.backend.entities.PrecioProducto;

public interface IPrecioProductoService {
	
	PrecioProducto buscarPorProductoSupermercadoYFecha(Long productoSupermercadoId, LocalDate fecha);
    PrecioProducto guardar(PrecioProducto precioProducto);
    List<PrecioProducto> buscarPorProductoYSupermercado(Long productoId, Long supermercadoId);
    public void borrarPrecioProducto(Long id);


}
