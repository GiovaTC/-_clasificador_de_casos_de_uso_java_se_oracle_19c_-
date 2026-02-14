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
