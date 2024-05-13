package org.project.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.project.components.models.Element;
import org.project.components.models.Unit;
import org.project.components.utils.UtilsView;

import static org.project.components.models.Bakery.*;
import static org.project.components.utils.UtilsElement.*;

public class ElementController implements Initializable {

    @FXML
    private TableView<Element> elementTableView;
    @FXML
    private TableColumn<Element, String> elementCode;
    @FXML
    private TableColumn<Element, String> elementName;
    @FXML
    private TableColumn<Element, String> elementQuantity;
    @FXML
    private TableColumn<Element, String> elementUnit;
    @FXML
    private TableColumn<Element, String> elementPricePurchase;
    @FXML
    private TableColumn<Element, String> elementPriceSelling;
    @FXML
    private TableColumn<Element, String> quantityToBuy;

    private int currentQuantity;
    private final Map<Element, Integer> orderListElements = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.elementCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCode()));
        this.elementName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        this.elementQuantity.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getQuantity())));
        this.elementUnit.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getUnit())));
        this.elementPricePurchase.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getPricePurchase())));
        this.elementPriceSelling.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getPriceSelling())));

        Callback<TableColumn<Element, String>, TableCell<Element, String>> cellElements = (TableColumn<Element, String> param) -> {
            final TableCell<Element, String> cell = new TableCell<Element, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button add = new Button("+");
                        Button less = new Button("-");
                        Label quantityText = new Label();
                        quantityText.setText(String.valueOf(currentQuantity));

                        Element element = getTableView().getItems().get(getIndex());
                        int limitMin = Integer.parseInt((element.getQuantity().toString()).substring(0,element.getQuantity().toString().length()-2));

                        add.setOnAction(e -> {
                            currentQuantity = Integer.parseInt(quantityText.getText());
                            quantityText.setText(Integer.toString(currentQuantity + 1));
                            orderListElements.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQuantity + 1));
                        });

                        less.setOnAction(e -> {
                            currentQuantity = Integer.parseInt(quantityText.getText());
                            quantityText.setText(Integer.toString(Math.max(- limitMin, currentQuantity - 1)));
                            orderListElements.put(getTableView().getItems().get(getIndex()), Math.max(- limitMin, currentQuantity - 1));
                        });


                        HBox quantityToBuy = new HBox();
                        quantityToBuy.getChildren().addAll(less, quantityText, add);
                        quantityToBuy.setStyle("-fx-alignment:center");

                        setGraphic(quantityToBuy);
                    }
                    setText(null);
                }
            };
            return cell;
        };

        this.quantityToBuy.setCellFactory(cellElements);
        this.elementTableView.setItems(getElements());
    }

    public void goToChains(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/home.fxml", actionEvent);
    }

    public void goToElements(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/elements.fxml", actionEvent);
    }

    public void transactAndGoToChains(ActionEvent actionEvent) throws IOException {
        this.addElements();
        UtilsView.goToScene("/views/home.fxml", actionEvent);
    }

    private void addElements() {
        for (Map.Entry<Element, Integer> entry : orderListElements.entrySet()) {
            Element element = entry.getKey();
            int additionalQuantity = entry.getValue();
            element.setQuantity(element.getQuantity() + additionalQuantity);
        }
    }
}
