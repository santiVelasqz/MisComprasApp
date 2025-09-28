package com.smarvel.springboot.backend.dto;

public class ProductoConComprasDTO {
	    private Long id;
	    private String nombre;
	    private String categoria;
	    private String marca;
	    private boolean tieneCompras; // <-- clave
	    
	    public ProductoConComprasDTO(Long id, String nombre, String categoria, String marca, boolean tieneCompras) {
	        this.id = id;
	        this.nombre = nombre;
	        this.categoria = categoria;
	        this.marca = marca;
	        this.tieneCompras = tieneCompras;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getCategoria() {
			return categoria;
		}

		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}

		public String getMarca() {
			return marca;
		}

		public void setMarca(String marca) {
			this.marca = marca;
		}

		public boolean isTieneCompras() {
			return tieneCompras;
		}

		public void setTieneCompras(boolean tieneCompras) {
			this.tieneCompras = tieneCompras;
		}
	    
	    // getters y setters
	    


}
