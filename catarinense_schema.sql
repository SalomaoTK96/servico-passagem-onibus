/*** CRIAÇÃO DO BANCO DE DADOS – SERVIÇO DE PASSAGENS CATARINENSE ***/

-- DROP SCHEMA catarinense;
CREATE SCHEMA catarinense DEFAULT CHARACTER SET utf8;

use catarinense;


/*** TABELAS ***/

-- cliente(id_cliente, nome, cpf, email, data_nascimento)
-- DROP TABLE Cliente;
CREATE TABLE Cliente (
                         id_cliente      INT             NOT NULL AUTO_INCREMENT,
                         nome            VARCHAR(100)    NOT NULL,
                         cpf             VARCHAR(14)     NOT NULL UNIQUE,
                         email           VARCHAR(200)    NOT NULL,
                         data_nascimento DATE            NOT NULL,
                         PRIMARY KEY (id_cliente)
);

-- cliente_telefone(id_cliente, telefone)
-- DROP TABLE Cliente_Telefone;
CREATE TABLE Cliente_Telefone (
                                  id_cliente  INT         NOT NULL,
                                  telefone    VARCHAR(20) NOT NULL,
                                  PRIMARY KEY (id_cliente, telefone),
                                  FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente)
);

-- motorista(id_motorista, nome, cpf, cnh, categoria_cnh, status)
-- DROP TABLE Motorista;
CREATE TABLE Motorista (
                           id_motorista    INT             NOT NULL AUTO_INCREMENT,
                           nome            VARCHAR(100)    NOT NULL,
                           cpf             VARCHAR(14)     NOT NULL UNIQUE,
                           cnh             VARCHAR(20)     NOT NULL UNIQUE,
                           categoria_cnh   VARCHAR(5)      NOT NULL,
                           status          VARCHAR(20)     NOT NULL DEFAULT 'ativo',
                           PRIMARY KEY (id_motorista)
);

-- motorista_telefone(id_motorista, telefone)
-- DROP TABLE Motorista_Telefone;
CREATE TABLE Motorista_Telefone (
                                    id_motorista    INT         NOT NULL,
                                    telefone        VARCHAR(20) NOT NULL,
                                    PRIMARY KEY (id_motorista, telefone),
                                    FOREIGN KEY (id_motorista) REFERENCES Motorista (id_motorista)
);

-- modelo_onibus(id_modelo, descricao, capacidade, tipo)
-- DROP TABLE Modelo_Onibus;
CREATE TABLE Modelo_Onibus (
                               id_modelo   INT             NOT NULL AUTO_INCREMENT,
                               descricao   VARCHAR(100)    NOT NULL,
                               capacidade  INT             NOT NULL,
                               tipo        VARCHAR(20)     NOT NULL,
                               PRIMARY KEY (id_modelo)
);

-- onibus(id_onibus, placa, id_modelo)
-- DROP TABLE Onibus;
CREATE TABLE Onibus (
                        id_onibus   INT         NOT NULL AUTO_INCREMENT,
                        placa       VARCHAR(10) NOT NULL UNIQUE,
                        id_modelo   INT         NOT NULL,
                        PRIMARY KEY (id_onibus),
                        FOREIGN KEY (id_modelo) REFERENCES Modelo_Onibus (id_modelo)
);

-- terminal(id_terminal, nome, cidade, uf, endereco)
-- DROP TABLE Terminal;
CREATE TABLE Terminal (
                          id_terminal INT             NOT NULL AUTO_INCREMENT,
                          nome        VARCHAR(100)    NOT NULL,
                          cidade      VARCHAR(100)    NOT NULL,
                          uf          CHAR(2)         NOT NULL,
                          endereco    VARCHAR(200)    NOT NULL,
                          PRIMARY KEY (id_terminal)
);

-- rota(id_rota, preco, status, id_terminal_origem, id_terminal_destino)
-- DROP TABLE Rota;
CREATE TABLE Rota (
                      id_rota             INT             NOT NULL AUTO_INCREMENT,
                      preco               DECIMAL(8,2)    NOT NULL,
                      status              VARCHAR(20)     NOT NULL DEFAULT 'ativa',
                      id_terminal_origem  INT             NOT NULL,
                      id_terminal_destino INT             NOT NULL,
                      PRIMARY KEY (id_rota),
                      FOREIGN KEY (id_terminal_origem)  REFERENCES Terminal (id_terminal),
                      FOREIGN KEY (id_terminal_destino) REFERENCES Terminal (id_terminal)
);

-- viagem(id_viagem, data_partida, data_chegada, status, id_onibus, id_rota, id_motorista)
-- DROP TABLE Viagem;
CREATE TABLE Viagem (
                        id_viagem       INT         NOT NULL AUTO_INCREMENT,
                        data_partida    DATETIME    NOT NULL,
                        data_chegada    DATETIME    NOT NULL,
                        status          VARCHAR(20) NOT NULL DEFAULT 'agendada',
                        id_onibus       INT         NOT NULL,
                        id_rota         INT         NOT NULL,
                        id_motorista    INT         NOT NULL,
                        PRIMARY KEY (id_viagem),
                        FOREIGN KEY (id_onibus)    REFERENCES Onibus    (id_onibus),
                        FOREIGN KEY (id_rota)      REFERENCES Rota      (id_rota),
                        FOREIGN KEY (id_motorista) REFERENCES Motorista (id_motorista)
);

-- passagem(id_passagem, numero_assento, data_compra, status, id_cliente, id_viagem)
-- DROP TABLE Passagem;
CREATE TABLE Passagem (
                          id_passagem     INT         NOT NULL AUTO_INCREMENT,
                          numero_assento  INT         NOT NULL,
                          data_compra     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          status          VARCHAR(20) NOT NULL DEFAULT 'ativa',
                          id_cliente      INT         NOT NULL,
                          id_viagem       INT         NOT NULL,
                          PRIMARY KEY (id_passagem),
                          FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente),
                          FOREIGN KEY (id_viagem)  REFERENCES Viagem  (id_viagem)
);

-- pagamento(id_pagamento, valor, forma_pagamento, status, data_pagamento, id_passagem)
-- DROP TABLE Pagamento;
CREATE TABLE Pagamento (
                           id_pagamento    INT             NOT NULL AUTO_INCREMENT,
                           valor           DECIMAL(8,2)    NOT NULL,
                           forma_pagamento VARCHAR(50)     NOT NULL,
                           status          VARCHAR(20)     NOT NULL DEFAULT 'pendente',
                           data_pagamento  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           id_passagem     INT             NOT NULL,
                           PRIMARY KEY (id_pagamento),
                           FOREIGN KEY (id_passagem) REFERENCES Passagem (id_passagem)
);

/*** INSERÇÃO DE DADOS ***/

-- populando Cliente
INSERT INTO Cliente (nome, cpf, email, data_nascimento) VALUES
 ('Ana Paula Souza',     '111.222.333-44', 'ana.souza@email.com',      '1990-03-15'),
 ('Carlos Eduardo Lima', '222.333.444-55', 'carlos.lima@email.com',    '1985-07-22'),
 ('Fernanda Costa',      '333.444.555-66', 'fernanda.costa@email.com', '1998-11-05'),
 ('Ricardo Alves',       '444.555.666-77', 'ricardo.alves@email.com',  '1975-01-30'),
 ('Juliana Martins',     '555.666.777-88', 'juliana.m@email.com',      '2000-06-18');

-- populando Cliente_Telefone
INSERT INTO Cliente_Telefone (id_cliente, telefone) VALUES
(1, '(47) 99101-1111'),
(2, '(47) 99202-2222'),
(2, '(47) 99202-3333'),
(3, '(48) 99303-3333'),
(4, '(49) 99404-4444'),
(5, '(47) 99505-5555');

-- populando Motorista
INSERT INTO Motorista (nome, cpf, cnh, categoria_cnh, status) VALUES
('José Santos',     '666.777.888-99', 'CNH-001234', 'D', 'ativo'),
('Marcos Oliveira', '777.888.999-00', 'CNH-005678', 'E', 'ativo'),
('Paulo Ferreira',  '888.999.000-11', 'CNH-009012', 'D', 'ativo'),
('Roberto Souza',   '999.000.111-22', 'CNH-003456', 'E', 'ativo'),
('Antonio Lima',    '000.111.222-33', 'CNH-007890', 'D', 'ativo');

-- populando Motorista_Telefone
INSERT INTO Motorista_Telefone (id_motorista, telefone) VALUES
(1, '(47) 98801-1111'),
(2, '(47) 98802-2222'),
(3, '(48) 98803-3333'),
(4, '(49) 98804-4444'),
(5, '(47) 98805-5555');

-- populando Modelo_Onibus
INSERT INTO Modelo_Onibus (descricao, capacidade, tipo) VALUES
('Mercedes-Benz O500', 46, 'convencional'),
('Volvo B340R',        42, 'executivo'),
('Scania K410',        28, 'leito'),
('Volvo B380R',        38, 'executivo'),
('Marcopolo Paradiso', 46, 'convencional');

-- populando Onibus
INSERT INTO Onibus (placa, id_modelo) VALUES
('ABC-1234', 1),
('DEF-5678', 2),
('GHI-9012', 3),
('JKL-3456', 4),
('MNO-7890', 5);

-- populando Terminal
INSERT INTO Terminal (nome, cidade, uf, endereco) VALUES
('Terminal Florianópolis', 'Florianópolis', 'SC', 'Av. Mauro Ramos, 1368 - Centro'),
('Terminal Joinville',     'Joinville',     'SC', 'R. Marquês de Olinda, 50 - Centro'),
('Terminal Blumenau',      'Blumenau',      'SC', 'R. 7 de Setembro, 1414 - Centro'),
('Terminal Itajaí',        'Itajaí',        'SC', 'R. Felipe Schmidt, 68 - Centro'),
('Terminal Porto Alegre',  'Porto Alegre',  'RS', 'Largo Vespasiano Júlio Veppo, 1 - Centro');

-- populando Rota
INSERT INTO Rota (preco, status, id_terminal_origem, id_terminal_destino) VALUES
(45.00,  'ativa', 1, 2),
(38.50,  'ativa', 2, 3),
(120.00, 'ativa', 1, 5),
(89.00,  'ativa', 4, 1),
(38.50,  'ativa', 3, 1);

-- populando Viagem
INSERT INTO Viagem (data_partida, data_chegada, status, id_onibus, id_rota, id_motorista) VALUES
('2025-07-01 06:00:00', '2025-07-01 08:30:00', 'agendada', 1, 1, 1),
('2025-07-01 09:00:00', '2025-07-01 11:00:00', 'agendada', 2, 2, 2),
('2025-07-01 22:00:00', '2025-07-02 06:00:00', 'agendada', 3, 3, 3),
('2025-07-02 07:00:00', '2025-07-02 14:00:00', 'agendada', 4, 4, 4),
('2025-07-02 15:00:00', '2025-07-02 17:00:00', 'agendada', 5, 5, 5);

-- populando Passagem
INSERT INTO Passagem (numero_assento, data_compra, status, id_cliente, id_viagem) VALUES
(12, '2025-06-20 10:00:00', 'ativa',     1, 1),
(15, '2025-06-20 10:05:00', 'ativa',     2, 1),
(5,  '2025-06-21 14:00:00', 'ativa',     3, 3),
(22, '2025-06-22 09:30:00', 'ativa',     4, 4),
(8,  '2025-06-22 16:00:00', 'ativa',     5, 2),
(10, '2025-06-23 08:00:00', 'cancelada', 1, 3),
(30, '2025-06-23 11:00:00', 'ativa',     2, 5);

-- populando Pagamento
INSERT INTO Pagamento (valor, forma_pagamento, status, data_pagamento, id_passagem) VALUES
(45.00,  'pix',            'aprovado',  '2025-06-20 10:01:00', 1),
(45.00,  'cartao_credito', 'aprovado',  '2025-06-20 10:06:00', 2),
(120.00, 'cartao_debito',  'aprovado',  '2025-06-21 14:01:00', 3),
(89.00,  'dinheiro',       'aprovado',  '2025-06-22 09:31:00', 4),
(38.50,  'pix',            'aprovado',  '2025-06-22 16:01:00', 5),
(120.00, 'cartao_credito', 'estornado', '2025-06-23 08:01:00', 6),
(38.50,  'pix',            'aprovado',  '2025-06-23 11:01:00', 7);