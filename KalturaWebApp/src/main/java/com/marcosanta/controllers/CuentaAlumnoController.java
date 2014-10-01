/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.controllers;

import com.marcosanta.util.FacesUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Santa
 */
/**
 *
 * @author Santa
 */
@Controller("cuentaAlumno")
@Scope("request")
public class CuentaAlumnoController implements Serializable {

    private String nombre;
    private String username;
    private String pw;
    private String rePw;
    private String correo;
    


    @PostConstruct
    public void init() {

    }
    
    public void test(){
        System.out.println(nombre+" "+username+" "+pw+" "+rePw+" "+correo);
        if(!pw.equals(rePw)){
            System.out.println("no son iguales");
            FacesUtils.addWarningMessage("idError","No coinciden las contraseñas"); 
        }

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getRePw() {
        return rePw;
    }

    public void setRePw(String rePw) {
        this.rePw = rePw;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
