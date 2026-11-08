package domain.model;

public class Empresa {

    private Long id;
    private String nombre;
    private String cif;

    public Empresa(Long id, String nombre, String cif) {
        this.id = id;
        this.nombre = nombre;
        this.cif = cif;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCif() {
        return cif;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
