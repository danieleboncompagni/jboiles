package org.dboncompagni.JBoiles.Gui;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AboutWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8273409547107002691L;

	public AboutWindow(){
		this.setSize(400,200);
		this.setTitle("JBOILES");
		this.setIconImage(new ImageIcon("support/carpa.gif").getImage());
		JTextArea txtAbout = new JTextArea("JBoiles\r\n Autore : Daniele Boncompagni\r\n email : daniele.boncompagni@gmail.com\r\n Rilasciato sotto licenza GPL v.2");
		txtAbout.setEditable(false);
		txtAbout.setFont(new Font("Monospaced",Font.PLAIN,16));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().add(txtAbout);
		this.setVisible(true);
	}
	
}
