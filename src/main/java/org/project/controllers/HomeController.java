package org.project.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.project.components.models.Chain;
import org.project.components.utils.UtilsView;

import static org.project.components.models.Bakery.*;
import static org.project.components.utils.UtilsOrder.parseHashmapToCommand;

public class HomeController implements Initializable {

    @FXML
    private TableView<Chain> chainTableView;
    @FXML
    private TableColumn<Chain, String> chainCode;
    @FXML
    private TableColumn<Chain, String> chainName;
    @FXML
    private TableColumn<Chain, String> chainInput;
    @FXML
    private TableColumn<Chain, String> chainOutput;
    @FXML
    private TableColumn<Chain, String> quantity;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int currentQuantity = 0;
    private final Map<Chain, Integer> orderListChains = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.chainCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCode()));
        this.chainName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        this.chainInput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getInputListFormatted()));
        this.chainOutput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOutputListFormatted()));

        Callback<TableColumn<Chain, String>, TableCell<Chain, String>> cellChains = (TableColumn<Chain, String> param) -> {
            final TableCell<Chain, String> cell = new TableCell<Chain, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button add = new Button("+");
                        Button less = new Button("-");
                        Label quantityText = new Label();
                        quantityText.setText(String.valueOf(currentQuantity));

                        add.setOnAction(e -> {
                            currentQuantity = Integer.parseInt(quantityText.getText());
                            quantityText.setText(Integer.toString(currentQuantity + 1));
                            orderListChains.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQuantity + 1));
                        });

                        less.setOnAction(e -> {
                            currentQuantity = Integer.parseInt(quantityText.getText());
                            quantityText.setText(Integer.toString(Math.max(0, currentQuantity - 1)));
                            orderListChains.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQuantity - 1));
                            if (currentQuantity - 1 == 0) {
                                orderListChains.remove(getTableView().getItems().get(getIndex()));
                            }
                        });

                        HBox quantityToProduce = new HBox();
                        quantityToProduce.getChildren().addAll(less, quantityText, add);
                        quantityToProduce.setStyle("-fx-alignment:center");

                        setGraphic(quantityToProduce);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        this.quantity.setCellFactory(cellChains);
        this.chainTableView.setItems(getChain());
    }

    public void goToResult(ActionEvent actionEvent) throws IOException {
        parseHashmapToCommand(this.orderListChains);
        UtilsView.goToScene("/views/result.fxml", actionEvent);
    }

    public void goToElements(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/elements.fxml", actionEvent);
    }
}