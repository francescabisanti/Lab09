package it.polito.tdp.borders.model;

public class Country {
	private String stateAbb;
	private int CCode;
	private String stateNme;
	private int grado;
	
	

	public Country(String stateAbb, int cCode, String stateNme, int grado) {
		super();
		this.stateAbb = stateAbb;
		CCode = cCode;
		this.stateNme = stateNme;
		this.grado = grado;
	}
	
	

	public String getStateAbb() {
		return stateAbb;
	}



	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}



	public int getCCode() {
		return CCode;
	}



	public void setCCode(int cCode) {
		CCode = cCode;
	}



	public String getStateNme() {
		return stateNme;
	}



	public void setStateNme(String stateNme) {
		this.stateNme = stateNme;
	}



	public int getGrado() {
		return grado;
	}



	public void setGrado(int grado) {
		this.grado = grado;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stateAbb == null) ? 0 : stateAbb.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (stateAbb == null) {
			if (other.stateAbb != null)
				return false;
		} else if (!stateAbb.equals(other.stateAbb))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return  stateNme + "    "
				;
	}
	
	
	
	
}
