package ui.customComponet;

import java.io.File;
import java.io.Serializable;

import javax.swing.filechooser.FileFilter;

public class MyFileFilter extends FileFilter implements Serializable {

	@Override
	public boolean accept(File f) {
		return (f.getName().toLowerCase().endsWith(".xls") || f.isDirectory());
	}

	@Override
	public String getDescription() {
		// TODO �Զ����ɵķ������
		return "Microsoft Excel�ļ�";
	}
}