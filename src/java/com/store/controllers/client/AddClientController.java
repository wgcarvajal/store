/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.client;

import com.store.controllers.util.Util;
import com.store.entities.Client;
import com.store.facade.ClientFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "addClientController")
@ViewScoped
public class AddClientController implements Serializable 
{
    private String identification;
    private String name;
    private String lastName;
    private String address;
    private String phones;
    
    @EJB private ClientFacade clientEJB;

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
    
    public void add()
    {
        Client client = new Client();
        client.setCliIdentity(identification);
        client.setCliName(Util.formatTextWithSapace(name));
        client.setCliLastName(Util.formatTextWithSapace(lastName));
        client.setCliAddress(address);
        client.setCliPhones(phones);
        client.setCliCredit(false);
        
        clientEJB.create(client);
        
        cleanFields();
        showMessageSuccessfull();
    }
    
    private void showMessageSuccessfull()
    {
        Util.addInfoMessage(ResourceBundle.getBundle("/Bundle").getString("Info")+":"
                , ResourceBundle.getBundle("/Bundle").getString("ClientAddSuccessfull"));
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
