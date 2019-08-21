/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.validators;

import com.store.facade.UserFacade;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Wilson Carvajal
 */
@FacesValidator(value="userNameValidator")
public class UserNameValidator implements Validator{

    @EJB private UserFacade userEJB;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
            throws ValidatorException {
        String texto = String.valueOf(value);
        String caracter=texto.charAt(0)+"";
        try
        {
           int numero= Integer.parseInt(caracter);
           String message = ResourceBundle.getBundle("/Bundle").getString("NoNumberFirtsLetterField");
           FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
           throw new ValidatorException(msg); 
           
            
        }catch(NumberFormatException e)
        {
            if(texto.length()>20)
            {
                String message = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMax"),20);
                FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
                throw new ValidatorException(msg); 
            }
            else
            {
                if(texto.length()<3)
                {
                    String message = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMin"),3);
                    FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
                    throw new ValidatorException(msg);  
                }
                else
                {
                    Pattern patron = Pattern.compile("[^A-Za-z_.ñÑ0-9]");
                    Matcher encaja = patron.matcher(texto);        
                    if(encaja.find())
                    {
                        String message = ResourceBundle.getBundle("/Bundle").getString("FieldAlphanumericUnderscoresPeriods");
                        FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
                        throw new ValidatorException(msg);
                    }
                    else
                    {
                        if(userEJB.userNameAlreadyExists(texto))
                        {
                            String message =ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists"); 
                            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
                            throw new ValidatorException(msg);  
                        }  
                    }
                }
            }
        }
    }
    
}
