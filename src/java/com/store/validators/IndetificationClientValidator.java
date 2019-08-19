/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.validators;

import com.store.facade.ClientFacade;
import java.util.ResourceBundle;
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
@FacesValidator(value="indetificationClientValidator")
public class IndetificationClientValidator implements Validator{
    
    @EJB private ClientFacade clientEJB;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String identificaton = String.valueOf(value);        
        FacesMessage msg;
        try {
            long number = Long.parseLong(identificaton);
            if (number < 0) {
                String msgString = ResourceBundle.getBundle("/Bundle").getString("FieldIntegerPositive");
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msgString,
                        msgString);
                throw new ValidatorException(msg);
            }
            
            if(identificaton.length()>50)
            {
                String msgString = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMax"), 50);
                msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msgString,
                msgString);
                throw new ValidatorException(msg);
            }
            else
            {
               if(clientEJB.identificationAlreadyExists(identificaton))
               {
                    msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists"),
                    ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists"));
                    throw new ValidatorException(msg);
               }
            }
            
        } catch (NumberFormatException n) {
            String msgString = ResourceBundle.getBundle("/Bundle").getString("FieldIntegerPositive");
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msgString,
                    msgString);
            throw new ValidatorException(msg);
        }
        
        
        
    }
}
