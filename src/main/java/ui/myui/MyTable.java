package ui.myui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MyTable extends JTable{

	private static final long serialVersionUID = 1L;
	
	public MyTable(String[] headers){
			
		Object[][] cellData = null;
		
		DefaultTableModel model = new DefaultTableModel(cellData, headers) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { //这里让表不可修改
				return false;
			}
		};
		
		this.setModel(model);
		this.setRowSelectionAllowed(true);	
	    JTableHeader tableH = this.getTableHeader();	 //获得表头 
	    tableH.setBackground(new Color(0, 1, 1, 0.65f));  //设置表头的背景色
	    tableH.setReorderingAllowed(false); 
	}
}
