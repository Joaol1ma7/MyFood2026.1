package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Empresa;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepositoryImpl implements EmpresaRepository {
    private List<Empresa> empresaList;
    private int proximoId;

    public EmpresaRepositoryImpl() {
        this.empresaList = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void adicionar(Empresa empresa) {
        empresa.setId(proximoId);
        empresaList.add(empresa);
        proximoId++;
    }

    @Override
    public Empresa obterPorId(int id) {
        for (Empresa empresa : empresaList) {
            if (empresa.getId() == id) {
                return empresa;
            }
        }
        return null;
    }

    @Override
    public List<Empresa> obterPorDono(String idDono) {
        List<Empresa> empresas = new ArrayList<>();
        for (Empresa empresa : empresaList) {
            if (empresa.getIdDono().equals(idDono)) {
                empresas.add(empresa);
            }
        }
        return empresas;
    }

    @Override
    public List<Empresa> obterTodas() {
        return new ArrayList<>(empresaList);
    }

    @Override
    public void limpar() {
        empresaList.clear();
        proximoId = 1;
    }
}

