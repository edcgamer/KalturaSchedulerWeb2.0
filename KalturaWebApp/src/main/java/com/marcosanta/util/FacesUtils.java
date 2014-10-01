package com.marcosanta.util;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author Santa
 */
public class FacesUtils {

    /**
     * Agrega un mensaje de error sin especificar a qué componente de mensaje
     * @param message El mensaje a mostrar
     */
    public static void addErrorMessage(String message) {
        addErrorMessage(null, message);
    }

    /**
     * Muestra un mensaje de error en un componente de mensaje específico
     * @param id Id del componente de mensaje que mostrará el error
     * @param message Mensaje a mostrar
     */
    public static void addErrorMessage(String id, String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, id, message);
    }
    
     public static void addWarningMessage(String id, String message) {
        addMessage(FacesMessage.SEVERITY_WARN, id, message);
    }

    /**
     * Muestra un mensaje informativo sin especificar componente de mensaje
     * @param message El mensaje a mostrar
     */
    public static void addInfoMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, null, message);
    }

    public static void addWarningMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, null, message);
    }
    
    /**
     * Muestra un mensaje informativo a un componente de mensaje específico
     * @param id Id del componente que mostrará el mensaje
     * @param message Menaje que se mostrará 
     */
    public static void addInfoMessage(String id, String message) {
        addMessage(FacesMessage.SEVERITY_INFO, id, message);
    }

    /**
     * Muestra un mensaje de cualquier tipo a un componente de mensaje específico
     * @param severity Tipo de mensaje según la enumeración Sevverity
     * @param id Id del mensaje que mostrará el mensaje
     * @param message  Mensaje a mostrar
     */
    public static void addMessage(Severity severity, String id, String message) {
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(severity, "",message ));
    }

    /**
     * Indica si la última validación realizada en la vista fue fallida
     * @return Booleano que indica si la validación fue fallida
     */
    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    /**
     * Indica si la última validación realizada en la vista fue exitosa
     * @return Booleano que indica si la validación fue exitosa
     */
    public static boolean isValidationSuccess() {
        return !FacesContext.getCurrentInstance().isValidationFailed();
    }

    /**
     * Indica si la vista actual fue resultado de un Postback
     * @return Resultado de la evaluación
     */
    public static boolean isPostback() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    /**
     * Obtiene un mensaje específico de algún Resource Bundle usado en la vista
     * @param resourceBundleName Nombre del resource bundle que contiene el mensaje
     * @param resourceBundleKey Llave del mensaje que se quiere recuperar
     * @return Mensaje recuperado. Null en caso de haber pasado alguno de los parámetros inválidos
     */
    public static String getBundleMessage(String resourceBundleName, String resourceBundleKey) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{"+resourceBundleName+"['"+resourceBundleKey+"']}", String.class);
    }
    
    /**
     * Busca un Resource Bundle en el conexto de JSF
     * @param name El nombre o ruta del bundle que se quiere recuperar
     * @return El resource bundle encontrado. Null en caso de no encontrarse
     */
    public static ResourceBundle getResourceBundle(String name){
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, name);
    }
    
    public static Locale getCurrentLocale(){
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }
    
    public static String getCurrentLanguage(){
        return FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
    }
}
