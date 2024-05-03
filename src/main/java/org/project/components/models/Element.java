package org.project.components.models;

import java.util.Objects;
public class Element {

    private String code;
    private String name;
    private Double quantity;
    private Unit unit;
    private Double pricePurchase;
    private Double priceSelling;
    public Element(String code, String name, Double quantity, Unit unit, Double pricePurchase, Double priceSelling) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.pricePurchase = pricePurchase;
        this.priceSelling = priceSelling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(this.code, element.code);
    }

    public String getCode() {
        return this.code;
    }
    public String getName() {
        return this.name;
    }
    public Double getQuantity() {
        return this.quantity;
    }
    public Unit getUnit() {
        return this.unit;
    }
    public Double getPricePurchase() {
        return this.pricePurchase;
    }
    public Double getPriceSelling() {
        return priceSelling;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    public void setPricePurchase(Double pricePurchase) {
        this.pricePurchase = pricePurchase;
    }
    public void setPriceSelling(Double priceSelling) {
        this.priceSelling = priceSelling;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
