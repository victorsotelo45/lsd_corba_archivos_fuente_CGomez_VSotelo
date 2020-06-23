package servidorAlertas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
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
import servidorAlertas.sop_corba.GestionAsintomaticosInt;
import servidorAlertas.sop_corba.GestionAsintomaticosIntPOATie;
import servidorAlertas.utilidades.UtilidadesConsola;


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
  public static void registrarOrb(String[]args){
    try{
      // crea e iniciia el ORB
      ORB orb = ORB.init(args, null);

      // obtiene la referencia del rootpoa & activate el POAManager
      POA rootpoa = 
      POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // crea el servant y lo registra con el ORB
      ClsGestionAsintomaticosImpl convref = new ClsGestionAsintomaticosImpl(orb);
       
      // obtiene la referencia del objeto desde el servant
      //org.omg.CORBA.Object obj = 
      //rootpoa.servant_to_reference(convref);
      //Count href = CountHelper.narrow(obj);
      
      int numeroPacientes = 0;
      String []opciones = {"1","2","3","4","5"};
      
        do
        {   String resp = (String) JOptionPane.showInputDialog(null, "Seleccione el maximo de pacientes en el sistema (1 a 5)", "Maximo de pacientes", JOptionPane.DEFAULT_OPTION,null, opciones, opciones[4]);
            numeroPacientes = Integer.parseInt(resp);
             if(numeroPacientes < 1 || numeroPacientes > 5)
                 JOptionPane.showMessageDialog(null,"El maximo numero de pacientes en el sistema es de 1 a 5, digite valor nuevamente: ");
        }while(numeroPacientes < 1 || numeroPacientes > 5);
        
        convref.setNumeroPacientes(numeroPacientes);
      
      //*** crea un tie, con el servant como delegado***
      GestionAsintomaticosIntPOATie gptie= new GestionAsintomaticosIntPOATie(convref);
      
      //*** Obtener la referencia para el tie
      GestionAsintomaticosInt reftie=gptie._this(orb);

	  
      // obtiene la base del contexto de nombrado
      org.omg.CORBA.Object objref =
      orb.resolve_initial_references("NameService");
      // Usa NamingContextExt el cual es parte de la especificacion 
      // Naming Service (INS).
      NamingContextExt ncref = NamingContextExtHelper.narrow(objref);

      // Realiza el binding de la referencia de objeto en el N_S
      String name = "objAsintomatico";
      NameComponent path[] = ncref.to_name( name );
      ncref.rebind(path, reftie);

      System.out.println("El Servidor esta listo y esperando ...");
      estaRegistrado = true;
      JOptionPane.showMessageDialog(null,"El Servidor esta listo y esperando ...");
      // esperan por las invocaciones desde los clientes
      java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                orb.run();
            }
      });
              
    } 
	
      catch (InvalidName | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound | AdapterInactive e) {
        System.err.println("ERROR: " + e);
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
