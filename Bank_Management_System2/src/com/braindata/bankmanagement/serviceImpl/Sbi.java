package com.braindata.bankmanagement.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.braindata.bankmanagement.database.DatabaseConnectivity;
import com.braindata.bankmanagement.model.Account;
import com.braindata.bankmanagement.service.Rbi;

public class Sbi implements Rbi {

	Scanner sc = new Scanner(System.in);
	Account ac = new Account();
	DatabaseConnectivity DC = new DatabaseConnectivity();

	@Override
	public void createAccount() {
		System.out.println("------------Create New Account-----------------");

		/*-------- Account number ----------*/
		System.out.println("Enter 12 digits Account no:");
		long accno = sc.nextLong();
		long r = accno;
		int count = 0;
		while (r != 0) {
			r = r / 10;
			count++;
		}
		if (count == 12) {
			ac.setAccNo(accno);
		} else {
			System.err.println("!! Please Enter correct accno !!");
			createAccount();
		}

		/*---------------- Name ---------------*/
		System.out.println("Enter your full name:");
		String name = sc.next() + sc.nextLine();
//		if (name.contains("$") || name.contains("@")) {
//			System.err.println("!! Do not use Special Character !!");
//			createAccount();
//		} else {
//			ac.setName(name);
//		}

		
		/*------------By using Patter and Matcher Class----------------*/
//		String regex = "[A-Za-z]+"; 
		String regex = "^[a-zA-Z\\s]+$"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches()) {
			ac.setName(name);
		} else {
			System.err.println("!! Do not use Special Character !!");
			createAccount();
		}

		/*------------ Mobile Number ---------------*/
		System.out.println("Enter your Mobile no:");
		String mobno = sc.next();
		if (mobno.length() == 10) {
			ac.setMobNo(mobno);
		} else {
			System.err.println("!! Please Enter correct mobile no !!");
			createAccount();
		}

		/*------------- Addhar Number ------------*/
		System.out.println("Enter your Aadhar no:");
		String adharno = sc.next();
		if (adharno.length() == 12) {
			ac.setAdharNo(adharno);
		} else {
			System.err.println("!! Enter correct Adharno !!");
			createAccount();
		}

		/*-------------- Gender ---------------*/
		System.out.println("Enter your Gender (Male) or (Female):");
		String gender = sc.next();
		if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("F")
				|| gender.equalsIgnoreCase("M")) {
			ac.setGender(gender);
		} else {
			System.err.println("!! Enter Correct Gender !!");
			createAccount();
		}

		/*-------------- Age ------------------*/
		System.out.println("Enter your Age:");
		int age = sc.nextInt();
		if (age >= 15) {
			ac.setAge(age);
		} else {
			System.err.println("!! Not applicable to create bank acc!!");
			createAccount();
		}

		/*------------ Balance -------------*/
		System.out.println("Enter your Balance :");
		double balance = sc.nextDouble();
		if (balance >= 0) {
			ac.setBalance(balance);
		} else {
			System.err.println("!!Enter balance properly!!");
			createAccount();
		}

		Connection con = DC.getConnection();
		String create_table = "create table account(accno varchar(12),name varchar(20), mobno varchar(10), adharno varchar(12), gender varchar(7), age int , balance double)";
		String insert_data = "insert into account values(?,?,?,?,?,?,?)";

		try {

			Statement st = con.createStatement();
			// st.execute(create_table);

			PreparedStatement ps = con.prepareStatement(insert_data);

			ps.setLong(1, ac.getAccNo());
			ps.setString(2, ac.getName());
			ps.setString(3, ac.getMobNo());
			ps.setString(4, ac.getAdharNo());
			ps.setString(5, ac.getGender());
			ps.setInt(6, ac.getAge());
			ps.setDouble(7, ac.getBalance());

			ps.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("------------Thankyou------------");
	}

	@Override
	public void displayAllDetails() {
//		System.out.println("---------Account Details----------");
//		System.out.println("Account no: " + ac.getAccNo());
//		System.out.println("Name:       " + ac.getName());
//		System.out.println("Mobile no:  " + ac.getMobNo());
//		System.out.println("Aadhar no:  " + ac.getAdharNo());
//		System.out.println("Gender:     " + ac.getGender());
//		System.out.println("Age:        " + ac.getAge());
//		System.out.println("Balance:    " + ac.getBalance());

		Connection con = DC.getConnection();
		System.out.println("Enter Account No for which U want to get all Details : ");
		String Accno = sc.next();

		String retrieve_data = "select * from account where accno = ?";

		try {
			PreparedStatement ps = con.prepareStatement(retrieve_data);
			ps.setString(1, Accno);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("Account No - " + rs.getString(1));
				System.out.println("Account Holder Name - " + rs.getString(2));
				System.out.println("Mobile Number - " + rs.getString(3));
				System.out.println("Adhar Number - " + rs.getString(4));
				System.out.println("Gender - " + rs.getString(5));
				System.out.println("Age - " + rs.getString(6));
				System.out.println("Balance - " + rs.getDouble(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("------------Thankyou------------");
	}

	@Override
	public void depositeMoney() {
		/*
		 * System.out.println("----------Deposit Money-------------");
		 * System.out.println("Enter your Account no:"); long accno = sc.nextLong(); if
		 * (accno == ac.getAccNo()) {
		 * System.out.print("Enter the amount to deposit: $"); double amount =
		 * sc.nextDouble();
		 * 
		 * double balance = ac.getBalance() + amount; System.out.println(amount +
		 * " has been deposited to your account.");
		 * System.out.println("Updated Balance"); System.out.println(balance);
		 * ac.setBalance(balance);
		 * 
		 * } else { System.err.println("!!Account does not exist!!"); }
		 * System.out.println("------------Thankyou------------"); }
		 */
		System.out.println("Enter Account No. for which you want to deposite Money : ");
		String Accno = sc.next();

		Connection con = DC.getConnection();
		String sql = "select * from account where accno = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, Accno);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("Current balance is : " + rs.getDouble(7));
				ac.setBalance(rs.getDouble(7));
			}

			System.out.print("\n--Enter amount to deposite : ");
			double depositeAmount = sc.nextDouble();
			double totalBalance = ac.getBalance() + depositeAmount;
			System.out.println("Amount credited to your account...");

			String deposit = "update account set balance=? where accno = ?";
			PreparedStatement ps1 = con.prepareStatement(deposit);
			ps1.setDouble(1, totalBalance);
			ps1.setString(2, Accno);
			ps1.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void withdrawal() {
		/*
		 * System.out.println("--------Withdraw Money-----------");
		 * System.out.println("Enter your Account no:"); long accno = sc.nextLong(); if
		 * (accno == ac.getAccNo()) {
		 * 
		 * System.out.print("Enter the amount to withdraw: $"); double amount =
		 * sc.nextDouble();
		 * 
		 * if (amount > ac.getBalance()) { System.err.println("!!Insufficient funds!!");
		 * } else { double balance = ac.getBalance() - amount; System.out.println(amount
		 * + " has been withdrawn from your account.");
		 * System.out.println("Updated Balance"); System.out.println(balance);
		 * ac.setBalance(balance); } }
		 * System.out.println("------------Thankyou------------"); }
		 */
		System.out.println("Enter Account No from which you want to withdraw Money : ");
		String Accno = sc.next();

		Connection con = DC.getConnection();
		String sql = "select * from account where accno = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, Accno);
			double remBalance = 0;
			double withdrawalAmount = 0;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("Current Account Balance - " + rs.getDouble(7));
				ac.setBalance(rs.getDouble(7));
				remBalance = rs.getDouble(7);
			}
			System.out.print("\n--Enter amount for withdrawal : ");
			withdrawalAmount = sc.nextDouble();
			if (remBalance > 500 && (remBalance - withdrawalAmount) > 500) {
				// System.out.print("\n--Enter amount for withdrawal : ");
				// withdrawalAmount =sc.nextDouble();
				// double remBalance= ac.getBalance()-withdrawalAmount;
				remBalance = ac.getBalance() - withdrawalAmount;
				System.out.println("Amount debited from your account...");
			} else {
				System.out.println("You cannot withdraw more amount from your account...");
				System.out.println("Account Balance should be 500 or greater... ");
			}
			/*
			 * ac.setBalance(remBalance);
			 * System.out.println("Current Account Balance - "+ac.getBalance());
			 */

			String withdraw = "update account set balance = ? where Accno = ?";
			PreparedStatement ps1 = con.prepareStatement(withdraw);
			ps1.setDouble(1, remBalance);
			ps1.setString(2, Accno);
			ps1.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void balanceCheck() {
		/*
		 * System.out.println("----------Check Balance-----------");
		 * System.out.println("Enter your Account no:"); long accno = sc.nextLong(); if
		 * (accno == ac.getAccNo()) { System.out.println("Balance:" + ac.getBalance());
		 * } else { System.err.println("!!Account does not exist!!"); }
		 * System.out.println("------------Thankyou------------"); }
		 * 
		 * }
		 */
		System.out.println("Enter Account No for which U want to check Balance : ");
		String Accno = sc.next();

		Connection con = DC.getConnection();
		String sql = "select * from account where accno = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, Accno);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("Current Account Balance - " + rs.getString(7));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
