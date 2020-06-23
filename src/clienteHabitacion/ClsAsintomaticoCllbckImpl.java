
package clienteHabitacion;

import servidorAlertas.sop_corba.AsintomaticoCllbckIntOperations;

public class ClsAsintomaticoCllbckImpl implements AsintomaticoCllbckIntOperations
{       
        //GUICliente GUIC;
        GUICliente2 GUIC;
        
        public ClsAsintomaticoCllbckImpl()
        {
                super(); //invoca al constructor de la clase base

        }
        
//        public ClsAsintomaticoCllbckImpl(GUICliente GUIC) 
//        {
//                this.GUIC = GUIC;
//        }
        
        public ClsAsintomaticoCllbckImpl(GUICliente2 GUIC) 
        {
                this.GUIC = GUIC;
        }
        
    
        @Override
        public void notificarMensajeCllbck(String mensaje){
            System.out.println("Desde notificarMensajeCllbck()...");
            GUIC.fijarAlerta(mensaje);
            System.out.println(mensaje); 
            System.out.println("Saliendo de notificarMensajeCllbck()...");
        }

}
