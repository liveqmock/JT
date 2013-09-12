package windows.customComponet;

import java.io.File;
import java.io.Serializable;

import javax.swing.filechooser.FileFilter;

public class MyFileFilter extends FileFilter implements Serializable {

	public boolean accept(File f) {
		return (f.getName().toLowerCase().endsWith(".xls") || f.isDirectory());
	}

	@Override
	public String getDescription() {
		// TODO 自动生成的方法存根
		return "Microsoft Excel文件";
	}
}