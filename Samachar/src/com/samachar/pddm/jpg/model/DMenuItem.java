package com.samachar.pddm.jpg.model;

public class DMenuItem {
	
	private String titulo;
	private int icono;
	private String count = "0";
	private boolean contVisible = false;
	
	public DMenuItem(){}

	public DMenuItem(String titulo, int icono){
		this.titulo = titulo;
		this.icono = icono;
	}
	
	public DMenuItem(String titulo, int icono, boolean contVisible, String count){
		this.titulo = titulo;
		this.icono = icono;
		this.contVisible = contVisible;
		this.count = count;
	}
	
	public String gettitulo(){
		return this.titulo;
	}
	
	public int geticono(){
		return this.icono;
	}
	
	public String getCont(){
		return this.count;
	}
	
	public boolean getCounterVisibility(){
		return this.contVisible;
	}
	
	public void settitulo(String titulo){
		this.titulo = titulo;
	}
	
	public void seticono(int icono){
		this.icono = icono;
	}
	
	public void setCont(String count){
		this.count = count;
	}
	
	public void setcontVisibilidad(boolean contVisible){
		this.contVisible = contVisible;
	}
}
