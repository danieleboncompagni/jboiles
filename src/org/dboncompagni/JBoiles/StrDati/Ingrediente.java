package org.dboncompagni.JBoiles.StrDati;

import java.util.StringTokenizer;

public class Ingrediente {
	private String nome;
	private Float digeribilita;
	private Float carboidrati;
	private Float grassi;
	private Float legante;
	private Float proteine;
	
	public Ingrediente(String dati) {
		StringTokenizer tok = new StringTokenizer(dati,";");
		nome = tok.nextToken();
		digeribilita = Float.parseFloat(tok.nextToken());
		carboidrati = Float.parseFloat(tok.nextToken());
		grassi = Float.parseFloat(tok.nextToken());
		legante = Float.parseFloat(tok.nextToken());
		proteine = Float.parseFloat(tok.nextToken());
	}
	
	public String getNome(){
		return this.nome;
	}
	public Float getDigeribilita(){
		return this.digeribilita;
	}
	public Float getCarboidrati(){
		return this.carboidrati;
	}
	public Float getGrassi(){
		return this.grassi;
	}
	public Float getIndiceLegante(){
		return this.legante;
	}
	public Float getProteine(){
		return this.proteine;
	}
	public String toStringln(){
		return this.nome+";"+this.digeribilita+";"+this.carboidrati+";"+this.grassi+";"+this.legante+";"+this.proteine+"\r\n";
	}
	@Override
	public String toString(){
		return this.nome+";"+this.digeribilita+";"+this.carboidrati+";"+this.grassi+";"+this.legante+";"+this.proteine;
	}
}
