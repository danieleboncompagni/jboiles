package org.dboncompagni.JBoiles.StrDati;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListaIngredienti {
	private ArrayList<Ingrediente> lista;
	
	public ListaIngredienti(File fileDati){
		lista = new ArrayList<Ingrediente>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(fileDati);
			BufferedReader fileBufereReader = new BufferedReader(fileReader ); 
			String s = fileBufereReader.readLine();
			while(s!=null){
				lista.add(new Ingrediente(s));
			    s = fileBufereReader.readLine();
			  }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString(){
		String s="";
		Ingrediente ingr;
		for(int i=0;i<lista.size();i++){
			ingr = lista.get(i);
			s+=ingr.toStringln();
		}
		return s;
	}
	public String[] getNomi(){
		Ingrediente in; 
		String[] n = new String[lista.size()];
		for(int i=0;i<lista.size();i++){
			in=lista.get(i);
			n[i]=in.getNome();
		}
		return n;
	}
	
	public Ingrediente getIngrediente(int i){
		return this.lista.get(i);
	}
}
