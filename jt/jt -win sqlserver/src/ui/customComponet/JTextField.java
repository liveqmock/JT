package ui.customComponet;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;


public class JTextField  extends javax.swing.JTextField{
	public static KeyListener enterKeyListener=new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				((JComponent) e.getSource()).transferFocus();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自动生成的方法存根

		}
	};
	public JTextField() {
		super();
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(getText()!=null){
					setSelectionStart(0);
					setSelectionEnd(getText().length());
				}
				
			}
		});
		addKeyListener(enterKeyListener );
	}
	
}
