# 💄 MakeUp World

Bem-vindo ao **MakeUp World**, uma aplicação completa de **E-commerce** para uma loja de maquiagem, desenvolvida com **Java** e **Spring Boot**.

O projeto simula uma experiência real de compra online, incluindo:

* Autenticação de usuários
* Carrinho de compras persistente na sessão
* Validação de estoque em tempo real
* Painel administrativo para gestão de produtos

---

## 🚀 Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot 3.2
* **Segurança:** Spring Security (Autenticação e Autorização baseada em Roles)
* **Banco de Dados:** H2 Database (Em memória, para fácil execução e testes)
* **Persistência:** Spring Data JPA
* **Frontend:** Thymeleaf
* **Estilização:** Tailwind CSS (via CDN)
* **Ferramentas:** Maven, Lombok

---

## ✨ Funcionalidades

### 👤 Área Pública e do Cliente

#### **Catálogo de Produtos**

* Visualização de produtos com imagens, preços e status de estoque.
* Barra de pesquisa para filtrar produtos por nome.

#### **Autenticação**

* Login e Logout seguros.
* Cadastro de novos usuários.

#### **Carrinho de Compras**

* Adicionar itens ao carrinho.
* Ajustar quantidades ou remover itens.
* Cálculo automático de subtotal e total.
* Validação de estoque no checkout.

#### **Perfil do Usuário**

* Edição de dados cadastrais (Nome, Endereço, Senha).
* Histórico de pedidos com detalhes das compras anteriores.

---

### 🛡️ Painel Administrativo (Role: `ADMIN`)

#### **Dashboard**

* Visão geral com total de vendas, produtos cadastrados e status do sistema.

#### **Gestão de Produtos (CRUD)**

* Adicionar novos produtos (com URL de imagem).
* Editar dados de produtos existentes (preço, estoque, nome, imagem).
* Excluir produtos.

#### **Monitoramento**

* Visualização dos últimos pedidos realizados na loja.

---

## 🛠️ Como Executar o Projeto

### ✔️ Pré-requisitos

* Java **JDK 17** ou superior instalado.
* IDE (recomendado VS Code) com o **Extension Pack for Java** instalado.

---

### ▶️ Passo a Passo

1. Clone ou baixe o repositório.
2. Abra a pasta do projeto no seu IDE (VS Code).
3. Aguarde o IDE carregar as dependências do Maven.
4. Navegue até o arquivo principal da aplicação:
   `src/main/java/com/example/makeup_store/MakeUpWorldApplication.java`
5. Clique na opção **Run** ou **Debug** que aparece acima do método `main` (ou clique com o botão direito no arquivo e selecione *Run Java*).
6. Aguarde a inicialização e acesse no navegador:
   **[http://localhost:8080](http://localhost:8080)**

---

## 🔑 Credenciais de Teste

O sistema inicializa automaticamente o banco de dados (arquivo `DataInitializer.java`) com usuários e produtos de exemplo.

| Perfil            | Email                                     | Senha  | Acesso                                        |
| ----------------- | ----------------------------------------- | ------ | --------------------------------------------- |
| **Administrador** | [admin@email.com](mailto:admin@email.com) | 123456 | Acesso total + Dashboard (`/admin/dashboard`) |
| **Cliente** | [user@email.com](mailto:user@email.com)   | 123456 | Compras e Perfil (`/perfil`)                  |

> Você também pode criar uma nova conta clicando em **Cadastrar** na tela de login.

---

## 🗄️ Acesso ao Banco de Dados (H2 Console)

1. Acesse: **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
2. Configure:

```
JDBC URL: jdbc:h2:mem:makeupdb
User Name: sa
Password: 
```

3. Clique em **Connect**.

---

## 📂 Estrutura do Projeto

```
makeup-store/
│
├── controller/       # Controladores MVC (Admin, Carrinho, Home, Perfil)
├── model/            # Entidades JPA (Cliente, Produto, Pedido, ItemPedido) + Carrinho (Session Scope)
├── repository/       # Interfaces Spring Data JPA
├── service/          # Regras de negócio (Processamento de pedidos)
├── config/           # Configurações de segurança (SecurityConfig)
├── templates/        # Páginas HTML (Thymeleaf)
└── ...
```

Desenvolvido como exemplo organizado de arquitetura **Spring Boot MVC**.

