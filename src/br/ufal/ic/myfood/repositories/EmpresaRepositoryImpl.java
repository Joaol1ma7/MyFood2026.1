package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.persistence.IdCounter;
import br.ufal.ic.myfood.persistence.PersistenceManager;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepositoryImpl implements EmpresaRepository {
    private static final String ARQUIVO_EMPRESAS = "empresas";
    private static final String ARQUIVO_ID = "empresas_id";
    private List<Empresa> empresaList;
    private IdCounter idCounter;

    public EmpresaRepositoryImpl() {
        this.empresaList = PersistenceManager.carregar(ARQUIVO_EMPRESAS);
        List<IdCounter> idList = PersistenceManager.carregar(ARQUIVO_ID);
        this.idCounter = idList.isEmpty() ? new IdCounter(1) : idList.get(0);
    }

    @Override
    public void adicionar(Empresa empresa) {
        empresa.setId(idCounter.obterEIncrementar());
        empresaList.add(empresa);
        salvar();
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
        idCounter = new IdCounter(1);
        salvar();
    }

    private void salvar() {
        PersistenceManager.salvar(empresaList, ARQUIVO_EMPRESAS);
        List<IdCounter> idList = new ArrayList<>();
        idList.add(idCounter);
        PersistenceManager.salvar(idList, ARQUIVO_ID);
    }
}

