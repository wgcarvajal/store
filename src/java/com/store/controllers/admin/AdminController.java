/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.controllers.admin;

import com.store.controllers.util.Encrypt;
import com.store.controllers.util.Rol;
import com.store.controllers.util.Util;
import com.store.entities.User;
import com.store.entities.Usergroup;
import com.store.facade.UserFacade;
import com.store.facade.UsergroupFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
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

@ManagedBean(name = "adminController")
@ViewScoped
public class AdminController implements Serializable {
    private User user;
    private String identification;
    private String name;
    private String lastName;
    private String userName;
    private String password;
    private String repeatPassword;
    private String email;
    private String address;
    private String phones;
    private String status;
    private List<String> listStatus;
    
    @EJB private UserFacade userEJB;
    @EJB private UsergroupFacade usergroupEJB;
    
    @PostConstruct
    public void init()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String cliId = paramMap.get("u");
        
        if (cliId != null && !cliId.equals("")) {
            try {
                Long id = Long.parseLong(cliId);
                user = userEJB.find(id);
                if (user == null) {
                    goAdmins();
                }
                else
                {
                    Usergroup usergroup = usergroupEJB.findByUsUserName(user.getUsUserName());
                    if (usergroup == null || !usergroup.getGrouId().getGrouId().equals(Rol.admin)) {
                         goAdmins();
                    }
                }
            } catch (NumberFormatException e) {
                goAdmins();
            }
        } else {
            goAdmins();
        }
    }
    
    private void goAdmins() {
        try {
            String uri = Util.projectPath+"/sAdmin/admin/admins.xhtml?i=0";
            FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User getUser()
    {
        return user;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getListStatus() {
        if(listStatus==null)
        {
            listStatus = new ArrayList<>();
            listStatus.add(ResourceBundle.getBundle("/Bundle").getString("Active"));
            listStatus.add(ResourceBundle.getBundle("/Bundle").getString("Inactive"));
        }
        return listStatus;
    }

    public void setListStatus(List<String> listStatus) {
        this.listStatus = listStatus;
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
    
    public void editIdentification()
    {
        identification = user.getUsIdentification();
        Util.update(":formEditIdentification");
        Util.openDialog("editIdentificationDialog");
    }
    
    public void editName()
    {
        name = user.getUsName();
        Util.update(":formEditName");
        Util.openDialog("editNameDialog");
    }
    
    public void editLastName()
    {
        lastName = user.getUsLastName();
        Util.update(":formEditLastName");
        Util.openDialog("editLastNameDialog");
    }
    
    public void editUserName()
    {
        userName = user.getUsUserName();
        Util.update(":formEditUserName");
        Util.openDialog("editUserNameDialog");
    }
    
    public void editPassword()
    {
        password = null;
        repeatPassword = null;
        Util.update(":formEditPassword");
        Util.openDialog("editPasswordDialog");
    }
    
    public void editEmail()
    {
        email = user.getUsEmail();
        Util.update(":formEditEmail");
        Util.openDialog("editEmailDialog");
    }
    
    public void editStatus()
    {
        status = user.getUsActive()? 
                ResourceBundle.getBundle("/Bundle").getString("Active"):
                ResourceBundle.getBundle("/Bundle").getString("Inactive");
        Util.update(":formEditStatus");
        Util.openDialog("editStatusDialog");
    }
    
    public void editAddress()
    {
        address = user.getUsAddress();
        Util.update(":formEditAddress");
        Util.openDialog("editAddressDialog");
    }
    
    public void editPhones()
    {
        phones = user.getUsPhone();
        Util.update(":formEditPhones");
        Util.openDialog("editPhonesDialog");
    }
    
    public void okIdentification()
    {
        if(!identification.equals(user.getUsIdentification()))
        {
            if(identification.length()>18)
            {
                String message = String.
                        format(ResourceBundle.getBundle("/Bundle").
                                getString("TextLengthMax"),18);
                Util.addErrorMessage(message,message);
            }
            else {
                try {
                    long number = Long.parseLong(identification);
                    if (number < 0) {
                        String msgString = ResourceBundle.getBundle("/Bundle").getString("FieldIntegerPositive");
                        Util.addErrorMessage(msgString, msgString);
                    } else if (userEJB.identificationAlreadyExists(identification)) {
                        String message = ResourceBundle.getBundle("/Bundle").
                                getString("UniqueFieldAlredyExists");
                        Util.addErrorMessage(message, message);
                    } else {
                        String message = ResourceBundle.
                                getBundle("/Bundle").
                                getString("EditSuccessfull");
                        user.setUsIdentification(identification);
                        userEJB.edit(user);
                        Util.addInfoMessage(message, message);
                        Util.update(":formAdmin:panelAdmin");
                        Util.update(":formAdmin:messageGrowl");
                        Util.closeDialog("editIdentificationDialog");
                    }
                } catch (NumberFormatException n) {
                    String msgString = ResourceBundle.getBundle("/Bundle").getString("FieldIntegerPositive");
                    Util.addErrorMessage(msgString, msgString);
                }
            }
        }else
        {
            Util.closeDialog("editIdentificationDialog");
        }
    }
    
    public void okEditName()
    {
        String formatName = Util.formatTextWithSapace(name);
        if(!formatName.equals(user.getUsName()))
        {
            user.setUsName(formatName);
            userEJB.edit(user);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formAdmin:panelAdmin");
            Util.update(":formAdmin:messageGrowl");
            Util.closeDialog("editNameDialog");
        }
        else
        {
            Util.closeDialog("editNameDialog");
        }
    }
    
    public void okEditLastName()
    {
        String formatName = Util.formatTextWithSapace(lastName);
        if(!formatName.equals(user.getUsLastName()))
        {
            user.setUsLastName(formatName);
            userEJB.edit(user);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formAdmin:panelAdmin");
            Util.update(":formAdmin:messageGrowl");
            Util.closeDialog("editLastNameDialog");
        }
        else
        {
            Util.closeDialog("editLastNameDialog");
        }
    }
    
    public void okEditUserName()
    {
        if(!userName.equals(user.getUsUserName()))
        {
            String caracter = userName.charAt(0) + "";
            try {
                int numero = Integer.parseInt(caracter);
                String message = ResourceBundle.getBundle("/Bundle").getString("NoNumberFirtsLetterField");
                Util.addErrorMessage(message, message);

            } catch (NumberFormatException e) {
                if (userName.length() > 20) {
                    String message = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMax"), 20);
                    Util.addErrorMessage(message, message);
                } else if (userName.length() < 3) {
                    String message = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMin"), 3);
                    Util.addErrorMessage(message, message);
                } else {
                    Pattern patron = Pattern.compile("[^A-Za-z_.ñÑ0-9]");
                    Matcher encaja = patron.matcher(userName);
                    if (encaja.find()) {
                        String message = ResourceBundle.getBundle("/Bundle").getString("FieldAlphanumericUnderscoresPeriods");
                        Util.addErrorMessage(message, message);
                    } else if (userEJB.userNameAlreadyExists(userName)) {
                        String message = ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists");
                        Util.addErrorMessage(message, message);
                    }
                    else{
                        Usergroup usuariogroup = usergroupEJB.findByUsUserName(user.getUsUserName());
                        user.setUsUserName(userName);
                        usuariogroup.setUsUserName(userName);
                        userEJB.edit(user);
                        usergroupEJB.edit(usuariogroup);
                        String message = ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull");
                        Util.addInfoMessage(message,message);
                        
                        Util.update(":formAdmin:panelAdmin");
                        Util.update(":formAdmin:messageGrowl");
                        Util.closeDialog("editUserNameDialog");
                    }
                }
            }
        }
        else
        {
            Util.closeDialog("editUserNameDialog");
        }
        
    }
    
    public void okEditPassword()
    {
        String encrypt = Encrypt.sha256(password);
        if(!encrypt.equals(user.getUsPassword()))
        {
            user.setUsPassword(encrypt);
            userEJB.edit(user);
            String message = ResourceBundle.
            getBundle("/Bundle").
            getString("EditSuccessfull");
            Util.addInfoMessage(message,message);

            Util.update(":formAdmin:panelAdmin");
            Util.update(":formAdmin:messageGrowl");
            Util.closeDialog("editPasswordDialog");
        }
        else
        {
            Util.closeDialog("editPasswordDialog");
        }
        
    }
    
    public void okEditEmail()
    {   
        if (!email.equals(user.getUsEmail())) {
            Pattern patron = Pattern.compile("([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))");
            Matcher encaja = patron.matcher(email);
            if (!encaja.find()) {
                String message = ResourceBundle.getBundle("/Bundle").getString("InvalidEmailFormat");
                Util.addErrorMessage(message, message);
            } else if (userEJB.emailAlreadyExists(email)) {
                String message = ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists");
                Util.addErrorMessage(message, message);
            }
            else
            {
                user.setUsEmail(email);
                userEJB.edit(user);
                String message = ResourceBundle.
                getBundle("/Bundle").
                getString("EditSuccessfull");
                Util.addInfoMessage(message,message);

                Util.update(":formAdmin:panelAdmin");
                Util.update(":formAdmin:messageGrowl");
                Util.closeDialog("editEmailDialog");
            }
        } else {
            Util.closeDialog("editEmailDialog");
        }
        
    }
    
    public void okEditStatus()
    {
        String currentStatus = user.getUsActive()? 
                ResourceBundle.getBundle("/Bundle").getString("Active"):
                ResourceBundle.getBundle("/Bundle").getString("Inactive");
        if(!currentStatus.equals(status))
        {
            boolean s = status.equals(ResourceBundle.getBundle("/Bundle")
                    .getString("Active"));
            user.setUsActive(s);
            userEJB.edit(user);
            Util.addInfoMessage(ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"),ResourceBundle.
                    getBundle("/Bundle").
                    getString("EditSuccessfull"));

            Util.update(":formAdmin:panelAdmin");
            Util.update(":formAdmin:messageGrowl");
            Util.closeDialog("editStatusDialog");
            
        }
        else
        {
            Util.closeDialog("editStatusDialog");
        }
    }
    
    public void okEditAddress()
    {
        if (!address.isEmpty()) {
            if (!address.equals(user.getUsAddress())) {
                user.setUsAddress(address);
                userEJB.edit(user);
                Util.addInfoMessage(ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"), ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"));

                Util.update(":formAdmin:panelAdmin");
                Util.update(":formAdmin:messageGrowl");
                Util.closeDialog("editAddressDialog");
            } else {
                Util.closeDialog("editAddressDialog");
            }
        }
        else
        {
            if(user.getUsAddress()!=null)
            {
                user.setUsAddress(null);
                userEJB.edit(user);
                Util.addInfoMessage(ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"), ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"));

                Util.update(":formAdmin:panelAdmin");
                Util.update(":formAdmin:messageGrowl");
                Util.closeDialog("editAddressDialog");
            }
            else
            {
                Util.closeDialog("editAddressDialog");
            }
        }
        
    }
    
    public void okEditPhones()
    {
        if (!phones.isEmpty()) {
            if (!phones.equals(user.getUsPhone())) {
                user.setUsPhone(phones);
                userEJB.edit(user);
                Util.addInfoMessage(ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"), ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"));

                Util.update(":formAdmin:panelAdmin");
                Util.update(":formAdmin:messageGrowl");
                Util.closeDialog("editPhonesDialog");
            } else {
                Util.closeDialog("editPhonesDialog");
            }
        }
        else
        {
            if(user.getUsPhone()!=null)
            {
                user.setUsPhone(null);
                userEJB.edit(user);
                Util.addInfoMessage(ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"), ResourceBundle.
                        getBundle("/Bundle").
                        getString("EditSuccessfull"));

                Util.update(":formAdmin:panelAdmin");
                Util.update(":formAdmin:messageGrowl");
                Util.closeDialog("editPhonesDialog");
            }
            else
            {
                Util.closeDialog("editPhonesDialog");
            }
        }
    }
}
