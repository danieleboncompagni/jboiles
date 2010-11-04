package org.dboncompagni.JBoiles.StrDati;

public class IgredienteRicetta {
	private Float qta; /**Quantità in grammi*/
	public Ingrediente ingrediente;
	
	public IgredienteRicetta(Ingrediente ing,Float qta) {
		this.ingrediente=ing;
		this.qta=qta;
	}
	public Float getQta(){
		return this.qta;
	}
	@Override
	public String toString(){
		return ingrediente.toString()+":"+this.qta+"\r\n";	
		}
}
