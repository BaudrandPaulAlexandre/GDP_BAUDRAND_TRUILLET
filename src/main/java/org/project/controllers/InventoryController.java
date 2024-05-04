package org.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.project.components.models.Element;
import org.project.components.models.Unit;
import org.project.components.utils.UtilsView;

import static org.project.components.models.Bakery.*;
import static org.project.components.utils.UtilsElement.*;

public class InventoryController implements Initializable {

    @FXML
    private TableView<Element> elementTableView;
    @FXML
    private TableColumn<Element, Integer> elementCode;
    @FXML
    private TableColumn<Element, String> elementName;
    @FXML
    private TableColumn<Element, Double> elementPricePurchase;
    @FXML
    private TableColumn<Element, Double> elementPriceSelling;
    @FXML
    private TableColumn<Element, Double> elementQuantity;
    @FXML
    private TableColumn<Element, String> elementUnit;
    @FXML
    private TextField addCode;
    @FXML
    private TextField addName;
    @FXML
    private TextField addPricePurchase;
    @FXML
    private TextField addPriceSelling;
    @FXML
    private TextField addQuantity;
    @FXML
    private ComboBox<Unit> addUnit;
    @FXML
    private Label message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elementCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        elementName.setCellValueFactory(new PropertyValueFactory<>("name"));
        elementPricePurchase.setCellValueFactory(new PropertyValueFactory<>("pricePurchase"));
        elementPriceSelling.setCellValueFactory(new PropertyValueFactory<>("priceSelling"));
        elementQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        elementUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));

        elementTableView.setItems(getElements());

        elementTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                addCode.setText(newSelection.getCode());
                addName.setText(newSelection.getName());
                addQuantity.setText(String.valueOf(newSelection.getQuantity()));
                addPricePurchase.setText(String.valueOf(newSelection.getPricePurchase()));
                addPriceSelling.setText(String.valueOf(newSelection.getPriceSelling()));
                addUnit.setValue(newSelection.getUnit());
            }
        });

        addUnit.getItems().addAll(Unit.values());
        addUnit.setValue(Unit.piece);
    }

    public void goToHome(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/home.fxml", actionEvent);
    }

    public void goToChainProduction(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/chain.fxml", actionEvent);
    }

    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/inventory.fxml", actionEvent);
    }

    public void printLabel(String style, String text){
        message.setStyle(style);
        message.setText(text);
    }

    public Element textfieldsToElement(){
        return new Element(addCode.getText(), addName.getText(),
                Double.parseDouble(addQuantity.getText()), addUnit.getValue(),
                Double.parseDouble(addPricePurchase.getText()), Double.parseDouble(addPriceSelling.getText()));
    }

    public Element selecteditemToElement(){
        return elementTableView.getSelectionModel().getSelectedItem();
    }

    public boolean checkEmptyField(){
        return addCode.getText().isEmpty() ||
                addName.getText().isEmpty() ||
                addQuantity.getText().isEmpty() ||
                addPricePurchase.getText().isEmpty() ||
                addPriceSelling.getText().isEmpty();
    }

    public boolean checkCodeIsTake(){
        Element element = getElementByCode(addCode.getText());
        if (element != null) {
            return !Objects.equals(element.getName(), addName.getText()) ||
                    !element.getPricePurchase().toString().equals(addPricePurchase.getText()) ||
                    !element.getPriceSelling().toString().equals(addPriceSelling.getText()) ||
                    element.getUnit() != addUnit.getValue();
        }
        return false;
    }

    public boolean checkElementExist() {
        Element element = getElementByName(addName.getText());
        if (element != null) {
            return !Objects.equals(element.getCode(), addCode.getText());
        }
        return false;
    }

    public boolean checkAllModif(){
        //TODO: reformuler les messages
        if(checkEmptyField()) {
            printLabel("-fx-text-fill: red", "Un des champs est vide");
            return false;
        }
        if (!checkElementCode(addCode.getText())) {
            printLabel("-fx-text-fill: red", "Code pas au bon format\nFormat : 'E000' - 'E999'");
            return false;
        }
        if (!checkElementQuantity(Double.parseDouble(addQuantity.getText()))) {
            printLabel("-fx-text-fill: red", "Qte doit être > 0");
            return false;
        }
        if (!checkElementPurchasePrice(Double.parseDouble(addPricePurchase.getText()))) {
            printLabel("-fx-text-fill: red", "Prix achat doit être > 0");
            return false;
        }
        if (!checkElementSellingPrice(Double.parseDouble(addPriceSelling.getText()))) {
            printLabel("-fx-text-fill: red", "Prix vente doit être > 0");
            return false;
        }
        return true;
    }

    public boolean checkAllAdd(){
        //TODO: reformuler les messages
        if (!checkAllModif()){
            return false;
        }
        if (checkCodeIsTake()) {
            printLabel("-fx-text-fill: red","Code déjà pris pour un autre\nnom, prix et/ou unite");
            return false;
        }
        if (checkElementExist()) {
            printLabel("-fx-text-fill: red","Element déjà présent\navec le code : " +
                    getElementByName(addName.getText()).getCode());
            return false;
        }
        return true;
    }

    public void addElement(){
        if (checkAllAdd()){
            Element element = textfieldsToElement();
            if (elementsContains(element)) {
                Double postqte = addQuantitiesOfElement(element);
                printLabel("-fx-text-fill: green", "Quantité mise à jour");
                elementTableView.refresh();
                addQuantity.setText(String.valueOf(postqte));
            }
            else {
                addToElements(element);
                printLabel("-fx-text-fill: green", "Element ajouté");
            }
        }
    }

    public Element delElement(){
        Element element = selecteditemToElement();
        if (!checkEmptyField()){
            removeToElements(element);
            printLabel("-fx-text-fill: red", "Element supprimé");
            return element;
        }
        else {
            printLabel("-fx-text-fill: red", "Aucun élément sélectionné");
            return null;
        }
    }

    public void modifyElement() {
        if (checkAllModif()){
            Element eltextf = textfieldsToElement();
            Element elselect = selecteditemToElement();
            if (elementsContains(elselect)){
                modifyToElements(elselect,eltextf);
                elementTableView.refresh();
                printLabel("-fx-text-fill: green", "Element modifié");
            }
            else{
                printLabel("-fx-text-fill: red", "Element non modifiable");
            }
        }
    }

    public void clearTextField() {
        addCode.setText("");
        addName.setText("");
        addQuantity.setText("");
        addPricePurchase.setText("");
        addPriceSelling.setText("");
        addUnit.setValue(Unit.piece);
        message.setText("");
    }
}
