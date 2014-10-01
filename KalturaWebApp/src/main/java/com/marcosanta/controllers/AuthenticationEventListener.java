/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.controllers;


import com.azteca.persistence.entities.Usuario;
import com.azteca.persistence.repository.SistemaUsuarioRepository;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Santa
 */
@Component
public class AuthenticationEventListener implements ApplicationListener<ApplicationEvent>, Serializable {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationEventListener.class);
    @Autowired
    private SistemaUsuarioRepository usuarioService;


      private void loguear(String tipo, String username) {   
        Usuario u = usuarioService.findByUsername(username);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent successEvent = (AuthenticationSuccessEvent) event;
            UserDetails ud = (UserDetails) successEvent.getAuthentication().getPrincipal();
            loguear("LOGIN", ud.getUsername());
            logger.info("Ha iniciado sesion " + ud.getUsername());
        } else if (event instanceof AuthenticationFailureBadCredentialsEvent || event instanceof AuthenticationFailureServiceExceptionEvent) {       
            logger.info("Login incorrecto");
            FacesContext.getCurrentInstance().addMessage("login_failed", new FacesMessage("Login failed"));
        } else if (event instanceof SessionDestroyedEvent) {
            SessionDestroyedEvent sessionEvent = (SessionDestroyedEvent) event;
            Iterator<SecurityContext> it = sessionEvent.getSecurityContexts().iterator();
            while (it.hasNext()) {
                SecurityContext context = it.next();
                if (context != null) {
                    logger.info("Logout " + (String) context.getAuthentication().getName());
                    loguear("LOGOUT", context.getAuthentication().getName());
                }
            }
        } else if (event instanceof HttpSessionDestroyedEvent) {
            HttpSessionDestroyedEvent ev = (HttpSessionDestroyedEvent) event;
            if (ev.getSession() != null) {
                ev.getSession().invalidate();
            }
        }
    }
}
