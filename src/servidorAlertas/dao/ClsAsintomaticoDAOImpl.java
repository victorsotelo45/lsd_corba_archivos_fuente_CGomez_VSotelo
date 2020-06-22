//
//package servidorAlertas.dao;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
///**
// * la clase permite registrar, eliminar, actualizar, consultar y listar datos del empleado
// */
//public class ClsAsintomaticoDAOImpl implements AsintomaticoDAOInt{
//    private final ConexionBD conexionABaseDeDatos;
//    
//    public ClsAsintomaticoDAOImpl()
//    {
//        conexionABaseDeDatos= new ConexionBD();
//    }
//
///**
// * 
//     * @param asintomatico
// * * @return verdadero si el asintomatico se registro correctamente, falso en caso contrario
//     * @return 
// */
//    
//    public boolean registrarAsintomatico(ClsAsintomaticoDAO asintomatico) 
//    {
//        
//        conexionABaseDeDatos.conectar();
//        int resultado=-1;
//        try {            
//            PreparedStatement sentencia = null;
//            String consulta = "insert into empleado(empleado.nombresEmpleado,empleado.apellidosEmpleado,empleado.salarioEmpleado) values(?,?,?)";
//            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
//            sentencia.setString(1, asintomatico.);
//            sentencia.setString(2, asintomatico.getApellidosEmpleado());
//            sentencia.setFloat(3, asintomatico.getSalarioEmpleado());
//            sentencia.setFloat(3, asintomatico.getSalarioEmpleado());
//            sentencia.setFloat(3, asintomatico.getSalarioEmpleado());
//            resultado = sentencia.executeUpdate(); 
//            sentencia.close();
//            conexionABaseDeDatos.desconectar();
//
//        } catch (SQLException e) {
//                  System.out.println("error en la inserción: "+e.getMessage());         
//        }
//        
//        return resultado == 1;
//    }
//    
//    
//    @Override
//    public ArrayList<ClsAsintomaticoDAO> consultarAsintomaticos()
//    {
//        ArrayList<ClsAsintomaticoDAO> empleados = new ArrayList();
//        
//        conexionABaseDeDatos.conectar();        
//        try {            
//            PreparedStatement sentencia = null;
//            String consulta = "select * from empleado";
//            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);            
//            ResultSet res = sentencia.executeQuery();
//            while(res.next()){
//            ClsEmpleadoDTO objEmpleado= new ClsEmpleadoDTO();
//            objEmpleado.setIdEmpleado(res.getInt("idEmpleado"));
//            objEmpleado.setNombresEmpleado(res.getString("nombresEmpleado"));
//            objEmpleado.setApellidosEmpleado(res.getString("apellidosEmpleado"));
//            objEmpleado.setSalarioEmpleado(res.getFloat("salarioEmpleado"));
//            empleados.add(objEmpleado);
//            }
//            sentencia.close();
//            conexionABaseDeDatos.desconectar();
//
//        } catch (SQLException e) {
//                  System.out.println("error en la inserción: "+e.getMessage());         
//        }
//        
//        return empleados;
//    }
//    
//    
//    @Override
//    public ClsAsintomaticoDAO consultarEmpleado(int idEmpleado)
//    {
//        ClsEmpleadoDTO objEmpleado=null;
//      
//        conexionABaseDeDatos.conectar();        
//        try {            
//            PreparedStatement sentencia = null;
//            String consulta = "select empleado.NombresEmpleado, empleado.ApellidosEmpleado, empleado.SalarioEmpleado from empleado where empleado.idEmpleado=?";
//            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);            
//            sentencia.setInt(1, idEmpleado);
//            ResultSet res = sentencia.executeQuery();
//            while(res.next()){
//                objEmpleado= new ClsEmpleadoDTO();
//                objEmpleado.setIdEmpleado(idEmpleado);
//                objEmpleado.setNombresEmpleado(res.getString("nombresEmpleado"));
//                objEmpleado.setApellidosEmpleado(res.getString("apellidosEmpleado"));
//                objEmpleado.setSalarioEmpleado(res.getFloat("salarioEmpleado"));
//            }
//            sentencia.close();
//            conexionABaseDeDatos.desconectar();
//
//        } catch (SQLException e) {
//                  System.out.println("error en la consulta de un empleado: "+e.getMessage());         
//        }
//        
//        return objEmpleado;
//    }
//    
//    @Override
//    public boolean actualizarAsintomatico(ClsAsintomaticoDAO objEmpleado, int idEmpleado)
//    {
//        
//        conexionABaseDeDatos.conectar();
//        int resultado=-1;
//        try {            
//            PreparedStatement sentencia = null;
//            String consulta = "update empleado set empleado.nombresEmpleado=?,"
//                                                 + "empleado.apellidosEmpleado=?,"
//                                                 + "empleado.salarioempleado=? "
//                                                 + "where empleado.idEmpleado=?";
//            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
//            sentencia.setString(1, objEmpleado.getNombresEmpleado());
//            sentencia.setString(2, objEmpleado.getApellidosEmpleado());
//            sentencia.setFloat(3, objEmpleado.getSalarioEmpleado());
//            sentencia.setInt(4, idEmpleado);
//            resultado = sentencia.executeUpdate(); 
//            sentencia.close();
//            conexionABaseDeDatos.desconectar();
//
//        } catch (SQLException e) {
//                  System.out.println("error en la actualización: "+e.getMessage());         
//        }
//        
//        return resultado == 1;
//    }
//    
//    @Override
//    public boolean eliminarAsintomatico(int idEmpleado){
//        
//        conexionABaseDeDatos.conectar();
//        int resultado=-1;
//        try {            
//            PreparedStatement sentencia = null;
//            String consulta = "delete from empleado where empleado.idEmpleado=?";
//            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);            
//            sentencia.setInt(1, idEmpleado);
//            resultado = sentencia.executeUpdate(); 
//            sentencia.close();
//            conexionABaseDeDatos.desconectar();
//
//        } catch (SQLException e) {
//                  System.out.println("error en la eliminación: "+e.getMessage());         
//        }
//        
//        return resultado == 1;
//    }
//}
