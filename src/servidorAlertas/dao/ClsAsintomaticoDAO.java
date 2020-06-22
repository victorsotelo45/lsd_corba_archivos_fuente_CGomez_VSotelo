
package servidorAlertas.dao;

import java.io.Serializable;

public class ClsAsintomaticoDAO implements Serializable
{
    private String fechaAlerta;
    private String horaAlerta;
    private String puntuacion;

    public ClsAsintomaticoDAO() {}

    public ClsAsintomaticoDAO(String fechaAlerta, String horaAlerta, String puntuacion) {
        this.fechaAlerta = fechaAlerta;
        this.horaAlerta = horaAlerta;
        this.puntuacion = puntuacion;
    }
    
    public String getFechaAlerta(){return fechaAlerta;}
    public void setFechaAlerta(String fechaAlerta){this.fechaAlerta = fechaAlerta;}
    public String getHoraAlerta(){return horaAlerta;}
    public void setHoraAlerta(String horaAlerta) {this.horaAlerta = horaAlerta;}
    public String getPuntuacion(){return puntuacion;}
    public void setPuntuacion(String puntuacion){this.puntuacion = puntuacion;}
    
}
