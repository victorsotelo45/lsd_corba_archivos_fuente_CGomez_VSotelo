package servidorNotificaciones;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import servidorNotificaciones.sop_corba.NotificacionInt;
import servidorNotificaciones.sop_corba.NotificacionIntPOATie;

public class ServidorDeObjetos extends JFrame{
private static boolean estaRegistrado;
JTextField jTextFieldIp = new JTextField();
JTextField jTextFieldPuerto = new JTextField();
public ServidorDeObjetos(){
    initComponents();
}
  public static void main(String args[]) {

    String [] vec;
    if(args.length == 4)
          registrarOrb(args);
      else
            if(args.length == 2){
                vec = new String[4];               
                vec[0] = "-ORBInitialHost";
                vec[1] = args[0];
                vec[2] = "-ORBInitialPort";
                vec[3] = args[1];  
                registrarOrb(vec);
            }
    if(!estaRegistrado)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServidorDeObjetos().setVisible(true);
            }
        });
    
  }
    
    private static void registrarOrb(String []vec){
        try{
            // crea e iniciia el ORB
            ORB orb = ORB.init(vec, null);

            // obtiene la referencia del rootpoa & activate el POAManager
            POA rootpoa = 
            POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // crea el servant y lo registra con el ORB
            ClsNotificacionImpl convref = new ClsNotificacionImpl();

            // obtiene la referencia del objeto desde el servant
            //org.omg.CORBA.Object obj = 
            //rootpoa.servant_to_reference(convref);
            //Count href = CountHelper.narrow(obj);

            //*** crea un tie, con el servant como delegado***
            NotificacionIntPOATie gptie= new NotificacionIntPOATie(convref);

            //*** Obtener la referencia para el tie
            NotificacionInt reftie=gptie._this(orb);


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
            estaRegistrado = true;
            System.out.println("El Servidor esta listo y esperando ...");
            JOptionPane.showMessageDialog(null, "El servidor esta listo y esperando.\nCuando se genere una alerta el servidor le notificara.");

            // esperan por las invocaciones desde los clientes
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    orb.run();
                }
            });
        } 

        catch (InvalidName | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound | AdapterInactive e) {
            System.err.println("ERROR: " + e);
            JOptionPane.showMessageDialog(null, "No se ha podido registrar, intentelo nuevamente.","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(System.out);
        }
        
	
  }
 
  private void initComponents(){
      
        //Iniciando componentes de form
        setSize(550,400);
        setTitle("Registrar ORB");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(200, 200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
      
        //creando jpanel y adicionando al form
        JPanel jPanelRegistrar = new JPanel();
        jPanelRegistrar.setLayout(null);
        this.getContentPane().add(jPanelRegistrar);
        
        //Adicionando jlabels
        JLabel jLabelIp = new JLabel("Ingrese la direccion ip del orbd");
        jLabelIp.setBounds(10, 40, 200, 30);
        JLabel jLabelPuerto = new JLabel("Ingrese el puerto de escucha del orbd");
        jLabelPuerto.setBounds(10, 100, 250, 30);
        jPanelRegistrar.add(jLabelIp);
        jPanelRegistrar.add(jLabelPuerto);
        
        //Adicionando jTextFields
        jTextFieldIp.setBounds(250, 40, 250, 25);
        jTextFieldPuerto.setBounds(250, 100, 250, 25);
        jPanelRegistrar.add(jTextFieldIp);
        jPanelRegistrar.add(jTextFieldPuerto);
        
        //Adicionando botones
        JButton jButtonRegistrar = new JButton("Registrar");
        JButton jButtonSalir = new JButton("Salir");
        jButtonRegistrar.setBounds(40, 200, 150, 30);
        jButtonSalir.setBounds(300, 200, 150, 30);
        jPanelRegistrar.add(jButtonRegistrar);
        jPanelRegistrar.add(jButtonSalir);
        
        //Agregando evento de tipo ActionListener
        jButtonRegistrar.addActionListener(oyenteDeAccionRegistrar);
        jButtonSalir.addActionListener(oyenteDeAccionSalir);
  }
    ActionListener oyenteDeAccionRegistrar = new ActionListener(){
       @Override
       public void actionPerformed(ActionEvent e) {
        String[] vec = new String [4];
        vec[0] = "-ORBInitialHost";
        vec[1] = jTextFieldIp.getText();
        vec[2] = "-ORBInitialPort";
        vec[3] = jTextFieldPuerto.getText();
        
        registrarOrb(vec);
        if (estaRegistrado)        
            setVisible(false);
       } 
    };
     ActionListener oyenteDeAccionSalir = new ActionListener(){
       @Override
       public void actionPerformed(ActionEvent e) {
           System.exit(0);
       }
     };
}
