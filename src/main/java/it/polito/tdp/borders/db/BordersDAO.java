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

	public void loadAllCountries(Map <String, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				if(!idMap.containsKey(rs.getString("StateAbb"))) {
				
				Country c= new Country (rs.getString("StateAbb"), rs.getInt("CCode"), rs.getString("StateNme"),0);
				idMap.put(c.getStateAbb(), c);
				
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
	
	public List <Country> getVertici(Map <String, Country> idMap, int x) {

		String sql = "SELECT  DISTINCT c.StateAbb, c.CCode, c.StateNme "
				+ "FROM country c, contiguity cc "
				+ "WHERE (c.StateAbb=cc.state1ab OR c.StateAbb=cc.state2ab) AND cc.year <= ?";
		
		List <Country> result= new LinkedList <Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				
				
				
				result.add(idMap.get(rs.getString("StateAbb")));
				
				
				}
			
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List <Rotta> getRotta(Map <String, Country> idMap) {

		String sql = "SELECT cc1.state1ab as c1, cc1.state2ab as c2 "
				+ "FROM contiguity cc1, contiguity cc2 "
				+ "WHERE cc1.conttype=1 AND cc1.state1ab>cc2.state1ab AND cc1.dyad=cc2.dyad ";
		
		List <Rotta> result= new LinkedList <Rotta>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				
				
				
				Country c1= idMap.get(rs.getString("c1"));
				Country c2=idMap.get(rs.getString("c2"));
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
