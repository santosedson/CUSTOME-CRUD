# CUSTOMER - CRUD
Esse projeto consiste em um CRUD simples de Clintes utilizando API Rest e persistência de dados.

- Versão: 0.0.1
- Linguagem: Java 8
- Banco de dados: Oracle 18c XE 
- Autor: Edson B. S. Filho
- Data: 14.09.2020 
- Local: São Paulo -SP

## Como executar essa aplicação: ## 

 1.	Configuração do banco de dados:
 
	- Antes de iniciar a aplicação, é necessário configurar a conexão com o banco de dados relacional de sua preferência.
	No meu caso utilizei o banco de dados Oracle e as configurações poderão ser editadas em `application.properties`
	localizado em `src\main\resources`.
		
	- Os Scripts de criação das tabelas se `encontram em src/main/resources/data/CREATE TABLES.sql`. 
	É necessário criar as tabelas primeiro  antes de rodar a aplicação.
		
	**Obs:** Se for utilizar o banco de dados da Oracle para testar essa aplicação, 
	somente será necessário alterar as informações de conexões do banco em `application.properties` 
	e rodar os scripts de criação de tabelas  `CREATE TABLES.sql`. 
	Caso prefira utilizar outro banco de dados, certifique-se de adicionar as devidas dependências  em `pom.xml`
	, configurar as conexões no arquivo `application.properties` e alterar os script `CREATE TABLES.sql` se necessário.
		

 2. Se for rodar a aplicação no Windows, execute o bat `START-CUSTOMER-CRUD-API.bat` que se encontra na raiz do projeto.
	Se o arquivo bat não funcionar corretamente, na pasta do projeto execute o seguinte comando :
	```shell
	$ mvnw spring-boot:run
	```
	
	**Obs:** Se for a primeira vez que estiver sendo executado uma aplicação spring boot, irá demorar um pouco baixando as dependências necessárias.
	Quando aparecer o banner significa que a aplicação foi iniciada com sucesso.
 
 3. Ao rodar a aplicação, a API Rest será iniciada. Detalhes:
		
	**Obs:** Por default a interface rest usará a porta 8081. Caso precise mudar a porta, alterar no arquivo `application.properties` localizado em `src\main\resources`
		
	- Descrição: Cadastro de novos clientes
	- Edpoint:  "http://localhost:8081/api/v1/customer/register"
	- Verbo Http: POST
	- Response code: 202 ou 400
	- Response body: String (Text)
	- Como utilizar: Para usar esse endpoint poderá utilizar a aplicação Postman.
	- Tipo de conteúdo esperado: JSON 
	- Exemplo: 
		
		```
			{
				"NAME": "edson filho",
				"CPF": "67064969017",
				"ADRESS": {
					"STREET_NAME": "RUA 23 de alguma coisa",
					"ADRESS_NUMBER": "123",
					"COMPLEMENT": "ap 44",
					"CEP": "50720000",
					"NEIGHBORHOOD": "madalena",
					"CITY": "recife",
					"STATE": "pe",
					"COUNTRY": "br"
				}
			}
		```
					
		
	- Descrição: Atualização de clientes
	- Edpoint:  "http://localhost:8081/api/v1/customer/update"
	- Verbo Http: PUT
	- Response code: 202 ou 400
	- Response body: String (Text)
	- Como utilizar: Para usar esse endpoint poderá utilizar a aplicação Postman.
	- Tipo de conteúdo esperado: JSON 
	- Exemplo: 
		
		```
			{
				"NAME": "edson filho",
				"CPF": "67064969017",
				"ADRESS": {
					"STREET_NAME": "RUA 23 de alguma coisa",
					"ADRESS_NUMBER": "123",
					"COMPLEMENT": "ap 44",
					"CEP": "50720000",
					"NEIGHBORHOOD": "madalena",
					"CITY": "recife",
					"STATE": "pe",
					"COUNTRY": "br"
				}
			}
		```
		
		
	- Descrição: Exclusão de clientes
	- Edpoint:  "http://localhost:8081/api/v1/customer/delete?cpf=CPF_DO_CLIENTE"
	- Verbo Http: DELETE
	- Response code: 202
	- Response body: String (Text)
	- Como utilizar: Para usar esse endpoint poderá utilizar a aplicação Postman.
	- Tipo de conteúdo esperado: parêmetro 
	- Exemplo: 
		
		```
			http://localhost:8081/api/v1/customer/delete?cpf=11386682063
		```
		
		
	- Descrição: Consultar todos os clientes
	- Edpoint:  "http://localhost:8081/api/v1/customer/findAll"
	- Verbo Http: GET
	- Response code: 202
	- Response body: String (Text) ou Json
	- Como utilizar: Para usar esse endpoint poderá utilizar a aplicação Postman.
	- Exemplo: 
		```
			http://localhost:8081/api/v1/customer/findAll
		```
					
					
	- Descrição: Consultar clientes por CPF
	- Edpoint: 
	- Verbo Http: GET "http://localhost:8081/api/v1/customer/findBy?cpf=CPF_DO_CLIENTE"
	- Response code: 202
	- Response body: String (Text) ou Json
	- Como utilizar: Para usar esse endpoint poderá utilizar a aplicação Postman.
	- Exemplo: 
		
		```
			http://localhost:8081/api/v1/customer/findBy?cpf=67064969017
		```
					
4. Para finalizar a aplicação, basta apenas fechar o terminal.

## Estrutura dos arquivos/pacotes: ## 

- **Estrutura de pacotes:**
	- com.edson.filho.customer : Estrutura base  que contém os demais subpacotes e classe Main da aplicação.
		- commons: 	Nesse pacote se encontram a classe Enums com todas as mensagens da aplicação exibidas para o usuário
		- controller: Nesse pacote se encontra a classe que possuis os endpoints que controlam as APIs Rest
		- entity: Nesse pacote se encontram todas as classes que serão entidades no banco de dados.
		- model: Nesse pacote se encontram todas as classes POJO (DTOs)utilizadas  na deserialização do json com informações do Cliente
		- model.repository: Nesse pacote se encontram todas as Interfaces de repositório utilizadas pelo JPA
		- service: Nesse pacote se encontram todas as classes com as regras de negócio da aplicação e demais validações.
		- util: Nesse pacote se encontram todas as classes utilitárias do projeto.

## Casos de teste: ## 	
	- Os casos de teste foram elaborados utilizando o Junit e podem ser encontrados em 
	`src/test/java/com/edson/customer/CustomerApplicationTests.java`. Foram construídos  33 cenários para esta aplicação. 	
	
## Decisões de design adotadas para a solução: ## 
	- Construí a aplicação procurando utilizar os conceitos de S.O.L.I.D e demais conceitos de Clean code
	e apliquei os conceitos de TDD.




