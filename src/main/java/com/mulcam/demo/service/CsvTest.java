package com.mulcam.demo.service;

import java.util.List;

public class CsvTest {

	public static void main(String[] args) {
		CsvUtil cu = new CsvUtilImpl();
		List<List<String>> list = cu.readCsv("/tmp/sample.csv", ",", 1);
		for (List<String> row: list) {
			for (String s: row)
				System.out.print(s + " ");
			System.out.println();
		}
		
		cu.writeCsv("/tmp/sample3.csv", list);
	}

}