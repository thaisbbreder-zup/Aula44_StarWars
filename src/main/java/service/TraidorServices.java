package service;

import java.sql.*;

import static org.example.Main.connection;

public class TraidorServices {
    // 4 - REPORTAR TRAIDOR
    public static void reportarRebelde(int idInformado) {
        if (RebeldeServices.isRebeldeAtivo(idInformado)) {
            int totalReportes = contagemReportes(idInformado);
            totalReportes++;
            atualizarContagemReportes(idInformado, totalReportes);
            if (totalReportes >= 3) {
                String sqlUpdate = "UPDATE rebeldes SET ativo = false WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
                    preparedStatement.setInt(1, idInformado);
                    preparedStatement.executeUpdate();
                    System.out.println("O rebelde foi marcado como traidor e agora está inativo na Aliança Rebelde.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Rebelde reportado como traidor. Total de reportes: " + (totalReportes));
            }
        } else {
            System.out.println("Rebelde com o ID informado não existe ou não está ativo.");
        }
    }

    public static int contagemReportes(int idInformado) {
        String sql = "SELECT reportes FROM rebeldes WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idInformado);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("reportes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void atualizarContagemReportes(int idInformado, int totalReportes) {
        String sqlUpdate = "UPDATE rebeldes SET reportes = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setInt(1, totalReportes);
            preparedStatement.setInt(2, idInformado);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
