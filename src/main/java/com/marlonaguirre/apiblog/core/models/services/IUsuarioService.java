package com.marlonaguirre.apiblog.core.models.services;


import com.marlonaguirre.apiblog.core.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> findAll();
    public Page<Usuario>findAll(Pageable pageable);
    public Usuario save(Usuario usuario);
    public Usuario findById(Long id);
    public void delete(Usuario usuario);
    public void delete(Long id);
}
