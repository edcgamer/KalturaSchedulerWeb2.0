/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcosanta.service.impl;

import com.marcosanta.service.RecuperaService;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author Santa
 */
public class RecuperaServiceImpl implements RecuperaService, Serializable{
    
    private static String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$";
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public String generaContrasena() {
        String contrasena = "";
        int longitud = base.length();
        for (int i = 0; i < 10; i++) {
            int numero = (int) (Math.random() * (longitud));
            String caracter = base.substring(numero, numero + 1);
            contrasena = contrasena + caracter;
        }
        return contrasena;
    }
    
    @Override
    public boolean enviaCorreo(String correo) {
        try {
            String contrasena = generaContrasena();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("edcgamer@hotmail.com");
            message.setTo(correo);
            message.setSubject("Contraseña temporal azteca ®");
            message.setText("Su nueva contraseña es: " + contrasena + " se recomienda cambiar de contraseña una vez conectado.\n"
                    + "1.    Inicie sesión con su usuario y la contraseña que se le proporciona en este correo.\n"
                    + "\n"
                    + "Después de realizar este cambio, la contraseña temporal dejara de ser válida para iniciar sesión.");
            mailSender.send(message);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
