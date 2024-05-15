package org.project.components.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.project.components.utils.UtilsChain.readChain;
import static org.project.components.utils.UtilsElement.readElement;

/**
 * Cette classe représente une boulangerie.
 * Elle gère les chaînes de production, les commandes et les éléments de la boulangerie.
 */
public class Bakery {

    private static Map<Integer, Bakery> instance = new HashMap<>(); // Stocke les instances uniques de la boulangerie avec leur identifiant
    private static ObservableList<Chain> chains = FXCollections.observableArrayList(); // Liste observable des chaînes de production de la boulangerie
    private static ObservableList<Order> orders = FXCollections.observableArrayList(); // Liste observable des commandes de la boulangerie
    private static ObservableList<Element> elements = FXCollections.observableArrayList(); // Liste observable des éléments de la boulangerie

    /**
     * Obtient l'instance unique de la boulangerie correspondant à l'identifiant spécifié.
     *
     * @param bakeryId L'identifiant de la boulangerie.
     * @return L'instance unique de la boulangerie.
     */
    public static Bakery getInstance(Integer bakeryId) {
        if (instance.get(bakeryId) == null) {
            instance.put(bakeryId,new Bakery());
        }
        return instance.get(bakeryId);
    }

    /**
     * Charge les chaînes de production depuis une source externe.
     */
    public void loadChains() {
        chains.addAll(readChain());
    }

    /**
     * Charge les éléments depuis une source externe.
     */
    public void loadElements() {
        elements.addAll(readElement());
    }

    /**
     * Obtient une liste non modifiable des chaînes de production.
     *
     * @return Une liste non modifiable des chaînes de production.
     */
    public static ObservableList<Chain> getChain() {
        return FXCollections.unmodifiableObservableList(chains);
    }

    /**
     * Ajoute une nouvelle chaîne de production à la liste.
     *
     * @param c La chaîne de production à ajouter.
     */
    public static void addToChains(Chain c) {
        chains.add(c);}

    /**
     * Vérifie si la liste de chaînes de production contient une chaîne spécifique.
     *
     * @param c La chaîne de production à rechercher.
     * @return true si la liste contient la chaîne, sinon false.
     */
    public static boolean chainsContains(Chain c) {
        return chains.contains(c);
    }

    /**
     * Retourne l'index de la première occurrence de la chaîne spécifiée dans la liste.
     *
     * @param c La chaîne de production à rechercher.
     * @return L'index de la chaîne dans la liste, ou -1 si elle n'est pas présente.
     */
    public static int chainsIndexOf(Chain c) {
        return chains.indexOf(c);
    }

    /**
     * Remplace une chaîne de production existante par une nouvelle.
     *
     * @param chpre  La chaîne de production à remplacer.
     * @param chpost La nouvelle chaîne de production.
     */
    public static void modifyToChaine(Chain chpre, Chain chpost){
        chains.set(chainsIndexOf(chpre),chpost);}

    /**
     * Supprime une chaîne de production de la liste.
     *
     * @param c La chaîne de production à supprimer.
     */
    public static void removeToChain(Chain c) {
        chains.remove(c);
    }

    /**
     * Supprime toutes les commandes de la liste.
     */
    public static void clearChainsOrder() {
        orders.clear();
    }

    /**
     * Ajoute une commande à la liste des commandes.
     *
     * @param order La commande à ajouter.
     */
    public static void addToOrder(Order order) {
        orders.add(order);
    }

    /**
     * Obtient une liste non modifiable des commandes.
     *
     * @return Une liste non modifiable des commandes.
     */
    public static ObservableList<Order> getOrders() {
        return FXCollections.unmodifiableObservableList(orders);
    }

    /**
     * Obtient une liste non modifiable des éléments.
     *
     * @return Une liste non modifiable des éléments.
     */
    public static ObservableList<Element> getElements() {
        return FXCollections.unmodifiableObservableList(elements);
    }

    /**
     * Obtient l'élément à l'index spécifié dans la liste des éléments.
     *
     * @param index L'index de l'élément à obtenir.
     * @return L'élément à l'index spécifié.
     */
    public static Element getElements(int index) {
        return elements.get(index);
    }

    /**
     * Obtient une liste des noms d'éléments.
     *
     * @return Une liste des noms d'éléments.
     */
    public static ObservableList<String> getElementName(){
        ObservableList<String> codeElements = FXCollections.observableArrayList();
        for(Element element : elements){
            codeElements.add(element.getName());
        }
        return codeElements;
    }
}