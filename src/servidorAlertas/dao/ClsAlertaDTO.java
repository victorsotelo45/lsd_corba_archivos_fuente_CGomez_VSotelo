
package servidorAlertas.dao;

import java.io.Serializable;

public class ClsAlertaDTO implements Serializable
{
    private int idAsintomatico;
    private String fechaAlerta;
    private String horaAlerta;
    private int frecuenciaCardiaca;
    private int frecuenciaRespiratoria;
    private float temperatura;
    private int puntuacion;

    public ClsAlertaDTO() {}

    public ClsAlertaDTO(int idAsintomatico,String fechaAlerta, String horaAlerta, int frecuenciaCardiaca, int frecuenciaRespiratoria, float temperatura, int puntuacion) {
        this.idAsintomatico = idAsintomatico;
        this.fechaAlerta = fechaAlerta;
        this.horaAlerta = horaAlerta;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.temperatura = temperatura;
        this.puntuacion = puntuacion;
    }
    
    public int getIdAsintomatico(){return idAsintomatico;}
    public void setIdAsintomatico(int idAsintomatico){this.idAsintomatico = idAsintomatico;}
    public String getFechaAlerta(){return fechaAlerta;}
    public void setFechaAlerta(String fechaAlerta){this.fechaAlerta = fechaAlerta;}
    public String getHoraAlerta(){return horaAlerta;}
    public void setHoraAlerta(String horaAlerta) {this.horaAlerta = horaAlerta;}
    public int getFrecuenciaCardiaca(){return frecuenciaCardiaca;}
    public void setFrecuenciaCardiaca(int frecuenciaCardiaca){this.frecuenciaCardiaca = frecuenciaCardiaca;}
    public int getFrecuenciaRespiratoria(){return frecuenciaRespiratoria;}
    public void setFrecuenciaRespiratoria(int frecuenciaRespiratoria){this.frecuenciaRespiratoria = frecuenciaRespiratoria;}
    public void setTemperatura(float temperatura){this.temperatura = temperatura;}
    public float getTemperatura(){return temperatura;}
    public int getPuntuacion(){return puntuacion;}
    public void setPuntuacion(int puntuacion){this.puntuacion = puntuacion;}

    
}




