package org.example;

import connection.Conexao;
import service.DadosServices;
import service.InventarioServices;
import service.RebeldeServices;
import service.TraidorServices;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static Connection connection;
    public static Statement statement;

    // MENU / INTERFACE
    public static void main(String[] args) throws SQLException {
        Connection connection = Conexao.getConnection();
        statement = connection.createStatement();

        Scanner entradaDoUsuario = new Scanner(System.in);
        System.out.println("MENU");
        System.out.println("1 - ADICIONAR REBELDES;");
        System.out.println("2 - ADICIONAR ITEM AO INVENTARIO;");
        System.out.println("3 - ATUALIZAR LOCALIZAÇÃO");
        System.out.println("4 - REPORTAR TRAIDOR;");
        System.out.println("5 - CONSULTAR RELATÓRIO EM PORCENTAGEM;");
        System.out.println("6 - CONSULTAR DADOS DOS REBELDES;");
        System.out.println("7 - CONSULTAR INVENTARIO;");
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

                RebeldeServices.insereDadosNoBanco(nome, idade, genero, localizacao, ativo);
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

                if (RebeldeServices.isRebeldeAtivo(idInformado)) {
                    InventarioServices.insereCompraNoBanco(idInformado, item_comprado, valor_item);
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

                RebeldeServices.atualizaLocalizacaoNaTabela(idInformado, novaLocalizacao);
                break;
            case 4:
                System.out.println("Informe o ID do rebelde que você deseja reportar como traidor: ");
                idInformado = entradaDoUsuario.nextInt();
                TraidorServices.reportarRebelde(idInformado);
                break;
            case 5:
                DadosServices.obterTotalRebeldes();
                DadosServices.obterTotalTraidores();
                break;
            case 6:
                DadosServices.consultaDadosNoBanco();
                break;
            case 7:
                System.out.println("Informe o id do rebelde: ");
                idInformado = entradaDoUsuario.nextInt();
                InventarioServices.invetarioDoRebelde(idInformado);
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}
