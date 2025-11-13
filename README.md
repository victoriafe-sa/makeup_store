# MakeUp World (Loja de Maquiagem)

Este é um projeto de aplicação web para uma loja de maquiagem online, desenvolvido com Spring Boot. A aplicação permite listar produtos, selecionar um cliente e criar um pedido, validando o estoque disponível em tempo real.

## Arquitetura e Tecnologias Utilizadas

O projeto segue uma arquitetura MVC (Model-View-Controller) e utiliza as seguintes tecnologias:

  * **Linguagem:** **Java 17**.
  * **Framework Principal:** **Spring Boot**.
  * **Build Tool:** **Apache Maven** (utilizando o Maven Wrapper).
  * **Banco de Dados:**
      * **Spring Data JPA** para persistência de dados.
      * **H2 Database** como banco de dados em memória.
      * O schema do banco é atualizado automaticamente na inicialização (`ddl-auto=update`).
  * **Front-end (View):**
      * **Thymeleaf** como template engine para renderizar as páginas HTML no servidor.
      * **Tailwind CSS** (via CDN) para estilização.
  * **Web:**
      * **Spring Web (MVC)** para criar os controllers e endpoints da aplicação.
  * **Utilitários:**
      * **Lombok** para reduzir boilerplate (getters, setters, construtores) nos modelos.

## Funcionalidades Principais

  * **Listagem de Produtos:** A página inicial (`/`) exibe todos os produtos disponíveis no banco de dados.
  * **Criação de Pedidos:** Um formulário na página inicial permite selecionar um cliente e as quantidades desejadas de cada produto.
  * **Validação de Estoque:** Ao tentar criar um pedido (`/criar-pedido`), o `LojaService` verifica se há estoque suficiente para cada item. Se o estoque for insuficiente, uma `EstoqueInsuficienteException` é lançada e uma mensagem de erro é exibida no front-end.
  * **Relatório de Pedidos:** A página `/pedidos` exibe um relatório de todos os pedidos já processados.
  * **Inicialização de Dados:** A classe `DataInitializer` (um `CommandLineRunner`) popula o banco de dados com produtos e clientes iniciais assim que a aplicação é iniciada.

## Pré-requisitos

Para executar este projeto, você precisará ter o **JDK 17** (Java Development Kit) ou superior instalado em sua máquina.

Não é necessário ter o Apache Maven instalado, pois o projeto utiliza o Maven Wrapper (`mvnw`), que baixará a versão correta automaticamente.

## Como Executar o Projeto (Usando um IDE)

Este projeto é um projeto Maven Spring Boot padrão, então a forma mais fácil de executá-lo é através de um Ambiente de Desenvolvimento Integrado (IDE) como IntelliJ IDEA, VS Code (com o "Extension Pack for Java") ou Eclipse.

1.  **Importe o Projeto:**
    * Abra o seu IDE.
    * Use a opção "Open Project" (ou "Import Project") e selecione a pasta raiz `makeup-store`.
    * O IDE irá reconhecer o arquivo `pom.xml` e baixar automaticamente as dependências (pode levar um momento).

2.  **Localize a Classe Principal:**
    * No seu explorador de arquivos, navegue até a classe principal do Spring Boot:
        `src/main/java/com/example/makeup_store/MakeUpWorldApplication.java`.

3.  **Execute a Aplicação:**
    * Clique com o botão direito no arquivo `MakeUpWorldApplication.java` ou diretamente no método `main` dentro dele.
    * Selecione a opção **"Run 'MakeUpWorldApplication.main()'"** (ou uma opção similar de execução).
    * O IDE irá compilar o projeto e iniciar o servidor Spring Boot embutido.

## Acessando a Aplicação

Quando a aplicação estiver em execução, você pode acessá-la pelo seu navegador:

* **Página Principal (Loja):**
    `http://localhost:8080/`

* **Relatório de Pedidos:**
    `http://localhost:8080/pedidos`

* **Console do Banco H2 (para Debug):**
    A aplicação expõe o console do banco H2, permitindo que você visualize os dados em memória (produtos, clientes, pedidos).
    * **URL:** `http://localhost:8080/h2-console`
    * **JDBC URL (use este valor no login):** `jdbc:h2:mem:makeupdb`
    * **Username:** `sa`
    * **Password:** (deixe em branco)
