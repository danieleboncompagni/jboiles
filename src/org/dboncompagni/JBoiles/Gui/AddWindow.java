package org.dboncompagni.JBoiles.Gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.dboncompagni.JBoiles.StrDati.IgredienteRicetta;
import org.dboncompagni.JBoiles.StrDati.ListaIngredienti;

public class AddWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9089327199082632125L;
	private JButton btnOk;
	private JButton btnAnnulla;
	
	private JLabel lblQta;
	private JLabel lblIngrediente;
	private JLabel lblDatiNutr;
	
	private JComboBox cbIngrediente;
	
	private JTextField txtQta;
	
	private ListaIngredienti lista = new ListaIngredienti(new File("support/valori_nutrizionali.csv"));
	
	private int ingredienteSelezionato;
	
	private MainWindow parent;
	
	public AddWindow(MainWindow parent){
		this.parent=parent;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setSize(400,170);
		this.setTitle("Aggiunta ingrediente");
		this.initCompnents();
		lblDatiNutr.setText("Carboidrati : "+lista.getIngrediente(ingredienteSelezionato).getCarboidrati()+" - Grassi : "+lista.getIngrediente(ingredienteSelezionato).getGrassi()+" - Proteine : "+lista.getIngrediente(ingredienteSelezionato).getProteine());
		this.setVisible(true);
	}
	private void initCompnents(){
		this.lblDatiNutr = new JLabel("");
		this.lblDatiNutr.setBounds(10,70,350,20);
		this.lblDatiNutr.setFont(new Font("Monospaced",Font.BOLD,12));
		
		this.lblIngrediente = new JLabel("Ingrediente");
		lblIngrediente.setBounds(10,10,100,20);
		
		this.cbIngrediente = new JComboBox(lista.getNomi());
		this.cbIngrediente.setBounds(120,10,270,20);
		this.cbIngrediente.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String s;
				ingredienteSelezionato=cbIngrediente.getSelectedIndex();
				s="Carboidrati : "+lista.getIngrediente(ingredienteSelezionato).getCarboidrati()+" - Grassi : "+lista.getIngrediente(ingredienteSelezionato).getGrassi()+" - Proteine : "+lista.getIngrediente(ingredienteSelezionato).getProteine();
				lblDatiNutr.setText(s);
				if(cbIngrediente.getSelectedItem().toString().equals("NUM. DI UOVA (MEDIE 52gr)")){
					lblQta.setText("Numero di Uova");
				}else{
					lblQta.setText("Quantità [g]");
				}
			}
		});
		
		this.btnOk = new JButton("Inserisci");
		this.btnOk.setBounds(280,100,100,20);
		this.btnOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(txtQta.getText().length()==0){
					javax.swing.JOptionPane.showMessageDialog(null,"Valorizzare il campo Quantit�");
				}else{
					parent.ric.aggiungiIngrediente(new IgredienteRicetta(lista.getIngrediente(ingredienteSelezionato),Float.valueOf(txtQta.getText())));
					parent.aggiornaDati();
					dispose();
				}
			}
		});
		
		this.btnAnnulla = new JButton("Annulla");
		this.btnAnnulla.setBounds(10,100,100,20);
		this.btnAnnulla.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		this.lblQta = new JLabel("Quantit� [g]");
		this.lblQta.setBounds(10,40,100,20);
		this.txtQta = new JTextField();
		this.txtQta.setBounds(120,40,80,20);
		
		this.getContentPane().add(lblIngrediente);
		this.getContentPane().add(cbIngrediente);
		this.getContentPane().add(lblQta);
		this.getContentPane().add(txtQta);
		this.getContentPane().add(lblDatiNutr);
		this.getContentPane().add(btnOk);
		this.getContentPane().add(btnAnnulla);
	}
}
