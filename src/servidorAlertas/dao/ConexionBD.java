/**
 * Utiliza la API JDBC de java para conectarse a una base de datos MySQL
 * -----------------------------------------------------------------
 */
package Datos;
import java.sql.*;

/**
 * Esta clase permite que las Clases tipo Entidad tengan persistencia
 * por medio de una base de datos relacional
 */
public class ConexionBD {
   
   private Connection con;
    
   private String bd;
   private String login;
   private String password;
   private String url;
    
   
    public ConexionBD() {
        con=null;
        bd="bdasintomaticos";
        login="root";
        password="";
        url = "jdbc:mysql://localhost/"+bd;
    }
    /**Permite hacer la conexion con la base de datos
     * @return 
     */
    public int conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
           //crea una instancia de la controlador de la base de datos
            con = DriverManager.getConnection(url,login,password);
            // gnera una conexión con la base de datos
             return 1;
        }
        catch(SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e){
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }
    /**Cierra la conexion con la base de datos
     *
     */
   public void desconectar(){
       try{
            con.close();
        }

        catch(SQLException e){
            System.out.println("Error " + e.getMessage());
        }
   }
     /**Retorna un objeto que almacena la referencia a la conexion con la base de datos
     *
     * @return 
     */
    public Connection getConnection(){
      return con;
   }
 
   
}
