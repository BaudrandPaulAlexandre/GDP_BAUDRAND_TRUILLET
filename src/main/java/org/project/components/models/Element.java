package org.project.components.models;

import java.util.Objects;

/**
 * Représente un élément.
 */
public class Element {

    private String code; // Le code de l'élément
    private String name; // Le nom de l'élément
    private Double quantity; // La quantité de l'élément
    private Unit unit; // L'unité de mesure de l'élément
    private Double pricePurchase; // Le prix d'achat de l'élément
    private Double priceSelling; // Le prix de vente de l'élément

    /**
     * Constructeur de la classe Element.
     *
     * @param code Le code de l'élément.
     * @param name Le nom de l'élément.
     * @param quantity La quantité de l'élément.
     * @param unit L'unité de mesure de l'élément.
     * @param pricePurchase Le prix d'achat de l'élément.
     * @param priceSelling Le prix de vente de l'élément.
     */
    public Element(String code, String name, Double quantity, Unit unit, Double pricePurchase, Double priceSelling) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.pricePurchase = pricePurchase;
        this.priceSelling = priceSelling;
    }

    /**
     * Vérifie si cet élément est égal à un autre objet.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(this.code, element.code);
    }

    /**
     * Obtient le code de l'élément.
     *
     * @return Le code de l'élément.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Obtient le nom de l'élément.
     *
     * @return Le nom de l'élément.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Obtient la quantité de l'élément.
     *
     * @return La quantité de l'élément.
     */
    public Double getQuantity() {
        return this.quantity;
    }

    /**
     * Obtient l'unité de mesure de l'élément.
     *
     * @return L'unité de mesure de l'élément.
     */
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * Obtient le prix d'achat de l'élément.
     *
     * @return Le prix d'achat de l'élément.
     */
    public Double getPricePurchase() {
        return this.pricePurchase;
    }

    /**
     * Obtient le prix de vente de l'élément.
     *
     * @return Le prix de vente de l'élément.
     */
    public Double getPriceSelling() {
        return priceSelling;
    }

    /**
     * Définit le code de l'élément.
     *
     * @param code Le nouveau code de l'élément.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Définit le nom de l'élément.
     *
     * @param name Le nouveau nom de l'élément.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Définit l'unité de mesure de l'élément.
     *
     * @param unit La nouvelle unité de mesure de l'élément.
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Définit le prix d'achat de l'élément.
     *
     * @param pricePurchase Le nouveau prix d'achat de l'élément.
     */
    public void setPricePurchase(Double pricePurchase) {
        this.pricePurchase = pricePurchase;
    }

    /**
     * Définit le prix de vente de l'élément.
     *
     * @param priceSelling Le nouveau prix de vente de l'élément.
     */
    public void setPriceSelling(Double priceSelling) {
        this.priceSelling = priceSelling;
    }

    /**
     * Définit la quantité de l'élément.
     *
     * @param quantity La nouvelle quantité de l'élément.
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
