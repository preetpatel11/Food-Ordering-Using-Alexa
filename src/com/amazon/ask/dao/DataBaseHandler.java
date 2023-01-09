package com.amazon.ask.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler {
	public Statement connection() {
		try {
			System.out.println("In connection");
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection(
					"jdbc:mysql://foodorder.cmqz4crlnbeh.us-east-1.rds.amazonaws.com/foodorder", "admin", "Mahy4867");
			Statement s = c.createStatement();
			return s;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public List areaRestaurantSearch(String finalArea) {

		List ls = new ArrayList();
		int id = 0;
		String name = "";
		try {
			System.out.println("Above connection object");
			Statement s = connection();
			ResultSet area = s.executeQuery("select area_Id from area_table where area_Name='" + finalArea + "'");

			System.out.println("After area data....." + area);

			while (area.next()) {
				id = Integer.parseInt(area.getString(1));
			}

			System.out.println("area id....." + id);

			ResultSet restaurantNames = s
					.executeQuery("select restaurant_Name from restaurant_table where areavo_area_Id='" + id + "'");

			while (restaurantNames.next()) {
				name = restaurantNames.getString(1);
				ls.add(name);
			}
			System.out.println("Name>>>>>>>>>>>>" + name);
			return ls;

		} catch (Exception e) {
			System.out.println("Error.....");
			System.out.println(e);
		}
		return null;
	}

	public List restaurantData(String finalRestaurant) {
		List ls = new ArrayList();
		try{
			System.out.println("Above connection object");
			Statement s = connection();
			ResultSet restaurantNames = s.executeQuery("select * from restaurant_table where restaurant_Name='" + finalRestaurant + "'");
			while (restaurantNames.next()) {
				String name =  restaurantNames.getString(1);
				ls.add(name);
				}
			System.out.println("Product>>>>>>>>>>>>" + ls);
			return ls;
		}
		catch(Exception e)
		{
			System.out.println("Error.....");
			System.out.println(e);
		}
		return null;
}}
