package service;

import dao.CasoUsoDAO;
import model.CasoUso;

import java.io.BufferedReader;
import java.io.FileReader;

public class ClasificadorService {

    private CasoUsoDAO dao = new CasoUsoDAO();

    public void procesarArchivo(String ruta) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;

        while ((linea = br.readLine()) != null) {

            String[] datos = linea.split("\\|");

            CasoUso c = new CasoUso(
                    datos[0], datos[1], datos[2],
                    datos[3], datos[4], datos[5]
            );

            dao.guardar(c);
            System.out.println("Registrado: " + c.getCodigo());
        }

        br.close();
    }
}   
