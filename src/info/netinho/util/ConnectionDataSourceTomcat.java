package info.netinho.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class ConnectionDataSourceTomcat {

    private ConnectionDataSourceTomcatTipo tipo;
    private Connection conexao;
    private ResultSet dados;

    public ConnectionDataSourceTomcat(String nome, ConnectionDataSourceTomcatTipo tipo)
            throws SQLException, NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup(nome);
        this.conexao = ds.getConnection();
        this.conexao.setAutoCommit(true);
        this.conexao.setTransactionIsolation(2);
        this.tipo = tipo;
    }

    public ConnectionDataSourceTomcat(String driver, String endereco, String usuario, String senha, ConnectionDataSourceTomcatTipo tipo) throws SQLException, NamingException {
        this.createConnection(driver, endereco, usuario, senha, tipo);
    }

    public ConnectionDataSourceTomcat(String driver, String endereco, String usuario, String senha, ConnectionDataSourceTomcatTipo tipo, boolean processvariables) throws SQLException, NamingException {
        this.createConnection(driver, endereco, usuario, senha, tipo);
        if (processvariables) {
            this.processVariablesOnConnection();
        }
    }

    public void processVariablesOnConnection() throws SQLException, NamingException {
        this.conexao.setAutoCommit(true);
        this.conexao.setTransactionIsolation(2);

        if (this.conexao != null) {
            if (this.tipo == ConnectionDataSourceTomcatTipo.MYSQL) {
                this.getExecuteStatement("SET NAMES UTF8");
            } else if (this.tipo == ConnectionDataSourceTomcatTipo.ORACLE) {
                // session case insensitive
                this.getExecuteStatement("ALTER SESSION SET NLS_COMP = LINGUISTIC");
                // session accent insensitive
                this.getExecuteStatement("ALTER SESSION SET NLS_SORT = BINARY_AI");
                // date in ISO 8601 format
                this.getExecuteStatement("ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD'");
                // timestamp in ISO 8601 format
                this.getExecuteStatement("ALTER SESSION SET NLS_TIMESTAMP_FORMAT = 'YYYY-MM-DD HH24:MI:SS'");
            }
        }
    }

    private void createConnection(String driver, String endereco, String usuario, String senha, ConnectionDataSourceTomcatTipo tipo) throws SQLException, NamingException {
        this.tipo = tipo;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ConnectionDataSourceTomcat.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.conexao = DriverManager.getConnection(endereco, usuario, senha);
    }

    public Connection getConnection() {
        return this.conexao;
    }

    public ConnectionDataSourceTomcatTipo getTipo() {
        return this.tipo;
    }

    public int getExecuteStatement(String sql) throws SQLException {
        return this.conexao.createStatement().executeUpdate(sql);
    }

    public PreparedStatementLogable getPreparedStatement(String sql) throws SQLException {
        return new PreparedStatementLogable(this.conexao, sql);
    }

    public ResultSet getSelectStatement(String sql) throws SQLException {
        return this.conexao.createStatement().executeQuery(sql);
    }

    public CallableStatement getCallableStatement(String sql) throws SQLException {
        return this.conexao.prepareCall(sql);
    }

    public Timestamp getDateTime() throws SQLException {
        Timestamp localTimestamp = null;
        String sql = null;

        if (this.tipo == ConnectionDataSourceTomcatTipo.MYSQL) {
            sql = "SELECT NOW();";
        } else if (this.tipo == ConnectionDataSourceTomcatTipo.MSSQL) {
            sql = "SELECT GETDATE();";
        } else if (this.tipo == ConnectionDataSourceTomcatTipo.ORACLE) {
            sql = "SELECT SYSDATE FROM DUAL;";
        }

        if (sql != null) {
            if ((this.dados = getSelectStatement(sql)).next()) {
                localTimestamp = this.dados.getTimestamp(1);
            }

            this.dados.close();
        }

        return localTimestamp;
    }

    public int getLastInsertedIdentification() throws SQLException {
        int i = -1;
        String str = null;

        if ((this.tipo == ConnectionDataSourceTomcatTipo.MYSQL) || (this.tipo == ConnectionDataSourceTomcatTipo.MSSQL)) {
            str = "@@IDENTITY";
        }

        if (str != null) {
            if ((this.dados = getSelectStatement("SELECT " + str)).next()) {
                i = this.dados.getInt(1);
            }

            this.dados.close();
        }

        return i;
    }
}