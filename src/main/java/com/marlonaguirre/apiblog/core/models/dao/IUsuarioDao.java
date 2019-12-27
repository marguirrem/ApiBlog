package com.marlonaguirre.apiblog.core.models.dao;

import com.marlonaguirre.apiblog.core.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDao extends JpaRepository<Usuario,Long> {
}
