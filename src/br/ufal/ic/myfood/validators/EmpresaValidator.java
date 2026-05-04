package br.ufal.ic.myfood.validators;

import br.ufal.ic.myfood.exceptions.*;

public class EmpresaValidator {

    public void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }
    }

    public void validarAtributo(String atributo) throws AtributoInvalidoException {
        if (atributo == null || atributo.isEmpty() || atributo.trim().isEmpty()) {
            throw new AtributoInvalidoException();
        }
    }

    public void validarIndice(int indice) throws IndiceInvalidoException {
        if (indice < 0) {
            throw new IndiceInvalidoException();
        }
    }

    public void validarEndereco(String endereco) throws EnderecoEmpresaInvalidoException {
        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoEmpresaInvalidoException();
        }
    }

    public void validarTipoEmpresa(String tipoEmpresa) throws TipoEmpresaInvalidaException {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new TipoEmpresaInvalidaException();
        }
    }

    public void validarTipoMercado(String tipoMercado) throws TipoMercadoInvalidoException {
        if (tipoMercado == null || tipoMercado.isEmpty()) {
            throw new TipoMercadoInvalidoException();
        }
        if (!tipoMercado.toLowerCase().equals("supermercado") &&
            !tipoMercado.toLowerCase().equals("minimercado") &&
            !tipoMercado.toLowerCase().equals("atacadista")) {
            throw new TipoMercadoInvalidoException();
        }
    }

    public void validarHorario(String abre, String fecha) throws FormatoHoraInvalidoException, HorarioInvalidoException {
        boolean abreVazio = abre == null || abre.isEmpty();
        boolean fechaVazio = fecha == null || fecha.isEmpty();

        if (abreVazio || fechaVazio) {
            throw new HorarioInvalidoException();
        }

        validarFormatoHora(abre);
        validarFormatoHora(fecha);
        validarLogicaHorario(abre, fecha);
    }

    public void validarHorarioParaCriar(String abre, String fecha) throws FormatoHoraInvalidoException, HorarioInvalidoException {
        boolean abreNull = abre == null;
        boolean fechaNull = fecha == null;
        boolean abreEmpty = !abreNull && abre.isEmpty();
        boolean fechaEmpty = !fechaNull && fecha.isEmpty();

        if (abreNull || fechaNull) {
            throw new HorarioInvalidoException();
        }

        if (abreEmpty || fechaEmpty) {
            throw new FormatoHoraInvalidoException();
        }

        validarFormatoHora(abre);
        validarFormatoHora(fecha);
        validarLogicaHorario(abre, fecha);
    }

    private void validarFormatoHora(String hora) throws FormatoHoraInvalidoException, HorarioInvalidoException {
        if (!hora.matches("\\d{2}:\\d{2}")) {
            throw new FormatoHoraInvalidoException();
        }

        String[] partes = hora.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);

        if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) {
            throw new HorarioInvalidoException();
        }
    }

    private void validarLogicaHorario(String abre, String fecha) throws HorarioInvalidoException {
        String[] parteAbre = abre.split(":");
        String[] parteFecha = fecha.split(":");

        int horaAbre = Integer.parseInt(parteAbre[0]);
        int minutoAbre = Integer.parseInt(parteAbre[1]);
        int horaFecha = Integer.parseInt(parteFecha[0]);
        int minutoFecha = Integer.parseInt(parteFecha[1]);

        int tempoAbre = horaAbre * 60 + minutoAbre;
        int tempoFecha = horaFecha * 60 + minutoFecha;

        if (tempoAbre >= tempoFecha) {
            throw new HorarioInvalidoException();
        }
    }
}

