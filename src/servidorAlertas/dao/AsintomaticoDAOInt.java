/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.util.ArrayList;


public interface AsintomaticoDAOInt {
    
    public boolean registrarAsintomaticoDAO(ClsAsintomaticoDAO asintomatico);
    
    public ArrayList<ClsAsintomaticoDAO> consultarAsintomaticosDAO(); 
    
    public ClsAsintomaticoDAO consultarAsintomaticoDAO(int idAsintomatico);   
    
    public boolean actualizarAsintomaticoDAO(ClsAsintomaticoDAO objAsintomatico, int idAsintomatico);  
    
    public boolean eliminarAsintomatico(int idAsintomatico);
}
