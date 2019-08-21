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
@FacesValidator(value="emailValidator")
public class EmailValidator implements Validator
{
    @EJB
    private UserFacade userEJB;
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        
        String texto = String.valueOf(value);        
        Pattern patron = Pattern.compile("([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))");
        Matcher encaja = patron.matcher(texto);        
        if(!encaja.find())
        {
            String message = ResourceBundle.getBundle("/Bundle").getString("InvalidEmailFormat");
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
            throw new ValidatorException(msg);
        }
        else
        {
            if(userEJB.emailAlreadyExists(texto))
            {
                String message = ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists");
                FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);
                throw new ValidatorException(msg);
            }
        }
        
    }
}
