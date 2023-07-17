package service;

import java.sql.*;
import static org.example.Main.statement;
import static org.example.Main.connection;

public class RebeldeServices {
    //1 - ADICIONAR REBELDE;
    public static void insereDadosNoBanco(String nome, int idade, String genero, String localizacao, boolean ativo) {
        String sql = "INSERT INTO rebeldes (nome, idade, genero, localizacao, ativo) VALUES (?, ?, ?, ?, ?)";
        try { //PreparedStatement é uma abstração fornecida pela API JDBC para permitir consultas parametrizadas
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, idade);
            preparedStatement.setString(3, genero);
            preparedStatement.setString(4, localizacao);
            preparedStatement.setBoolean(5, ativo);
            preparedStatement.executeUpdate();
            System.out.println("Inserção feita com sucesso!\n");
            DadosServices.consultaDadosNoBanco();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //VERIFICA SE O ID INFORMADO EXISTE OU NÃO
    public static boolean isRebeldeAtivo(int idInformado) {
        String sql = "SELECT ativo FROM rebeldes WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idInformado);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                boolean ativo = resultSet.getBoolean("ativo");
                return ativo;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3 - ATUALIZA LOCALIZACAO
    public static void atualizaLocalizacaoNaTabela(int idInformado, String novaLocalizacao) {
        String sql = "UPDATE rebeldes set localizacao = '" + novaLocalizacao + "' where id = '" + idInformado + "'";
        try {
            statement.executeUpdate(sql);
            System.out.println("Localização atualizada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
