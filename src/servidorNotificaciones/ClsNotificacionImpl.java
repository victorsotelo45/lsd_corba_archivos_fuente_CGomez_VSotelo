
package servidorNotificaciones;

import java.util.HashMap;
import servidorNotificaciones.sop_corba.NotificacionIntPackage.ClsMensajeNotificacionDTO;
import java.applet.AudioClip;
import servidorNotificaciones.sop_corba.NotificacionIntOperations;
import servidorAlertas.dao.ClsAlertaDAOImpl;


public class ClsNotificacionImpl implements NotificacionIntOperations{
    HashMap<Integer, GUINotificaciones> GUIAsintomaticos;

    public ClsNotificacionImpl(){
        GUIAsintomaticos = new HashMap();
    }
    
    @Override
    public void notificarRegistro(ClsMensajeNotificacionDTO objMensajeNotificacion){
        
        System.out.println("Desde notificarRegistro()...");
     
        int id, frecuanciaCardiaca, frecuenciaRespiratoria;
        float temperatura;
        String nombres, apellidos, tipo_id, direccion;
        
        id = objMensajeNotificacion.id;
        nombres = objMensajeNotificacion.nombres;
        apellidos = objMensajeNotificacion.apellidos;
        tipo_id = objMensajeNotificacion.tipo_id;
        direccion = objMensajeNotificacion.direccion;
        
        
        //Para cada paciente se crea un GUI
        GUINotificaciones GUI;
        if(GUIAsintomaticos.containsKey(id)){
            GUI = GUIAsintomaticos.get(id); 
            GUI.limpiarIndicadores();
            GUI.limpiarAlertas();
        }else{
            GUI = new GUINotificaciones();
            GUIAsintomaticos.put(id, GUI);
        }
            
        if(!GUI.isVisible())
            GUI.setVisible(true);
        //Enviar al GUI los datos del paciente
        GUI.fijarAsintomatico(tipo_id,id,nombres,apellidos,direccion);
        frecuanciaCardiaca = objMensajeNotificacion.frecuenciaCardiaca;
        frecuenciaRespiratoria = objMensajeNotificacion.frecuenciaRespiratoria;
        temperatura = objMensajeNotificacion.temperatura;
        //System.out.println("Indicadores que generaron la Alerta:");
        //Enviar al GUI los indicadores
        int cont = 0;
        if(frecuanciaCardiaca != 0){
            //System.out.println("Frecuencia Cardiaca: "+frecuanciaCardiaca);
            GUI.fijarFrecuenciaCardiaca(frecuanciaCardiaca);
            cont++;
        }
        if(frecuenciaRespiratoria != 0){
            //System.out.println("Frecuencia Respiratoria: "+frecuenciaRespiratoria);
            GUI.fijarFrecuenciaRespiratoria(frecuenciaRespiratoria);
            cont++;
        }
        if(temperatura != 0){
            //System.out.println("Temperatura: "+temperatura);
            GUI.fijarTemperatura(temperatura);
            cont++;
        }

        String fecha = objMensajeNotificacion.fechaAlerta;
        String hora = objMensajeNotificacion.horaAlerta;
        GUI.fijarHoraFecha(hora, fecha);
        //Enviar al GUI el mensaje del tipo de alerta
        String mensaje = objMensajeNotificacion.mensaje;
        if(cont==2){
            GUI.setImagen(1);
        }else{
            GUI.setImagen(2);
        }
            
        GUI.fijarMensajeTipoAlerta(mensaje);
        AudioClip sonido;
        sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/alerta.wav"));
        sonido.play();
        ClsAlertaDAOImpl alerta = new ClsAlertaDAOImpl();
        GUI.fijarAlerta(alerta.consultarUltimas5Alertas(id));
        
        System.out.println("Saliendo de notificarRegistro()...");
        
    }
   
}
