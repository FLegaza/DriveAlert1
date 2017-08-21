package data.model;

/*
Clase INCIDENCIA - Creada para albergar cada una de las incidencias de tráfico que se recojan.
 */

import io.realm.RealmObject;

public class Incidencia extends RealmObject{

    // Atributos de la clase Incidencia
    private String carretera;
    private Integer estado;
    private String alias;
    private String sentido;
    private Double PK;
    private String tipo;
    private Double lng;
    private String codEle;
    private Double lat;
    private String precision;
    private String fecha;
    private String poblacion;
    private String descripcion;
    private String fechaFin;
    private String tipoInci;
    private Integer pkFinal;
    private String provincia;
    private String causa;
    private String hora;
    private Integer pkIni;
    private String icono;
    private String horaFin;
    private String nivel;

    public Incidencia(String carretera, Integer estado, String alias, String sentido,
                      Double PK, String tipo, Double lng, String codEle, Double lat,
                      String precision, String fecha, String poblacion, String descripcion,
                      String fechaFin, String tipoInci, Integer pkFinal, String provincia,
                      String causa, String hora, Integer pkIni, String icono, String horaFin, String nivel) {
        this.carretera = carretera;
        this.estado = estado;
        this.alias = alias;
        this.sentido = sentido;
        this.PK = PK;
        this.tipo = tipo;
        this.lng = lng;
        this.codEle = codEle;
        this.lat = lat;
        this.precision = precision;
        this.fecha = fecha;
        this.poblacion = poblacion;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.tipoInci = tipoInci;
        this.pkFinal = pkFinal;
        this.provincia = provincia;
        this.causa = causa;
        this.hora = hora;
        this.pkIni = pkIni;
        this.icono = icono;
        this.horaFin = horaFin;
        this.nivel = nivel;
    }

    public String getCarretera() {
        return carretera;
    }

    public Integer getEstado() {
        return estado;
    }

    public String getAlias() {
        return alias;
    }

    public String getSentido() {
        return sentido;
    }

    public Double getPK() {
        return PK;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getLng() {
        return lng;
    }

    public String getCodEle() {
        return codEle;
    }

    public Double getLat() {
        return lat;
    }

    public String getPrecision() {
        return precision;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getTipoInci() {
        return tipoInci;
    }

    public Integer getPkFinal() {
        return pkFinal;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCausa() {
        return causa;
    }

    public String getHora() {
        return hora;
    }

    public Integer getPkIni() {
        return pkIni;
    }

    public String getIcono() {
        return icono;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getNivel() {
        return nivel;
    }


}
