package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Product_table")
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    private String name;
    private float price;

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
