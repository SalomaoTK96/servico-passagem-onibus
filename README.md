# 🚌 Sistema de Passagens de Ônibus – Catarinense

Projeto acadêmico desenvolvido para a disciplina de **Banco de Dados II** da **UNIVALI – Escola Politécnica**, com o objetivo de modelar e implementar um sistema de gerenciamento de passagens de ônibus inspirado na empresa Catarinense Transporte e Turismo.

---

## 👥 Autores

- Salomão Patrick França Alves Panas
- João Vitor

**Professor:** Maurício Pasetto de Freitas  
**Instituição:** UNIVALI – Escola Politécnica 
**Curso:** Ciência da Computação

---

## 📋 Sobre o projeto

O sistema permite gerenciar as operações principais de uma empresa de transporte rodoviário de passageiros, contemplando o cadastro de clientes, viagens, passagens e pagamentos por meio de um CRUD funcional via console.

---

## 🗄️ Banco de Dados

O banco de dados foi desenvolvido no **MySQL** e contém 11 tabelas normalizadas até a 3ª Forma Normal (3FN):

| Tabela | Descrição |
|---|---|
| `cliente` | Dados dos passageiros |
| `cliente_telefone` | Telefones dos clientes (multivalorado) |
| `motorista` | Dados dos motoristas |
| `motorista_telefone` | Telefones dos motoristas (multivalorado) |
| `modelo_onibus` | Modelos de ônibus com capacidade e tipo |
| `onibus` | Ônibus cadastrados na frota |
| `terminal` | Terminais rodoviários de origem e destino |
| `rota` | Rotas fixas entre dois terminais |
| `viagem` | Ocorrências específicas de uma rota |
| `passagem` | Passagens compradas por clientes |
| `pagamento` | Pagamentos das passagens |

---

## 💻 Tecnologias utilizadas

- **Java 25** — linguagem de programação
- **JDBC** — conexão direta com o banco de dados (sem ORM)
- **MySQL 8.0** — banco de dados relacional
- **IntelliJ IDEA** — IDE de desenvolvimento

---

## 📁 Estrutura do projeto

```
src/
├── Main.java                  # ponto de entrada do sistema
├── connection/
│   └── ConnectionDB.java      # gerencia a conexão com o MySQL
├── model/
│   ├── Cliente.java           # representa a tabela cliente
│   ├── Viagem.java            # representa a tabela viagem
│   ├── Passagem.java          # representa a tabela passagem
│   └── Pagamento.java         # representa a tabela pagamento
├── dao/
│   ├── ClienteDAO.java        # CRUD da tabela cliente
│   ├── ViagemDAO.java         # CRUD da tabela viagem
│   ├── PassagemDAO.java       # CRUD da tabela passagem
│   └── PagamentoDAO.java      # CRUD da tabela pagamento
└── menu/
    ├── MenuPrincipal.java     # menu inicial do sistema
    ├── MenuCliente.java       # menu de operações de cliente
    ├── MenuViagem.java        # menu de operações de viagem
    ├── MenuPassagem.java      # menu de operações de passagem
    └── MenuPagamento.java     # menu de operações de pagamento
```

---

## ⚙️ Como executar

### Pré-requisitos
- Java 25 instalado
- MySQL 8.0 instalado e rodando
- IntelliJ IDEA

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/SalomaoTK96/servico-passagem-onibus.git
```

**2. Importe o banco de dados**
- Abra o MySQL Workbench
- Execute o arquivo `catarinense_schema.sql` para criar o schema e popular as tabelas

**3. Configure a conexão**
- Abra o arquivo `src/connection/ConnectionDB.java`
- Altere a senha para a sua senha local do MySQL:
```java
private static final String SENHA = "sua_senha_aqui";
```

**4. Adicione o conector MySQL**
- No IntelliJ: `File → Project Structure → Libraries → + → From Maven`
- Digite: `com.mysql:mysql-connector-j:8.0.33`
- Clique em OK

**5. Execute o projeto**
- Rode o arquivo `Main.java`

---

## 🖥️ Funcionalidades

O sistema oferece CRUD completo para 4 entidades principais:

- **Clientes** — cadastrar, listar, buscar, atualizar e remover
- **Viagens** — cadastrar, listar, buscar, atualizar e remover
- **Passagens** — comprar, listar, buscar, atualizar e cancelar
- **Pagamentos** — registrar, listar, buscar, atualizar e remover