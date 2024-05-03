package org.project.components.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.project.components.models.*;
import static org.project.components.models.Bakery.*;

public class UtilsOrder {

    public static boolean writeResult() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = dateTime.format(formatter);

        String separator = "\n--------------------------------------------------------------------\n";
        try {
            Path destinationDir = Paths.get(System.getProperty("user.home"), "Downloads", "Commandes");
            Files.createDirectories(destinationDir);
            Path destinationPath = destinationDir.resolve("commande_" + formattedDate + ".txt");
            File file = destinationPath.toFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file));

            writer.println("Date de la commande -> " + dateTime);
            writer.println(separator);
            writer.println("L'incateur de valeur est égal à -> " + productionProfitability() + "€");
            writer.println(separator);
            writer.println("La liste des commandes \n");

            for (Order o : getOrders()) {
                writer.println(getCommandeDetails(o));
                writer.println("\n");
            }

            writer.println(separator);
            writer.close();

        } catch (IOException ex) {
            System.err.println("Problème d'accès au fichier");
            return false;
        }
        return true;
    }

    private static String getCommandeDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        if (order.getFeasible()) {
            sb.append("\tChaîne : ").append(order.getChain().getCode()).append(" - ").append(order.getChain().getName()).append("\n");
            sb.append("\tQuantité : ").append(order.getQuantity()).append("\n");
            sb.append("\tListe d'éléments d'entrée : ").append(order.getChain().getInputListFormatted()).append("\n");
            sb.append("\tListe d'éléments de sortie : ").append(order.getChain().getOutputListFormatted()).append("\n");
        } else {
            sb.append("############ ! Erreur ! ############\n");
            sb.append("\tChaîne : ").append(order.getChain().getCode()).append(" - ").append(order.getChain().getName()).append("\n");
            sb.append("\tQuantité : ").append(order.getQuantity()).append("\n");
            sb.append("\tListe d'éléments d'entrée : ").append(order.getChain().getInputListFormatted()).append("\n");
            sb.append("\tListe d'éléments de sortie : ").append(order.getChain().getOutputListFormatted()).append("\n");
            sb.append("############ ! Erreur ! ############\n");
        }
        return sb.toString();
    }

     public static void parseHashmapToCommand(Map<Chain, Integer> orderList) {
        for (Map.Entry<Chain, Integer> entry : orderList.entrySet()) {
            addToOrder(new Order(entry.getKey(), entry.getValue()));
        }
    }

   public static double productionProfitability() {
        double total = 0;
        for(Order order : getOrders()) {
            for (Map.Entry<Element, Double> element : order.getChain().getElementsInput().entrySet()) {
                total -= element.getKey().getPriceSelling() * element.getValue() * order.getQuantity();
            }
            for (Map.Entry<Element, Double> element : order.getChain().getElementsOutput().entrySet()) {
                total += element.getKey().getPriceSelling() * element.getValue() * order.getQuantity();
            }
        }
        return total;
    }

    public static void toOrder() {
        for(Order order : getOrders()) {
            if (order.getFeasible()) {
                for (Map.Entry<Element, Double> currentElement : order.getChain().getElementsInput().entrySet()) {
                    Element element = currentElement.getKey();
                    if (getElements().contains(element)) {
                        int index = getElements().indexOf(element);
                        getElements().get(index).setQuantity(element.getQuantity()-(currentElement.getValue() * order.getQuantity()));
                    }
                }
                for (Map.Entry<Element, Double> currentElement : order.getChain().getElementsInput().entrySet()) {
                    Element element = currentElement.getKey();
                    if (getElements().contains(element)) {
                        int index = getElements().indexOf(element);
                        getElements().get(index).setQuantity(element.getQuantity()+(currentElement.getValue() * order.getQuantity()));
                    }
                }
            }
        }
    }

    public static String getNbOrder() {
        int countFeasible = 0;
        int countInfeasible = 0;
        for (Order order : getOrders()) {
            if (order.getFeasible()) {
                countFeasible += order.getQuantity();
            } else {
                countInfeasible += order.getQuantity();
            }
        }
        return countFeasible + "/" + countInfeasible;
    }

    public static String getUsedElement() {
        HashMap<Element, Integer> d = new HashMap<>();
        String str = "";

        for (Order order : getOrders()) {
            if (order.getFeasible()) {
                order.getQuantity();
            }
        }
        return str;
    }
}
