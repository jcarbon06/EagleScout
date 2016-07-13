import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

public class SheetCombiner{
	public static void main(String[] args) {
	try{ 
	 FileInputStream file = new FileInputStream(new File("Robot Data.xls"));
	 FileInputStream dataFile = new FileInputStream(new File("Scouting Data.xls"));
	    HSSFWorkbook robotDataWorkbook = new HSSFWorkbook(file);
	    HSSFWorkbook databaseWorkbook = new HSSFWorkbook(dataFile);
	    FileOutputStream fileOut = new FileOutputStream("Scouting Data.xls");
	    HSSFSheet sheet = robotDataWorkbook.getSheetAt(0);
	    HSSFSheet databaseSheet = databaseWorkbook.getSheetAt(0);

	    Iterator<Row> rowIterator = sheet.iterator();
	   
	    while(rowIterator.hasNext()) {
	        Row row = rowIterator.next();
		    int nextRow = databaseSheet.getLastRowNum()+1;
	        //For each row, iterate through each columns
		    int cellCount = 0;
	        Iterator<Cell> cellIterator = row.cellIterator();
            Row dataRow = databaseSheet.createRow(nextRow);    
	        while(cellIterator.hasNext()) {
	            	Cell cell = cellIterator.next();
	            	Cell dataCell = dataRow.createCell(cellCount); 
	            	switch (cell.getCellType()){
	            	case Cell.CELL_TYPE_STRING:
	            		dataCell.setCellValue(cell.getStringCellValue());
	    	            System.out.println("Data Cell :"+cellCount+" Value:"+dataCell.getStringCellValue()+"\n");
	            		break;            	
	            	case Cell.CELL_TYPE_NUMERIC:
	            		dataCell.setCellValue(cell.getNumericCellValue());
	    	            System.out.println("Data Cell :"+cellCount+" Value:"+dataCell.getNumericCellValue()+"\n");
	            		break;
	            	default: 
	            		dataCell.setCellValue(cell.getStringCellValue());
	            		break;   	
	            	}
	            cellCount=cell.getColumnIndex()+1;
	        }
	    }


	    databaseWorkbook.write(fileOut);
	    fileOut.close();
	    
	    
	        }catch(FileNotFoundException e){
	        	e.printStackTrace();
	        }catch (IOException e) {
	        	e.printStackTrace();
	}
}
}
