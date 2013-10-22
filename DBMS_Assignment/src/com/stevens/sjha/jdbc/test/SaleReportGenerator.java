package com.stevens.sjha.jdbc.test;
//package Test;
/*This is a simple program using Embedded SQL to read all
 *of the rows from the table "sales" and output to the console.
 * Then, using Java the maximum sales, minimum sales and average sales 
 *  has been calculated for the unique combination of customer name and 
 *  product. In the report the respective state and  date of the sale 
 *  also has been shown.
 */

/* Steps to run this program : 
*  Log in to PostgreSQL server (ssh [username]@postgres.cs.stevens.edu).
*  In the program, modify username, database and password to
*  [pgupta2], [pgupta2], [Priyabhi0302.] 
*/
import java.sql.*;
import java.util.*;

public class SaleReportGenerator 
{
	public static void main(String args[]) throws SQLException 
	{
		String usr = "sjha";
		String pwd = "Mishra10375164";
		String url = "jdbc:postgresql://155.246.89.29:5432/sjha";
		/*
		String usr = "postgres";
		String pwd = "123";
		String url = "jdbc:postgresql://155.246.89.29:5432/postgres";*/
		Connection conn = null;
		try 
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Success loading Driver!");
			conn = DriverManager.getConnection(url,usr, pwd);
			System.out.println("Success connecting server!");
			
		}

		catch (Exception e) 
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
			System.exit(-1);
		}

		Statement stmt = conn.createStatement();
		//fetching the data from the database and storing it in the resultSet
		ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
		//creating an array of class to store the result of the data computed
		Map<String,List<Sale>> salesMap = new HashMap<String,List<Sale>>();
		while (rs.next())
		{
			int quantity = rs.getInt("quantity");
			String customer = rs.getString("cuctomer").toLowerCase();
			String product = rs.getString("product").toLowerCase();
			int day = rs.getInt("day");
			int month = rs.getInt("month");
			int year =  rs.getInt("year");
			String state = rs.getString("state").toLowerCase();
			
			Sale sale = new Sale();
			sale.setCustomer(customer);
			sale.setDay(day);
			sale.setMonth(month);
			sale.setProduct(product);
			sale.setQuantity(quantity);
			sale.setState(state);
			sale.setYear(year);
			String custProdCombo = customer+":"+product;
			List<Sale> sales = salesMap.get(custProdCombo);
			if(sales == null)
			{
				sales = new ArrayList<Sale>();
				salesMap.put(custProdCombo, sales);
			}
			sales.add(sale);
			
		}
		System.out.println("Totla number of Sale Map : "+salesMap.size());
		printAverageSales(salesMap);
	}

	private static void printAverageSales(Map<String, List<Sale>> salesMap) {
		
		Map<String,AverageSale> averageSaleMap = new HashMap<String, AverageSale>();
		for(String custProdCombo: salesMap.keySet())
		{
			List<Sale> sales = salesMap.get(custProdCombo);
			double nyAvg = 0;
			int nyCount = 0;
			double njAvg = 0;
			int njCount = 0;
			double ctAvg = 0;
			int ctCount = 0;
			for(Sale sale: sales)
			{
				if(sale.getState().equalsIgnoreCase("NY"))
				{
					nyAvg = (nyAvg*nyCount + sale.getQuantity())/(nyCount+1);
					nyCount++;
					AverageSale avgSale = averageSaleMap.get(custProdCombo);
					if(avgSale == null)
					{
						avgSale = new AverageSale();
						averageSaleMap.put(custProdCombo, avgSale);
					}
					avgSale.setNyAvg(nyAvg);
				}
				else if(sale.getState().equalsIgnoreCase("NJ"))
				{
					if(sale.getYear() >= 1990 && sale.getYear() <= 1995)
					{
						njAvg = (njAvg*njCount + sale.getQuantity())/(njCount+1);
						njCount++;
						AverageSale avgSale = averageSaleMap.get(custProdCombo);
						if(avgSale == null)
						{
							avgSale = new AverageSale();
							averageSaleMap.put(custProdCombo, avgSale);
						}
						avgSale.setNjAvg(njAvg);
					}
				}
				else if(sale.getState().equalsIgnoreCase("CT"))
				{
					if(sale.getYear() >= 1990 && sale.getYear() <= 1995)
					{
						ctAvg = (ctAvg*ctCount + sale.getQuantity())/(ctCount+1);
						ctCount++;
						AverageSale avgSale = averageSaleMap.get(custProdCombo);
						if(avgSale == null)
						{
							avgSale = new AverageSale();
							averageSaleMap.put(custProdCombo, avgSale);
						}
						avgSale.setCtAvg(ctAvg);
					}
				}
			}
			
		}
		System.out.format("%-10s%-10s%10s%10s%10s%n", "Customer","Product","NY Avg","NJ Avg","CT Avg");
		for(String customerProdCombo : averageSaleMap.keySet())
		{
			AverageSale sale = averageSaleMap.get(customerProdCombo);
			String[] fields = customerProdCombo.split(":");
			System.out.format("%-10s%-10s%10.0f%10.0f%10.0f%n", fields[0],fields[1],sale.getNyAvg(),sale.getNjAvg(),sale.getCtAvg());
		}
		
	}
	
	private static class AverageSale
	{
		String customerProdCombo;
		double nyAvg;
		double njAvg;
		double ctAvg;
		/**
		 * @return the customerProdCombo
		 */
		public String getCustomerProdCombo() {
			return customerProdCombo;
		}
		/**
		 * @param customerProdCombo the customerProdCombo to set
		 */
		public void setCustomerProdCombo(String customerProdCombo) {
			this.customerProdCombo = customerProdCombo;
		}
		/**
		 * @return the nyAvg
		 */
		public double getNyAvg() {
			return nyAvg;
		}
		/**
		 * @param nyAvg the nyAvg to set
		 */
		public void setNyAvg(double nyAvg) {
			this.nyAvg = nyAvg;
		}
		/**
		 * @return the njAvg
		 */
		public double getNjAvg() {
			return njAvg;
		}
		/**
		 * @param njAvg the njAvg to set
		 */
		public void setNjAvg(double njAvg) {
			this.njAvg = njAvg;
		}
		/**
		 * @return the ctAvg
		 */
		public double getCtAvg() {
			return ctAvg;
		}
		/**
		 * @param ctAvg the ctAvg to set
		 */
		public void setCtAvg(double ctAvg) {
			this.ctAvg = ctAvg;
		}
		
	}
}
