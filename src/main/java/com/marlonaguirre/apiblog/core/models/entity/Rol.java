package com.marlonaguirre.apiblog.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rol")
public class Rol  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Debe proporcionar un nombre para el rol")
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estado")
    private int estado;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;
}
