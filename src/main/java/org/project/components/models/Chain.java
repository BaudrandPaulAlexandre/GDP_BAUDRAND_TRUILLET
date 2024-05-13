package org.project.components.models;
import java.util.HashMap;
import java.util.Map;

import static org.project.components.models.Bakery.getElements;

public class Chain {

    private String code;
    private String name;
    protected HashMap<Element, Double> elementsInput;
    private HashMap<Element, Double> elementsOutput;

    public Chain(String code, String name, HashMap<Element, Double> elementsInput, HashMap<Element, Double> elementsOutput) {
        this.code = code;
        this.name = name;
        this.elementsInput = elementsInput;
        this.elementsOutput = elementsOutput;
    }
    public String getCode() {
        return this.code;
    }
    public String getName() {
        return this.name;
    }
    public HashMap<Element, Double> getElementsInput() {
        return this.elementsInput;
    }
    public HashMap<Element, Double> getElementsOutput() {
        return this.elementsOutput;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }

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


    public String toString() {
        return this.code + "\n" + this.name + "\n" + this.getInputListFormatted() + "\n" + this.getOutputListFormatted();
    }


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