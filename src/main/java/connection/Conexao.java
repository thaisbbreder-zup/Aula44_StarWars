package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import connection.Conexao;

import static org.example.Main.statement;
import static org.example.Main.connection;
public class Conexao {
    // CONEXAO COM O BANCO
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "thais123");
            if (connection != null) {
                System.out.println("Banco de dados conectado! Que a força esteja com você!");
            } else {
                System.out.println("Conexão com o banco de dados falhou!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
