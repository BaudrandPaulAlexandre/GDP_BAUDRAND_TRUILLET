package org.project;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.Objects;

import org.project.components.models.Chain;
import org.project.components.models.Bakery;
import org.project.components.utils.UtilsElement;
import org.project.components.utils.UtilsChain;

import static org.project.components.models.Bakery.getChain;
import static org.project.components.models.Bakery.getElements;

public class Main extends Application {
    public Bakery bakery;

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/home.fxml")));
            Scene scene = new Scene(root);
            String css = Objects.requireNonNull(this.getClass().getResource("/style/styles.css")).toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("GDP");
            stage.setScene(scene);
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.show();

            bakery = Bakery.getInstance(0);
            bakery.loadElements();
            bakery.loadChains();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    UtilsElement.writeElement(getElements());
                    UtilsChain.writeChain(getChain().toArray(new Chain[0]));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}