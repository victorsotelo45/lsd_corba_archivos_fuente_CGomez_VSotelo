package servidorAlertas.sop_corba;


/**
* servidorAlertas/sop_corba/GestionAsintomaticosIntOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from salertas.idl
* domingo 21 de junio de 2020 11:50:32 PM COT
*/

public interface GestionAsintomaticosIntOperations 
{
  boolean registrarAsintomatico (servidorAlertas.sop_corba.ClsAsintomaticoDTO objAsintomaticoDTO);
  servidorAlertas.sop_corba.ClsAsintomaticoDTO consultarAsintomatico (int id);
  boolean enviarIndicadores (int id, int frecuenciaCardiaca, int frecuenciaRespiratoria, float temperatura);
} // interface GestionAsintomaticosIntOperations
