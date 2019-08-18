/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.validators;

import java.util.ResourceBundle;
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
@FacesValidator(value="lengthMax100Validator")
public class LengthMax100Validator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String barCode = String.valueOf(value);        
        FacesMessage msg;
        
        if(barCode.length()>100)
        {
            String msgString = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMax"), 100);
            msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,
            msgString,
            msgString);
            throw new ValidatorException(msg);
        }
    }
    
}
