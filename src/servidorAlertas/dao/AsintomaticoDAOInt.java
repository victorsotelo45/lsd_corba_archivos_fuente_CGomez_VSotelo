/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.util.ArrayList;
import servidorAlertas.sop_corba.ClsAsintomaticoDTO;


public interface AsintomaticoDAOInt {
    
    public boolean registrarAsintomaticoDTO(ClsAsintomaticoDTO asintomatico);
    
    public ArrayList<ClsAsintomaticoDTO> consultarAsintomaticosDTO(); 
    
    public ClsAsintomaticoDTO consultarAsintomaticoDTO(int idAsintomatico);   
    
    public boolean actualizarAsintomaticoDTO(ClsAsintomaticoDTO objAsintomatico, int idAsintomatico);  
    
    public boolean eliminarAsintomaticoDTO(int idAsintomatico);
}
