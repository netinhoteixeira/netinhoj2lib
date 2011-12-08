package info.netinho.util;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

public class SistemaHistorico {

    public static int ENTRADA = 1;
    public static int SAIDA = 2;
    public static int RESGATEDASENHA = 3;
    public static int CONSULTA = 4;
    public static int INSERCAO = 5;
    public static int EDICAO = 6;
    public static int REMOCAO = 7;
    private ConnectionDataSourceTomcat conexao;
    private int usuario;
    private String sessao;

    public SistemaHistorico() {
        this.conexao = null;
        this.usuario = -1;
        this.sessao = null;
    }

    public SistemaHistorico(ConnectionDataSourceTomcat conexao) {
        this.conexao = conexao;
        this.usuario = -1;
        this.sessao = null;
    }

    public SistemaHistorico(ConnectionDataSourceTomcat conexao, int usuario) {
        this.conexao = conexao;
        this.usuario = usuario;
        this.sessao = null;
    }

    public SistemaHistorico(ConnectionDataSourceTomcat conexao, String sessao) {
        this.conexao = conexao;
        this.usuario = -1;
        this.sessao = sessao;
    }

    public SistemaHistorico(ConnectionDataSourceTomcat conexao, int usuario, String sessao) {
        this.conexao = conexao;
        this.usuario = usuario;
        this.sessao = sessao;
    }

    public ConnectionDataSourceTomcat getConexao() {
        return this.conexao;
    }

    public void setConexao(ConnectionDataSourceTomcat conexao) {
        this.conexao = conexao;
    }

    public int getUsuario() {
        return this.usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getSessao() {
        return this.sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    private boolean gravar(int acao, String tabela, String chavenome, String chavevalor, HttpServletRequest requisicao, int larguradajanela, int alturadajanela)
            throws NullPointerException, SQLException {
        String sql = null;

        String susuario = "null";
        String stabela = "null";
        String schavequantidade = "null";
        String schavenome = "null";
        String schavevalor = "null";
        String ssessao = "null";
        String scliente = "null";
        String sagente = "null";
        String saceita = "null";
        String saceitaidioma = "null";
        String saceitafluxo = "null";
        String saceitacodificacao = "null";
        String slarguradajanela = "null";
        String salturadajanela = "null";

        if ((this.conexao.getTipo() != ConnectionDataSourceTomcatTipo.MYSQL)
                && (this.conexao.getTipo() != ConnectionDataSourceTomcatTipo.MSSQL)
                && (this.conexao.getTipo() == ConnectionDataSourceTomcatTipo.ORACLE)) {
            sql = "INSERT INTO sistema_historico (id, Cadastro, Usuario, Acao, Tabela, ChaveQuantidade, ChaveNome, ChaveValor, Sessao, Cliente, Agente, Aceita, AceitaIdioma, AceitaFluxo, AceitaCodificacao, JanelaLargura, JanelaAltura) VALUES (seq_historico.nextval, SYSDATE, %s, %d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)";
        }

        if (this.usuario > 0) {
            susuario = Integer.toString(this.usuario);
        }
        if (tabela != null) {
            stabela = "'" + tabela + "'";
        }
        if (chavenome != null) {
            String[] chavequantidade = Text.splitPreserveAllTokens(chavenome, "|");
            schavequantidade = Integer.toString(chavequantidade.length);
            schavenome = "'";
            if (chavenome.length() > 100) {
                schavenome = schavenome + chavenome.substring(0, 100);
            } else {
                schavenome = schavenome + chavenome;
            }
            schavenome = schavenome + "'";
        }
        if (chavevalor != null) {
            schavevalor = "'";
            if (chavevalor.length() > 1000) {
                schavevalor = schavevalor + chavevalor.substring(0, 1000);
            } else {
                schavevalor = schavevalor + chavevalor;
            }
            schavevalor = schavevalor + "'";
        }
        if (this.sessao != null) {
            ssessao = "'";
            if (this.sessao.length() > 50) {
                ssessao = ssessao + this.sessao.substring(0, 50);
            } else {
                ssessao = ssessao + this.sessao;
            }
            ssessao = ssessao + "'";
        }
        if (requisicao != null) {
            if (requisicao.getRemoteAddr() != null) {
                scliente = "'";
                if (requisicao.getRemoteAddr().length() > 150) {
                    scliente = scliente + requisicao.getRemoteAddr().substring(0, 150);
                } else {
                    scliente = scliente + requisicao.getRemoteAddr();
                }
                scliente = scliente + "'";
            }
            if (requisicao.getHeader("User-Agent") != null) {
                sagente = "'";
                if (requisicao.getHeader("User-Agent").length() > 300) {
                    sagente = sagente + requisicao.getHeader("User-Agent").substring(0, 300);
                } else {
                    sagente = sagente + requisicao.getHeader("User-Agent");
                }
                sagente = sagente + "'";
            }
            if (requisicao.getHeader("Accept") != null) {
                saceita = "'";
                if (requisicao.getHeader("Accept").length() > 150) {
                    saceita = saceita + requisicao.getHeader("Accept").substring(0, 150);
                } else {
                    saceita = saceita + requisicao.getHeader("Accept");
                }
                saceita = saceita + "'";
            }
            if (requisicao.getHeader("Accept-Language") != null) {
                saceitaidioma = "'";
                if (requisicao.getHeader("Accept-Language").length() > 150) {
                    saceitaidioma = saceitaidioma + requisicao.getHeader("Accept-Language").substring(0, 150);
                } else {
                    saceitaidioma = saceitaidioma + requisicao.getHeader("Accept-Language");
                }
                saceitaidioma = saceitaidioma + "'";
            }
            if (requisicao.getHeader("Accept-Encoding") != null) {
                saceitafluxo = "'";
                if (requisicao.getHeader("Accept-Encoding").length() > 150) {
                    saceitafluxo = saceitafluxo + requisicao.getHeader("Accept-Encoding").substring(0, 150);
                } else {
                    saceitafluxo = saceitafluxo + requisicao.getHeader("Accept-Encoding");
                }
                saceitafluxo = saceitafluxo + "'";
            }
            if (requisicao.getHeader("Accept-Charset") != null) {
                saceitacodificacao = "'";
                if (requisicao.getHeader("Accept-Charset").length() > 150) {
                    saceitacodificacao = saceitacodificacao + requisicao.getHeader("Accept-Charset").substring(0, 150);
                } else {
                    saceitacodificacao = saceitacodificacao + requisicao.getHeader("Accept-Charset");
                }
                saceitacodificacao = saceitacodificacao + "'";
            }
        }
        if (larguradajanela > 0) {
            slarguradajanela = Integer.toString(larguradajanela);
        }
        if (alturadajanela > 0) {
            salturadajanela = Integer.toString(alturadajanela);
        }

        sql = String.format(sql, new Object[]{susuario, Integer.valueOf(acao), stabela, schavequantidade, schavenome, schavevalor, ssessao, scliente, sagente, saceita, saceitaidioma, saceitafluxo, saceitacodificacao, slarguradajanela, salturadajanela});

        return this.conexao.getExecuteStatement(sql) > 0;
    }

    public boolean gravarEntrada(String chavenome, String chavevalor, HttpServletRequest requisicao, int larguradajanela, int alturadajanela)
            throws NullPointerException, SQLException {
        return gravar(ENTRADA, null, chavenome, chavevalor, requisicao, larguradajanela, alturadajanela);
    }

    public boolean gravarEntrada(HttpServletRequest requisicao, int larguradajanela, int alturadajanela)
            throws NullPointerException, SQLException {
        return gravarEntrada(null, null, requisicao, larguradajanela, alturadajanela);
    }

    public boolean gravarSaida()
            throws NullPointerException, SQLException {
        return gravar(SAIDA, null, null, null, null, -1, -1);
    }

    public boolean gravarResgateDaSenha()
            throws NullPointerException, SQLException {
        return gravar(RESGATEDASENHA, null, null, null, null, -1, -1);
    }

    public boolean gravarInsercao(String tabela, String chavenome, String chavevalor)
            throws NullPointerException, SQLException {
        return gravar(INSERCAO, tabela, chavenome, chavevalor, null, -1, -1);
    }

    public boolean gravarEdicao(String tabela, String chavenome, String chavevalor)
            throws NullPointerException, SQLException {
        return gravar(EDICAO, tabela, chavenome, chavevalor, null, -1, -1);
    }

    public boolean gravarRemocao(String tabela, String chavenome, String chavevalor)
            throws NullPointerException, SQLException {
        return gravar(REMOCAO, tabela, chavenome, chavevalor, null, -1, -1);
    }

    public boolean gravarConsulta(String tabela, String chavenome, String chavevalor)
            throws NullPointerException, SQLException {
        return gravar(CONSULTA, tabela, chavenome, chavevalor, null, -1, -1);
    }
}