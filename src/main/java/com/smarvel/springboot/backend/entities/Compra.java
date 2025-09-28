package com.smarvel.springboot.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "precio_producto_id")
    private PrecioProducto precioProducto; // a qué precio se compró

    private Integer cantidad;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioPagado; // opcional si quieres guardar redundancia

    private LocalDate fechaCompra;

	public Compra() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PrecioProducto getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(PrecioProducto precioProducto) {
		this.precioProducto = precioProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioPagado() {
		return precioPagado;
	}

	public void setPrecioPagado(BigDecimal precioPagado) {
		this.precioPagado = precioPagado;
	}

	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDate fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
    
    
}

	
    

