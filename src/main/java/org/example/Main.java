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
        System.out.println("MENU PRINCIPAL - BASE DA ALIANÇA REBELDE");
        System.out.println("1️⃣ - RECRUTAR NOVOS REBELDES");
        System.out.println("2️⃣ - ADICIONAR ITEM AO INVENTÁRIO DE BORDO");
        System.out.println("3️⃣ - ATUALIZAR COORDENADAS DE LOCALIZAÇÃO");
        System.out.println("4️⃣ - REPORTAR TRAIDOR DA LUTA");
        System.out.println("5️⃣ - CONSULTAR RELATÓRIO DE ATIVIDADE");
        System.out.println("6️⃣ - CONSULTAR DADOS DOS REBELDES E TRAIDORES");
        System.out.println("7️⃣ - CONSULTAR INVENTÁRIO DE BORDO");
        System.out.println("8️⃣ - SAIR");
        System.out.println("Faça sua escolha usando o número correspondente, que a Força esteja com você:");

        int opcao = entradaDoUsuario.nextInt();
        entradaDoUsuario.nextLine();

              switch (opcao) {
                case 1:
                    System.out.println("ADICIONAR NOVO RECRUTA");
                    System.out.println("---------------------------------");
                    System.out.println("Insira os dados do novo recruta!");

                    System.out.println("Nome do Rebelde: ");
                    String nome = entradaDoUsuario.nextLine();

                    System.out.println("Idade do Rebelde: ");
                    int idade = entradaDoUsuario.nextInt();

                    System.out.println("Gênero do Rebelde: ");
                    String genero = entradaDoUsuario.next();

                    System.out.println("Localização do Rebelde: ");
                    String localizacao = entradaDoUsuario.nextLine();

                    entradaDoUsuario.nextLine();
                    System.out.println("Status do Rebelde (Ativo ou Inativo): ");
                    boolean ativo = entradaDoUsuario.nextBoolean();

                    System.out.println("Que a Força esteja com você, recrutando um novo membro para a Aliança Rebelde!\n");
                    RebeldeServices.insereDadosNoBanco(nome, idade, genero, localizacao, ativo);
                    break;
                case 2:
                    System.out.println("LOJA GALÁCTICA");
                    System.out.println("--------------------------------");
                    System.out.println("|     Item     |    Valor    |");
                    System.out.println("--------------------------------");
                    System.out.println("|     Sabre     |    $100    |");
                    System.out.println("|    Blaster    |    $30     |");
                    System.out.println("|      Água     |    $5      |");
                    System.out.println("|    Ration     |    $15     |");
                    System.out.println("--------------------------------");

                    System.out.println("Informe o ID do rebelde: ");
                    int idInformado = entradaDoUsuario.nextInt();

                    System.out.println("Informe o nome do produto desejado:");
                    String item_comprado = entradaDoUsuario.next();

                    System.out.println("Informe o valor do produto:");
                    int valor_item = entradaDoUsuario.nextInt();

                    if (RebeldeServices.isRebeldeAtivo(idInformado)) {
                        InventarioServices.insereCompraNoBanco(idInformado, item_comprado, valor_item);
                    } else {
                        System.out.println("Este rebelde está do lado sombrio da Força e não pode realizar compras na loja galáctica.");
                    }
                    break;
                case 3:
                    System.out.println("ATUALIZAR LOCALIZAÇÃO DO REBELDE");
                    System.out.println("---------------------------------");

                    System.out.println("Informe o ID do rebelde: ");
                    idInformado = entradaDoUsuario.nextInt();
                    entradaDoUsuario.nextLine();

                    System.out.println("Informe a nova localização do rebelde:");
                    String novaLocalizacao = entradaDoUsuario.nextLine();

                    System.out.println("Que a Força esteja com você ao atualizar a localização do rebelde!\n");
                    RebeldeServices.atualizaLocalizacaoNaTabela(idInformado, novaLocalizacao);
                    break;
                case 4:
                    System.out.println("REPORTAR REBELDE COMO TRAIDOR");
                    System.out.println("-------------------------------");

                    System.out.println("Informe o ID do rebelde que você deseja reportar como traidor: ");
                    idInformado = entradaDoUsuario.nextInt();
                    entradaDoUsuario.nextLine();

                    TraidorServices.reportarRebelde(idInformado);

                    System.out.println("Que a Força esteja com você!\n");
                    break;
                case 5:
                    System.out.println("RELATÓRIO DE REBELDES E TRAIDORES");
                    System.out.println("----------------------------------");
                    System.out.println("Que a Força esteja com você ao obter os dados sobre a quantidade de rebeldes e traidores na Aliança Rebelde!");
                    System.out.println("Use com sabedoria!\n");
                    DadosServices.obterTotalRebeldes();
                    DadosServices.obterTotalTraidores();
                    break;
                case 6:
                    System.out.println("CONSULTAR DADOS DOS REBELDES E TRAIDORES");
                    System.out.println("----------------------------------------");
                    System.out.println("Que a Força esteja com você ao consultar os dados dos rebeldes e traidores na Aliança Rebelde!");
                    DadosServices.consultaDadosNoBanco();
                    break;
                case 7:
                    System.out.println("CONSULTAR INVENTÁRIO DO REBELDE");
                    System.out.println("-------------------------------");
                    System.out.println("Que a Força esteja com você ao consultar o inventário do rebelde na Aliança Rebelde!");
                    System.out.println("Informe o ID do rebelde: ");
                    idInformado = entradaDoUsuario.nextInt();
                    entradaDoUsuario.nextLine();

                    InventarioServices.invetarioDoRebelde(idInformado);
                    break;
                case 8:
                    System.out.println("Ok, deixando o sistema...Que a força esteja sempre com você.");
                    break;
                default:
                    System.out.println("Opção inválida! Que a Força esteja com você na próxima escolha.");
                    break;
            }
        }
    }

