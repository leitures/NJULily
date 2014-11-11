package ui.myui;

import java.awt.Color;

import javax.swing.JComboBox;

public class MyComboBox extends JComboBox<String>{

	private static final long serialVersionUID = 1L;

	public MyComboBox(int x, int y, int width, int height, String[] str){
		
		this.setBounds(x, y, width, height);
		for(int i = 0; i < str.length; i++)
			this.addItem(str[i]);
		this.setBackground(Color.white);
		this.setVisible(true);
		
	}
}
