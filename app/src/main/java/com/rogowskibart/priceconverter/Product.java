package com.rogowskibart.priceconverter;

/**
 * Created by bartoszrogowski on 24/08/2017.
 */

public class Product {
    private String productName;
    private double productAmount;
    private String productType;
    private double pricePerKilo;

    public Product(String productName, double productAmount, String productType, double pricePerKilo) {
        this.productName = productName;
        this.productAmount = productAmount;
        this.productType = productType;
        this.pricePerKilo = pricePerKilo;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductAmount() {
        return String.format("%.2f", productAmount);
    }

    public String getProductType() {
        return productType;
    }

    public String getPricePerKilo() {
        return String.format("%.2f zł", pricePerKilo);
    }
}
