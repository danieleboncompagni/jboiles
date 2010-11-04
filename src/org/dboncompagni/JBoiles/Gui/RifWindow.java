package org.dboncompagni.JBoiles.Gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class RifWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5376075425347674424L;
	
	private ImageIcon grafico;
	private JLabel lblGrafico;
	private JTextArea txtDig;
	private JTextArea txtLeg;
	
	public RifWindow(){
		this.setSize(800,600);
		this.setTitle("JBoiles - Riferimenti");
		this.setLayout(null);
		this.initComponents();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void initComponents(){
		grafico = new ImageIcon("support/Grafico.gif");
		lblGrafico = new JLabel(grafico);
		lblGrafico.setBounds(10,10,500,550);
		lblGrafico.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		txtDig = new JTextArea("Digeribilità : \r\n\r\n  1 - SCARSA\r\n  2 - MEDIA\r\n  3 - BUONA\r\n  4 - MOLTA\r\n  5 - OTTIMA");
		txtDig.setBounds(520,10,260,250);
		txtDig.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		txtDig.setFont(new Font("Century Gothic",Font.PLAIN,18));
		txtDig.setEditable(false);
		
		txtLeg = new JTextArea("Indice legante : \r\n\r\n    -1  SLEGANTE\r\n    0 - NEUTRA\r\n    1 - LEGANTE\r\n    2 - MEDIA\r\n    3 - BUONA\r\n    4 - MOLTA\r\n    5 - OTTIMA");
		txtLeg.setBounds(520,280,260,280);
		txtLeg.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		txtLeg.setFont(new Font("Century Gothic",Font.PLAIN,18));
		txtLeg.setEditable(false);
		
		this.getContentPane().add(lblGrafico);
		this.getContentPane().add(txtDig);
		this.getContentPane().add(txtLeg);
	}

}
