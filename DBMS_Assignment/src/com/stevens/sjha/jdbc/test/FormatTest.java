package com.stevens.sjha.jdbc.test;

import org.omg.CORBA.NVList;

public class FormatTest {

	public static void main(String[] args) {
		/*Customer	Product	NY Avg	NJ Avg	CT Avg
		knuth	yogurt	3575.0	3705.0	0.0
		bloom	milk	3306.3333333333335	3306.3333333333335	0.0*/
		String cust1 = "knuth";
		String prod1 = "yogurt";
		
		String cust2 = "bloom";
		String prod2 = "milk";
		double nyAvg1 = 3575.0;
		double njAvg1 = 3705.0;
		double ctAvg1 = 0;
		
		double nyAvg2 = 3575.0;
		double njAvg2 = 3306.3333333333335;
		double ctAvg2 = 3306.3333333333335;
		
		//System.out.println("Customer  Product   NY Avg    NJ Avg    CT Avg    ");
		System.out.format("%-10s%-10s%10s%10s%10s%n", "Customer","Product","NY Avg","NJ Avg","CT Avg");
		System.out.format("%-10s%-10s%10.0f%10.0f%10.0f%n", cust1,prod1,nyAvg1,njAvg1,ctAvg1);
		System.out.format("%-10s%-10s%10.0f%10.0f%10.0f%n", cust2,prod2,nyAvg2,njAvg2,ctAvg2);

	}

}
