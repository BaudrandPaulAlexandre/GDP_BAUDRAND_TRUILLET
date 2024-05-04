package org.project.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.project.components.models.Chain;
import org.project.components.utils.UtilsView;

import static org.project.components.models.Bakery.*;
import static org.project.components.utils.UtilsChain.*;

public class ChainController implements Initializable {

    @FXML
    private TextField quantityInput;
    @FXML
    private TextField quantityOutput;
    @FXML
    private TableView<Chain> chaineTableView;
    @FXML
    private TableColumn<Chain, Integer> chainCode;
    @FXML
    private TableColumn<Chain, String> chainName;
    @FXML
    private TableColumn<Chain, String> chainInput;
    @FXML
    private TableColumn<Chain, String> chainOuput;
    @FXML
    private TextField addCode;
    @FXML
    private TextField addName;
    @FXML
    private TextField addListInput;
    @FXML
    private TextField addListOutput;
    @FXML
    private ComboBox comboBoxElementInput;
    @FXML
    private ComboBox comboBoxElementOutput;
    @FXML
    private Label message;

    public void initialize(URL location, ResourceBundle resources) {
        chainCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chainName.setCellValueFactory(new PropertyValueFactory<>("name"));
        chainInput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getInputListFormatted()));
        chainOuput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOutputListFormatted()));

        chaineTableView.setItems(getChain());
        comboBoxElementInput.setItems(getElementName());
        comboBoxElementOutput.setItems(getElementName());

        chaineTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                addCode.setText(newSelection.getCode());
                addName.setText(newSelection.getName());
                addListInput.setText(String.valueOf(newSelection.getInputListCSV()));
                addListOutput.setText(String.valueOf(newSelection.getOutputListCSV()));
            }
        });
    }

    public void goToHome(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/home.fxml", actionEvent);
    }

    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/inventory.fxml", actionEvent);
    }

    public void createList(TextField listField, ComboBox<String> elementComboBox, TextField quantityField) {
        String currentList = listField.getText();
        String selectedElement = elementComboBox.getSelectionModel().getSelectedItem();
        String quantity = quantityField.getText();
        if (!currentList.isEmpty()) {
            currentList += ",";
        }
        currentList += "(" + getCodeFromName(selectedElement) + "," + quantity + ")";
        if (Objects.equals(quantity, "") || selectedElement==null)
            printLabel("-fx-text-fill: red","Séléctionnez un élément et une quantité");
        else
            listField.setText(currentList);
    }

    public void createInputList(){createList(addListInput, comboBoxElementInput, quantityInput);}

    public void createOutputList(){createList(addListOutput, comboBoxElementOutput, quantityOutput);}

    public void addChaine() {
        try{
            Chain chain = new Chain(addCode.getText(), addName.getText(),
                    parseElementList(addListInput.getText()),
                    parseElementList(addListOutput.getText()));

            if(checkChainCode(addCode.getText())
                    && checkChainName(addName.getText())){
                addToChains(chain);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            printLabel("-fx-text-fill: red","Entrer vos chaines dans le format suivant :" + '\n' + "(Code Element,Nombre Element),...");
        }
    }

    public void delChaine() {
        Chain chai = chaineTableView.getSelectionModel().getSelectedItem();
        removeToChain(chai);
    }

    public void modifyChaine() {
        Chain post = new Chain(addCode.getText(), addName.getText(),
                parseElementList(addListInput.getText()),
                parseElementList(addListOutput.getText()));

        Chain pre = chaineTableView.getSelectionModel().getSelectedItem();

        if (chainsContains(pre)){
            modifyToChaine(pre,post);
            chaineTableView.refresh();
        }
    }

    public void clearTextField() {
        addCode.setText("");
        addName.setText("");
        addListInput.setText("");
        addListOutput.setText("");
    }

    public void printLabel(String style, String text){
        message.setStyle(style);
        message.setText(text);
    }
}
