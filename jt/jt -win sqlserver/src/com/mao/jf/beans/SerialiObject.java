package com.mao.jf.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerialiObject { 
	public static void save(Object object, File file) throws Exception {
		if (file.createNewFile() || file.isFile()) {
			FileOutputStream fs = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(object);
			os.close();
			fs.close();
		} else {
			throw new Exception("����һ����Ч���ļ���");
		}
	}

	public static Object loadFile(File file) {
		Object object = null;
		if (file.exists())
			try {
				FileInputStream fs = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fs);
				object = in.readObject();
				in.close();
				fs.close();
			} catch (Exception|ExceptionInInitializerError e) {

			}
		return object;
	}
}
