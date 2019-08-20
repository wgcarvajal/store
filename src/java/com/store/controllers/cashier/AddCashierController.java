/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.cashier;

import com.store.controllers.util.Util;
import com.store.entities.User;
import com.store.facade.UserFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Wilson Carvajal
 */@ManagedBean(name = "addCashierController")
@ViewScoped
public class AddCashierController implements Serializable 
{
    private String identification;
    private String name;
    private String lastName;
    private String address;
    private String phones;
    private String userName;
    private String password;
    private String repeatPassword;
    private String email;
    
    @EJB private UserFacade userEJB;

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    public void add()
    {
        User user = new User();
        user.setUsIdentification(identification);
        user.setUsName(Util.formatTextWithSapace(name));
        user.setUsLastName(Util.formatTextWithSapace(lastName));
        user.setUsAddress(address);
        user.setUsPhone(phones);
        
        userEJB.create(user);
        
        cleanFields();
        showMessageSuccessfull();
    }
    
    private void showMessageSuccessfull()
    {
        Util.addInfoMessage(ResourceBundle.getBundle("/Bundle").getString("Info")+":"
                , ResourceBundle.getBundle("/Bundle").getString("CashierAddSuccessfull"));
        Util.update("form");
    }
    
    private void cleanFields()
    {
        identification = null;
        name = null;
        lastName = null;
        address = null;
        phones = null;
    }
}
