/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;


/**
 *
 * @author Santa
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory{

    private ExceptionHandlerFactory parent;
 

    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {

        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler eh = parent.getExceptionHandler();
        eh = new CustomExceptionHandler(eh);
        return eh;
    }

    
    
}
