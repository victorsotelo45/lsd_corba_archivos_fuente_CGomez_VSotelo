package servidorAlertas.servidor;

import servidorAlertas.sop_corba.*;
import servidorAlertas.sop_corba.GestionAsintomaticosPackage.*;

import java.util.ArrayList;
import org.omg.CORBA.BooleanHolder;
import servidorAlertas.sop_corba.GestionAsintomaticosPackage.asintomaticoDTOHolder;
import servidorNotificaciones.sop_corba.GestionNotificaciones;
import servidorNotificaciones.sop_corba.GestionNotificacionesPackage.ClsMensajeNotificacionDTO;


public class GestionAsintomaticosImpl implements GestionAsintomaticosOperations
{
        static GestionNotificaciones ref;
        private ArrayList<asintomaticoDTO> asintomaticos; 

    public GestionAsintomaticosImpl() {
        this.asintomaticos = new ArrayList<asintomaticoDTO>();
        
    }

    @Override
    public void registrarAsintomatico(asintomaticoDTO asin_reg, BooleanHolder res) {

        ClsMensajeNotificacionDTO objNotificacion;
        String mensaje;
        res.value = false;
        boolean bandera = existeAsintomatico(asin_reg.id);
         if(!bandera)
         {
            if(asintomaticos.size() < 5)
            {
                res.value = this.asintomaticos.add(asin_reg);
                mensaje = "Se registro asintoamtico en el servidor de alertas!!!";
                objNotificacion = new ClsMensajeNotificacionDTO(mensaje);
                ref.notificarRegistro(objNotificacion);
                
            }else 
            {
                System.out.println("Se han registrado el maximo numero de pacientes: "+5+"!!!");
            }
         }else
         {
             System.out.println("El asintomatico con id "+asin_reg.id+ " ya se ha registrado!!!");
         }
             
    }

    @Override
    public boolean consultarAsintomatico(int id, asintomaticoDTOHolder asin_bus) {
            boolean bandera = false;
        asin_bus.value = new  asintomaticoDTO("","","",0,"");
        for(asintomaticoDTO objAsintomatico: asintomaticos)
        {
            if(objAsintomatico.id == id)
            {
                asin_bus.value = objAsintomatico; 
                bandera = true;
                break;
            }
        }
        return bandera;
        
    }
    
    public boolean existeAsintomatico(int id)
    {
        boolean bandera = false;
        for(asintomaticoDTO objAsintomatico: asintomaticos)
        {
            if(objAsintomatico.id == id)
            {
                bandera = true; 
                break;
            }
        }
        return bandera;
    }
  
} 
