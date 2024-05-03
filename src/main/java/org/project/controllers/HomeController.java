package org.project.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private int currentQte;

    private Map<Chain, Integer> orderListHome = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentQte = 0; // initialize to 0 when you go in this page

        // set value for each column of the tableview
        chainCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCode()));
        chainName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        chainInput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getInputListFormatted()));
        chainOutput.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOutputListFormatted()));

        // custom button to set the quantity of the chaine
        Callback<TableColumn<Chain, String>, TableCell<Chain, String>> cellFoctory = (TableColumn<Chain, String> param) -> {
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
                        TextFieldTableCell tf = new TextFieldTableCell();

                        tf.setText(String.valueOf(currentQte));
                        tf.setStyle(" -fx-cursor: hand ;" + "-glyph-size:50px;" + "-fx-fill:#ff1744;" +
                                    "-fx-cell-size: 80px;" + "-fx-start-margin: 30px;" + "-fx-pref-width: 60px;" +
                                    "-fx-alignment: center;"
                        );

                        add.setStyle("-fx-cursor: hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;"+ "-fx-border-radius: 50px;");

                        less.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;" + "-fx-border-radius: 50px;"
                        );

                        tf.setEditable(true);

                        // function to change on clic the value of the label
                        add.setOnAction(e -> {
                            currentQte = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(currentQte + 1));
                            orderListHome.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQte + 1));
                        });

                        // function to change on clic the value of the label
                        less.setOnAction(e -> {
                            currentQte = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(Math.max(0, currentQte - 1)));
                            orderListHome.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQte - 1));
                            if (currentQte - 1 == 0) {
                                orderListHome.remove(getTableView().getItems().get(getIndex()));
                            }
                        });

                        HBox managebtn = new HBox();
                        managebtn.getChildren().addAll(add, tf, less);
                        managebtn.setStyle("-fx-alignment:center");

                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };

            return cell;
        };

        quantity.setCellFactory(cellFoctory);
        chainTableView.setItems(getChain());
    }

    public void goToResult(ActionEvent actionEvent) throws IOException {
        parseHashmapToCommand(orderListHome);
        UtilsView.goToScene("/views/result.fxml", actionEvent);
    }

    public void goToChainProduction(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/chain.fxml", actionEvent);
    }

    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/inventory.fxml", actionEvent);
    }
}