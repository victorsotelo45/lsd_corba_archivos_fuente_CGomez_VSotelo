
package servidorNotificaciones;

//import servidorAlertas.dao.ClsAsintomaticoDAOImpl;
import java.util.ArrayList;
import java.util.HashMap;
//import servidorAlertas.dao.AsintomaticoDAOInt;
import servidorAlertas.dao.ClsAlertaDTO;
import servidorNotificaciones.sop_corba.NotificacionIntPackage.ClsMensajeNotificacionDTO;
import java.applet.AudioClip;
import servidorAlertas.sop_corba.ClsAsintomaticoDTO;
import servidorNotificaciones.sop_corba.NotificacionIntOperations;
import servidorAlertas.dao.ClslAlertaDAOImpl;


public class ClsNotificacionImpl implements NotificacionIntOperations{
    HashMap<Integer, GUINotificaciones> GUIAsintomaticos;

    public ClsNotificacionImpl(){
        GUIAsintomaticos = new HashMap();
    }
    
    @Override
    public void notificarRegistro(ClsMensajeNotificacionDTO objMensajeNotificacion){
        
        System.out.println("Desde notificarRegistro()...");
     
        ClsAsintomaticoDTO pacienteAsintomatico = objMensajeNotificacion.pacienteAsintomatico;
        int frecuanciaCardiaca, frecuenciaRespiratoria;
        float temperatura;
        
        /*System.out.println("Alerta Generada");
        System.out.println("Id: "+pacienteAsintomatico.getId());
        System.out.println("Nombres: "+pacienteAsintomatico.getNombres());
        System.out.println("Apellidos: "+pacienteAsintomatico.getApellidos());
        System.out.println("Direccion: "+pacienteAsintomatico.getDireccion());*/
        //Para cada paciente se crea un GUI
        GUINotificaciones GUI;
        if(GUIAsintomaticos.containsKey(pacienteAsintomatico.id)){
            GUI = GUIAsintomaticos.get(pacienteAsintomatico.id); 
            GUI.limpiarIndicadores();
            //GUI.limpiarAlertas();
        }else{
            GUI = new GUINotificaciones();
            GUIAsintomaticos.put(pacienteAsintomatico.id, GUI);
        }
            
        if(!GUI.isVisible())
            GUI.setVisible(true);
        //Enviar al GUI los datos del paciente
        GUI.fijarAsintomatico(pacienteAsintomatico.tipo_id,pacienteAsintomatico.id,pacienteAsintomatico.nombres,
        pacienteAsintomatico.apellidos, pacienteAsintomatico.direccion);
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
        ClslAlertaDAOImpl alerta = new ClslAlertaDAOImpl();
        GUI.fijarAlerta(alerta.consultarUltimas5Alertas(pacienteAsintomatico.id));
        
        System.out.println("Saliendo de notificarRegistro()...");
        
    }
   
}
