package ui.costPanes;

import java.awt.Component;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class DateCellRenderer extends DefaultTableCellRenderer {



	SimpleDateFormat dateDf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeStampDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat timeDf = new SimpleDateFormat("HH:mm:ss");



	@Override

	public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);
		if(value==null)return c;
		if (c instanceof JLabel ) {

			JLabel label = (JLabel) c;
			label.setHorizontalAlignment(SwingConstants.RIGHT);	
			if(value instanceof Date)
				value = new java.util.Date(((Date)value).getTime());
			else if(value instanceof Timestamp)
				value = new java.util.Date(((Timestamp)value).getTime());
			else if(value instanceof Time)
				value = new java.util.Date(((Time)value).getTime());
			
			if(value instanceof java.util.Date){
				java.util.Date date = (java.util.Date)value;
				String text=String.valueOf(value);
				if(date.getTime()%24*60*60*1000==0&&date.getTime()>24*60*60*1000)
					text = dateDf.format(date);
				else if(date.getTime()<=24*60*60*1000)
					text = timeDf.format(date);
				else 
					text = timeStampDf.format(date);
				label.setText(text);
			}

		}

		return c;

	}

}
