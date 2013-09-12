package windows;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/*
*  Use the Java(TM) Print Service API to locate a print service which
*  can print a GIF-encoded image. A GIF image is printed according to
*  a job template specified as a set of printing attributes.
*/
public class PrintPic {
	public static void print(InputStream is,String imgType) throws PrintException, FileNotFoundException {

//		String[] si=fileName.split("\\.");
//		System.out.println(si.length);
//		String s=si[si.length-1];
		DocFlavor flavor = DocFlavor.INPUT_STREAM.JPEG;
		switch (imgType.toLowerCase()) {
		case "gif":
			flavor = DocFlavor.INPUT_STREAM.GIF;
			break;
		case "jpeg":
		case "jpg":
			flavor = DocFlavor.INPUT_STREAM.JPEG;
			break;
		case "png":
			flavor = DocFlavor.INPUT_STREAM.PNG;
			break;
		case "pdf":
			flavor = DocFlavor.INPUT_STREAM.PDF;
			break;

		default:
			break;
		}
		/* Create a set which specifies how the job is to be printed */
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		PrinterJob printer = PrinterJob.getPrinterJob();
	      if(printer.printDialog(aset)){

				DocPrintJob printJob = printer.getPrintService() .createPrintJob();
				Doc doc = new SimpleDoc(is, flavor, null);
				
				printJob.print(doc, aset);
	      }

	}

}
