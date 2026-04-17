package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.persistence.PersistenceManager;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    private static final String ARQUIVO = "usuarios";
    private List<Usuario> usuarioList;

    public UsuarioRepositoryImpl() {
        this.usuarioList = PersistenceManager.carregar(ARQUIVO);
    }

    @Override
    public void adicionar(Usuario usuario) {
        usuarioList.add(usuario);
        salvar();
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
        salvar();
    }

    private void salvar() {
        PersistenceManager.salvar(usuarioList, ARQUIVO);
    }
}

