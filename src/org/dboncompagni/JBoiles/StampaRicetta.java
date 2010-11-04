package org.dboncompagni.JBoiles;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;

import org.dboncompagni.JBoiles.StrDati.Ricetta;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class StampaRicetta {
	
	private static Font fTitolo1 = new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD);
	private static Font fTitolo2 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font fIntestazione = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.ITALIC);
	
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
			p = new Paragraph("JBoile v0.2 - Daniele Boncompagni",fIntestazione);
			p.setAlignment(Paragraph.ALIGN_RIGHT);
			doc.add(p);
			p = new Paragraph("Ricetta : "+ r.nome,fTitolo1);
			doc.add(p);
			p = new Paragraph("");
			doc.add(p);
			p = new Paragraph("Ingredienti",fTitolo2);
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			PdfPTable tab = new PdfPTable(2);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Ingrediente"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Quantità [g]"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab.addCell(c1);
			
			tab.setHeaderRows(1);
			
			for(int i=0;i<r.ingredienti.size();i++){
				tab.addCell(r.ingredienti.get(i).ingrediente.getNome());
				tab.addCell(String.valueOf(r.ingredienti.get(i).getQta()));
			}
			
			doc.add(tab);
			
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph("Peso senza uova : " + r.pesoSenzaUova);
			doc.add(p);
			p = new Paragraph("Peso totale : " + r.pesoTot);
			doc.add(p);
			p = new Paragraph("Note : " + r.note);
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph("Valori nutrizionali : ",fTitolo2);
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			
			tab = new PdfPTable(3);
			
			c1 = new PdfPCell(new Phrase("Elemento"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Grammi"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Percentuale [%]"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab.addCell(c1);
			
			tab.addCell("Carboidrati");
			tab.addCell(String.valueOf(r.carboidrati));
			tab.addCell(String.valueOf(r.carboidratiX100));
			tab.addCell("Grassi");
			tab.addCell(String.valueOf(r.grassi));
			tab.addCell(String.valueOf(r.grassiX100));
			tab.addCell("Proteine");
			tab.addCell(String.valueOf(r.proteine));
			tab.addCell(String.valueOf(r.proteineX100));
			
			doc.add(tab);
			
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph("Indice di digeribilità : " + r.digeribilita,fTitolo2);
			doc.add(p);
			p = new Paragraph("Indice legante : " + r.indiceLegante,fTitolo2);
			doc.add(p);
			
			doc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}