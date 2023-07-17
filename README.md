# ⭐️ Sistema de Gerenciamento dos Rebeldes da Galáxia ⚔️ 
Este é um sistema de gerenciamento de rebeldes inspirado no universo de Star Wars, desenvolvido em Java, que utiliza um banco de dados PostgreSQL para armazenar informações sobre os rebeldes, seus inventários e relatórios.

O sistema permite executar as seguintes funcionalidades:
- __🧝 Adicionar Rebeldes:__ Insere informações sobre um rebelde, como nome, idade, gênero, localização e status de atividade. Os rebeldes adicionados podem ser personagens icônicos do universo de Star Wars.
- __⚔️ Adicionar Item ao Inventário:__ Permite adicionar itens ao inventário de um rebelde, como arma, munição, água e comida. Os itens disponíveis no inventário são baseados nos elementos presentes no universo de Star Wars, como sabres de luz, blasters, rations e água destilada.
- __🌍 Atualizar Localização:__ Atualiza a localização de um rebelde específico no banco de dados. 
- __⚠️ Reportar Traidor:__ Marca um rebelde como traidor se o mesmo for reportado 3 vezes e ele será marcado como inativo e perderá seus privilégios de rebelde. Essa funcionalidade visa manter a ordem e a segurança da rebelião contra o Império Galáctico!
- __📊 Consultar Relatório em Porcentagem:__ Exibe a porcentagem de rebeldes ativos e inativos com base nos dados armazenados no banco de dados. Isso permite que os líderes da rebelião tenham uma visão geral do status e engajamento dos rebeldes na luta contra o Império!
- __📋 Consultar Dados dos Rebeldes:__ Mostra uma lista com todos os rebeldes e suas informações armazenadas no banco de dados. Os dados incluem nome, idade, gênero, localização e status de atividade de cada rebelde.
- __🔍 Consultar Inventário:__ Exibe os itens presentes no inventário de um rebelde específico. Isso permite verificar quais armas, munições, água e comida um rebelde possui, fornecendo informações importantes para a organização da resistência.

#### ℹ️ Pré-requisitos
- Java Development Kit (JDK) instalado.
- PostgreSQL instalado.
- Conexão com o banco de dados configurada corretamente.
- Driver JDBC do PostgreSQL no classpath do projeto.
  
#### ▶️ Executando o Programa
- Clone este repositório em seu computador ou faça o download dos arquivos.
- Certifique-se de ter atendido aos pré-requisitos mencionados acima.
- Configure corretamente as informações de conexão com o banco de dados no arquivo Conexao.java.
- Compile o código-fonte do programa executando o comando javac org/example/Main.java.
- Execute o programa com o comando java org.example.Main.
- Siga as instruções apresentadas no console para interagir com o sistema.

#### 📝 Observações
- Os dados dos rebeldes e seus inventários são armazenados no banco de dados PostgreSQL configurado.
- Certifique-se de ter inserido os dados no banco de dados antes de executar o programa.
- Cada instância do programa Java terá sua própria conexão com o banco de dados local.
- Os dados inseridos em seu banco de dados local não estarão automaticamente disponíveis para outras pessoas.
- Para compartilhar os dados, você pode exportar o banco de dados, compartilhar scripts SQL ou configurar um banco de dados remoto.

### ⚔️✨ Que a Força esteja com você enquanto gerencia e lidera os rebeldes na luta pela liberdade contra o Império Galáctico!
