package mx.edu.diseniosistemas.restaurante.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class ConexionBD {
    private static final String JNDI_NAME = "jdbc/restauranteDB";

    private ConexionBD() {}

    public static Connection getConnection() throws SQLException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(JNDI_NAME);
            return ds.getConnection();
        } catch (NamingException e) {
            throw new SQLException("No se encontro el recurso JDBC " + JNDI_NAME, e);
        }
    }
}