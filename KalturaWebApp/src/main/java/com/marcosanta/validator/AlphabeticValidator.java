/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.validator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Santa
 */
@FacesValidator("alfabeticvalidator")
public class AlphabeticValidator implements Validator{
    
     @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Locale local = facesContext.getViewRoot().getLocale();
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)$");
        String cad = (String) o;
        Matcher matcher = pattern.matcher(cad);
        if (cad.trim().equals("")) {
            FacesMessage fm = new FacesMessage(local.getLanguage().equals("es") ? "Valor inválido" : "Invalid value");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(fm);
        } else if (!matcher.matches() && !cad.trim().equals("")) {
            FacesMessage fm = new FacesMessage(local.getLanguage().equals("es") ? "Solo acepta letras" : "Only acept letters");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(fm);
        }
    }
    
}
