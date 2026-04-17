package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Usuario;
import java.util.List;

public interface UsuarioRepository {
    void adicionar(Usuario usuario);
    Usuario obterPorId(String id);
    Usuario obterPorEmail(String email);
    List<Usuario> obterTodos();
    void limpar();
}

