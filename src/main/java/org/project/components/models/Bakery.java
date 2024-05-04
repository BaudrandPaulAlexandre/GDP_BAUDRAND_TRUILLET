package org.project.components.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.project.components.utils.UtilsChain.readChain;
import static org.project.components.utils.UtilsElement.readElement;

public class Bakery {

    private static Map<Integer, Bakery> instance = new HashMap<>();
    private static ObservableList<Chain> chains = FXCollections.observableArrayList();
    private static ObservableList<Order> orders = FXCollections.observableArrayList();
    private static ObservableList<Element> elements = FXCollections.observableArrayList();

    //TODO: ?
    private Bakery(){}

    public static Bakery getInstance(Integer bakeryId) {
        if (instance.get(bakeryId) == null) {
            instance.put(bakeryId,new Bakery());
        }
        return instance.get(bakeryId);
    }
    public void loadChains() {
        chains.addAll(readChain());
    }
    public void loadElements() {
        elements.addAll(readElement());
    }


    public static ObservableList<Chain> getChain() {
        return FXCollections.unmodifiableObservableList(chains);
    }
    public static void addToChains(Chain c) {
        chains.add(c);}
    public static boolean chainsContains(Chain c) {
        return chains.contains(c);
    }
    public static int chainsIndexOf(Chain c) {
        return chains.indexOf(c);
    }
    public static void modifyToChaine(Chain chpre, Chain chpost){
        chains.set(chainsIndexOf(chpre),chpost);}
    public static void removeToChain(Chain c) {
        chains.remove(c);
    }
    public static void clearChainsOrder() {
        orders.clear();
    }
    public static void addAllInChainsOrder(Order order) {
        orders.addAll(order);
    }

    public static void addToOrder(Order order) {
        orders.add(order);
    }

    public static ObservableList<Order> getOrders() {
        return FXCollections.unmodifiableObservableList(orders);
    }

    public static int getSizeChainsOrder() {
        return orders.size();
    }

    public static void removeToCommande(Order c) {
        orders.remove(c);
    }

    public static void removeToCommandeByChaine(Chain c) {
        for(Order order : orders) {
            if (order.getChain().equals(c)) {
                removeToCommande(order);
            }
        }
    }

    public static ObservableList<Element> getElements() {
        return FXCollections.unmodifiableObservableList(elements);
    }
    public static void addToElements(Element e) {
        elements.add(e);
    }
    public static void removeToElements(Element e) {
        elements.remove(e);
    }
    public static void modifyToElements(Element pre, Element post) {
        pre.setCode(post.getCode());
        pre.setName(post.getName());
        pre.setUnit(post.getUnit());
        pre.setQuantity(post.getQuantity());
        pre.setPricePurchase(post.getPricePurchase());
        pre.setPriceSelling(post.getPriceSelling());
    }

    public static Double addQuantitiesOfElement(Element element) {
        Double total = elements.get(elementIndexOf(element)).getQuantity() + element.getQuantity();
        elements.get(elementIndexOf(element)).setQuantity(total);
        return total;
    }

    public static boolean elementsContains(Element element) {
        return elements.contains(element);
    }

    public static int elementIndexOf(Element element) {
        return elements.indexOf(element);
    }

    public static Element getElements(int index) {
        return elements.get(index);
    }

    public static Element getElementByCode(String code){
        for(Element element : elements){
            if (Objects.equals(element.getCode(), code)) return element;
        }
        return null;
    }

    public static Element getElementByName(String name){
        for(Element element : elements){
            if (Objects.equals(element.getName(), name)) return element;
        }
        return null;
    }

    public static void addAllElement(List<Element> element) {
        elements.addAll(element);
    }

    public static ObservableList<String> getElementName(){
        ObservableList<String> codeElements = FXCollections.observableArrayList();
        for(Element element : elements){
            codeElements.add(element.getName());
        }
        return codeElements;
    }
}