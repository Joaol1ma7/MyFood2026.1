package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repositories.UsuarioRepository;
import br.ufal.ic.myfood.repositories.UsuarioRepositoryImpl;
import br.ufal.ic.myfood.validators.UsuarioValidator;

import java.util.List;

public class UsuarioManager {

    private UsuarioRepository usuarioRepository;
    private UsuarioValidator usuarioValidator;

    public UsuarioManager() {
        this.usuarioRepository = new UsuarioRepositoryImpl();
        this.usuarioValidator = new UsuarioValidator();
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws UsuarioJaExisteException, DadosInvalidosException {
        usuarioValidator.validarNome(nome);
        usuarioValidator.validarEmail(email);
        usuarioValidator.validarSenha(senha);
        usuarioValidator.validarEndereco(endereco);

        if (cpf != null) {
            usuarioValidator.validarCPF(cpf);
        }

        if (usuarioRepository.obterPorEmail(email) != null) {
            throw new UsuarioJaExisteException();
        }

        usuarioRepository.adicionar(new Usuario(nome, email, senha, endereco, cpf));
    }

    public String getAtributoUsuario(String id, String atributo) throws UsuarioNaoExisteException {
        Usuario usuario = usuarioRepository.obterPorId(id);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }

        switch(atributo.toLowerCase()) {
            case "nome":
                return usuario.getNome();
            case "email":
                return usuario.getEmail();
            case "senha":
                return usuario.getSenha();
            case "endereco":
                return usuario.getEndereco();
            case "cpf":
                return usuario.getCpf();
            case "id":
                return usuario.getId();
            default:
                return null;
        }
    }

    public String login(String email, String senha) throws LoginInvalidoException {
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            throw new LoginInvalidoException();
        }

        Usuario usuario = usuarioRepository.obterPorEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario.getId();
        }
        throw new LoginInvalidoException();
    }

    public Usuario getUsuarioById(String id) {
        return usuarioRepository.obterPorId(id);
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.obterTodos();
    }

    public void zerarDados() {
        usuarioRepository.limpar();
    }

}
