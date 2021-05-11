package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Rotta;

public class BordersDAO {

	public void loadAllCountries(Map <Integer, Country> idMap) {

		String sql = "SELECT CCode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				if(!idMap.containsKey(rs.getInt("CCode"))) {
				
				Country c= new Country (rs.getString("StateAbb"), rs.getInt("CCode"), rs.getString("StateNme"),0);
				idMap.put(c.getCCode(), c);
				
				}
				}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}
	
	public List <Country> getVertici(Map <Integer, Country> idMap, int x) {

		String sql="SELECT c.CCode AS id "
			      + "FROM country c, contiguity co "
				+ "WHERE (c.CCode = co.state1no OR c.CCode = co.state2no) "
				+ "AND co.year <= ? AND co.conttype = 1 "
				+ "GROUP BY c.CCode ";
		
		
		List <Country> result= new LinkedList <Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				
				
				
				result.add(idMap.get(rs.getInt("id")));
				
				
				}
			
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List <Rotta> getRotta(Map <Integer, Country> idMap, int anno) {

		String sql="SELECT state1no AS stato1, state2no AS stato2 "
	      		+ "FROM contiguity "
	      		+ "WHERE year <= ? AND conttype = 1 ";
		List <Rotta> result= new LinkedList <Rotta>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				
				
				
				Country c1= idMap.get(rs.getInt("stato1"));
				Country c2=idMap.get(rs.getInt("stato2"));
				if(c1!=null && c2!=null) {
				Rotta r= new Rotta(c1,c2);
				result.add(r);
				}
				
				}
			
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c= new Country (rs.getString("StateAbb"), rs.getInt("CCode"), rs.getString("StateNme"),0);
				result.add(c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
