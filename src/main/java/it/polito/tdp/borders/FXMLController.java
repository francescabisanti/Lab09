package it.polito.tdp.borders;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;



public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ChoiceBox<Country> cbmBox;

    @FXML
    private Button btmStatiRaggiungibili;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	String annoS= this.txtAnno.getText();
    	int anno;
    	try {
    		anno=Integer.parseInt(annoS);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un anno tra 1816 e il 2016");
    		return;
    	}
    	if(anno>=1816 && anno<=2016) {
    		this.model.creaGrafo(anno);
    		
    		String result="";
    		for(Country c: model.getGrafo().vertexSet()) {
    			
    			result=result+c.toString()+" "+c.getGrado()+"\n";
    		}
    		result=result+"Le componenti connesse sono: "+this.model.getConnesse();
    		result=result+"#VERTICI: "+this.model.getNumeroVertici()+"\n";
    		result=result+"#Archi: "+this.model.getNumeroArchi()+"\n";
    		
    		this.txtResult.setText(result);
    	}
    	
    }
    @FXML
    void doRaggiungibili(ActionEvent event) {
    	Country c= this.cbmBox.getSelectionModel().getSelectedItem();
    	List <Country> raggiungibili= new LinkedList <Country>(this.model.contryRaggiungibili(c));
    	String result="";
    	for(Country cc: raggiungibili) {
    		result=result+cc.toString()+"\n";
    	}
    	this.txtResult.setText(result);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cbmBox != null : "fx:id=\"cbmBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btmStatiRaggiungibili != null : "fx:id=\"btmStatiRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cbmBox.getItems().addAll(model.getDao().loadAllCountries());
    	
    }
}
