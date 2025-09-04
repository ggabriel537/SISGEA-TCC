package com.BancoDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String usuario = "postgres";
    private static final String senha = "sisgea123";
    //criar um arquivo para guardar os dados da conexao com o banco de dados
    public static Connection getConexao() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }
}
