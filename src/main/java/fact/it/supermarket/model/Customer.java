// Dina Boshnaq
// r0823887
// 1ITF 1ACS
package fact.it.supermarket.model;

import java.util.ArrayList;

public class Customer extends Person{
    private int cardNumber = -1;
    private int yearOfBirth;
    private ArrayList<String> shoppingList = new ArrayList<>();

    public Customer(String firstName, String surName) {
        super(firstName, surName);
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }

    public boolean addToShoppingList(String productName) {
        if (shoppingList.size() < 5) {
            shoppingList.add(productName);
            return true;
        } else {
            return false;
        }
    }

    public int getNumberOnShoppingList() {
        return shoppingList.size();
    }

    public String toString() {
        return "Customer " + super.toString() + " with card number " + cardNumber;
    }


}
