package org.java.nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

public static void main(String[] args) {
		
		final String url = "jdbc:mysql://localhost:8889/db-nations";
		final String user = "root";
		final String password = "root";
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Ricerca: ");
        String word = scanner.nextLine();
		
		final String sql = "SELECT c2.name , c2.country_id , r.name , c.name " + "\n"
							+ "FROM continents c" + "\n"
							+ "JOIN regions r" + "\n"
								+ "ON c.continent_id  = r.continent_id" + "\n"
							+ "JOIN countries c2" + "\n"
								+ "ON c2.region_id = r.region_id" + "\n"
//							+ "ORDER BY r.name;";
							+ "WHERE c2.name LIKE ? OR ? OR ?;";
		
		
		
		
		
		try (Connection con = DriverManager.getConnection(url, user, password) )
		{
			PreparedStatement natStat = con.prepareStatement(sql);
			
			
			natStat.setString(1,  word + "%");
			natStat.setString(2, "%" + word );
			natStat.setString(3, "%" +  word + "%");
			
			ResultSet bh = natStat.executeQuery();
			
			
			while(bh.next()) {
				
				String nameNation = bh.getString("c2.name");
				int idNation = bh.getInt("c2.country_id");
				String nameRegion = bh.getString("r.name");
				String nameContinent = bh.getString("c.name");
				
				System.out.println(nameNation + " | " + idNation + " | " + nameRegion + " | " + nameContinent);
				System.out.println("\n--------------------------------------\n");
			}
		} catch (Exception e) {
			
			System.out.println("Errore di connessione: " + e.getMessage());
		}
		
			System.out.println("The end");
			
			scanner.close();
	}
}
