package org.dboncompagni.JBoiles.Gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.dboncompagni.JBoiles.StampaRicetta;
import org.dboncompagni.JBoiles.StrDati.IgredienteRicetta;
import org.dboncompagni.JBoiles.StrDati.Ricetta;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8517292028364409753L;

	private JMenuBar mBar;
	private JMenu mFile;
	private JMenu mIngredienti;
	private JMenu mAiuto;
	private JMenuItem aiutoRiferimenti;
	private JMenuItem aiutoAbout;
	private JMenuItem ingredientiAggiungi;
	private JMenuItem ingredientiRimuovi;
	private JMenuItem fileEsci;
	private JMenuItem fileSalva;
	private JMenuItem fileNuovaRicetta;
	private JMenuItem fileCarica;
	private JMenuItem fileStampa;
	
	private JLabel lblCarboidrati;
	private JLabel lblCarboidratiX100;
	private JLabel lblGrassi;
	private JLabel lblGrassiX100;
	private JLabel lblProteine;
	private JLabel lblProteineX100;
	private JLabel lblPesoSenaUova;
	private JLabel lblPesoTot;
	private JLabel lblNote;
	private JLabel lblLegante;
	private JLabel lblDigeribilita;
	private JTextArea txtB;
	private JButton btnCalcola;
	
	private ImageIcon icona;
	
	private String nomeRicetta="";
	
	public Ricetta ric;
	
	/*Tabella*/
	private String[] intestazioneTab;
	private JTable tabella;
	private Object[][] datiTab;
	private JScrollPane scrollPane;
	
	public MainWindow(){
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.setLayout(null);
		this.initData();
		this.initComponents();
		this.setJMenuBar(mBar);
		this.setIconImage(icona.getImage());
		this.setTitle("JBoiles V 0.1a - Ricetta : "+nomeRicetta);
		this.setVisible(true);
	}
	private void initComponents(){
		icona = new ImageIcon("support/carpa.gif");
		mBar = new JMenuBar();
		mFile = new JMenu("File");
		mIngredienti = new JMenu("Ingredienti");
		mAiuto = new JMenu("Aiuto");
		
		aiutoRiferimenti = new JMenuItem("Riferimenti");
		aiutoRiferimenti.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new RifWindow();
			}
		});
		
		aiutoAbout = new JMenuItem("Informazioni");
		aiutoAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new AboutWindow();
			}
		});
		
		fileEsci = new JMenuItem("Esci");
		fileEsci.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		ingredientiAggiungi = new JMenuItem("Aggiungi");
		ingredientiAggiungi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(nomeRicetta.length()==0){
					txtB.setText("");
					nomeRicetta=JOptionPane.showInputDialog("Nome ricetta :");
					setTitle("JBoiles V 0.1a - Ricetta : "+nomeRicetta);
					ric = new Ricetta(nomeRicetta);
				}
				new AddWindow(MainWindow.this);
			}
		});
		
		ingredientiRimuovi = new JMenuItem("Rimuovi");
		ingredientiRimuovi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int sel = tabella.getSelectedRow();
				if(sel==-1){
					JOptionPane.showMessageDialog(null,"Nessune ingrediente selezionato, selezionarne uno e riprovare");
				}else{
					ric.ingredienti.remove(sel);
					aggiornaDati();
				}
			}
		});
		
		fileSalva = new JMenuItem("Salva");
		fileSalva.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ric.note=txtB.getText();
				String f;
				JFileChooser jf = new JFileChooser();
				jf.showSaveDialog(null);
				f=jf.getSelectedFile().getPath();
				ric.salvaRicetta(f);
			}
		});
		
		fileCarica = new JMenuItem("Carica");
		fileCarica.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String f;
				JFileChooser jf = new JFileChooser();
				jf.showOpenDialog(null);
				f=jf.getSelectedFile().getPath();
				ric = new Ricetta(new File(f));
				nomeRicetta=ric.nome;
				aggiornaDati();
				txtB.setText(ric.note);
				/*ricalcolo i valori nutrizionali*/
				if(nomeRicetta==""){
					JOptionPane.showMessageDialog(null, "Non è presente alcun ingrediente");
				}else{
					ric.calcola();
					DecimalFormat decimal = new DecimalFormat("##0.##"); //, symbols)
					lblCarboidrati.setText("Carboidrati : "+decimal.format(ric.carboidrati));
					lblCarboidratiX100.setText("Carboidrati % : "+decimal.format(ric.carboidratiX100));
					lblPesoSenaUova.setText("Peso senza uova : " + decimal.format(ric.pesoSenzaUova));
					lblPesoTot.setText("Peso totale : "+decimal.format(ric.pesoTot));
					lblCarboidrati.setText("Carobidrati : "+decimal.format(ric.carboidrati));
					lblCarboidratiX100.setText("Carboidrati % : "+decimal.format(ric.carboidratiX100));
					lblGrassi.setText("Grassi : "+decimal.format(ric.grassi));
					lblProteine.setText("Proteine : "+decimal.format(ric.proteine));
					lblProteineX100.setText("Proteine % : "+decimal.format(ric.proteineX100));
					lblGrassiX100.setText("Grassi % : "+decimal.format(ric.grassiX100));
					lblLegante.setText("Indice legante : "+decimal.format(ric.indiceLegante));
					lblDigeribilita.setText("Digeribilità : "+decimal.format(ric.digeribilita));
					ric.note=txtB.getText();
				}
			}
		});
		
		fileNuovaRicetta = new JMenuItem("Nuova ricetta");
		fileNuovaRicetta.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(null,"L'operazione eliminerà tutti i dati, confermi ?","Nuova ricetta",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					txtB.setText("");
					nomeRicetta=JOptionPane.showInputDialog("Nome ricetta");
					setTitle("JBoiles V 0.1a - Ricetta : "+nomeRicetta);
					ric = new Ricetta(nomeRicetta);
					datiTab = new Object[1][5];
					DefaultTableModel tm= new DefaultTableModel(datiTab,intestazioneTab);
					tabella.setModel(tm);
				}
			}
		});
		
		fileStampa = new JMenuItem("Esporta in PDF");
		fileStampa.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new StampaRicetta(ric).stampaPDF();
			}
		});
		
		mAiuto.add(aiutoRiferimenti);
		mAiuto.add(aiutoAbout);
		mFile.add(fileNuovaRicetta);
		mFile.add(fileCarica);
		mFile.add(fileSalva);
		mFile.add(fileStampa);
		mFile.add(fileEsci);
		mIngredienti.add(ingredientiAggiungi);
		mIngredienti.add(ingredientiRimuovi);
		mBar.add(mFile);
		mBar.add(mIngredienti);
		mBar.add(mAiuto);
		
		/*note*/
		lblNote = new JLabel ("Note :");
		lblNote.setBounds(10,330,70,20);
		txtB = new JTextArea();
		txtB.setBounds(10, 360, 300, 150);
		txtB.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		txtB.setLineWrap(true);
		
		scrollPane = new JScrollPane(tabella);
		scrollPane.setBounds(10,10,770,300);
		this.getContentPane().add(scrollPane);
		
		this.lblCarboidrati = new JLabel("Carobidrati");
		this.lblCarboidrati.setBounds(350,330,160,20);
		this.lblCarboidrati.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblCarboidratiX100 = new JLabel("Carobidrati %");
		this.lblCarboidratiX100.setBounds(350,360,160,20);
		this.lblCarboidratiX100.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblGrassi = new JLabel("Grassi");
		this.lblGrassi.setBounds(520,330,140,20);
		this.lblGrassi.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblGrassiX100 = new JLabel("Grassi %");
		this.lblGrassiX100.setBounds(520,360,140,20);
		this.lblGrassiX100.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblProteine = new JLabel("Proteine");
		this.lblProteine.setBounds(650,330,140,20);
		this.lblProteine.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblProteineX100 = new JLabel("Proteine %");
		this.lblProteineX100.setBounds(650,360,140,20);
		this.lblProteineX100.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblPesoSenaUova = new JLabel("Peso senza uova");
		this.lblPesoSenaUova.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblPesoSenaUova.setBounds(350,390,170,20);
		this.lblPesoTot = new JLabel("Peso totale");
		this.lblPesoTot.setBounds(550,390,150,20);
		this.lblPesoTot.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblLegante = new JLabel("Indice legante : ");
		this.lblLegante.setBounds(350,420,150,20);
		this.lblLegante.setFont(new Font("Monospaced",Font.BOLD,12));
		this.lblDigeribilita = new JLabel("Digeribilità : ");
		this.lblDigeribilita.setBounds(550,420,150,20);
		this.lblDigeribilita.setFont(new Font("Monospaced",Font.BOLD,12));
		this.btnCalcola = new JButton("Calcola");
		this.btnCalcola.setBounds(400,460,300,50);
		this.btnCalcola.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(nomeRicetta==""){
					JOptionPane.showMessageDialog(null, "Non è presente alcun ingrediente");
				}else{
					ric.calcola();
					DecimalFormat decimal = new DecimalFormat("##0.##"); //, symbols)
					lblCarboidrati.setText("Carboidrati : "+decimal.format(ric.carboidrati));
					lblCarboidratiX100.setText("Carboidrati % : "+decimal.format(ric.carboidratiX100));
					lblPesoSenaUova.setText("Peso senza uova : " + decimal.format(ric.pesoSenzaUova));
					lblPesoTot.setText("Peso totale : "+decimal.format(ric.pesoTot));
					lblCarboidrati.setText("Carobidrati : "+decimal.format(ric.carboidrati));
					lblCarboidratiX100.setText("Carboidrati % : "+decimal.format(ric.carboidratiX100));
					lblGrassi.setText("Grassi : "+decimal.format(ric.grassi));
					lblProteine.setText("Proteine : "+decimal.format(ric.proteine));
					lblProteineX100.setText("Proteine % : "+decimal.format(ric.proteineX100));
					lblGrassiX100.setText("Grassi % : "+decimal.format(ric.grassiX100));
					lblLegante.setText("Indice legante : "+decimal.format(ric.indiceLegante));
					lblDigeribilita.setText("Digeribilità : "+decimal.format(ric.digeribilita));
					ric.note=txtB.getText();
				}
			}
		});
		
		this.getContentPane().add(lblCarboidrati);
		this.getContentPane().add(lblCarboidratiX100);
		this.getContentPane().add(lblGrassi);
		this.getContentPane().add(lblGrassiX100);
		this.getContentPane().add(lblProteine);
		this.getContentPane().add(lblProteineX100);
		this.getContentPane().add(lblPesoSenaUova);
		this.getContentPane().add(lblPesoTot);
		this.getContentPane().add(lblLegante);
		this.getContentPane().add(lblDigeribilita);
		this.getContentPane().add(lblNote);
		this.getContentPane().add(txtB);
		this.getContentPane().add(btnCalcola);
	}
	
	private void initData(){
		intestazioneTab = new String[] {"Nome","Carboidrati","Grassi","Proteine","Quantità"};
		datiTab = new Object[1][5];
		tabella = new JTable(datiTab,intestazioneTab);
	}
	public void aggiornaDati(){
		datiTab = new Object[ric.ingredienti.size()][5];
		IgredienteRicetta in;
		for(int i=0;i<ric.ingredienti.size();i++){
			in = ric.ingredienti.get(i);
			datiTab[i][0]=in.ingrediente.getNome();
			datiTab[i][1]=in.ingrediente.getCarboidrati();
			datiTab[i][2]=in.ingrediente.getGrassi();
			datiTab[i][3]=in.ingrediente.getProteine();
			datiTab[i][4]=in.getQta();
		}
		DefaultTableModel tm= new DefaultTableModel(datiTab,intestazioneTab);
		tabella.setModel(tm);
	}
}
