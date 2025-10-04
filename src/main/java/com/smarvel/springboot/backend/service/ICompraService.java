package com.smarvel.springboot.backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.smarvel.springboot.backend.dto.CompraDTO;
import com.smarvel.springboot.backend.entities.Compra;

public interface ICompraService {
	
	public List<Compra> mostrarCompras();
	
	public List<Compra> mostrarComprasPorFecha(LocalDate fecha);
	
	public List<Compra> mostrarComprasEntreFechas(LocalDate fecha1, LocalDate fecha2);
	
	public List<Compra> searchCompra(String search);
	public List<CompraDTO> searchCompra2(String search);
	
	public Compra buscarCompraPorId(Long id);
	
	public List<Compra> buscarPorSupermercado(Long supermercadoId);
	
	public List<Compra> buscarPorSupermercadoYFecha(Long supermercadoId, LocalDate fecha);
	public List<Compra> buscarPorSupermercadoYFechas(Long supermercadoId, LocalDate fecha1, LocalDate fecha2);
	
	public BigDecimal totalGastadoPorMes(int mes, int anio);
	public List<Compra> buscarComprasPorMesYAnio(int mes, int anio);
	
	public BigDecimal totalGastadoPorAnio(int anio);
	public List<Compra> buscarComprasPorAnio(int anio);
	
	BigDecimal totalGastadoPorSupermercado(Long supermercadoId);
	BigDecimal totalGastadoPorFecha(LocalDate fecha);
	BigDecimal totalGastadoPorFechas(LocalDate fecha1, LocalDate fecha2);
	BigDecimal totalGastadoPorSupermercadoYFecha(Long supermercadoId, LocalDate fecha);
	BigDecimal totalGastadoPorSupermercadoYFechas(Long supermercadoId, LocalDate fecha1, LocalDate fecha2);

	public boolean tieneComprasAsociadas(Long productoId);
	public boolean tieneComprasAsociadasSupermercado(Long supermercadoId);
	public boolean existeCompraDuplicada(Long productoId, Long supermercadoId, LocalDate fechaCompra, Long compraId);
	
	public Compra guardarCompra(Compra compra);
	
	public void borrarCompra(Long id);
	

}
