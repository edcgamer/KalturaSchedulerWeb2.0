/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosanta.util;

import com.sun.faces.context.FacesFileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Santa
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler parentExceptionHandler;
    //private static final Logger log = Logger.getLogger(CustomExceptionHandler.class);

    CustomExceptionHandler(ExceptionHandler parentExceptionHandler) {
        this.parentExceptionHandler = parentExceptionHandler;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return parentExceptionHandler;
    }

    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
        while (iterator.hasNext()) {
            ExceptionQueuedEvent exceptionEvent = iterator.next();
            Throwable throwable = ((ExceptionQueuedEventContext) exceptionEvent.getSource()).getException();
            try {
                handleException(throwable);
            } catch (IOException ioe) {
                //log.log(Level.ERROR, ioe.getMessage(), ioe);
            } finally {
                iterator.remove();
            }
        }
        getWrapped().handle();
    }

    private void handleException(Throwable throwable) throws IOException {
        if (throwable == null) {
            return;
        }
        String message = null;
        System.out.println("************************");
        System.out.println(throwable);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext externalCtx = ctx.getExternalContext();
        //String path = externalCtx.getRequestServletPath();
        if (causeIs(DataIntegrityViolationException.class, throwable)) {
            FacesUtils.addErrorMessage("La acción se detuvo ya que causa conflictos de integridad en los datos.");
        } else if (causeIs(ViewExpiredException.class, throwable)) {
            message = "Sesion expirada: " + throwable.getMessage();
        } else if (causeIs(NullPointerException.class, throwable)) {
            message = "NullPointer: " + throwable.getMessage();
        } else if (causeIs(FacesFileNotFoundException.class, throwable)) {
            String root = externalCtx.getRequestContextPath();
            externalCtx.redirect(root + "/index.xhtml");
        } else {
            message = throwable.getCause() != null ? throwable.getCause().getMessage() : "Ocurrió un error en el sistema";
        }
        //log.log(Level.ERROR, message, throwable);              
        String root = externalCtx.getRequestContextPath();
        externalCtx.redirect(root + "/error.xhtml?message=" + message);
    }

    private boolean causeIs(Class clazz, Throwable root) {
        Throwable cause;
        while ((cause = root.getCause()) != null) {
            if (clazz.isInstance(cause)) {
                return true;
            } else {
                return causeIs(clazz, cause);
            }
        }
        return false;
    }
}
