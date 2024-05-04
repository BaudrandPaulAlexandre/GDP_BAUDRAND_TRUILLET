package org.project.components.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.project.components.models.Element;
import org.project.components.models.Chain;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.project.components.models.Bakery.getChain;
import static org.project.components.models.Bakery.getElements;
public class UtilsChain {

    //TODO: accès à chains.csv // C:\Users\Paul-AlexandreBaudra\Desktop\GDP_BAUDRAND_TRUILLET\src\main\resources\data\chains.csv
    private static final String CHAINES_FILE_PATH = "src/main/resources/data/chains.csv";
    private static final String CHAINE_CODE_FORMAT = "C\\d{3}";
    private static final String CHAINE_CODE_ERROR_MESSAGE = "Code sour format : 'C000' - 'C999'";
    private static final String CHAINE_CODE_EXISTS_ERROR_MESSAGE = "Déja existant";
    private static final String CHAINE_NAME_EXISTS_ERROR_MESSAGE = "Déja existant";

    public static ArrayList<Chain> readChain() {
        ArrayList<Chain> chains = new ArrayList<>();
        try (BufferedReader file = new BufferedReader(new FileReader(CHAINES_FILE_PATH))) {
            String line;
            while ((line = file.readLine()) != null) {
                chains.add(parseChain(line));
            }
        } catch (IOException e) {
            System.out.println("Problème d'accès au fichier chains.csv");
        }
        return chains;
    }

    public static Chain parseChain(String line) {
        String[] parts = line.split(";");

        String code = parts[0];
        String name = parts[1];

        HashMap<Element, Double> elementsInput = parseElementList(parts[2]);
        HashMap<Element, Double> elementsOutput = parseElementList(parts[3]);

        return new Chain(code, name, elementsInput, elementsOutput);
    }

    public static HashMap<Element, Double> parseElementList(String input) {
        HashMap<Element, Double> elementMap = new HashMap<>();
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i += 2) {
            String code = elements[i].replaceAll("[(]", "");
            Double value = Double.parseDouble(elements[i + 1].replaceAll("[)]", ""));

            Element existingElement = findElementByCode(getElements(), code);

            if (existingElement != null && getElements().contains(existingElement)) {
                elementMap.put(existingElement, value);
            } else {
                showErrorAlert("Élément inexistant");
            }
        }
        return elementMap;
    }

    private static Element findElementByCode(List<Element> elements, String code) {
        for (Element element : elements) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        return null;
    }

    public static void writeChain(Chain[] chains) {
        try (PrintWriter file = new PrintWriter(new FileWriter(CHAINES_FILE_PATH))) {
            for (Chain chain : chains) {
                file.println(
                        chain.getCode() + ";"
                        + chain.getName() + ";"
                        + chain.getInputListCSV() + ";"
                        + chain.getOutputListCSV());
            }
        } catch (IOException ex) {
            System.out.println("Problème d'accès au fichier chains.csv");
        }
    }

    public static boolean checkChainCode(String code) {
        if (!code.matches(CHAINE_CODE_FORMAT)) {
            showErrorAlert(CHAINE_CODE_ERROR_MESSAGE);
            return false;
        }
        for (Chain chain : getChain()) {
            if (code.equals(chain.getCode())) {
                showErrorAlert(CHAINE_CODE_EXISTS_ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public static boolean checkChainName(String nom) {
        for (Chain chain : getChain()) {
            if (nom.equals(chain.getName())) {
                showErrorAlert(CHAINE_NAME_EXISTS_ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public static boolean checkChainQuantity(Double qte) {
        return qte > 0;
    }

    public static String getCodeFromName(String name){
        for(Element element : getElements()){
            if(name == element.getName()){
                return element.getCode();
            }
        }
        return null;
    }

    private static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Chain getChaineByName(ObservableList<Chain> chains, String name) {
        for (Chain chain : chains) {
            if (chain.getName().equals(name)) {
                return chain;
            }
        }
        return null;
    }
}



