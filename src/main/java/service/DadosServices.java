package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.Main.statement;
import static org.example.Main.connection;

public class DadosServices {
    // 5 - CONSULTAR RELATORIO EM PORCENTAGEM
    public static void obterTotalRebeldes() {
        String sql = "SELECT COUNT(*) AS total FROM rebeldes";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int totalRebeldes = resultSet.getInt("total");

                String sqlAtivos = "SELECT COUNT(*) AS ativos FROM rebeldes WHERE ativo = true";
                PreparedStatement preparedStatementAtivos = connection.prepareStatement(sqlAtivos);
                ResultSet resultSetAtivos = preparedStatementAtivos.executeQuery();

                if (resultSetAtivos.next()) {
                    int totalAtivos = resultSetAtivos.getInt("ativos");
                    double porcentagemAtivos = (totalAtivos * 100.0) / totalRebeldes;
                    System.out.println("--------> Porcentagem de rebeldes ativos: " + porcentagemAtivos + "%");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void obterTotalTraidores() {
        String sql = "SELECT COUNT(*) AS total FROM rebeldes";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int totalRebeldes = resultSet.getInt("total");
                String sqlInativos = "SELECT COUNT(*) AS inativos FROM rebeldes WHERE ativo = false";
                PreparedStatement preparedStatementInativos = connection.prepareStatement(sqlInativos);
                ResultSet resultSetInativos = preparedStatementInativos.executeQuery();
                if (resultSetInativos.next()) {
                    int totalInativos = resultSetInativos.getInt("inativos");
                    double porcentagemInativos = (totalInativos * 100.0) / totalRebeldes;
                    System.out.println("--------> Porcentagem de rebeldes inativos(traidores): " + porcentagemInativos + "%");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 6 - VISUALIZAR LISTA DE REBELDES;
    public static void consultaDadosNoBanco() {
        String sql = "SELECT * FROM rebeldes";
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("\nID\t| NOME DO REBELDE\t| IDADE\t| GÊNERO\t| LOCALIZAÇÃO\t| ATIVO");
            System.out.println("-----------------------------------------------------------------");

            while (resultSet.next()) {
                System.out.printf("%-3d\t| %-15s\t| %-5d\t| %-7s\t| %-20s\t| %-5b\n",
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getInt("idade"),
                        resultSet.getString("genero"),
                        resultSet.getString("localizacao"),
                        resultSet.getBoolean("ativo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
