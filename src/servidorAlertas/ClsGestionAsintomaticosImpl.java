
package servidorAlertas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import servidorAlertas.dao.AlertaDAOInt;
import servidorAlertas.dao.AsintomaticoDAOInt;
import servidorAlertas.dao.ClsAlertaDTO;
import servidorAlertas.dao.ClsAsintomaticoDAOImpl;
import servidorAlertas.dao.ClsAlertaDAOImpl;
import servidorAlertas.sop_corba.ClsAsintomaticoDTO;
import servidorAlertas.sop_corba.GestionAsintomaticosIntOperations;
import servidorNotificaciones.sop_corba.NotificacionInt;
import servidorNotificaciones.sop_corba.NotificacionIntHelper;
import servidorNotificaciones.sop_corba.NotificacionIntPackage.ClsMensajeNotificacionDTO;


public class ClsGestionAsintomaticosImpl implements GestionAsintomaticosIntOperations {

    
    private static NotificacionInt RefNotificaciones;
    private ArrayList<ClsAsintomaticoDTO> asintomaticos;
    private int numeroPacientes;

    public ClsGestionAsintomaticosImpl(ORB orb){
        this.asintomaticos = new ArrayList<ClsAsintomaticoDTO>();
        this.numeroPacientes=0;
        
        try
        {
            // obtiene la base del naming context
            org.omg.CORBA.Object objRef = 
            orb.resolve_initial_references("NameService");
            // usa NamingContextExt en lugar de NamingContext.Esto es 
            // parte del Interoperable naming Service.  
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // *** Resuelve la referencia del objeto en el N_S ***
            String name = "objNotificaciones";
            RefNotificaciones = NotificacionIntHelper.narrow(ncRef.resolve_str(name));
            //System.out.println("Obtenido el manejador sobre el servidor de objetos: " + RefNotificaciones);

            }catch(InvalidName | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound e)
            {
                System.out.println("ERROR : " + e) ;
                e.printStackTrace(System.out);

            }
             
    }

    public void setNumeroPacientes(int numeroPacientes) {
        this.numeroPacientes = numeroPacientes;
    }

    public int getNumeroPacientes() {
        return this.numeroPacientes;
    }
    
    @Override
    public boolean registrarAsintomatico(ClsAsintomaticoDTO objAsintomaticoCllbck){
            
        System.out.println("Desde registrarAsintomatico()...");
        int id = objAsintomaticoCllbck.id;
        AsintomaticoDAOInt objAsintomaticoDAO = new ClsAsintomaticoDAOImpl();
        ClsAsintomaticoDTO pacienteAsintomatico = consultarAsintomatico(id);
        boolean  bandera = false;
        if(pacienteAsintomatico.id == -1)       
        {   if(this.asintomaticos.size() < this.numeroPacientes)
            {
                  bandera = this.asintomaticos.add(objAsintomaticoCllbck);
                  objAsintomaticoDAO.registrarAsintomaticoDTO(objAsintomaticoCllbck);
                  
                  

            }else
            {
                JOptionPane.showMessageDialog(null, "Ya se registraron el maximo numero de pacientes: "+this.numeroPacientes+" !!!");
            }
        }else
        {
            JOptionPane.showMessageDialog(null, "El paciente con id "+id+" ya se encuentra registrado!!!");
        }
        System.out.println("Saliendo de registrarAsintomatico()...");
        return bandera;
           
    }
    
    @Override
    public ClsAsintomaticoDTO consultarAsintomatico(int id)
    {
            System.out.println("Desde consultarAsintomatico()...");
            
            ClsAsintomaticoDTO pacienteAsintomatico = new ClsAsintomaticoDTO("","","",-1,"",null);
            for(ClsAsintomaticoDTO objAsintomaticoCllbck:asintomaticos)
            {
                if(objAsintomaticoCllbck.id == id)
                {    pacienteAsintomatico = objAsintomaticoCllbck;
                      break;  
                }
            }

            System.out.println("Saliendo de consultarAsintomatico()...");

            return pacienteAsintomatico;
		
    }

    @Override
    public boolean enviarIndicadores(int id, int frecuenciaCardiaca, int frecuenciaRespiratoria,float temperatura){
            
        System.out.println("Desde enviarIndicadores()...");
        String nombres, apellidos, tipo_id, direccion, mensaje;
        String fechaAlerta, horaAlerta, strDateFormatFecha, strDateFormatHora;
        Date fechaActual;
        SimpleDateFormat objSDF;
        strDateFormatFecha = "dd-MM-yyyy";
        strDateFormatHora = "HH:mm:ss";
        fechaActual = new Date();
        objSDF = new SimpleDateFormat(strDateFormatFecha);
        fechaAlerta = objSDF.format(fechaActual);
        objSDF = new SimpleDateFormat(strDateFormatHora);
        horaAlerta =  objSDF.format(fechaActual);
        boolean bandera = false;
        int puntuacionIndicadores = 0; 
        int puntuacionFrecCardiaca = 0, puntuacionFrecRespiratoria = 0, puntuacionTemperatura = 0;
        AlertaDAOInt objAlertaDAOInt;  
        ClsAlertaDTO objAlerta;
        ClsMensajeNotificacionDTO objMensajeNotificacion;
                
        ClsAsintomaticoDTO objAsintomaticoCllbck = consultarAsintomatico(id);
        if(objAsintomaticoCllbck.id != -1)
        {
            if(frecuenciaCardiaca < 60 || frecuenciaCardiaca > 80) 
            {
                puntuacionIndicadores++;
                puntuacionFrecCardiaca = 1;
            }
            if(frecuenciaRespiratoria < 70 || frecuenciaRespiratoria > 90)
            { 
                puntuacionIndicadores++;
                puntuacionFrecRespiratoria = 1;
            }
            if(temperatura < 36.2 || temperatura > 37.2) 
            {
                puntuacionIndicadores++;
                puntuacionTemperatura = 1;
            }
            
            nombres = objAsintomaticoCllbck.nombres;
            apellidos = objAsintomaticoCllbck.apellidos;
            tipo_id = objAsintomaticoCllbck.tipo_id;
            direccion = objAsintomaticoCllbck.direccion;
            
            
            objAlertaDAOInt = new ClsAlertaDAOImpl();
            
            if(puntuacionIndicadores == 0 || puntuacionIndicadores == 1)
            {
                System.out.println("El paciente "+nombres+" "+apellidos+" identificado con ["+tipo_id+"]["+id+"] debe continuar monitorizacion!!!");
                
            }
            
            if(puntuacionIndicadores == 2)
            {
                mensaje = "Alerta, el personal médico debe visitar al paciente "+nombres+" "+apellidos+" identificado con ["+tipo_id+"]["+id+"]!!!";
                objAsintomaticoCllbck.objAsintomaticoCllbck.notificarMensajeCllbck(mensaje);
                if(puntuacionFrecCardiaca == 0) frecuenciaCardiaca = 0;
                if(puntuacionFrecRespiratoria == 0) frecuenciaRespiratoria = 0;
                if(puntuacionTemperatura == 0) temperatura = 0;
                
                objAlerta = new ClsAlertaDTO(id, fechaAlerta, horaAlerta, frecuenciaCardiaca, frecuenciaRespiratoria, temperatura, puntuacionIndicadores);
                objAlertaDAOInt.registrarAlerta(objAlerta);
                objAlertaDAOInt.registrarAlerta(objAlerta);
                
                objMensajeNotificacion = new ClsMensajeNotificacionDTO(nombres, apellidos, tipo_id, id, direccion,frecuenciaCardiaca, frecuenciaRespiratoria, temperatura, fechaAlerta, horaAlerta, mensaje);
                RefNotificaciones.notificarRegistro(objMensajeNotificacion);
            }
            
            if(puntuacionIndicadores >= 3)
            {   
                mensaje = "Alerta, el personal médico debe remitir el paciente "+nombres+" "+apellidos+" identificado con ["+tipo_id+"]["+id+"] al hospital!!!";
                objAsintomaticoCllbck.objAsintomaticoCllbck.notificarMensajeCllbck(mensaje);
                objAlerta = new ClsAlertaDTO(id, fechaAlerta, horaAlerta, frecuenciaCardiaca, frecuenciaRespiratoria, temperatura, puntuacionIndicadores);
                objAlertaDAOInt.registrarAlerta(objAlerta);
                objAlertaDAOInt.registrarAlerta(objAlerta);
                objMensajeNotificacion = new ClsMensajeNotificacionDTO(nombres, apellidos, tipo_id, id, direccion,frecuenciaCardiaca, frecuenciaRespiratoria, temperatura, fechaAlerta, horaAlerta, mensaje);
                RefNotificaciones.notificarRegistro(objMensajeNotificacion);
            }
               
            bandera = true;
        }else System.out.println("El id del paciente asintomatico no existe!!!");
        
        System.out.println("Saliendo de enviarIndicadores()...");
        return bandera;
    }
      
    
}
