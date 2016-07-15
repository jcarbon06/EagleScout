import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

public class SheetCombiner {
	public static void main(String[] args) {
		try {
			FileInputStream robotDataFile = new FileInputStream(new File("Robot Data.xls"));
			FileInputStream masterDataFile = new FileInputStream(new File("Scouting Data Master.xls"));
			FileOutputStream masterDataBackup = new FileOutputStream(new File("Scouting Data Master Backup.xls"));
			HSSFWorkbook robotDataWorkbook = new HSSFWorkbook(robotDataFile);
			HSSFWorkbook databaseWorkbook = new HSSFWorkbook(masterDataFile);
			databaseWorkbook.write(masterDataBackup);
			masterDataBackup.close();
			FileOutputStream scoutingMasterOutput = new FileOutputStream("Scouting Data Master.xls");
			HSSFSheet sheet = robotDataWorkbook.getSheetAt(0);
			HSSFSheet databaseSheet = databaseWorkbook.getSheetAt(0);
			double matchNumber = 0;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				int rowNumber = 0;

				Row row = rowIterator.next();
				int nextRow = databaseSheet.getLastRowNum() + 1;
				switch (rowNumber) {

				}
				// For each row, iterate through each columns
				int cellCount = 0;
				Iterator<Cell> cellIterator = row.cellIterator();
				Row dataRow = databaseSheet.createRow(nextRow);

				while (cellIterator.hasNext()) {
					int columnNumber = 0;
					Cell cell = cellIterator.next();
					Cell dataCell = dataRow.createCell(cellCount);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						dataCell.setCellValue(cell.getStringCellValue());
						System.out
						.println("Data Cell :" + cellCount + " Value:" + dataCell.getStringCellValue() + "\n");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						dataCell.setCellValue(cell.getNumericCellValue());
						System.out
						.println("Data Cell :" + cellCount + " Value:" + dataCell.getNumericCellValue() + "\n");
						break;
					default:
						dataCell.setCellValue(cell.getStringCellValue());
						break;
					}
					if (rowNumber == 0 && columnNumber == 0) {
						matchNumber = cell.getNumericCellValue();
					}
					cellCount = cell.getColumnIndex() + 1;
				}
				rowNumber++;
			}

			File folder = new File("./Raw Data");
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File movedRobotData = new File("./Raw Data/Robot Data from match " + (int) matchNumber + ".xls");
			if (!movedRobotData.exists()) {
				movedRobotData.createNewFile();
			}
			robotDataWorkbook.close();
			databaseWorkbook.close();
			FileOutputStream robotDataMover = new FileOutputStream(movedRobotData);
			robotDataWorkbook.write(robotDataMover);
			databaseWorkbook.write(scoutingMasterOutput);
			scoutingMasterOutput.close();
			robotDataFile.close();
			masterDataFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
