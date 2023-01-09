package com.amazon.ask.helloworld.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BaseMethods
{

	public static void sendMail(String receiver,String data)
	{
		final String from = "noreply.bankofai@gmail.com";
		final String username = "";
		final String password = "BankAI@alexa";

		String host = "smtp.gmail.com";

		System.out.println("1");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mails.smtp.ssl.trust","smtp.gmail.com");

		System.out.println("2");

		
		Session session = Session.getInstance(props, new javax.mail.Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(from, password);
				}		});
		
		System.out.println("3");

		try 
		{
			System.out.println("4");
			
			String o = "Your one time password is given below.\nPlz do not share with any one.\nOTP=";
			String otp = o.concat(data);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(receiver));
			message.setSubject("Approve ");
			message.setText(otp);

			System.out.println("5");
			Transport.send(message);

			System.out.println("Sent mail successfully....");

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}
	
	public static String generatePassword() 
	{
		int n = 8;
        String AlphaNumericString ="0123456789" + "abcdefghijklmnopqrstuvxyz"; 
  
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) 
        { 
            int index = (int)(AlphaNumericString.length() * Math.random()); 
            sb.append(AlphaNumericString.charAt(index)); 
        } 
        return sb.toString(); 
	}

}
