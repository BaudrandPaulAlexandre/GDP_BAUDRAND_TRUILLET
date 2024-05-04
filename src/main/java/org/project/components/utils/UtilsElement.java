package org.project.components.utils;

import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;

import org.project.components.models.Element;
import org.project.components.models.Unit;

public class UtilsElement {

    private final static String fileName = "src/main/resources/data/elements.csv";

    public static ArrayList<Element> readElement() {
        String line;
        ArrayList<Element> element = new ArrayList<>();

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));

            while ((line = file.readLine()) != null) {
                element.add(parseElement(line));
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès au fichier elements.csv");
        }
        return element;
    }

    private static Element parseElement(String line) {
        String[] l = line.split(";");
        return new Element(l[0], l[1], Double.parseDouble(l[2]), Unit.valueOf(l[3]), Double.parseDouble(l[4]), Double.parseDouble(l[5]));
    }

    public static void writeElement(ObservableList<Element> elements) {
        try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));

            for (Element element : elements) {
                file.println(
                        element.getCode() + ";"
                        + element.getName() + ";"
                        + element.getQuantity() + ";"
                        + element.getUnit() + ";"
                        + element.getPricePurchase() + ";"
                        + element.getPriceSelling());
            }
            file.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès au fichier elements.csv");
        }
    }

    public static boolean checkElementCode(String code) {
        return code.matches("E\\d{3}");
    }
    public static boolean checkElementQuantity(Double qte) {
        return qte > 0;
    }
    public static boolean checkElementPurchasePrice(Double pp) {
        return pp > 0;
    }
    public static boolean checkElementSellingPrice(Double sp) {
        return sp > 0;
    }

}