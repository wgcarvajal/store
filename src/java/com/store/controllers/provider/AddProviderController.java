/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.provider;

import com.store.controllers.util.Util;
import com.store.entities.Provider;
import com.store.facade.ProviderFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "addProviderController")
@ViewScoped
public class AddProviderController implements Serializable 
{
    private String name;
    private String address;
    private String phone;
    private String employee;
    private String nit;
    private String rut;
    private String web;
    
    @EJB private ProviderFacade providerEJB;

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
    
    public void add()
    {
        Provider provider = new Provider();
        provider.setProvName(Util.formatText(name));
        provider.setProvAddress(Util.formatText(address));
        provider.setProvPhones(phone);
        
        if(employee!=null && !employee.isEmpty())
            provider.setProvEmployeeName(Util.formatText(employee));
        if(nit!=null && !nit.isEmpty())
            provider.setProvNit(nit);
        if(rut!=null && !rut.isEmpty())
            provider.setProvRut(rut);
        if(web!=null && !web.isEmpty())
            provider.setProvWeb(web);
        providerEJB.create(provider);
        cleanFields();
        showMessageSuccessfull();
    }
    
    public void cancel()
    {
        System.out.println("entro");
        goProviders();
    }
    
    private void showMessageSuccessfull()
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                ResourceBundle.getBundle("/Bundle").getString("Info")+":", 
                ResourceBundle.getBundle("/Bundle").getString("ProviderAddSuccessfull")));
        PrimeFaces.current().ajax().update("form"); 
    }
    
    private void cleanFields()
    {
        address = null;
        name = null;
        employee = null;
        phone = null;
        web = null;
        nit = null;
        rut = null;
    }
    
    private void goProviders()
    {
        try {
            String uri = Util.projectPath+"/admin/provider/providers.xhtml?i=4";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(AddProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
