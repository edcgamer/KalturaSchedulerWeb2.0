/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.controllers;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Santa
 */
@Controller("loginController")
@Scope("request")
public class LoginController implements Serializable {
    
     private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Realiza la autenticación haciendo uso de spring security
     *
     * @return null, solo se sigue lo que pide el módulo de spring security
     * @throws IOException
     * @throws ServletException
     */
    public String login() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    /**
     * Realiza el logout con spring security
     *
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public String logout() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_logout");
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    /**
     * Valida que haya un usuario autenticado
     *
     * @return resultado de la validacion
     */
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    /**
     * Devuelve al usuario conectado
     *
     * @return username del usuario conectado
     */
    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
}
