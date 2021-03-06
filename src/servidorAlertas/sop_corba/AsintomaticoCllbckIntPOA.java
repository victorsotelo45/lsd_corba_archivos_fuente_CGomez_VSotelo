package servidorAlertas.sop_corba;


/**
* servidorAlertas/sop_corba/AsintomaticoCllbckIntPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from salertas.idl
* domingo 21 de junio de 2020 11:50:32 PM COT
*/

public abstract class AsintomaticoCllbckIntPOA extends org.omg.PortableServer.Servant
 implements servidorAlertas.sop_corba.AsintomaticoCllbckIntOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("notificarMensajeCllbck", new java.lang.Integer (0));
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
       case 0:  // servidorAlertas/sop_corba/AsintomaticoCllbckInt/notificarMensajeCllbck
       {
         String mensaje = in.read_string ();
         this.notificarMensajeCllbck (mensaje);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:servidorAlertas/sop_corba/AsintomaticoCllbckInt:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public AsintomaticoCllbckInt _this() 
  {
    return AsintomaticoCllbckIntHelper.narrow(
    super._this_object());
  }

  public AsintomaticoCllbckInt _this(org.omg.CORBA.ORB orb) 
  {
    return AsintomaticoCllbckIntHelper.narrow(
    super._this_object(orb));
  }


} // class AsintomaticoCllbckIntPOA
