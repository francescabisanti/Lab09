package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;


public class Model {
	SimpleGraph <Country, DefaultEdge> grafo;
	BordersDAO dao;
	Map <Integer, Country> idMap;
	int connesse;
	Map <Country, Country> visita;
	public Model() {
		dao= new BordersDAO();
		idMap= new HashMap <Integer, Country>();
		dao.loadAllCountries(idMap);
		
	}
	
	public void creaGrafo(int year) {
		grafo= new SimpleGraph <Country, DefaultEdge> (DefaultEdge.class);
		// Aggiunta vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, year));
		
		for(Rotta r: dao.getRotta(idMap, year)) {
			if(grafo.containsVertex(r.getC1())&&grafo.containsVertex(r.getC2())) {
				DefaultEdge e=this.grafo.getEdge(r.getC1(), r.getC2());
				if(e==null) {
				Graphs.addEdgeWithVertices(this.grafo, r.getC1(), r.getC2());
				}
				}
		}
		
		for(Country c: this.dao.getVertici(idMap, year)) {
			
			c.setGrado(this.grafo.degreeOf(c));
			if(c.getGrado()==0) {
				this.grafo.removeVertex(c);
			}
			//System.out.println(c);
			
		}
		
		//System.out.println("#Vertici : "+this.grafo.vertexSet().size());
		
		
		
	}
	public List <Country> contryRaggiungibili( Country partenza){
		List <Country> result= new LinkedList <Country>();
		
		BreadthFirstIterator <Country, DefaultEdge> bfv= new BreadthFirstIterator <Country, DefaultEdge>(this.grafo, partenza);
		
		while(bfv.hasNext()) {
			Country c= bfv.next();
			result.add(c);
		}
		
		return result;
		
	}
	public SimpleGraph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(SimpleGraph<Country, DefaultEdge> grafo) {
		this.grafo = grafo;
	}

	public BordersDAO getDao() {
		return dao;
	}

	public void setDao(BordersDAO dao) {
		this.dao = dao;
	}

	public Map<Integer, Country> getIdMap() {
		return idMap;
	}

	

	public int getConnesse() {
		ConnectivityInspector <Country, DefaultEdge> cI = new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		return cI.connectedSets().size();
	}

	public int getNumeroVertici(){
		if(this.grafo!=null) {
			return this.grafo.vertexSet().size();
		}
		return 0;
	}
	
	public int getNumeroArchi() {
		if(this.grafo!=null) {
			return this.grafo.edgeSet().size();
		}
		return 0;
	}
	
	
}
