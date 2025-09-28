package com.smarvel.springboot.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smarvel.springboot.backend.entities.Producto;
import com.smarvel.springboot.backend.entities.Supermercado;

public class ProductoSupermercadoConComprasDTO {
	
	 private Long id;
	    private Producto producto;
	    private Supermercado supermercado;
	    private BigDecimal precioActual;
	    private LocalDate fechaActual;
	    private boolean tieneCompras;  // nuevo campo
	    public ProductoSupermercadoConComprasDTO() {
	        // Constructor por defecto necesario para instanciar sin parámetros
	    }

	    public ProductoSupermercadoConComprasDTO(Long id, Producto producto, Supermercado supermercado, BigDecimal precioActual, LocalDate fechaActual, boolean tieneCompras) {
	        this.id = id;
	        this.producto = producto;
	        this.supermercado = supermercado;
	        this.precioActual = precioActual;
	        this.fechaActual = fechaActual;
	        this.tieneCompras = tieneCompras;
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

		public boolean isTieneCompras() {
			return tieneCompras;
		}

		public void setTieneCompras(boolean tieneCompras) {
			this.tieneCompras = tieneCompras;
		}

	    // getters y setters
	    
	    

}
