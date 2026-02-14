package model;

public class CasoUso {

    private String codigo;
    private String nombre;
    private String actor;
    private String tipo;
    private String complejidad;
    private String descripcion;

    public CasoUso(String codigo, String nombre, String actor,
                   String tipo, String complejidad, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.actor = actor;
        this.tipo = tipo;
        this.complejidad = complejidad;
        this.descripcion = descripcion;
    }

    public String getCodigo() {return codigo;}
    public String getNombre() {return nombre;}
    public String getActor() {return actor;}
    public String getTipo() {return tipo;}
    public String getComplejidad() {return complejidad;}
    public String getDescripcion() {return descripcion;}
}
