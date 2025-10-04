package com.smarvel.springboot.backend.dto;

import java.math.BigDecimal;

public class CompraDTO {

	private Long id;
    private String producto;
    private String supermercado;
    private BigDecimal precioVenta;
    private int cantidad;
    private BigDecimal precioPagado;
    private String fecha;
	public CompraDTO(Long id, String producto, String supermercado, BigDecimal precioVenta, int cantidad, BigDecimal precioPagado,
			String fecha) {
		super();
		this.id = id;
		this.producto = producto;
		this.supermercado = supermercado;
		this.precioVenta = precioVenta;
		this.cantidad = cantidad;
		this.precioPagado = precioPagado;
		this.fecha = fecha;
	}
	
	
	
	public CompraDTO() {
		super();
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getSupermercado() {
		return supermercado;
	}
	public void setSupermercado(String supermercado) {
		this.supermercado = supermercado;
	}
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioPagado() {
		return precioPagado;
	}
	public void setPrecioPagado(BigDecimal precioPagado) {
		this.precioPagado = precioPagado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
    
    
}
