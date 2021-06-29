package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	String ruolo = this.boxRuolo.getValue();
    	if(ruolo == null) {
    		txtResult.appendText("errore: seleziona un ruolo"+"\n");
    	}
    	
    	if(model.getGrafo()!= null) {
    		for(Adiacenza a : model.getCoppiePeso(ruolo)) {
    			txtResult.appendText(a.toString()+"\n");
    		}
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Integer id = 0;
    	try {
    		id = Integer.parseInt(this.txtArtista.getText());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Errore: inserire un formato corretto");
    	}
    	
    	if(this.model.getNomeArtista(id)!= null) {
    		List<Integer> percorso = this.model.trovaCammino(id);
    		txtResult.appendText("Percorso più lungo: "+percorso.size()+" con peso che massimizza: "+this.model.getLunghezzaMassima()+"\n");
    		for(Integer v : percorso) {
    			txtResult.appendText(v+ " ");
    		}
    	}
    	else {
    		txtResult.appendText("Errore: artista non esistente!");
    	}
    
    }
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String ruolo = this.boxRuolo.getValue();
    	if(ruolo == null) {
    		txtResult.appendText("errore: seleziona un ruolo"+"\n");
    	}
    	
    	this.model.creaGrafo(ruolo);
    	txtResult.appendText("numero vertici: "+this.model.getGrafo().vertexSet().size()+"\n");
    	txtResult.appendText("numero archi: "+this.model.getGrafo().edgeSet().size()+"\n");
   
    	btnCalcolaPercorso.setDisable(false);
    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxRuolo.getItems().addAll(model.getRoles());
		btnCalcolaPercorso.setDisable(true);
	}
}

