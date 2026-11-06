package domain.model;

public class Empleado {
    private Long id;
    private String nombre;
    private String email;
    private Long departamentoId;

    public Empleado(Long id, String nombre, String email, Long departamentoId) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }
}
