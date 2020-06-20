package cliente;

import cliente.utilidades.UtilidadesConsola;
import static java.lang.System.exit;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import servidorAlertas.sop_corba.GestionAsintomaticos;
import servidorAlertas.sop_corba.GestionAsintomaticosHelper;
import servidorAlertas.sop_corba.GestionAsintomaticosPackage.asintomaticoDTO;
import servidorAlertas.sop_corba.GestionAsintomaticosPackage.asintomaticoDTOHolder;

public class ClienteDeObjetos
{
  //*** Atributo estático ***
  static GestionAsintomaticos ref;
  
  public static void main(String args[])
    {
      try{
        // crea e inicia el ORB
	ORB orb = ORB.init(args, null);

        // obtiene la base del naming context
        org.omg.CORBA.Object objRef = 
	    orb.resolve_initial_references("NameService");
        // usa NamingContextExt en lugar de NamingContext.Esto es 
        // parte del Interoperable naming Service.  
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
 
        // *** Resuelve la referencia del objeto en el N_S ***
        String name = "objAsintomatico";
        ref = GestionAsintomaticosHelper.narrow(ncRef.resolve_str(name));

        asintomaticoDTO asin_reg = null;
        BooleanHolder res = new BooleanHolder();
        asintomaticoDTOHolder asin_bus = new asintomaticoDTOHolder();  
                
        System.out.println("Obtenido el manejador sobre el servidor deobjetos: " +ref);
        
        int id;
        String nombres, apellidos, tipo_id, direccion;
        boolean existeAsintomatico;
        int opcion;
        
        do
        {
            System.out.println("Menu");
            System.out.println("1. Registrar asintomatico:");
            System.out.println("2. Consultar asintomatico:");
            System.out.println("3. Salir");
            System.out.print("Escoge una opcion: ");
            opcion = UtilidadesConsola.leerEntero();
            switch(opcion)
            {
                case 1:
                        System.out.println("Registrar Datos del Asintomatico:");
                        System.out.print("Id: ");
                        id = UtilidadesConsola.leerEntero();
                        System.out.print("Nombres: ");
                        nombres = UtilidadesConsola.leerCadena();
                        System.out.print("Apellidos: ");
                        apellidos = UtilidadesConsola.leerCadena();
                        System.out.print("Tipo de Id: ");
                        tipo_id = UtilidadesConsola.leerCadena();
                        System.out.print("Direccion: ");
                        direccion = UtilidadesConsola.leerCadena();
                        asin_reg = new asintomaticoDTO(nombres, apellidos, tipo_id, id, direccion);
                        ref.registrarAsintomatico(asin_reg, res);
                        if(res.value)
                            System.out.println("Se registro asintomatico exitosamente!!!");
                        else System.out.println("No se registro paciente!!!");
                        
                break;
                case 2:
                        System.out.println("Consultar Datos del Asintomatico:");
                        System.out.print("Digite id del asintomatico: ");
                        id = UtilidadesConsola.leerEntero();
                        existeAsintomatico = ref.consultarAsintomatico(id, asin_bus);
                        if(existeAsintomatico)
                        {   if(asin_bus.value != null)
                            {
                                System.out.println("Id: "+asin_bus.value.id);
                                System.out.println("Nombres: "+asin_bus.value.nombres);
                                System.out.println("Apellidos: "+asin_bus.value.apellidos);
                                System.out.println("Tipo de Id: "+asin_bus.value.tipo_id);
                                System.out.println("Direccion: "+asin_bus.value.direccion);

                            }
                        }else System.out.println("El asintomatico con id "+id+" no existe!!!");
                        
                break;
                        
                case 3:
                        exit(0);
                break;
                default:
                        System.out.println("Opcion no valida!!!");
                break;
            
            }
                    
        }while(opcion != 3);
        

	} catch (InvalidName | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound e) {
          System.out.println("ERROR : " + e) ;
	  e.printStackTrace(System.out);
	  }
    }

}

