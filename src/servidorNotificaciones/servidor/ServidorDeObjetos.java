package servidorNotificaciones.servidor;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import servidorNotificaciones.sop_corba.GestionNotificaciones;
import servidorNotificaciones.sop_corba.GestionNotificacionesPOATie;

public class ServidorDeObjetos {

  public static void main(String args[]) {
    try{
      // crea e iniciia el ORB
      ORB orb = ORB.init(args, null);

      // obtiene la referencia del rootpoa & activate el POAManager
      POA rootpoa = 
      POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // crea el servant y lo registra con el ORB
      GestionNotificacionesImpl convref = new GestionNotificacionesImpl();
       
      // obtiene la referencia del objeto desde el servant
      //org.omg.CORBA.Object obj = 
      //rootpoa.servant_to_reference(convref);
      //Count href = CountHelper.narrow(obj);
      
      //*** crea un tie, con el servant como delegado***
      GestionNotificacionesPOATie gptie= new GestionNotificacionesPOATie(convref);
      
      //*** Obtener la referencia para el tie
      GestionNotificaciones reftie=gptie._this(orb);

	  
      // obtiene la base del contexto de nombrado
      org.omg.CORBA.Object objref =
          orb.resolve_initial_references("NameService");
      // Usa NamingContextExt el cual es parte de la especificacion 
      // Naming Service (INS).
      NamingContextExt ncref = NamingContextExtHelper.narrow(objref);

      

      // Realiza el binding de la referencia de objeto en el N_S
      String name = "objNotificaciones";
      NameComponent path[] = ncref.to_name( name );
      ncref.rebind(path, reftie);

      System.out.println("El Servidor esta listo y esperando ...");

      // esperan por las invocaciones desde los clientes
      orb.run();
    } 
	
      catch (Exception e) {
        System.err.println("ERROR: " + e);
        e.printStackTrace(System.out);
      }
	  
      System.out.println("HelloServer: Saliendo ...");
	
  }
}
