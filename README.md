
# Projeto Financeiro Empresa XPTO

Projeto para avaliação do processo seletivo
Foi desenvolvido um Backend para controle de clientes e de receitas de uma empresa de movimentações financeiras.

O projeito foi desenvolvido em Java usando o Framework Spring versão 2.4.4 e o Oracle DataBase versão 19.3.
Para os testes unitários, foi usado o JUnit. 

## Sumário  

*  [Funcionalidades](#features)
*  [Pré-requisitos](#pré-requisitos)
*  [Criando a base de dados](#criando-a-base-de-dados)
*  [Populando a base de dados](#populando-a-base-de-dados)
*  [Iniciando o sistema](#iniciando-o-sistema)
*  [Usando o sistema](#usando-o-sistema)
*  [Api's](#api)
	*  [Conta Bancaria](#conta-bancaria)
	*  [Cliente](#cliente)
	*  [Conta Cliente](#conta-cliente)
 	*  [Movimentação](#movimentacao)
	*  [Relatórios](#relatorios)
* [Testes](#testes)

## Funcionalidades  

-  [x] Cadastro de clientes

-  [x] Cadastro de conta bancaria

-  [x] Cadastro de movimentação financeira

-  [x] Relatórios de cadastro

-  [x] Relatórios financeiros  

## Pré-requisitos  

- Ter o Oracle DataBase instalado na máquina.

- Criar uma base de dados com o nome XPTO_DATA_BASE.

- O banco deve estar rodando na porta: 1521.

## Criando a base de dados
- Após a base ser criada, execute os scripts abaixo. *Script disponíveis também no arquivo 'craete_data_base.sql'*
```sql
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
```

## Populando a base de dados
- Opcionalmente, rode o script abaixo para popular a base dados com 5 clientes, 2 contas bancarias e 10 movimentações por cliente. *Script disponíveis também no arquivo 'insert_data_base.sql'*
##### OBS: Na criação desses lançamentos os campos de data presentes nas tabelas 'MOVIMENTACAO' serão preenchidos com um dia a mais da data em que rodou o script. 
- Exemplo: Se o script for executado no dia 01/01/2021, os registros serão do dia 01/01/2021 até 10/01/2021.
```sql
BEGIN
	-- Cria as 2 contas bancarias
	INSERT INTO XPTO.CONTA_BANCARIA (ID_CONTA_BANCARIA, NOME, CODIGO_BANCO, NUMERO_CONTA, AGENCIA) VALUES (1, 'Conta Banco do brasil', '001', 123456, 1234);
	INSERT INTO XPTO.CONTA_BANCARIA (ID_CONTA_BANCARIA, NOME, CODIGO_BANCO, NUMERO_CONTA, AGENCIA) VALUES (2, 'Conta Bradesco', '237', 654321, 4321);

	-- Cria 5 clientes
	INSERT INTO XPTO.CLIENTE (ID_CLIENTE, NOME, CPF_CNPJ, ENDERECO, BAIRRO, CIDADE, NUMERO, COMPLEMENTO, CEP, TELEFONE, DATA_CADASTRO, SALDO_INICIAL)
	VALUES (1, 'Joao', '999.999.999-99', 'Rua Joao', 'bairro Joao', 'cidade Joao', 9, 'complemento Joao', 99999999, '(99) 99999-9999', (SELECT CURRENT_DATE FROM dual), 5000);

	INSERT INTO XPTO.CLIENTE (ID_CLIENTE, NOME, CPF_CNPJ, ENDERECO, BAIRRO, CIDADE, NUMERO, COMPLEMENTO, CEP, TELEFONE, DATA_CADASTRO, SALDO_INICIAL)
	VALUES (2, 'Maria', '888.888.888-88', 'Rua Maria', 'bairro Maria', 'cidade Maria', 8, 'complemento Maria', 888888888, '(88) 88888-88888', (SELECT CURRENT_DATE FROM dual), 6000);

	INSERT INTO XPTO.CLIENTE (ID_CLIENTE, NOME, CPF_CNPJ, ENDERECO, BAIRRO, CIDADE, NUMERO, COMPLEMENTO, CEP, TELEFONE, DATA_CADASTRO, SALDO_INICIAL)
	VALUES (3, 'Pedro', '777.777.777-77', 'Rua Pedro', 'bairro Pedro', 'cidade Pedro', 7, 'complemento Pedro', 777777777, '(77) 77777-7777', (SELECT CURRENT_DATE FROM dual), 7000);

	INSERT INTO XPTO.CLIENTE (ID_CLIENTE, NOME, CPF_CNPJ, ENDERECO, BAIRRO, CIDADE, NUMERO, COMPLEMENTO, CEP, TELEFONE, DATA_CADASTRO, SALDO_INICIAL)
	VALUES (4, 'Joana', '666.666.666-66', 'Rua Joana', 'bairro Joana', 'cidade Joana', 6, 'complemento Joana', 666666666, '(66) 66666-6666', (SELECT CURRENT_DATE FROM dual), 8000);

	INSERT INTO XPTO.CLIENTE (ID_CLIENTE, NOME, CPF_CNPJ, ENDERECO, BAIRRO, CIDADE, NUMERO, COMPLEMENTO, CEP, TELEFONE, DATA_CADASTRO, SALDO_INICIAL)
	VALUES (5, 'Marcos', '555.555.555-55', 'Rua Marcos', 'bairro Marcos', 'cidade Marcos', 5, 'complemento Marcos', 555555555, '(55) 55555-5555', (SELECT CURRENT_DATE FROM dual), 9000);

	-- Cria o vínculo das duas contas bancárias para os clientes
	DECLARE
		idCliente int := 1;
		i int := 1;
	BEGIN
		LOOP
			IF i > 5 THEN
				EXIT;
			END IF;
		
			INSERT INTO XPTO.CONTA_CLIENTE (ID_CLIENTE, ID_CONTA_BANCARIA) VALUES (idCliente, 1);
			INSERT INTO XPTO.CONTA_CLIENTE (ID_CLIENTE, ID_CONTA_BANCARIA) VALUES (idCliente, 2);	
		
			idCliente := idCliente + 1;
			i := i + 1;	
		END LOOP;
	END;	
	
	DECLARE
		i int := 1;
		j int := 0;
		idCliente int := 1;
		valor float := 7000.00;
		dias int := 0;
	BEGIN
		LOOP
			LOOP
				INSERT INTO XPTO.MOVIMENTACAO (ID_CLIENTE, ID_CONTA_BANCARIA, VALOR, DATA) VALUES (idCliente, 1, valor, (SELECT CURRENT_DATE + dias FROM dual));
			
				valor := valor - 1000;
				dias := dias + 1;
				j := j + 1;
			
				IF valor = 0 THEN
					valor := -1000;
				END IF;
			
				IF j > 9 THEN
					EXIT;
				END IF;
			
			END LOOP;
		
			i := i + 1;
			valor := 7000.00;
			dias := 0;
			j := 0;
			idCliente := idCliente + 1;
		
			IF i > 5 THEN
				EXIT;
			END IF;
		END LOOP;
	END;	
END;
```

## Iniciando o sistema
- O sistema deve ser inicializado através do CMD ou de uma ferramenta de desenvolvimento, VsCode, Eclipse, etc..

## Usando o sistema
- O sistema deverá ser testado através de chamadas via API. Recomendo uso de ferramentas como postman, insominia, etc..

## Api's
### Conta bancaria:
- (GET) Retorna todas as contas bancarias cadastradas: "api/v1/conta-bancaria"
- (GET) Retorna uma contas bancarias cadastradas, através do idContaBancaria: "api/v1/conta-bancaria/{id}"
- (POST) Grava uma nova conta bancaria. (*Necessário envio de um JSON na requisição**):  "api/v1/conta-bancaria"
- (PUT) Altera uma conta bancaria, através do idContaBancaria. (*Necessário envio de um JSON na requisição**):  "api/v1/conta-bancaria/{id}"
- (DELETE) Apaga uma conta bancaria, através do idContaBancaria***: "api/v1/conta-bancaria/{id}"
- *****_Só é permitido a exclusão da conta bancária, caso não tenha movimentação para a mesma._**
- ***_Modelo JSON para POST e PUT_** 
```json
{
	"nome": "Conta caixa economica",
	"codigoBanco": "104",
	"numeroConta": 123456,
	"agencia": 1234
}
```

### Cliente:
- (GET) Retorna todos os clientes cadastrados: "api/v1/cliente"
- (GET) Retorna um clientes cadastrado, através do idCliente: "api/v1/cliente/{id}"
- (POST) Grava um novo cliente. (*Necessário envio de um JSON na requisição**):  "api/v1/cliente"
- (PUT) Altera um cliente cadastrado, através do idCliente. (*Necessário envio de um JSON na requisição***):  "api/v1/cliente/{id}"
- (DELETE) Apaga um clientes cadastrado, através do idCliente: "api/v1/cliente/{id}"
- ***_Modelo JSON para POST_** 
```json
{
	"nome": "Fulano",
	"cpfCnpj": "999.999.999-99",
	"endereco": "Rua teste",
	"bairro": "Bairro teste",
	"cidade": "Cidade teste",
	"numero": 999,
	"cep": 99999999,
	"complemento": "complemento teste",
	"telefone": "(99) 99999-9999",
	"dataCadastro": "2021-01-01",
	"saldoInicial": 1000.00,
	"contasBancarias":[
	{
		"idContaBancaria": 4
	},
	{
			"idContaBancaria": 6
	},
	]
}
```
- ****_Modelo JSON para PUT_** 
```json
{
	"nome": "Fulano",
	"endereco": "Rua teste",
	"bairro": "Bairro teste",
	"cidade": "Cidade teste",
	"numero": 999,
	"cep": 99999999,
	"complemento": "complemento teste",
	"telefone": "(99) 99999-9999"
}
```
### Conta cliente:
- (GET) Retorna todas as contas bancarias de um determinado cliente, através do IdCliente: "api/v1/conta-cliente/{idCliente}"
- (DELETE) Apaga o vínculo de um conta bancária com um cliente. (*Necessário envio de um JSON na requisição**): "api/v1/conta-bancaria/{id}"
- - ***_Modelo JSON para DELETE_** 
```json
{
	"idCliente": 1,
	"idContaBancaria": 1
}
```
### Movimentação:
- (GET) Retorna todas as movimentações cadastradas: "api/v1/movimentacao"
- (GET) Retorna uma movimentação cadastrada, através do idMovimentacao: "api/v1/movimentacao/{id}"
- (POST) Grava uma nova movimentação. (*Necessário envio de um JSON na requisição**):  "api/v1/movimentacao"
- ***_Modelo JSON para POST_** 
```json
{
	"cpfCnpj":  "999.999.999-99",
	"codigoBanco":  "104",
	"numeroConta":  123456,
	"agencia":  1234,
	"dataMovimentacao":  "2021-01-01",
	"valor":  3000
}
```
### Relatórios:
- (GET) Relatório de saldo por cliente, através do idCliente: "api/v1/relatorio/relatorio_de_saldo_do_cliente/{idCliente}"
- (GET) Relatório de saldo por cliente por período, através do idCliente, dataInicial e dataFinal: /relatorio_de_saldo_do_cliente_por_periodo/{idCliente}/{dataInicial}/{dataFinal}"
- (GET) Relatório financeiro de todos os clientes: "api/v1/relatorio/relatorio_de_todos_os_clientes"
- (GET) Relatório de receita da empresa por período através da dataInicial e dataFinal: "api/v1/relatorio//relatorio_de_receitas_da_empresa/{dataInicial}/{dataFinal}"

### Testes
- Foram criados os testes automatizados usando o JUnit. Os testes tem por objetivo garantir o correto funcionamento da aplicação.
