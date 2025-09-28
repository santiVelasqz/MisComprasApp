package com.smarvel.springboot.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smarvel.springboot.backend.entities.Producto;
import com.smarvel.springboot.backend.entities.Supermercado;

public class ProductoSupermercadoDTO {
	
	 private Long id;
	    private Producto producto;
	    private Supermercado supermercado;
	    private BigDecimal precioActual;
	    private LocalDate fechaActual;
	    
	    
	    
		public ProductoSupermercadoDTO() {
			super();
		}
		public ProductoSupermercadoDTO(Long id, Producto producto, Supermercado supermercado, BigDecimal precioActual,
				LocalDate fechaActual) {
			super();
			this.id = id;
			this.producto = producto;
			this.supermercado = supermercado;
			this.precioActual = precioActual;
			this.fechaActual = fechaActual;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Producto getProducto() {
			return producto;
		}
		public void setProducto(Producto producto) {
			this.producto = producto;
		}
		public Supermercado getSupermercado() {
			return supermercado;
		}
		public void setSupermercado(Supermercado supermercado) {
			this.supermercado = supermercado;
		}
		public BigDecimal getPrecioActual() {
			return precioActual;
		}
		public void setPrecioActual(BigDecimal precioActual) {
			this.precioActual = precioActual;
		}
		public LocalDate getFechaActual() {
			return fechaActual;
		}
		public void setFechaActual(LocalDate fechaActual) {
			this.fechaActual = fechaActual;
		}
	    
	    

}
