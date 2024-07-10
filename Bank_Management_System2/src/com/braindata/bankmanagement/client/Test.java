package com.braindata.bankmanagement.client;

import java.util.Scanner;

import com.braindata.bankmanagement.service.Rbi;
import com.braindata.bankmanagement.serviceImpl.Sbi;

public class Test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Rbi bank = new Sbi();

		while (true) {
			System.out.println("-------$-Welcome to Sbi Bank-$-------");
			System.out.println("Choose from follwing:\n" + "1.Create Account\n" + "2.Display Details\n"
					+ "3.Deposit Money\n" + "4.Withdraw\n" + "5.Check Balance\n" + "6.Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				bank.createAccount();
				break;
			}
			case 2: {
				bank.displayAllDetails();
				break;
			}
			case 3: {
				bank.depositeMoney();
				break;
			}
			case 4: {
				bank.withdrawal();
				break;
			}
			case 5: {
				bank.balanceCheck();
				break;
			}
			case 6: {
				System.exit(0);
				break;
			}
			default:
				System.out.println("Invalid Choice");
			}
		}
	}
}
