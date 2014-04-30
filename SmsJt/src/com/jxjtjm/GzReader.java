package com.jxjtjm;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jxjtjm.beans.LoginUser;
import com.jxjtjm.beans.Sms;

public class GzReader {
	
	private ArrayList<Sms> smses=new ArrayList<Sms>();
	public  void readSmsFromXls(InputStream in) throws IOException {
//		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(in);
		HSSFSheet sheet = wb.getSheetAt(0);
		Row row=sheet.getRow(1);
		int telNoColNo = 0,smsColNo=0,nameColNo=0;
		for(int colNo=0 ;colNo< row.getLastCellNum();colNo++){
			try{
				if(row.getCell(colNo).getStringCellValue().equals("手机号"))
					telNoColNo=colNo;
				if(row.getCell(colNo).getStringCellValue().equals("姓名"))
					nameColNo=colNo;
				if(row.getCell(colNo).getStringCellValue().equals("手机信息"))
					smsColNo=colNo;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		smses.clear();
		for(int rowNo=2;rowNo<sheet.getLastRowNum();rowNo++){
			try{
				row=sheet.getRow(rowNo);
				Cell cell = row.getCell(telNoColNo);
				String telno=null;
				if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
				  telno =new DecimalFormat("#").format(cell.getNumericCellValue());
				else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
					telno =cell.getStringCellValue();
				else continue;
				String smsString =row.getCell(smsColNo).getStringCellValue();
				String name =row.getCell(nameColNo).getStringCellValue();
				if(StringUtils.isNotBlank(telno)&&StringUtils.isNotBlank(smsString)){
					Sms sms=new Sms();
					sms.setContent(smsString);
					sms.setTelNo(telno);
					sms.setReceiveName(name);
					smses.add(sms);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		in.close();
	}
	
	public  void readSmsFromXlsx(InputStream in) throws IOException {
//		POIFSFileSystem fs = new POIFSFileSystem(in);
		XSSFWorkbook wb = new XSSFWorkbook(in);
		XSSFSheet sheet = wb.getSheetAt(0);
		Row row=sheet.getRow(1);
		int telNoColNo = 0,smsColNo=0,nameColNo=0;
		for(int colNo=0 ;colNo< row.getLastCellNum();colNo++){
			try{
				if(row.getCell(colNo).getStringCellValue().equals("手机号"))
					telNoColNo=colNo;
				if(row.getCell(colNo).getStringCellValue().equals("姓名"))
					nameColNo=colNo;
				if(row.getCell(colNo).getStringCellValue().equals("手机信息"))
					smsColNo=colNo;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		for(int rowNo=2;rowNo<sheet.getLastRowNum();rowNo++){
			try{

				row=sheet.getRow(rowNo);
				Cell cell = row.getCell(telNoColNo);
				String telno=null;
				if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
				  telno =new DecimalFormat("#").format(cell.getNumericCellValue());
				else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
					telno =cell.getStringCellValue();
				else continue;
				String smsString =row.getCell(smsColNo).getStringCellValue();
				String name =row.getCell(nameColNo).getStringCellValue();
				if(StringUtils.isNotBlank(telno)&&StringUtils.isNotBlank(smsString)){
					Sms sms=new Sms();
					sms.setContent(smsString);
					sms.setTelNo(telno);
					sms.setReceiveName(name);
					smses.add(sms);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		in.close();
	}

	public ArrayList<Sms> getSmses() {
		return smses;
	}

	public void setSmses(ArrayList<Sms> smses) {
		this.smses = smses;
	}
	public void sendAll(LoginUser sender) {
		for(Sms sms:smses){
			sms.setSender(sender);
			sms.send();
		}
	}
}
