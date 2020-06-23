/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorAlertas.dao;

import java.util.ArrayList;


/**
 *
 * @author cesarluis
 */
public interface AlertaDAOInt {
    
    public boolean registrarAlerta(ClsAlertaDTO objAlerta);
    public ArrayList<ClsAlertaDTO> consultarUltimas5Alertas(int idAsintomatico);
}
