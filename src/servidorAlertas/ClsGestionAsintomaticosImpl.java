
package servidorAlertas;

//import servidorAlertas.dao.ClsAsintomaticoDAOImpl;
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
import servidorAlertas.dao.AsintomaticoDAOInt;
import servidorAlertas.sop_corba.ClsAsintomaticoDTO;
import servidorAlertas.sop_corba.GestionAsintomaticosIntOperations;
import servidorNotificaciones.sop_corba.NotificacionInt;
import servidorNotificaciones.sop_corba.NotificacionIntHelper;
import servidorNotificaciones.sop_corba.NotificacionIntPackage.ClsMensajeNotificacionDTO;


public class ClsGestionAsintomaticosImpl implements GestionAsintomaticosIntOperations {

    
    private static NotificacionInt objetoRefServidorNotificaciones;
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
            objetoRefServidorNotificaciones = NotificacionIntHelper.narrow(ncRef.resolve_str(name));

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
        boolean  bandera = false;
        if(consultarAsintomatico(id) == null)       
        {   if(this.asintomaticos.size() < this.numeroPacientes)
            {
                  bandera = this.asintomaticos.add(objAsintomaticoCllbck);

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
            
            ClsAsintomaticoDTO pacienteAsintomatico = null;
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
        String nombres, apellidos, tipo_id, mensaje;
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
        AsintomaticoDAOInt objetoAsintomaticoDAO;    
        ClsMensajeNotificacionDTO objMensajeNotificacion;
        ClsAsintomaticoDTO pacienteAsintomatico;
        
        ClsAsintomaticoDTO objAsintomaticoCllbck = consultarAsintomatico(id);
        if(objAsintomaticoCllbck != null)
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
            
            //objetoAsintomaticoDAO = new ClsAsintomaticoDAOImpl();
            
            if(puntuacionIndicadores == 0 || puntuacionIndicadores == 1)
            {
                System.out.println("El paciente "+nombres+" "+apellidos+" identificado con ["+tipo_id+"]["+id+"] debe continuar monitorizacion!!!");
                
            }
            
            if(puntuacionIndicadores == 2)
            {
                //objetoAsintomaticoDAO.escribirHistorialAsintomatico(pacienteAsintomatico, fechaAlerta, horaAlerta, puntuacionIndicadores);
                mensaje = "Alerta, el personal médico debe visitar al paciente "+nombres+" "+apellidos+" identificado con ["+tipo_id+"]["+id+"]!!!";
                objAsintomaticoCllbck.objAsintomaticoCllbck.notificarMensajeCllbck(mensaje);
                if(puntuacionFrecCardiaca == 0) frecuenciaCardiaca = 0;
                if(puntuacionFrecRespiratoria == 0) frecuenciaRespiratoria = 0;
                if(puntuacionTemperatura == 0) temperatura = 0;
                objMensajeNotificacion = new ClsMensajeNotificacionDTO(objAsintomaticoCllbck, frecuenciaCardiaca, frecuenciaRespiratoria, temperatura, fechaAlerta, horaAlerta, mensaje);
                objetoRefServidorNotificaciones.notificarRegistro(objMensajeNotificacion);
            }
            
            if(puntuacionIndicadores >= 3)
            {   
                //objetoAsintomaticoDAO.escribirHistorialAsintomatico(pacienteAsintomatico, fechaAlerta, horaAlerta, puntuacionIndicadores);
                mensaje = "Alerta, el personal médico debe remitir el paciente "+nombres+" "+apellidos+" identificado con ["+tipo_id+"]["+id+"] al hospital!!!";
                objAsintomaticoCllbck.objAsintomaticoCllbck.notificarMensajeCllbck(mensaje);
                objMensajeNotificacion = new ClsMensajeNotificacionDTO(objAsintomaticoCllbck, frecuenciaCardiaca, frecuenciaRespiratoria, temperatura, fechaAlerta, horaAlerta, mensaje);
                objetoRefServidorNotificaciones.notificarRegistro(objMensajeNotificacion);
            }
               
            bandera = true;
        }else System.out.println("El id del paciente asintomatico no existe!!!");
        
        System.out.println("Saliendo de enviarIndicadores()...");
        return bandera;
    }
    
    
}
