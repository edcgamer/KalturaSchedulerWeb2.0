/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.controllers;

import com.azteca.persistence.entities.SistemaModulo;
import com.azteca.persistence.entities.Usuario;
import com.azteca.persistence.repository.SistemaUsuarioRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Santa
 */
@Controller("appCredentials")
@Scope("session")
public class AppCredentials implements Serializable {

    private static Pattern pattern = Pattern.compile("^([\\w\\dÁÉÍÓÚáéíóúñÑ:\\+\\- ]+)$");

    /**
     * @return the pattern
     */
    public static Pattern getPattern() {
        return pattern;
    }

    /**
     * @param aPattern the pattern to set
     */
    public static void setPattern(Pattern aPattern) {
        pattern = aPattern;
    }
    private SpringUser user;
    private Date fechaActual = new Date();
    private String idioma;
    private Usuario usuarioReal;
    private List<SistemaModulo> listaModulos;
    private String cadenaModulos;
    
    @Autowired
    private SistemaUsuarioRepository usuarioRepository;

    public AppCredentials() {
    }

    /**
     * Valida que el usuario tenga permisos visualizar el contenido Inicializa
     * el intervalo y la query Obtiene la institucion con la que se esta
     * trabajando, si la institucion es DSA trae todas las instituciones
     * Reconoce el idioma que esta como activo en la vista
     */
    @PostConstruct
    public void init() {
        
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        String usuario = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        this.user = (SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.usuarioReal=usuarioRepository.findByUsername(this.user.getUsername());
        this.listaModulos = new ArrayList<>();
        if (this.usuarioReal.getRol().getListaModulos() != null) {
            listaModulos = usuarioReal.getRol().getListaModulos();
        }
        cadenaModulos= modulosCad();
        idioma = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
    }

    /**
     *
     * @return SelectItem[] reconoce el idioma que esta como activo en la vista,
     * para usarlo tambien en la busqueda general
     */
    public static SelectItem[] obtenerIdiomas() {
        Locale local = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        SelectItem[] items = new SelectItem[2];
        switch (local.getLanguage()) {
            case "es":
                items[0] = new SelectItem("es", "Español");
                items[1] = new SelectItem("en", "English");
                break;
            case "en":
                items[0] = new SelectItem("en", "English");
                items[1] = new SelectItem("es", "Español");
                break;
        }
        return items;
        
    }

    public void cambiaLocal(ValueChangeEvent event) {
        idioma = event.getNewValue().toString();
    }

    /**
     * Cambia el idioma actual a Español (es)
     *
     */
    public void cambiaEs() {
        idioma = "es";
    }

    /**
     * Cambia el idioma actual por Inglés (en)
     */
    public void cambiaEn() {
        idioma = "en";
    }

    public boolean asignaPermisos(String modulo) {
        return cadenaModulos.contains(modulo);
//        return true;
    }
    
    public String modulosCad(){
        StringBuilder sb= new StringBuilder();
        for(SistemaModulo mod:listaModulos){
            sb.append(mod.getNombre());
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Valida que el usuario con la secion activa tenga acceso a aeste módulo
     *
     * @return
     */
    public static void validaAcceso(Boolean acceso) {
        try {
            if (!acceso) {
                /* alternativa de uso para la redirección
                 FacesContext fc = FacesContext.getCurrentInstance();
                 ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler)fc.getApplication().getNavigationHandler();
                 nav.performNavigation("home");
                 */
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String url = context.encodeActionURL(context.getRequestContextPath() + "/index.xhtml");
                context.redirect(url);
            }
        } catch (Exception ex) {
            Logger.getLogger(AppCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public SpringUser getUser() {
        return user;
    }

    public void setUser(SpringUser user) {
        this.user = user;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<SistemaModulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<SistemaModulo> listaModulos) {
        this.listaModulos = listaModulos;
    }
    

    /**
     * @return the usuarioReal
     */
    public Usuario getUsuarioReal() {
        return usuarioReal;
    }


}
