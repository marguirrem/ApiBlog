package com.marlonaguirre.apiblog.core.models.dao;

import com.marlonaguirre.apiblog.core.models.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolDao extends JpaRepository<Rol,Long> {
}
