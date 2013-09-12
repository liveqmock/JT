package windows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jcifs.smb.SmbFileOutputStream;

public class Dddd {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException,
	IOException {
		jcifs.Config.setProperty("jcifs.netbios.wins", "192.168.1.102");
		jcifs.Config.setProperty("jcifs.smb.client.username", "test");
		jcifs.Config.setProperty("jcifs.smb.client.password", "test");
		Copy(new File("c:/aaa.xls"),"smb://192.168.1.102/txt/12333.txt");
		
	}

	public static void Copy(File localFile, String smbFile) throws IOException {
	
			if (localFile.exists()) {
				int byteread = 0;
				InputStream inStream = new FileInputStream(localFile);
				SmbFileOutputStream fs = new SmbFileOutputStream("smb://192.168.1.103/pics/"+smbFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {

					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
	}
	
}
