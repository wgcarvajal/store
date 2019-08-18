/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.validators;

import com.store.facade.ProductFacade;
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
@FacesValidator(value="barCodeValidator")
public class BarCodeValidator implements Validator{
    
    @EJB private ProductFacade productEJB;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String barCode = String.valueOf(value);        
        FacesMessage msg;
        if(barCode.length()>50)
        {
            String msgString = String.format(ResourceBundle.getBundle("/Bundle").getString("TextLengthMax"), 50);
            msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,
            msgString,
            msgString);
            throw new ValidatorException(msg);
        }
        else
        {
           if(productEJB.barcodeAlreadyExists(barCode))
           {
                msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,
                ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists"),
                ResourceBundle.getBundle("/Bundle").getString("UniqueFieldAlredyExists"));
                throw new ValidatorException(msg);
           }
        }
    }
}
