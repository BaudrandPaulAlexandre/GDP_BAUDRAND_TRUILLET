package org.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.project.components.utils.UtilsView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable {

    @FXML
    private Label labelConfirmation;

    public void initialize(URL location, ResourceBundle resources) {
        String text;
        if(ResultController.isOrderWritten) {
            text = "Commande réalisée";
        } else {
            text = "Commande non réalisée";
        }
        labelConfirmation.setText(text);
    }

    public void goToHome(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/home.fxml", actionEvent);
    }

    public void goToResult(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/result.fxml", actionEvent);
    }

    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/chains.fxml", actionEvent);
    }

    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        UtilsView.goToScene("/views/elements.fxml", actionEvent);
    }
}
