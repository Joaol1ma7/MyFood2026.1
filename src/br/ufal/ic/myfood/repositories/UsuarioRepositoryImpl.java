package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    private List<Usuario> usuarioList;

    public UsuarioRepositoryImpl() {
        this.usuarioList = new ArrayList<>();
    }

    @Override
    public void adicionar(Usuario usuario) {
        usuarioList.add(usuario);
    }

    @Override
    public Usuario obterPorId(String id) {
        for (Usuario usuario : usuarioList) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario obterPorEmail(String email) {
        for (Usuario usuario : usuarioList) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> obterTodos() {
        return new ArrayList<>(usuarioList);
    }

    @Override
    public void limpar() {
        usuarioList.clear();
    }
}

