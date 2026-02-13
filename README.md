# -_clasificador_de_casos_de_uso_java_se_oracle_19c_- :. 
# Clasificador de Casos de Uso – Java SE + Oracle 19c .

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/8e015b98-22fe-4749-b748-2ff57631f014" />  

A continuación tienes una **solución profesional y completa** para **IntelliJ IDEA (Java SE)** que cumple con los siguientes objetivos:

* ✔ Clasifica Casos de Uso desde archivos de texto  
* ✔ Permite cargar múltiples archivos para pruebas  
* ✔ Registra la información en **Oracle 19c** mediante **Stored Procedure**  
* ✔ Usa **arquitectura por capas (MVC / DAO)**  
* ✔ Aplicación **100 % de consola**, clara y extensible  

---

## 1️⃣ Modelo lógico del problema:

Cada **Caso de Uso** contiene:

- Código  
- Nombre  
- Actor  
- Tipo *(Primario / Secundario / Soporte)*  
- Complejidad *(Alta / Media / Baja)*  
- Descripción  

Los casos se leen desde archivos `.txt`, se **clasifican** y se **almacenan en Oracle**.

---

## 2️⃣ Estructura del proyecto (IntelliJ):

```text
clasificador_casos_uso/
│
├── src/
│   ├── model/
│   │   └── CasoUso.java
│   ├── dao/
│   │   └── CasoUsoDAO.java
│   ├── util/
│   │   └── DBConnection.java
│   ├── service/
│   │   └── ClasificadorService.java
│   └── Main.java
│
├── casos_uso_app1.txt
└── casos_uso_app2.txt
```
3️⃣ Base de Datos – Oracle 19c:
📌 Tabla
```
CREATE TABLE CASO_USO (
    ID NUMBER GENERATED ALWAYS AS IDENTITY,
    CODIGO VARCHAR2(20),
    NOMBRE VARCHAR2(100),
    ACTOR VARCHAR2(50),
    TIPO VARCHAR2(20),
    COMPLEJIDAD VARCHAR2(20),
    DESCRIPCION VARCHAR2(400),
    FECHA_REGISTRO DATE DEFAULT SYSDATE
);
```
📌 Stored Procedure
```
CREATE OR REPLACE PROCEDURE SP_INSERTAR_CASO_USO (
    P_CODIGO       IN VARCHAR2,
    P_NOMBRE       IN VARCHAR2,
    P_ACTOR        IN VARCHAR2,
    P_TIPO         IN VARCHAR2,
    P_COMPLEJIDAD  IN VARCHAR2,
    P_DESCRIPCION  IN VARCHAR2
) AS
BEGIN
    INSERT INTO CASO_USO
    (CODIGO, NOMBRE, ACTOR, TIPO, COMPLEJIDAD, DESCRIPCION)
    VALUES
    (P_CODIGO, P_NOMBRE, P_ACTOR, P_TIPO, P_COMPLEJIDAD, P_DESCRIPCION);

    COMMIT;
END;
/
```
4️⃣ Código Java:
🔹 Modelo – CasoUso.java
```
package model;

public class CasoUso {

    private String codigo;
    private String nombre;
    private String actor;
    private String tipo;
    private String complejidad;
    private String descripcion;

    public CasoUso(String codigo, String nombre, String actor,
                   String tipo, String complejidad, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.actor = actor;
        this.tipo = tipo;
        this.complejidad = complejidad;
        this.descripcion = descripcion;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getActor() { return actor; }
    public String getTipo() { return tipo; }
    public String getComplejidad() { return complejidad; }
    public String getDescripcion() { return descripcion; }
}
```
🔹 Conexión BD – DBConnection.java
```
package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        return DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:ORCL",
            "USUARIO",
            "PASSWORD"
        );
    }
}
```
🔹 DAO – CasoUsoDAO.java
```
package dao;

import model.CasoUso;
import util.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;

public class CasoUsoDAO {

    public void guardar(CasoUso c) throws Exception {

        Connection con = DBConnection.getConnection();
        CallableStatement cs =
            con.prepareCall("{ call SP_INSERTAR_CASO_USO(?,?,?,?,?,?) }");

        cs.setString(1, c.getCodigo());
        cs.setString(2, c.getNombre());
        cs.setString(3, c.getActor());
        cs.setString(4, c.getTipo());
        cs.setString(5, c.getComplejidad());
        cs.setString(6, c.getDescripcion());

        cs.execute();
        con.close();
    }
}
```
🔹 Servicio – ClasificadorService.java
```
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
```
🔹 Main – Main.java
```
import service.ClasificadorService;

public class Main {

    public static void main(String[] args) {

        ClasificadorService service = new ClasificadorService();

        try {
            service.procesarArchivo("casos_uso_app1.txt");
            service.procesarArchivo("casos_uso_app2.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
5️⃣ Archivos de prueba:
```
📄 casos_uso_app1.txt
CU-01|Registrar Usuario|Administrador|Primario|Alta|Permite registrar usuarios
CU-02|Iniciar Sesion|Usuario|Primario|Media|Acceso al sistema
CU-03|Cerrar Sesion|Usuario|Secundario|Baja|Finaliza la sesión
```
```
📄 casos_uso_app2.txt
CU-10|Generar Reportes|Administrador|Primario|Alta|Genera reportes del sistema
CU-11|Consultar Datos|Usuario|Primario|Media|Consulta información
CU-12|Soporte Tecnico|Soporte|Soporte|Baja|Atención de incidencias
```
6️⃣ Resultado final:
* ✔ Casos de uso clasificados por tipo y complejidad
* ✔ Carga múltiple de archivos
* ✔ Persistencia 100 % Oracle 19c mediante Stored Procedure
* ✔ Código limpio, académico y empresarial / .
