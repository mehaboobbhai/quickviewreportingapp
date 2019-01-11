package com.teamsankya.quickviewreporting.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.teamsankya.quickviewreportingapp.dao.Productdetailsdao;
import com.teamsankya.quickviewreportingapp.dto.ProductBean;

public class Excelreport {

	private static String columns[] = { "ProductId", "ProductName", "ProductPrice" };

	public static Productdetailsdao productdetailsdao = new Productdetailsdao();

	public static void main(String[] args) throws IOException {
		List<ProductBean> productbean = productdetailsdao.getproductdetails();

		Workbook workbook = new XSSFWorkbook();
		CreationHelper creationHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("ProductExcel");
		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		// Create a Row
		Row headerRow = sheet.createRow(0);
		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
		// Create Other rows and cells with productBeans data
		int rowNum = 1;

		for (ProductBean productBean2 : productbean) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(productBean2.getProductId());
			row.createCell(1).setCellValue(productBean2.getProductName());
			row.createCell(2).setCellValue(productBean2.getProductPrice());
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xlsx");
		workbook.write(fileOut);
		FileInputStream chart_file_input = new FileInputStream(new File("poi-generated-file.xlsx"));
		XSSFWorkbook my_workbook = new XSSFWorkbook(chart_file_input);
		XSSFSheet my_sheet = my_workbook.getSheetAt(0);
		DefaultPieDataset my_pie_chart_data = new DefaultPieDataset();
		Iterator<Row> rowIterator = my_sheet.iterator();
		String chart_label = "a";
		Number chart_data = 0;
		while (rowIterator.hasNext()) {
			// Read Rows from Excel document
			Row row = rowIterator.next();
			// Read cells in Rows and get chart data
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					chart_data = cell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_STRING:
					chart_label = cell.getStringCellValue();
					break;
				}
			}
			/* Add data to the data set */
			my_pie_chart_data.setValue(chart_label, chart_data);
		}
		/* Create a logical chart object with the chart data collected */
		JFreeChart myPieChart = ChartFactory.createPieChart("Product details sold out", my_pie_chart_data, true, true,
				false);
		int width = 580; /* Width of the chart */
		int height = 480; /* Height of the chart */
		float quality = 1; /* Quality factor */
		
		ByteArrayOutputStream chart_out = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsJPEG(chart_out, quality, myPieChart, width, height);
		
		int my_picture_id = my_workbook.addPicture(chart_out.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
		chart_out.close();
	
		XSSFDrawing drawing = my_sheet.createDrawingPatriarch();
		/* Create an anchor point */
		ClientAnchor my_anchor = new XSSFClientAnchor();
		my_anchor.setCol1(1);
		my_anchor.setRow1(8);
		XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
		/* Call resize method, which resizes the image */
		my_picture.resize();
		chart_file_input.close();
		FileOutputStream out = new FileOutputStream(new File("poi-generated-file.xlsx"));
		my_workbook.write(out);
		out.close();
		fileOut.close();
		workbook.close();

	}
}
