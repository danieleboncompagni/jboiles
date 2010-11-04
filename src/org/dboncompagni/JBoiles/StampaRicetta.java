package org.dboncompagni.JBoiles;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;

import org.dboncompagni.JBoiles.StrDati.Ricetta;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;



public class StampaRicetta {
	
	private Ricetta r;
	
	public StampaRicetta(Ricetta r){
		this.r=r;
	}
	
	public void stampaPDF(){
		OutputStream os;
		JFileChooser jf = new JFileChooser();
		try {
			jf.showSaveDialog(null);
			os = new FileOutputStream(jf.getSelectedFile().getPath());
			Document doc = new Document();
			PdfWriter docWtriter = null;
			docWtriter = PdfWriter.getInstance(doc, os);
			doc.open();
			doc.setPageSize(com.itextpdf.text.PageSize.A4);
			Paragraph p;
			p = new Paragraph("JBoile v0.1");
			doc.add(p);
			p = new Paragraph("Ricetta : "+ r.nome);
			doc.add(p);
			p = new Paragraph("Ingrediente                  |  Carboidrati                 | Grassi             | Protine           ");
			doc.add(p);
			for(int i=0;i<r.ingredienti.size();i++){
				p = new Paragraph(r.ingredienti.get(i).ingrediente.getNome()+" | "+r.ingredienti.get(i).ingrediente.getCarboidrati()+" | "+r.ingredienti.get(i).ingrediente.getGrassi()+" | "+r.ingredienti.get(i).ingrediente.getProteine()+" || Quantita' : "+r.ingredienti.get(i).getQta());
				doc.add(p);
			}
			p = new Paragraph("");
			doc.add(p);
			p = new Paragraph("Note : "+ r.note);
			doc.add(p);
			doc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
