package com.smarvel.springboot.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="producto_supermercado_precios")
public class PrecioProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="producto_supermercado_id")
    private ProductoSupermercado productoSupermercado;

    private BigDecimal precio;

    private LocalDate fecha;
    
    

	public PrecioProducto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductoSupermercado getProductoSupermercado() {
		return productoSupermercado;
	}

	public void setProductoSupermercado(ProductoSupermercado productoSupermercado) {
		this.productoSupermercado = productoSupermercado;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
    
    
    
    
}
