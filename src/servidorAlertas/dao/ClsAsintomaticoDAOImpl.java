
package servidorAlertas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import servidorAlertas.sop_corba.ClsAsintomaticoDTO;

/**
 * la clase permite registrar, eliminar, actualizar, consultar y listar datos del empleado
 */
public class ClsAsintomaticoDAOImpl implements AsintomaticoDAOInt{
    private final ConexionBD conexionABaseDeDatos;
    
    public ClsAsintomaticoDAOImpl()
    {
        conexionABaseDeDatos= new ConexionBD();
    }

/**
 * 
     * @param asintomatico
 * * @return verdadero si el asintomatico se registro correctamente, falso en caso contrario
     * @return 
 */
    @Override
    public boolean registrarAsintomaticoDTO(ClsAsintomaticoDTO asintomatico) 
    {
        
        conexionABaseDeDatos.conectar();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "insert into asintomatico(asintomatico.id_asintomatico,asintomatico.nombres,asintomatico.apellidos,asintomatico.tipo_id,asintomatico.direccion) values(?,?,?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, asintomatico.id);
            sentencia.setString(2, asintomatico.nombres);
            sentencia.setString(3, asintomatico.apellidos);
            sentencia.setString(4, asintomatico.tipo_id);
            sentencia.setString(5, asintomatico.direccion);
            resultado = sentencia.executeUpdate(); 
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
    }
    
    
    @Override
    public ArrayList<ClsAsintomaticoDTO> consultarAsintomaticosDTO()
    {
        ArrayList<ClsAsintomaticoDTO> asintomaticos = new ArrayList();
        
        conexionABaseDeDatos.conectar();        
        try {            
            PreparedStatement sentencia = null;
            String consulta = "select * from asintomatico";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);            
            ResultSet res = sentencia.executeQuery();
            while(res.next()){
            ClsAsintomaticoDTO objAsintomaticoDTO = new ClsAsintomaticoDTO();
            objAsintomaticoDTO.id = res.getInt("id_asintomatico");
            objAsintomaticoDTO.nombres = res.getString("nombres");
            objAsintomaticoDTO.apellidos = res.getString("apellidos");
            objAsintomaticoDTO.tipo_id = res.getString("tipo_id");
            objAsintomaticoDTO.direccion = res.getString("direccion");
            asintomaticos.add(objAsintomaticoDTO);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return asintomaticos;
    }
    
    
    @Override
    public ClsAsintomaticoDTO consultarAsintomaticoDTO(int idAsintomatico)
    {
        ClsAsintomaticoDTO objAsintomaticoDTO=null;
      
        conexionABaseDeDatos.conectar();        
        try {            
            PreparedStatement sentencia = null;
            String consulta = "select asintomatico.nombres,asintomatico.apellidos,asintomatico.tipo_id,asintomatico.direccion from asintomatico where asintomatico.id_asintomatico=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);            
            sentencia.setInt(1, idAsintomatico);
            ResultSet res = sentencia.executeQuery();
            while(res.next()){
                objAsintomaticoDTO= new ClsAsintomaticoDTO();
                objAsintomaticoDTO.id = idAsintomatico;
                objAsintomaticoDTO.nombres = res.getString("nombres");
                objAsintomaticoDTO.apellidos = res.getString("apellidos");
                objAsintomaticoDTO.tipo_id = res.getString("tipo_id");
                objAsintomaticoDTO.direccion = res.getString("direccion");
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la consulta de un empleado: "+e.getMessage());         
        }
        
        return objAsintomaticoDTO;
    }
    
    @Override
    public boolean actualizarAsintomaticoDTO(ClsAsintomaticoDTO objAsintomaticoDTO, int idAsintomatico)
    {
        
        conexionABaseDeDatos.conectar();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "update asintomatico set asintomatico.nombres=?,"
                                                 + "asintomatico.apellidos=?,"
                                                 + "asintomatico.tipo_id=?,"   
                                                 + "asintomatico.direccion=? "
                                                 + "where asintomatico.id_asintomatico=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setString(1, objAsintomaticoDTO.nombres);
            sentencia.setString(2, objAsintomaticoDTO.apellidos);
            sentencia.setString(3, objAsintomaticoDTO.tipo_id);
            sentencia.setString(4, objAsintomaticoDTO.tipo_id);
            sentencia.setInt(5, idAsintomatico);
            resultado = sentencia.executeUpdate(); 
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la actualización: "+e.getMessage());         
        }
        
        return resultado == 1;
    }
    
    @Override
    public boolean eliminarAsintomaticoDTO(int idAsintomatico){
        
        conexionABaseDeDatos.conectar();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "delete from asintomatico where asintomatico.id_asintomatico=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);            
            sentencia.setInt(1, idAsintomatico);
            resultado = sentencia.executeUpdate(); 
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la eliminación: "+e.getMessage());         
        }
        
        return resultado == 1;
    }
}
