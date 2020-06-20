//package sop_corba;
package servidorNotificaciones.servidor;

/**
* sop_corba/CountImpl.java .
*/

import servidorNotificaciones.sop_corba.GestionNotificacionesOperations;
import servidorNotificaciones.sop_corba.GestionNotificacionesPackage.ClsMensajeNotificacionDTO;

//public class CountImpl extends CountPOA 
public class GestionNotificacionesImpl implements GestionNotificacionesOperations
{

    public GestionNotificacionesImpl() {
    }
    
    

    @Override
    public void notificarRegistro(ClsMensajeNotificacionDTO objNotificacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
} 
