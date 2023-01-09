package com.amazon.ask.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Hello2Handler
{
	public static void main(String args[])
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://alexadb.cdf2ngxnzgex.us-east-1.rds.amazonaws.com/alexadb","admin","admin1234");
			Statement s = c.createStatement();
			System.out.print("Done");
			s.executeUpdate("insert into demo(name) values ('kathan')");
			System.out.print("Done");
			s.close();
			c.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
