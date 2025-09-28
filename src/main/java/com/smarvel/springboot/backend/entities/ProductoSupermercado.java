package com.smarvel.springboot.backend.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;

@Entity
@Table(name="productos_supermercados", uniqueConstraints = @UniqueConstraint(columnNames = {"producto_id", "supermercado_id"}))
public class ProductoSupermercado {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name="producto_id")
	    private Producto producto;

	    @ManyToOne
	    @JoinColumn(name="supermercado_id")
	    private Supermercado supermercado;

	    @OneToMany(mappedBy = "productoSupermercado")
	    private List<PrecioProducto> historicoPrecios = new ArrayList<>();
	    
	    @Transient
	    private PrecioProducto precioActual;

	    public PrecioProducto getPrecioActual() { return precioActual; }
	    public void setPrecioActual(PrecioProducto precioActual) { this.precioActual = precioActual; }

	public ProductoSupermercado() {}

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

	public List<PrecioProducto> getHistoricoPrecios() {
		return historicoPrecios;
	}

	public void setHistoricoPrecios(List<PrecioProducto> historicoPrecios) {
		this.historicoPrecios = historicoPrecios;
	}
	
	
	
	
	

}
