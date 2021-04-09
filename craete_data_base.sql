-- Cria o usuario XPTO
CREATE USER xpto IDENTIFIED BY 123456;

-- Permição ilimitada ao usuario XPTO 
GRANT unlimited tablespace TO xpto;

-- Cria a tabela CLIENTE
CREATE TABLE xpto.cliente(
	id_cliente int NOT NULL,
	nome varchar(80) NULL,
	cpf_cnpj varchar(20) NOT NULL,
	endereco varchar(100) NULL,
	bairro varchar(20) NULL,
	cidade varchar(30) NULL,
	numero int NULL,
	complemento varchar(100) NULL,
	cep int NULL,
	telefone varchar(20) NULL,
	data_cadastro DATE NULL,
	saldo_inicial float NOT NULL,		
	CONSTRAINT "cliente_PK" PRIMARY KEY (id_cliente)	
);

-- Cria a sequence para CLIENTE
CREATE SEQUENCE xpto.cliente_seq START WITH 1 INCREMENT BY 1; 
 
-- Cria a trigger para CLIENTE 
CREATE OR REPLACE TRIGGER xpto.cliente_tri
  BEFORE INSERT ON xpto.cliente FOR EACH ROW
WHEN (NEW.id_cliente IS NULL OR NEW.id_cliente = 0)
BEGIN
  SELECT cliente_seq.NEXTVAL INTO :NEW.id_cliente FROM dual;
END; 

-- Cria a tabela CONTA_BANCARIA
CREATE TABLE xpto.conta_bancaria(
	id_conta_bancaria int NOT NULL,
	nome varchar(80) NOT NULL,
	codigo_banco varchar(3) NOT NULL,
	numero_conta int NOT NULL,
	agencia int NOT NULL,	
	CONSTRAINT "conta_bancaria_PK" PRIMARY KEY (id_conta_bancaria)	
);

-- Cria a sequence para CONTA_BANCARIA
CREATE SEQUENCE xpto.conta_bancaria_seq START WITH 1 INCREMENT BY 1; 
 
-- Cria a trigger para CONTA_BANCARIA
CREATE OR REPLACE TRIGGER xpto.conta_bancaria_tri
  BEFORE INSERT ON xpto.conta_bancaria FOR EACH ROW
WHEN (NEW.id_conta_bancaria IS NULL OR NEW.id_conta_bancaria = 0)
BEGIN
  SELECT conta_bancaria_seq.NEXTVAL INTO :NEW.id_conta_bancaria FROM dual;
END;
 
-- Cria a tabela CONTA_CLIENTE
CREATE TABLE XPTO.CONTA_CLIENTE (
	ID_CLIENTE INTEGER NOT NULL,
	ID_CONTA_BANCARIA INTEGER NOT NULL,	
	CONSTRAINT "cliente_FK" FOREIGN KEY (ID_CLIENTE) REFERENCES XPTO.CLIENTE(ID_CLIENTE),
	CONSTRAINT "conta_bancaria_FK" FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES XPTO.CONTA_BANCARIA(ID_CONTA_BANCARIA)
);
 
-- Cria a tabela MOVIMENTACAO
CREATE TABLE xpto.movimentacao(
	ID_MOVIMENTACAO int NOT NULL,
	ID_CLIENTE INTEGER NOT NULL,
	ID_CONTA_BANCARIA INTEGER NOT NULL,
	valor real NOT NULL,
	DATA DATE NOT NULL,	
	CONSTRAINT "movimentacao_PK" PRIMARY KEY (ID_MOVIMENTACAO),	
	CONSTRAINT "cliente_mov_FK" FOREIGN KEY (ID_CLIENTE) REFERENCES XPTO.CLIENTE(ID_CLIENTE),
	CONSTRAINT "conta_bancaria_mov_FK" FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES XPTO.CONTA_BANCARIA(ID_CONTA_BANCARIA)
);

-- Cria a sequence para MOVIMENTACAO
CREATE SEQUENCE xpto.movimentacao_seq START WITH 1 INCREMENT BY 1; 
 
-- Cria a trigger para MOVIMENTACAO
CREATE OR REPLACE TRIGGER xpto.movimentacao_tri
  BEFORE INSERT ON xpto.movimentacao FOR EACH ROW
WHEN (NEW.id_movimentacao IS NULL OR NEW.id_movimentacao = 0)
BEGIN
  SELECT movimentacao_seq.NEXTVAL INTO :NEW.id_movimentacao FROM dual;
END;
