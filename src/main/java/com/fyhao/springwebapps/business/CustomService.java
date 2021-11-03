package com.fyhao.springwebapps.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomService {

	public static int c = 0;
	public int add(int a, int b) {
		return a + b;
	}
	public String connectdb() {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return "ok1";
	        } 
	    catch(Exception ex) {
	        System.out.println("MySQL driver couldn't be loaded!");
	        return "fail1";
	        }
		/*
	    try {
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mysql",
	                                       "root","rootpw");
	        
	        return "ok";
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	        return "fail";
	    }
	    */
	}
}
