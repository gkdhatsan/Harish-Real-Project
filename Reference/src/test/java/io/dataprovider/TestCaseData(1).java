package io.dataprovider;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import io.testbase.TestBase;

public class TestCaseData extends TestBase {
	@DataProvider(name="mytestdata", parallel=true)
	public Object[][] getinput(Method m, ITestContext testContext) throws Throwable {
	    if(System.getProperty("testmodule").equalsIgnoreCase(testContext.getName()) || System.getProperty("testmodule").equalsIgnoreCase("DEFAULT")) {
	    	// create file for datasheet
			FileInputStream tsdata;
			tsdata = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator")
					+ "Excelinput" + System.getProperty("file.separator") + "MyTestData.xlsx");

			// create workbook and module worksheet
			XSSFWorkbook wb = new XSSFWorkbook(tsdata);
	    	Sheet sheet = wb.getSheet(testContext.getName());
	    	
	    	//get rows from module worksheet
			Iterable<Row> rows = sheet::rowIterator;
			List<Map<String, String>> results = new ArrayList<>();
			
			//initialize values
			boolean validtestphase = false;
			
			//section for default module
			if (System.getProperty("testmodule").equalsIgnoreCase("DEFAULT")) {
				//create object for test base sheet
				Sheet sheet1 = wb.getSheet("TestBase");
				Iterable<Row> rows1 = sheet1::rowIterator;
				
				//initialize values
				boolean header1 = true;
				int testphaseindex = -1;
				int testscenarioindex = -1;
				
				//loop for each row
				for (Row row : rows1) {
					List<String> values = getValuesInEachRow(row);
					
					//get header column index
					if (header1) {
						for (int headindex = 0; headindex < values.size(); headindex++) {
							if (values.get(headindex).equalsIgnoreCase(System.getProperty("testphase"))) {
								testphaseindex = headindex;
							}
							if (values.get(headindex).equalsIgnoreCase("TestScenario")) {
								testscenarioindex = headindex;
							}
						}
						header1 = false;
						continue;
					}

					//check test module is YES test name is matching
					if (values.get(testphaseindex).contains("YES") && values.get(testscenarioindex).contains(testContext.getName())) {
						validtestphase = true;
						break;
					}
				}
			}else {
				validtestphase = true;
			}
			
			//module sheet check YES
			if (validtestphase) {
				//initialize variables
				int testphaseindex = -1;
				boolean header = true;
				List<String> keys = null;
				
				//loop for module sheet rows
				for (Row row : rows) {
					List<String> values = getValuesInEachRow(row);
					
					//get header column names
					if (header) {
						header = false;
						keys = values;
						for (int headindex = 0; headindex < values.size(); headindex++) {
							if (values.get(headindex).equalsIgnoreCase(System.getProperty("testphase"))) {
								testphaseindex = headindex;
								break;
							}
						}
						continue;
					}
					
					//get values and add it in hashmap
					if (values.get(0).contains(m.getName()) && values.get(testphaseindex).equalsIgnoreCase("YES")) {
						results.add(transform(keys, values));
					}
				}
			} else {
//				System.out.println("skipping test scenario:" + sheetname);
			}
			
			//workbook close
			wb.close();
			
			//return data object
			Object data[][] = asTwoDimensionalArray(results);
	    	return data;
	    }else {
	    	//return empty object
	    	Object[][] results = new Object[0][1];
	    	return results;
	    }
	}
	
	private Object[][] asTwoDimensionalArray(List<Map<String, String>> interimResults) {
		Object[][] results = new Object[interimResults.size()][1];
		int index = 0;
		for (Map<String, String> interimResult : interimResults) {
			results[index++] = new Object[] { interimResult };
		}
		return results;
	}

	private Map<String, String> transform(List<String> names, List<String> values) {
		Map<String, String> results = new HashMap<>();
		for (int i = 0; i < names.size(); i++) {
			String key = names.get(i);
			String value = "";
			try {
				value = values.get(i);
			} catch (Exception e) {
			}
			results.put(key, value);
		}
//		System.out.println(results);
		return results;
	}

	private List<String> getValuesInEachRow(Row row) {
		// declare data formatter
		DataFormatter format = new DataFormatter();
		List<String> data = new ArrayList<>();
		Iterable<Cell> columns = row::cellIterator;
		for (Cell column : columns) {
			data.add(format.formatCellValue(column));
		}
		return data;
	}
}
