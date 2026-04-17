package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Empresa;
import java.util.List;

public interface EmpresaRepository {
    void adicionar(Empresa empresa);
    Empresa obterPorId(int id);
    List<Empresa> obterPorDono(String idDono);
    List<Empresa> obterTodas();
    void limpar();
}

