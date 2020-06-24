/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author cesarluis
 */
public class ClsAlertaDAOImpl implements AlertaDAOInt{

    
    private final ConexionBD conexionABaseDeDatos;
    
    public ClsAlertaDAOImpl()
    {
        conexionABaseDeDatos= new ConexionBD();
    }
    @Override
    public boolean registrarAlerta(ClsAlertaDTO objAlerta) {
        
        conexionABaseDeDatos.conectar();
        int resultado=-1;
        try {            
            PreparedStatement sentencia = null;
            String consulta = "insert into alerta(alerta.id_asintomatico,alerta.fecha_alerta,alerta.hora_alerta,alerta.frecuencia_cardiaca,alerta.frecuencia_respiratoria,alerta.temperatura,alerta.puntuacion) values(?,?,?,?,?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, objAlerta.getIdAsintomatico());
            sentencia.setString(2, objAlerta.getFechaAlerta());
            sentencia.setString(3, objAlerta.getHoraAlerta());
            sentencia.setInt(4, objAlerta.getFrecuenciaCardiaca());
            sentencia.setInt(5, objAlerta.getFrecuenciaRespiratoria());
            sentencia.setFloat(6, objAlerta.getTemperatura());
            sentencia.setInt(7, objAlerta.getPuntuacion());
            resultado = sentencia.executeUpdate(); 
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return resultado == 1;
    }

    @Override
    public ArrayList<ClsAlertaDTO> consultarUltimas5Alertas(int idAsintomatico) {
        
        ArrayList<ClsAlertaDTO> historialAlertas = new ArrayList();
        
        conexionABaseDeDatos.conectar();        
        try {            
            PreparedStatement sentencia = null;
            String consulta = "select * from alerta where alerta.id_asintomatico=? order by alerta.fecha_alerta desc, alerta.hora_alerta desc limit 5";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);  
            sentencia.setInt(1, idAsintomatico);
            ResultSet res = sentencia.executeQuery();
            while(res.next()){
            ClsAlertaDTO objAlerta = new ClsAlertaDTO();
            objAlerta.setIdAsintomatico(idAsintomatico);
            objAlerta.setFechaAlerta(res.getString("fecha_alerta"));
            objAlerta.setHoraAlerta(res.getString("hora_alerta"));
            objAlerta.setFrecuenciaCardiaca(res.getInt("frecuencia_cardiaca"));
            objAlerta.setFrecuenciaRespiratoria(res.getInt("frecuencia_respiratoria"));
            objAlerta.setTemperatura(res.getFloat("temperatura"));
            objAlerta.setPuntuacion(res.getInt("puntuacion"));
            historialAlertas.add(objAlerta);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
                  System.out.println("error en la inserción: "+e.getMessage());         
        }
        
        return historialAlertas;
        
        
    }
    
}
