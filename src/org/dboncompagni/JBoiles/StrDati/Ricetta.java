package org.dboncompagni.JBoiles.StrDati;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Ricetta {

	public String nome;
	public ArrayList<IgredienteRicetta> ingredienti;
	public String note;
	public Float proteineX100;
	public Float grassiX100;
	public Float carboidratiX100;
	public Float proteine;
	public Float grassi;
	public Float carboidrati;
	public Float indiceLegante;
	public Float digeribilita;
	public Float pesoSenzaUova;
	public Float pesoUova;
	public Float pesoTot;
	
	public Ricetta(String nome){
		ingredienti = new ArrayList<IgredienteRicetta>();
		this.nome=nome;
	}
	public Ricetta(File fileDati){
		ingredienti = new ArrayList<IgredienteRicetta>();
		this.caricaRicetta(fileDati);
	}
	
	public void aggiungiIngrediente(IgredienteRicetta ir){
		ingredienti.add(ir);
	}
	public void salvaRicetta(String filePath){
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = df.newDocumentBuilder();
			Document docXml = docBuilder.newDocument();
			
			docXml.setXmlStandalone(true);
			
			
			Element Ricetta = docXml.createElement("Ricetta");
			Ricetta.setAttribute("Nome", this.nome);
			Element ingr= docXml.createElement("Ingredienti");
			Element in;
			Element qta;
			Element note;
			IgredienteRicetta ir;
			for(int i=0;i<ingredienti.size();i++){
				ir = ingredienti.get(i);
				in = docXml.createElement("Ingrediente");
				in.setAttribute("Nome", ir.ingrediente.getNome());
				in.setAttribute("Carboidrati", String.valueOf(ir.ingrediente.getCarboidrati()));
				in.setAttribute("Grassi", String.valueOf(ir.ingrediente.getGrassi()));
				in.setAttribute("Proteine", String.valueOf(ir.ingrediente.getProteine()));
				in.setAttribute("Digeribilita", String.valueOf(ir.ingrediente.getDigeribilita()));
				in.setAttribute("IndiceLegante", String.valueOf(ir.ingrediente.getIndiceLegante()));
				
				qta = docXml.createElement("Quantita");
				qta.setTextContent(String.valueOf(ir.getQta()));
				
				in.appendChild(qta);
				ingr.appendChild(in);
			}
			
			note = docXml.createElement("Note");
			note.setTextContent(this.note);
			
			Ricetta.appendChild(ingr);
			Ricetta.appendChild(note);
			docXml.appendChild(Ricetta);
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trasf = tf.newTransformer();

			trasf.setOutputProperty(OutputKeys.INDENT,"yes");

			DOMSource sorg = new DOMSource (docXml);
			StreamResult sr = new StreamResult (new File(filePath));
			trasf.transform (sorg, sr);

			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		IgredienteRicetta in;
		String s = "Ricetta : "+this.nome+"\r\n Ingredienti: \r\n";
		for(int i=0;i<ingredienti.size();i++){
			in=ingredienti.get(i);
			s+=in.toString();
		}
		s+="Peso senza uova : "+this.pesoSenzaUova+"\r\n";
		s+="Peso totale : "+this.pesoTot+"\r\n";
		s+="Proteine % : "+this.proteineX100+" - gr. : "+this.proteine+"\r\n";
		s+="Grassi % : "+this.grassiX100+" - gr. : "+this.grassi+"\r\n";
		s+="Carboidrati % :"+this.carboidratiX100+" - gr. : "+this.carboidrati+"\r\n";
		s+="Note:\r\n"+this.note+"\r\n";
		return s;
	}
	
	public void calcola(){
		this.carboidrati=0f;
		this.proteine=0f;
		this.grassi=0f;
		this.pesoSenzaUova=0f;
		this.pesoUova=0f;
		this.pesoTot=0f;
		this.indiceLegante=0f;
		this.digeribilita=0f;
		Float legante=0f;
		Float diger=0f;
		IgredienteRicetta in;
		for(int i=0;i<ingredienti.size();i++){
			in=ingredienti.get(i);
			if(in.ingrediente.getNome().equals("NUM. DI UOVA (MEDIE 52gr)")){
				pesoUova+=(in.getQta()*52);
				this.carboidrati+=(in.ingrediente.getCarboidrati()/100)*(in.getQta()*52);
				this.proteine+=(in.ingrediente.getProteine()/100)*(in.getQta()*52);
				this.grassi+=(in.ingrediente.getGrassi()/100)*(in.getQta()*52);
				diger+=(in.ingrediente.getDigeribilita()/100)*(in.getQta()*52);
			}else if(in.ingrediente.getNome().equals("UOVA Personalizzato (gr)")){
				pesoUova+=in.getQta();
				this.carboidrati+=(in.ingrediente.getCarboidrati()/100)*in.getQta();
				this.proteine+=(in.ingrediente.getProteine()/100)*in.getQta();
				this.grassi+=(in.ingrediente.getGrassi()/100)*in.getQta();
				diger+=(in.ingrediente.getDigeribilita()/100)*in.getQta();
			}else{
				this.pesoSenzaUova+=in.getQta();
				this.carboidrati+=(in.ingrediente.getCarboidrati()/100)*in.getQta();
				this.proteine+=(in.ingrediente.getProteine()/100)*in.getQta();
				this.grassi+=(in.ingrediente.getGrassi()/100)*in.getQta();
				legante+=(in.ingrediente.getIndiceLegante()/100)*in.getQta();
				diger+=(in.ingrediente.getDigeribilita()/100)*in.getQta();
			}
		}
		pesoTot=pesoSenzaUova+pesoUova;
		this.carboidratiX100=(this.carboidrati/this.pesoTot)*100;
		this.grassiX100=(this.grassi/this.pesoTot)*100;
		this.proteineX100=(this.proteine/this.pesoTot)*100;
		this.indiceLegante=(legante/(pesoSenzaUova/100));
		this.digeribilita=(diger/(pesoTot/100));
	}
	
	public void caricaRicetta(File fileDati){
		
		Ingrediente ingred = null;
		Float q =0f;
		String tmp="";
		
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = df.newDocumentBuilder();
			Document docXml = docBuilder.parse(fileDati);
			
			Element ricetta =(Element) docXml.getFirstChild();
			this.nome=ricetta.getAttribute("Nome");
			
			Element ingr = (Element) ricetta.getElementsByTagName("Ingredienti").item(0);
			
			Element ingrediente;
			for(int i=0;i<ingr.getElementsByTagName("Ingrediente").getLength();i++){
				ingrediente = (Element) ingr.getElementsByTagName("Ingrediente").item(i);
				tmp=ingrediente.getAttribute("Nome")+";"+ingrediente.getAttribute("Digeribilita")+";"+ingrediente.getAttribute("Carboidrati")+";"+ingrediente.getAttribute("Grassi")+";"+ingrediente.getAttribute("IndiceLegante")+";"+ingrediente.getAttribute("Proteine");
				System.out.println(tmp);
				q = Float.valueOf(ingrediente.getElementsByTagName("Quantita").item(0).getTextContent());
				ingred = new Ingrediente(tmp);
				this.aggiungiIngrediente(new IgredienteRicetta(ingred,q));
			}

			this.note = ricetta.getElementsByTagName("Note").item(0).getTextContent();
	
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*Vecchia lettura file txt
		FileReader fileReader;
		try {
			fileReader = new FileReader(fileDati);
			BufferedReader fileBufereReader = new BufferedReader(fileReader ); 
			this.nome = fileBufereReader.readLine();
			this.note = fileBufereReader.readLine();
			String s = fileBufereReader.readLine();
			//System.out.println(s);
			StringTokenizer tok;
			while(s!=null){
				//System.out.println(s);
				tok = new StringTokenizer(s,":");
				String tmp=tok.nextToken();
				s = fileBufereReader.readLine();
				Ingrediente in = new Ingrediente(tmp);
				ingredienti.add(new IgredienteRicetta(in,Float.parseFloat(tok.nextToken())));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
