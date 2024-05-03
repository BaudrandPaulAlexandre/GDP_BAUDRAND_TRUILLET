package org.project.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.project.components.models.Order;
import org.project.components.utils.*;

import static org.project.components.models.Bakery.*;
import static org.project.components.utils.UtilsOrder.*;

public class ResultController implements Initializable {

    @FXML
    private Label stat;
    @FXML
    private Label valueIndicator;
    @FXML
    private ProgressBar orderResult;
    private Stage stage;
    private Scene scene;
    private Parent root;

    protected static boolean isOrderWritten = false;
    public static String indicValue = String.valueOf(0);


    @FXML
    private TableView<Order> chaineTableView;
    @FXML
    private TableColumn<Order, String> chainCode;
    @FXML
    private TableColumn<Order, String> chainName;
    @FXML
    private TableColumn<Order, String> chainInput;
    @FXML
    private TableColumn<Order, String> chainOutput;
    @FXML
    private TableColumn<Order, Integer> quantity;
    @FXML
    private TableColumn<Order, String> feasibility;

    public void goToHome(ActionEvent actionEvent) throws IOException {
        clearChainsOrder();
        UtilsView.goToScene("/views/home.fxml", actionEvent);
    }

    public void goToConfirmation(ActionEvent actionEvent) throws IOException {
        toOrder();
        UtilsElement.writeElement(getElements());
        isOrderWritten = UtilsOrder.writeResult();
        clearChainsOrder();
        UtilsView.goToScene("/views/confirmation.fxml", actionEvent);
    }

    public void goToChainProduction(ActionEvent actionEvent) throws IOException {
        clearChainsOrder();
        UtilsView.goToScene("/views/chain.fxml", actionEvent);
    }

    public void goToInventory(ActionEvent actionEvent) throws IOException {
        clearChainsOrder();
        UtilsView.goToScene("/views/inventory.fxml", actionEvent);
    }

    public void initialize(URL location, ResourceBundle resources) {
        valueIndicator.setText("Valeur totale : " + String.valueOf(productionProfitability()) + "€");
        indicValue = valueIndicator.toString();

        chainCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChain().getCode()));
        chainName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChain().getName()));
        chainInput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChain().getInputListFormatted()));
        chainOutput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChain().getOutputListFormatted()));

        quantity.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getQuantity()).asObject());
        feasibility.setCellFactory(column -> new TableCell<Order, String>() {
            @Override
        protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                    setStyle("");
                } else {
                    boolean isFeasible = getTableView().getItems().get(getIndex()).getFeasible();
                    if (isFeasible) {
                        setText("Faisable");
                        setStyle("-fx-text-fill: green;");
                    } else {
                        setText("Non Faisable");
                        setStyle("-fx-text-fill: red;");
                    }
                }
            }
        });

        chaineTableView.setItems(getOrders());

        String[] s = getNbOrder().split("/");
        double resultat = (double) Double.parseDouble(s[0]) / Double.parseDouble(s[1]) ;
        orderResult.setProgress(resultat);
        int total = Integer.parseInt(s[0]) + Integer.parseInt(s[1]);
        int realisees = Integer.parseInt(s[0]);
        double pourcentage = ((double) realisees / total) * 100;

        stat.setText("Résultat : " + realisees + "/" + total + " réalisées (" + pourcentage + "%) !");
    }
}