package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {

		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");

			PowerOutageDAO dao = new PowerOutageDAO();

			dao.getNercList().forEach(n -> System.out.println(n));

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
