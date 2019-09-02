 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.cashier;

import com.store.controllers.util.Encrypt;
import com.store.controllers.util.Util;
import com.store.entities.Groupp;
import com.store.entities.User;
import com.store.entities.Usergroup;
import com.store.facade.UserFacade;
import com.store.facade.UsergroupFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Wilson Carvajal
 */
@ManagedBean(name = "addCashierController")
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
    @EJB private UsergroupFacade userGroupEJB;

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
        user.setUsUserName(userName);
        user.setUsPassword(Encrypt.sha256(password));
        user.setUsEmail(email);
        if(address!=null && !address.isEmpty())
            user.setUsAddress(address);
        if(phones!=null && !phones.isEmpty())
            user.setUsPhone(phones);
        
        userEJB.create(user);
        Groupp groupp = new Groupp("cashier");
        Usergroup usergroup = new Usergroup();
        usergroup.setGrouId(groupp);
        usergroup.setUsId(user);
        usergroup.setUsUserName(userName);
        
        userGroupEJB.create(usergroup);
        
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
        email = null;
        password = null;
        repeatPassword = null;
        userName = null;
    }
    
    public void validatePassword(FacesContext arg0, UIComponent arg1, Object arg2)throws ValidatorException {
      
        this.password=String.valueOf(arg2);
    }
    
   public void validateRepeatPassword(FacesContext arg0, UIComponent arg1, Object arg2)throws ValidatorException 
   {
      String texto = String.valueOf(arg2);      
      if (!(texto.equals(this.password))) {
         String message = ResourceBundle.getBundle("/Bundle").getString("PasswordMach");
         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message));
      }
   } 
}
