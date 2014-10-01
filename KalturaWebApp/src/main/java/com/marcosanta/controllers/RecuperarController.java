/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.controllers;

import com.azteca.persistence.entities.Usuario;
import com.marcosanta.service.ConsultasBDService;
import com.marcosanta.service.RecuperaService;
import com.marcosanta.util.FacesUtils;
import javax.annotation.PostConstruct;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Santa
 */
@Controller("recuperarController")
@Scope("request")
public class RecuperarController {

    private String user;
    private String correo;
    private Usuario usuario;
    
    @Autowired
    ConsultasBDService consultasBDservice;
    
    @Autowired
    RecuperaService recuperaService;
    
    
    
    @PostConstruct
    public void init() {
        usuario = new Usuario();
        user = "";
        correo = "";
    }
    
    
    
    public void recuperaContrasena() {
        usuario = consultasBDservice.findUsuarioByUsername(user);
        if (usuario != null) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                if (recuperaService.enviaCorreo(correo)) {
                    String pw=recuperaService.generaContrasena();
                    System.out.println(pw);
                    String crypt = DigestUtils.sha256Hex(pw);
                    System.out.println("");
                    usuario.setPw(crypt);
                    consultasBDservice.saveUser(usuario);
                    FacesUtils.addInfoMessage("info", "El correo fue enviado de manera correcta");
                } else {
                    FacesUtils.addWarningMessage("error", "El correo no pudo llegar al destinatario");
                }
            } else {
                FacesUtils.addWarningMessage("error", "El correo ingresado, no coincide con el que se tiene registrado");
            }
        } else {
            FacesUtils.addWarningMessage("error", "El usuario ingresado no existe");
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
