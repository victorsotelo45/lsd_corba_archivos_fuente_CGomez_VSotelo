package servidorAlertas.sop_corba;


/**
* servidorAlertas/sop_corba/GestionAsintomaticosIntPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from salertas.idl
* domingo 21 de junio de 2020 11:50:32 PM COT
*/

public abstract class GestionAsintomaticosIntPOA extends org.omg.PortableServer.Servant
 implements servidorAlertas.sop_corba.GestionAsintomaticosIntOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("registrarAsintomatico", new java.lang.Integer (0));
    _methods.put ("consultarAsintomatico", new java.lang.Integer (1));
    _methods.put ("enviarIndicadores", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // servidorAlertas/sop_corba/GestionAsintomaticosInt/registrarAsintomatico
       {
         servidorAlertas.sop_corba.ClsAsintomaticoDTO objAsintomaticoDTO = servidorAlertas.sop_corba.ClsAsintomaticoDTOHelper.read (in);
         boolean $result = false;
         $result = this.registrarAsintomatico (objAsintomaticoDTO);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // servidorAlertas/sop_corba/GestionAsintomaticosInt/consultarAsintomatico
       {
         int id = in.read_long ();
         servidorAlertas.sop_corba.ClsAsintomaticoDTO $result = null;
         $result = this.consultarAsintomatico (id);
         out = $rh.createReply();
         servidorAlertas.sop_corba.ClsAsintomaticoDTOHelper.write (out, $result);
         break;
       }

       case 2:  // servidorAlertas/sop_corba/GestionAsintomaticosInt/enviarIndicadores
       {
         int id = in.read_long ();
         int frecuenciaCardiaca = in.read_long ();
         int frecuenciaRespiratoria = in.read_long ();
         float temperatura = in.read_float ();
         boolean $result = false;
         $result = this.enviarIndicadores (id, frecuenciaCardiaca, frecuenciaRespiratoria, temperatura);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:servidorAlertas/sop_corba/GestionAsintomaticosInt:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public GestionAsintomaticosInt _this() 
  {
    return GestionAsintomaticosIntHelper.narrow(
    super._this_object());
  }

  public GestionAsintomaticosInt _this(org.omg.CORBA.ORB orb) 
  {
    return GestionAsintomaticosIntHelper.narrow(
    super._this_object(orb));
  }


} // class GestionAsintomaticosIntPOA
