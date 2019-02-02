/*
Author: Carl Baumbach
Last update: June 26, 2016

Class description:

*/

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

//RowHeaders class - creates uneditable row headers for a JTable
public class RowHeaders extends JTable implements TableModelListener{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private User user;
	private final int COLUMN_WIDTH = 40;
	private int type;

	public RowHeaders(JTable table, User user, int type)
	{
		this.user = user;
		this.table = table;
		this.type = type;
		//gets the table's selection model
		setSelectionModel(this.table.getSelectionModel());

		TableColumn column = new TableColumn();
		//two types of row headers are required for the project, one for 
		//the default frame to display the hours and one for the 
		//availability frame for the shift selection
		if(type == 1) column.setHeaderValue("Hours");
		if(type == 2) column.setHeaderValue("Shift");
		addColumn(column);
		column.setCellRenderer(new RowRenderer());

		getColumnModel().getColumn(0).setPreferredWidth(COLUMN_WIDTH);
		setPreferredScrollableViewportSize(getPreferredSize());
	}

	//getRowCount method - returns the number of rows
	@Override
	public int getRowCount()
	{
		return table.getRowCount();
	}
	
	//getValueAt - formats the row header hours properly
	@Override
	public Object getValueAt(int row, int column)
	{
		String result = null;
		if(type == 1){//hours
			if (row < 10){
				result = "0" + Integer.toString(row) + "00";
			}
			else if(row == 24){
				result = "0000";
			}
			else {
				result = Integer.toString(row) + "00";
			}
		}
		else{//shifts
			result = Integer.toString(row + 1);
		}
		return result;
	}

	//isCellEditable method - allows for editing of times
	@Override
	public boolean isCellEditable(int row, int column)
	{
		if(user.getAdmin().equals("true")){
			return true;
		}
		return false;
	}

	//RowRenderer class - displays each row nicely
	private static class RowRenderer extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = 1L;
		//constructor
		public RowRenderer()
		{
			setHorizontalAlignment(JLabel.CENTER);
		}

		//Overrides method in the DefaultTableCellRenderer class
		public Component getTableCellRendererComponent(
				JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			if (table != null)
			{
				JTableHeader tableHeader = table.getTableHeader();
				if (tableHeader != null)
				{
					setForeground(tableHeader.getForeground());
					setBackground(tableHeader.getBackground());
					setFont(tableHeader.getFont());
				}
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
}