CREATE TABLE IF NOT EXISTS empresa (
                                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                       nombre VARCHAR(100) NOT NULL,
    cif VARCHAR(20) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS departamento (
                                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                            nombre VARCHAR(100) NOT NULL,
    empresa_id BIGINT NOT NULL,
    CONSTRAINT fk_departamento_empresa
    FOREIGN KEY (empresa_id)
    REFERENCES empresa(id)
    );

CREATE TABLE IF NOT EXISTS empleado (
                                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    departamento_id BIGINT NOT NULL,
    CONSTRAINT fk_empleado_departamento
    FOREIGN KEY (departamento_id)
    REFERENCES departamento(id)
    );