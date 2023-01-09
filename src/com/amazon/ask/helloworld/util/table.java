package com.amazon.ask.helloworld.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class table 
{
	public static String search(String e1)
	{
		String email = e1;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost/alexa","root","root");
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("select email from email_table where email="+email);
			while(rs.next()) 
			{	
				String s1 = rs.getString(2);
				if(s1.equals(email))
				{
					email=s1;
				}
				else
				{
					email="";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return email;
	}


}
