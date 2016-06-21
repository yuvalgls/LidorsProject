package tools;

import gui.ChartPanel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class excel {
	List<String> timeList = new ArrayList<String>();
	ArrayList<ArrayList<Double>> daysList = new ArrayList<ArrayList<Double>>();

	public ChartPanel buildChart() {
		String[] timeArray = new String[timeList.size()];
		for (int a = 0; a < timeList.size(); a++) {
			timeArray[a] = timeList.get(a);
		}
		double[] daysArray = new double[daysList.get(0).size()];
		for (int a = (daysList.get(0).size() - 1); a > 0; a--) {
			daysArray[a] = daysList.get(0).get(a);
		}
		return new ChartPanel(daysArray, timeArray,
				"נתוני צריכה/ייצור רציפים רבע שעתיים");
	}

	public void read(String inputFile) {
		int startIndex = 1;
		int endIndex = 98;
		ArrayList<Double> dataList = new ArrayList<Double>();

		try {
			InputStream ExcelFileToRead = new FileInputStream(inputFile);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = null;
			XSSFCell cell;

			// read the time spaces lines
			try {
				for (int columnIndex = startIndex; columnIndex < endIndex; columnIndex++) {
					try {
						cell = sheet.getRow(20).getCell(columnIndex);
						timeList.add(String.valueOf(cell.getDateCellValue())
								.substring(11, 16));
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// read the days
			try {
				for (int rowIndex = 22; rowIndex < 49; rowIndex++) {
					row = sheet.getRow(rowIndex);
					Iterator<Cell> cells = row.cellIterator();
					dataList.clear();
					for (int columnIndex = startIndex; columnIndex < endIndex; columnIndex++) {
						cell = (XSSFCell) cells.next();
						if (cell.getColumnIndex() != 0) {
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_BLANK:
								System.err.println("ERROR: CELL_TYPE_BLANK");
								break;
							case XSSFCell.CELL_TYPE_BOOLEAN:
								System.err.println("ERROR: CELL_TYPE_BOOLEAN");
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								System.err.println("ERROR: CELL_TYPE_ERROR");
								break;
							case XSSFCell.CELL_TYPE_FORMULA:
								System.err.println("ERROR: CELL_TYPE_FORMULA");
								break;
							case XSSFCell.CELL_TYPE_STRING:
								dataList.add(Double.valueOf(cell
										.getStringCellValue()));
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								dataList.add(cell.getNumericCellValue());
								break;
							default:
								System.out.println("none");
							}
						}
					}
					daysList.add(dataList);
				}
			} catch (Exception e) {
			}
			wb.close();
		} catch (Exception e) {
		}
	}
}
