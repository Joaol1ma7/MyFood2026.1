package br.ufal.ic.myfood.validators;

import br.ufal.ic.myfood.exceptions.*;

public class UsuarioValidator {

    public void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }
    }

    public void validarEmail(String email) throws EmailInvalidoException {
        if (email == null || email.isEmpty()) {
            throw new EmailInvalidoException();
        }
        if (!email.contains("@")) {
            throw new EmailInvalidoException();
        }
    }

    public void validarSenha(String senha) throws SenhaInvalidaException {
        if (senha == null || senha.isEmpty()) {
            throw new SenhaInvalidaException();
        }
    }

    public void validarEndereco(String endereco) throws EnderecoInvalidoException {
        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoInvalidoException();
        }
    }

    public void validarCPF(String cpf) throws CpfInvalidoException {
        if (cpf == null || cpf.isEmpty()) {
            throw new CpfInvalidoException();
        }
        if (cpf.length() != 14) {
            throw new CpfInvalidoException();
        }
    }
}

