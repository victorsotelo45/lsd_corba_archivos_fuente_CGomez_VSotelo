package servidorAlertas.sop_corba;

/**
* servidorAlertas/sop_corba/GestionAsintomaticosHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ges-asintomaticos.idl
* jueves 18 de junio de 2020 03:00:38 PM COT
*/

public final class GestionAsintomaticosHolder implements org.omg.CORBA.portable.Streamable
{
  public servidorAlertas.sop_corba.GestionAsintomaticos value = null;

  public GestionAsintomaticosHolder ()
  {
  }

  public GestionAsintomaticosHolder (servidorAlertas.sop_corba.GestionAsintomaticos initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = servidorAlertas.sop_corba.GestionAsintomaticosHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    servidorAlertas.sop_corba.GestionAsintomaticosHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return servidorAlertas.sop_corba.GestionAsintomaticosHelper.type ();
  }

}
