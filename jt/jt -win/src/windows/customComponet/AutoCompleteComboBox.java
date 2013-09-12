package windows.customComponet;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class AutoCompleteComboBox<T> extends JComboBox {
	private static final Locale[] INSTALLED_LOCALES = Locale.getAvailableLocales();
	private ComboBoxModel model = null;
	private T t;

	/**
	 * * Constructor for AutoCompleteComboBox - The Default Model is a TreeSet
	 * which is alphabetically sorted and doesnt allow duplicates. * @param
	 * items
	 */
	public T getValue() {
		return t;
	}

	public AutoCompleteComboBox(LinkedList<T> items) {
		super(items.toArray());

		model = new ComboBoxModel(items);
		setModel(model);
		setEditable(true);
		setEditor(new AutoCompleteEditor(this));
		getEditor().getEditorComponent().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				t = (T) AutoCompleteComboBox.this.getSelectedItem();

			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	public AutoCompleteComboBox(TreeSet<T> items) {
		super(items.toArray());

		model = new ComboBoxModel(items);
		setModel(model);
		setEditable(true);
		setEditor(new AutoCompleteEditor(this));
		getEditor().getEditorComponent().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				t = (T) AutoCompleteComboBox.this.getSelectedItem();

			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	/* * ComboBoxModel.java */
	public class ComboBoxModel extends DefaultComboBoxModel {
		/**
		 * * The TreeSet which holds the combobox's data (ordered no duplicates)
		 */
		private TreeSet values = null;

		public ComboBoxModel(LinkedList<T> items) {
			super();
			this.values = new TreeSet();
			int i, c;
			for (i = 0, c = items.size(); i < c; i++)
				values.add(items.get(i).toString());
			Iterator it = values.iterator();
			while (it.hasNext())
				super.addElement(it.next().toString());
		}

		public ComboBoxModel(TreeSet<T> items) {
			super();
			this.values = items;
			Iterator it = values.iterator();
			while (it.hasNext())
				super.addElement(it.next().toString());
		}
	}

	public void setModel(TreeSet<T> items) {
		setModel(new ComboBoxModel(items));
	}

	/* * AutoCompleteEditor.java */
	public class AutoCompleteEditor extends BasicComboBoxEditor {
		private JTextField editor = null;

		/**
		 * @return the editor
		 */
		public JTextField getEditor() {
			return this.editor;
		}

		/**
		 * @param editor
		 *            the editor to set
		 */
		public void setEditor(JTextField editor) {
			this.editor = editor;
		}

		public AutoCompleteEditor(JComboBox combo) {
			super();
			editor = new AutoCompleteEditorComponent(combo);
		}

		/**
		 * overrides BasicComboBox's getEditorComponent to return custom
		 * TextField (AutoCompleteEditorComponent)
		 */
		public Component getEditorComponent() {
			return editor;
		}
	}

	/* * AutoCompleteEditorComponent.java */
	public class AutoCompleteEditorComponent extends JTextField {
		JComboBox combo = null;
		boolean caseSensitive = false;

		public AutoCompleteEditorComponent(JComboBox combo) {
			super();
			this.combo = combo;
		}

		@Override
		protected Document createDefaultModel() {
			return new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

					super.insertString(offs, str, a);
					if (str == null || str.length() == 0) return;
					int size = combo.getItemCount();
					String text = getText(0, getLength());
					LinkedList<T> newList2 = new LinkedList();
					LinkedList<T> newList = new LinkedList();
					for (int i = 0; i < size; i++) {
						String item = combo.getItemAt(i).toString();
						if (item.indexOf(text) > -1) {
							newList.add((T) combo.getItemAt(i));

						} else {
							newList2.add((T) combo.getItemAt(i));
						}
					}
					newList.addAll(newList2);
					combo.setModel(new ComboBoxModel(newList));
					if (!combo.isPopupVisible()) combo.setPopupVisible(true);
					return;
				}
			};
		}

	}

	public String getText() {
		return ((AutoCompleteEditor) getEditor()).getEditor().getText();
	}

	public void setText(String text) {
		((AutoCompleteEditor) getEditor()).getEditor().setText(text);
	}

}
