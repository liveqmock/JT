package ui.customComponet;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;

/* ImageFileView.java is used by FileChooserDemo2.java. */
public class MyFileView extends FileView {
	public String getName(File f) {
		return null; // let the L&F FileView figure this out
	}

	public String getDescription(File f) {
		if (f.getName().toLowerCase().endsWith(".xls"))
			return "Microsoft ExcelÎÄ¼þ(*.xls)";
		else {
			return null;
		}
	}

	public Boolean isTraversable(File f) {
		return null;

	}

	public String getTypeDescription(File f) {
		return null;
		// return
		// FileSystemView.getFileSystemView().getSystemTypeDescription(f);
	}

	public Icon getIcon(File f) {
		return FileSystemView.getFileSystemView().getSystemIcon(f);
	}
}