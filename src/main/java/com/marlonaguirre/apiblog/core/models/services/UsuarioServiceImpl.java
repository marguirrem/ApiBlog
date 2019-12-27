package com.marlonaguirre.apiblog.core.models.services;

import com.marlonaguirre.apiblog.core.models.dao.IUsuarioDao;
import com.marlonaguirre.apiblog.core.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioDao usuarioDao;

    public UsuarioServiceImpl(IUsuarioDao usuarioDao){
        this.usuarioDao = usuarioDao;
    }

    @Override
    public List<Usuario> findAll() {
        return this.usuarioDao.findAll();
    }

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return this.usuarioDao.findAll(pageable);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return this.usuarioDao.save(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return this.usuarioDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Usuario usuario) {
        this.usuarioDao.delete(usuario);
    }

    @Override
    public void delete(Long id) {
        this.usuarioDao.deleteById(id);
    }
}
