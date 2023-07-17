package service;

import java.sql.*;
import static org.example.Main.connection;
public class InventarioServices {
    // 2 - ADICIONAR ITEM AO INVENTARIO;
    public static void insereCompraNoBanco(int idInformado, String item_comprado, int valor_item) {
        String sql = "INSERT INTO recursos (rebelde_id, item_comprado, valor_item) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idInformado);
            preparedStatement.setString(2, item_comprado);
            preparedStatement.setInt(3, valor_item);
            preparedStatement.executeUpdate();
            System.out.println("Inserção feita com sucesso!\n");
            System.out.println("INVENTÁRIO");
            InventarioServices.invetarioDoRebelde(idInformado);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //7 - CONSULTAR INVENTARIO INDIVIDUAL: LISTA TODAS AS COMPRAS DO REBELDE
    public static void invetarioDoRebelde(int idInformado) {
        String sql = "SELECT * FROM recursos WHERE rebelde_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idInformado);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("rebelde_id") + " | Item: " + resultSet.getString("item_comprado") +
                        " | Valor: $" + resultSet.getInt("valor_item"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
