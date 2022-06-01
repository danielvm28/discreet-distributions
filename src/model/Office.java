package model;

public class Office {
    private String city;
    private int products;

    public Office(String city, int products) {
        this.city = city;
        this.products = products;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public String toString() {
        return city;
    }
}
