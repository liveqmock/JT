package ui.panels;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.VerticalLayout;

public class JCheckBoxPanel extends JScrollPane {

	ArrayList<JCheckBox> checkBoxs=new ArrayList<>();
	public JCheckBoxPanel(String[] values) {
		JPanel panel=new JPanel();
		panel.setLayout(new VerticalLayout());
		for(String value:values){
			JCheckBox checkBox=new JCheckBox(value);
			checkBox.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					firePropertyChange();
					
				}

			});
			panel.add(checkBox);
			checkBoxs.add(checkBox);
			setViewportView(panel);
		}
		
	}

	private void firePropertyChange() {
		firePropertyChange("value", null, getValue());
		
	}
	public void setValue(Collection<String> values) {
		for(JCheckBox checkBox:checkBoxs){
			checkBox.setSelected(values.contains(checkBox.getText()));
		}
	}

	public Collection<String> getValue( ) {
		ArrayList<String> values=new ArrayList<>();
		for(JCheckBox	checkBox:checkBoxs){
			if(checkBox.isSelected())
				values.add(checkBox.getText());
		}
		return values;
	}
}
