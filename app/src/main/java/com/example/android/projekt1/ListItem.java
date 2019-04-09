package com.example.android.projekt1;

public class ListItem {

    private Integer id;
    private String name;
    private double price;
    private int quantity;
    boolean checked;

    public ListItem() {}

    public ListItem(Integer id, String name, double price, int quantity, boolean checked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checked = checked;
    }
    public ListItem(String name, double price, int quantity, boolean checked){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checked = checked;
    }

    /*ListItem(Integer id,String name, double price, int quantity, int checked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checked = checked;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setPrice(double price) {
        this.price = price;
    }

    void setQuantity(int quantity) {this.quantity = quantity;
    }

    void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public  boolean getChecked() { return checked; }

    /*public boolean isChecked() {
        if (checked == 1)
            return true;
        else
            return false;
    }*/
}
