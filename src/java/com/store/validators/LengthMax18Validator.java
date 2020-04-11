/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.validators;

/*
 * @author Wilson Carvajal
 */

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="lengthMax18Validator")
public class LengthMax18Validator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String barCode = String.valueOf(value);        
        FacesMessage msg;
        
        if(barCode.length()>20)
        {
            String msgString = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMax"), 18);
            msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,
            msgString,
            msgString);
            throw new ValidatorException(msg);
        }
    }
}
