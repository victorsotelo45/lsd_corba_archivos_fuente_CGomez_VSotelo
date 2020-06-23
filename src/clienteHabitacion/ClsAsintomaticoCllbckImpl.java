
package clienteHabitacion;

import servidorAlertas.sop_corba.AsintomaticoCllbckIntOperations;

public class ClsAsintomaticoCllbckImpl implements AsintomaticoCllbckIntOperations
{       
        GUICliente GUIC1;
        GUICliente2 GUIC2;
        
        public ClsAsintomaticoCllbckImpl()
        {
                super(); //invoca al constructor de la clase base

        }
        
        public ClsAsintomaticoCllbckImpl(GUICliente GUIC) 
        {
                this.GUIC1 = GUIC;
        }
        
        public ClsAsintomaticoCllbckImpl(GUICliente2 GUIC) 
        {
                this.GUIC2 = GUIC;
        }
        
    
        @Override
        public void notificarMensajeCllbck(String mensaje){
            System.out.println("Desde notificarMensajeCllbck()...");
            GUIC2.fijarAlerta(mensaje);
            System.out.println(mensaje); 
            System.out.println("Saliendo de notificarMensajeCllbck()...");
        }

}
