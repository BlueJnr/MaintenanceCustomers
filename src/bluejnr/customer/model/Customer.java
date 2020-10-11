/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluejnr.customer.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author kcuadror
 */
public class Customer {

    private final IntegerProperty idCustomer;
    private final StringProperty names;
    private final StringProperty surnames;
    private final StringProperty email;
    private final StringProperty telephone;

    public Customer() {
        this(0, null, null, null, null);
    }

    public Customer(int idCustomer, String names, String surnames, String email, String telephone) {
        this.names = new SimpleStringProperty(names);
        this.surnames = new SimpleStringProperty(surnames);

        this.idCustomer = new SimpleIntegerProperty(idCustomer);
        this.email = new SimpleStringProperty(email);
        this.telephone = new SimpleStringProperty(telephone);
    }

    public int getIdCustomer() {
        return idCustomer.get();
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer.set(idCustomer);
    }

    public IntegerProperty idCustomerProperty() {
        return idCustomer;
    }

    public String getNames() {
        return names.get();
    }

    public void setNames(String names) {
        this.names.set(names);
    }

    public StringProperty namesProperty() {
        return names;
    }

    public String getSurnames() {
        return surnames.get();
    }

    public void setSurnames(String surnames) {
        this.surnames.set(surnames);
    }

    public StringProperty surnamesProperty() {
        return surnames;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    @Override
    public String toString() {
        return "Customer{" + "idCustomer=" + idCustomer + ", names=" + names + ", surnames=" + surnames + ", email=" + email + ", telephone=" + telephone + '}';
    }

}
