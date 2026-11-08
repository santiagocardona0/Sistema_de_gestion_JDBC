package domain.model;

public class Departamento {

    private Long id;
    private String nombre;
    private Long empresaId;

    public Departamento(Long id, String nombre, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
        this.empresaId = empresaId;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
