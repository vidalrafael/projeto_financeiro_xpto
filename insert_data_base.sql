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
--END;

--BEGIN
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