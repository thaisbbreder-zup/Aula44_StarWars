package org.example;


import java.sql.*;
import java.util.Scanner;


public class Main {
    public int id;
    public String nome;
    public int idade;
    public String genero;
    public String localizacao;
    public boolean ativo;
    public int recurso_id;
    public int rebelde_id;
    public String item_comprado;
    public double valor_item;


    private static Connection connection;
    private static Statement statement;


    // CONEXAO COM O BANCO
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "thais123");
            if (connection != null) {
                System.out.println("Banco de dados conectado!");
            } else {
                System.out.println("Conexão com o banco de dados falhou!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    // MENU / INTERFACE
    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        statement = connection.createStatement();


        Scanner entradaDoUsuario = new Scanner(System.in);
        System.out.println("MENU");
        System.out.println("1 - ADICIONAR NOVO REBELDE;");
        System.out.println("2 - ADICIONAR ITEM AO INVENTARIO;");
        System.out.println("3 - ATUALIZAR LOCALIZAÇÃO");
        System.out.println("4 - REPORTAR TRAIDOR;");
        System.out.println("5 - CONSULTAR RELATÓRIO EM PORCENTAGEM;");
        System.out.println("6 - CONSULTAR DADOS DOS REBELDES;");
        System.out.println("7 - CONSULTAR INVENTARIO INDIVIDUAL;");
        System.out.println("Escolha uma opção:");


        int opcao = entradaDoUsuario.nextInt();
        entradaDoUsuario.nextLine();


        switch (opcao) {
            case 1:
                System.out.println("Insira os dados");
                System.out.println("Nome: ");
                String nome = entradaDoUsuario.nextLine();
                System.out.println("Idade: ");
                int idade = entradaDoUsuario.nextInt();
                System.out.println("Gênero: ");
                String genero = entradaDoUsuario.next();
                System.out.println("Localização: ");
                String localizacao = entradaDoUsuario.nextLine();
                entradaDoUsuario.nextLine();
                System.out.println("Ativo: ");
                boolean ativo = entradaDoUsuario.nextBoolean();


                insereDadosNoBanco(nome, idade, genero, localizacao, ativo);
                break;
            case 2:
                System.out.println("LOJA");
                System.out.println("Item: Arma \nValor: $100");
                System.out.println("Item: Munição \nValor: $30");
                System.out.println("Item: Água \nValor: $5");
                System.out.println("Item: Comida \nValor: $15");


                System.out.println("Informe o id do rebelde: ");
                int idInformado = entradaDoUsuario.nextInt();
                System.out.println("Informe o nome do produto comprado:");
                String item_comprado = entradaDoUsuario.next();
                System.out.println("Informe o valor do produto:");
                int valor_item = entradaDoUsuario.nextInt();

                if (isRebeldeAtivo(idInformado)) {
                    insereCompraNoBanco(idInformado, item_comprado, valor_item);
                } else {
                    System.out.println("O rebelde informado é um traidor e não pode realizar compras.");
                }
                break;
            case 3:
                System.out.println("Informe o id do rebelde: ");
                idInformado = entradaDoUsuario.nextInt();
                entradaDoUsuario.nextLine();
                System.out.println("Informe o a nova localização: ");
                String novaLocalizacao = entradaDoUsuario.nextLine();


                atualizaLocalizacaoNaTabela(idInformado, novaLocalizacao);
                break;


            case 4:
                System.out.println("Informe o ID do rebelde que você deseja reportar como traidor: ");
                idInformado = entradaDoUsuario.nextInt();
                reportarRebelde(idInformado);
                break;
            case 5:
                obterTotalRebeldes();
                obterTotalTraidores();
                break;
            case 6:
                consultaDadosNoBanco();
                break;
            case 7:
                System.out.println("Informe o id do rebelde: ");
                idInformado = entradaDoUsuario.nextInt();
                invetarioDoRebelde(idInformado);
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }


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
            consultaDadosNoBanco();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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
            invetarioDoRebelde(idInformado);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //VERIFICA SE O ID INFORMADO NA HORA DE INSERIR A COMPRA EXISTE OU NÃO
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


    // 3 - ATUALIZA DADOS
    public static void atualizaLocalizacaoNaTabela(int idInformado, String novaLocalizacao) {
        String sql = "UPDATE rebeldes set localizacao = '" + novaLocalizacao + "' where id = '" + idInformado + "'";
        try {
            statement.executeUpdate(sql);
            System.out.println("Localização atualizada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 4 - REPORTAR TRAIDOR
    public static void reportarRebelde(int idInformado) {


        if (isRebeldeAtivo(idInformado)) {
            int totalReportes = contagemReportes(idInformado);
            totalReportes++;
            atualizarContagemReportes(idInformado, totalReportes);
            if (totalReportes >= 3) {
                String sqlUpdate = "UPDATE rebeldes SET ativo = false WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
                    preparedStatement.setInt(1, idInformado);
                    preparedStatement.executeUpdate();
                    System.out.println("Rebelde marcado como traidor e se tornou inativo!");
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
                    System.out.println("Porcentagem de rebeldes ativos: " + porcentagemAtivos + "%");
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
                    System.out.println("Porcentagem de rebeldes inativos: " + porcentagemInativos + "%");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //INVENTÁRIO: LISTA TODAS AS COMPRAS DO REBELDE
    public static void invetarioDoRebelde(int idInformado) {
        String sql = "SELECT * FROM recursos WHERE rebelde_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idInformado);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Item: " + resultSet.getString("item_comprado") +
                        " | Valor: $" + resultSet.getInt("valor_item"));
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
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") + " | NOME: " +
                        resultSet.getString("nome") + " | IDADE: " + resultSet.getInt("idade") +
                        " | GÊNERO: " + resultSet.getString("genero") +
                        " | LOCALIZAÇÃO: " + resultSet.getString("localizacao") +
                        " | ATIVO: " + resultSet.getBoolean("ativo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
