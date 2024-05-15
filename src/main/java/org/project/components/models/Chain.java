package org.project.components.models;
import java.util.HashMap;
import java.util.Map;

import static org.project.components.models.Bakery.getElements;
/**
 * Représente une chaîne de production.
 */
public class Chain {

    private String code; // Le code de la chaîne de production
    private String name; // Le nom de la chaîne de production
    protected HashMap<Element, Double> elementsInput; // Les éléments en entrée de la chaîne de production avec leur quantité
    private HashMap<Element, Double> elementsOutput; // Les éléments en sortie de la chaîne de production avec leur quantité

    /**
     * Constructeur de la classe Chain.
     *
     * @param code Le code de la chaîne de production.
     * @param name Le nom de la chaîne de production.
     * @param elementsInput Les éléments en entrée de la chaîne de production avec leur quantité.
     * @param elementsOutput Les éléments en sortie de la chaîne de production avec leur quantité.
     */
    public Chain(String code, String name, HashMap<Element, Double> elementsInput, HashMap<Element, Double> elementsOutput) {
        this.code = code;
        this.name = name;
        this.elementsInput = elementsInput;
        this.elementsOutput = elementsOutput;
    }

    /**
     * Obtient le code de la chaîne de production.
     *
     * @return Le code de la chaîne de production.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Obtient le nom de la chaîne de production.
     *
     * @return Le nom de la chaîne de production.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Obtient les éléments en entrée de la chaîne de production avec leur quantité.
     *
     * @return Les éléments en entrée de la chaîne de production avec leur quantité.
     */
    public HashMap<Element, Double> getElementsInput() {
        return this.elementsInput;
    }

    /**
     * Obtient les éléments en sortie de la chaîne de production avec leur quantité.
     *
     * @return Les éléments en sortie de la chaîne de production avec leur quantité.
     */
    public HashMap<Element, Double> getElementsOutput() {
        return this.elementsOutput;
    }

    /**
     * Définit le code de la chaîne de production.
     *
     * @param code Le nouveau code de la chaîne de production.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Obtient une représentation textuelle formatée des éléments d'entrée de la chaîne de production.
     *
     * @return Une chaîne de caractères représentant les éléments d'entrée avec leur quantité formatés.
     */
    public String getInputListFormatted() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : elementsInput.entrySet()) {
            str.append("(");
            str.append(entry.getKey().getCode());
            str.append(", ");
            str.append(entry.getValue());
            str.append("); ");
        }
        str.setLength(str.length() - 2);
        return str.toString();
    }

    /**
     * Obtient une représentation textuelle formatée des éléments de sortie de la chaîne de production.
     *
     * @return Une chaîne de caractères représentant les éléments de sortie avec leur quantité formatés.
     */
    public String getOutputListFormatted() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> input : elementsOutput.entrySet()) {
            str.append("(");
            str.append(input.getKey().getCode());
            str.append("), ");
            str.append(input.getValue());
            str.append("; ");
        }
        str.setLength(str.length() - 2);
        return str.toString();
    }

    /**
     * Obtient une représentation CSV des éléments d'entrée de la chaîne de production.
     *
     * @return Une chaîne de caractères représentant les éléments d'entrée en format CSV.
     */
    public String getInputListCSV(){
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> input : elementsInput.entrySet()) {
            if(str.length() > 0){
                str.append(",");
            }
            str.append("(");
            str.append(input.getKey().getCode());
            str.append(",");
            str.append(input.getValue());
            str.append(")");
        }
        return str.toString();
    }

    /**
     * Obtient une représentation CSV des éléments de sortie de la chaîne de production.
     *
     * @return Une chaîne de caractères représentant les éléments de sortie en format CSV.
     */
    public String getOutputListCSV(){
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : elementsOutput.entrySet()) {
            str.append("(");
            str.append(entry.getKey().getCode());
            str.append(",");
            str.append(entry.getValue());
            str.append(")");
        }
        return str.toString();
    }

    /**
     * Obtient une représentation textuelle de la chaîne de production.
     *
     * @return Une chaîne de caractères représentant la chaîne de production avec ses éléments d'entrée et de sortie.
     */
    public String toString() {
        return this.code + "\n" + this.name + "\n" + this.getInputListFormatted() + "\n" + this.getOutputListFormatted();
    }

    /**
     * Vérifie si la production de la chaîne est réalisable pour une quantité donnée.
     *
     * @param qtt La quantité à produire.
     * @return true si la production est réalisable, sinon false.
     */
    public boolean isFeasible(int qtt) {
        boolean feasible = false;

        for (Map.Entry<Element, Double> currentElement : this.elementsInput.entrySet()) {
            Element element = currentElement.getKey();
            if (getElements().contains(element)) {
                int index = getElements().indexOf(element);
                if (getElements().get(index).getQuantity()-(currentElement.getValue() * qtt) >= 0) {
                    feasible = true;
                } else {
                    System.out.println("Quantité insuffisante");
                    return false;
                }
            } else {
                System.out.println("Erreur, element inexistant");
            }
        }
        return feasible;
    }

}