package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private int lunghezzaMassima=0;
	
	private List<Integer> best;
	
	public Model () {
		this.dao = new ArtsmiaDAO ();
	}
	
	public List<String> getRoles (){
		return dao.listRoles();
	}
	
	public void creaGrafo(String ruolo) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// vertici 
		Graphs.addAllVertices(this.grafo, dao.getArtistiByRuolo(ruolo));
	
		// archi
		for(Adiacenza a: dao.getAdiacenze(ruolo)) {
			Graphs.addEdge(this.grafo, a.getArtID1(), a.getArtID2(), a.getPeso());
		}
		
		
	}

	public Graph<Integer, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<Adiacenza> getCoppiePeso (String ruolo) {
		List<Adiacenza> result = dao.getAdiacenze(ruolo);
		Collections.sort(result);
		return result;
	}
	
	public String getNomeArtista (Integer id) {
		return dao.getNomeArtista(id);
	}
	

	public List<Integer> trovaCammino (Integer partenza) {
		this.best = new ArrayList<>();
		List<Integer> parziale = new ArrayList<>();
		double pesoCammino =0.0;
		
		parziale.add(partenza);
		ricorsione(parziale, -1);
		return best;
	}

	private void ricorsione(List<Integer> parziale, double pesoCammino) {
		if(parziale.size()>lunghezzaMassima) {
			lunghezzaMassima = parziale.size();
			this.best = new ArrayList<>(parziale);
		}
		
		Integer ultimo = parziale.get(parziale.size()-1);
		
		
		for(Integer vicino : Graphs.neighborListOf(this.grafo, ultimo)) {
			if(!parziale.contains(vicino) && pesoCammino ==-1) {
				parziale.add(vicino);
				ricorsione(parziale, this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)));
				parziale.remove(vicino);
			}
			else {
				if(!parziale.contains(vicino) && this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)) == pesoCammino) {
					parziale.add(vicino);
					ricorsione(parziale, vicino);
					parziale.remove(vicino);
				}
			}
		}
	}

	public int getLunghezzaMassima() {
		return lunghezzaMassima;
	}
	
	
}
