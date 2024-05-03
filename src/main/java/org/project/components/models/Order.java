package org.project.components.models;

public class Order {

    private Chain chain;
    private int quantity;
    private boolean feasible;

    public Order(Chain chain, int quantity) {
        this.chain = chain;
        this.quantity = quantity;
        this.feasible = chain.isFeasible(quantity);
    }

    public Chain getChain() {
        return this.chain;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public boolean getFeasible() {
        return this.feasible;
    }

    public void setFeasible(boolean b) {
        this.feasible = b;
    }
    public void setQuantity(int i) {
        if (this.quantity - i >= 0) {
            this.quantity -= i;
        }
    }

    public String toString() {
        return "" + chain.toString() + " " + this.quantity + this.feasible;
    }
}