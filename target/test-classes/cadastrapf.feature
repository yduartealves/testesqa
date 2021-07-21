#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Testes de API Brasilprev. cadastro de pessoa fisica

  Scenario: Cadastro de Pessoa fisica CPF
    Given Inicializa base url "http://localhost:8080" e a base path "/pessoas" 
    When requisitar os cadastros conforme tabela
        | pessoa  					            |  cpf 		        | ddd     | telefone  |
        | joao da silva                 |  43213424353    | 24     | 935349572  |
        | Charles Wellington            |  04842343231    | 21     | 445253541  |
        | Aparecida De Fatima Ferreira  |  20152644321    | 32     | 984354663  |
        | joao manuel silva             |  43213424323    | 12     | 932889572  |
        | Charles testes2               |  04842345434    | 11     | 443253541  |
        | Aparecida De manuel sabrina   |  20152643251    | 82     | 984332163  |
    Then validar as requisicoes para chaves duplicadas ou outros erros na insercao do cadastro
    And caso ocorra corretamente retornar o codigo gerado no banco de dados 
    
    
   
  
  