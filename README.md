🚗 Sistema de Gestão de Venda de Veículos

📖 Sobre o Projeto

Este projeto é uma aplicação Full Stack para gerenciamento de uma concessionária de veículos. O sistema permite o cadastro de clientes, vendedores e veículos (Carros e Motos), além de realizar o processo completo de Venda, com baixa automática de estoque e geração de Nota Fiscal em PDF.
O projeto foi desenvolvido como requisito avaliativo da disciplina de Programação Orientada a Objetos, aplicando conceitos de arquitetura em camadas, herança, polimorfismo e APIs RESTful.

🚀 Funcionalidades

✅ Backend (API REST)

CRUD Completo para Clientes e Vendedores.

Gestão de Veículos com herança (Carro e Moto) utilizando estratégia JOINED no banco de dados.

Registro de Vendas com validação de regras de negócio:

Verificação de disponibilidade do veículo em estoque.

Atualização automática do status do veículo para Vendido.

Geração de PDF: Endpoint exclusivo para download da Nota Fiscal da venda.

Documentação Automática: Integração com Swagger/OpenAPI.

✅ Frontend (React)

Interface amigável para cadastro de entidades.

Listagem dinâmica de registros.

Formulário de Venda integrado com validação de IDs.

Botão direto para download da Nota Fiscal.

🛠️ Tecnologias Utilizadas

Backend

Java 21: Linguagem de programação.

Spring Boot 3: Framework principal.

Spring Data JPA (Hibernate): Persistência de dados.

PostgreSQL: Banco de dados relacional.

Apache PDFBox: Biblioteca para geração de PDFs.

SpringDoc OpenAPI: Documentação Swagger.

Lombok: Redução de código boilerplate.

Frontend

React: Biblioteca JavaScript para construção de interfaces.

Vite: Ferramenta de build rápida.

JavaScript (ES6+): Lógica do frontend.

📂 Estrutura do Projeto (Pacotes)

O backend segue uma arquitetura em camadas bem definida:

com.leninauto.vendaveiculos.controllers: Pontos de entrada da API REST (GET, POST, PUT, DELETE).

com.leninauto.vendaveiculos.services: Regras de negócio (ex: validação de estoque, geração de PDF).

com.leninauto.vendaveiculos.repositories: Interfaces de comunicação com o banco de dados.

com.leninauto.vendaveiculos.automoveis: Entidades de Veículo (Veiculo, Carro, Moto).

com.leninauto.vendaveiculos.pessoa: Entidades de Pessoa (Cliente, Vendedor).

com.leninauto.vendaveiculos.Venda: Entidades de Transação (Venda, NotaFiscal).

⚙️ Como Executar o Projeto

Pré-requisitos

Java JDK 21+ instalado.

Node.js e NPM instalados.

PostgreSQL instalado e rodando.

Maven.

1. Configuração do Banco de Dados

Crie um banco de dados vazio no PostgreSQL com o nome venda_veiculos_db (ou ajuste no application.properties).

2. Executando o Backend (API)

Navegue até a pasta do projeto Java.

Verifique o arquivo src/main/resources/application.properties e ajuste seu usuário/senha do banco:

spring.datasource.url=jdbc:postgresql://localhost:5432/venda_veiculos_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha


Execute a aplicação via IntelliJ ou terminal:

mvn spring-boot:run


A API estará rodando em: http://localhost:8080.

Documentação Swagger: Acesse http://localhost:8080/swagger-ui.html.

3. Executando o Frontend (React)

Navegue até a pasta do frontend (venda-veiculos-front).

Instale as dependências:

npm install


Inicie o servidor de desenvolvimento:

npm run dev


Acesse a aplicação no navegador (geralmente em http://localhost:5173).

🧪 Testando o Fluxo de Venda

Cadastre um Cliente (O sistema gerará um ID, ex: 1).

Cadastre um Vendedor (O sistema gerará um ID, ex: 1).

Cadastre um Veículo (O sistema gerará um ID, ex: 1).

Nota: O veículo será salvo com status Disponivel.

Registre uma Venda:

Insira os IDs gerados acima nos campos do formulário de Venda.

Resultado:

A venda será salva.

O status do veículo mudará para Vendido_Aguardando_retirada no banco.

O botão "Gerar NF (PDF)" aparecerá na lista de vendas para download do documento.

👨‍💻 Autor:
[Henrique Railson] - Desenvolvedor Principal (Full Stack)

Este projeto é de uso acadêmico.
