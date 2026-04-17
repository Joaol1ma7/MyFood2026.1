package br.ufal.ic.myfood.persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {
    private static final String DATA_DIR = "data";

    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static void salvar(List<?> lista, String nomeArquivo) {
        File file = new File(DATA_DIR + File.separator + nomeArquivo + ".xml");
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             XMLEncoder encoder = new XMLEncoder(bos)) {
            encoder.writeObject(lista);
            encoder.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregar(String nomeArquivo) {
        File file = new File(DATA_DIR + File.separator + nomeArquivo + ".xml");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             XMLDecoder decoder = new XMLDecoder(bis)) {
            Object obj = decoder.readObject();
            if (obj instanceof List) {
                return (List<T>) obj;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void deletarArquivo(String nomeArquivo) {
        File file = new File(DATA_DIR + File.separator + nomeArquivo + ".xml");
        if (file.exists()) {
            file.delete();
        }
    }

    public static void limparTodosDados() {
        File dir = new File(DATA_DIR);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".xml")) {
                        file.delete();
                    }
                }
            }
        }
    }
}

