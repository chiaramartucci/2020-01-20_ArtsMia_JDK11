package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable <Adiacenza>{
	
	private Integer artID1;
	private Integer artID2;
	private Integer peso;
	
	
	public Adiacenza(Integer artID1, Integer artID2, Integer peso) {
		super();
		this.artID1 = artID1;
		this.artID2 = artID2;
		this.peso = peso;
	}


	public Integer getArtID1() {
		return artID1;
	}


	public void setArtID1(Integer artID1) {
		this.artID1 = artID1;
	}


	public Integer getArtID2() {
		return artID2;
	}


	public void setArtID2(Integer artID2) {
		this.artID2 = artID2;
	}


	public Integer getPeso() {
		return peso;
	}


	public void setPeso(Integer peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Adiacenza o) {
		return -(this.peso.compareTo(o.getPeso()));
	}


	@Override
	public String toString() {
		return "Artista 1: " + artID1 + "  Artista 2: " + artID2 + "  numero di esposizioni comuni: " + peso;
	}
	
	

}
