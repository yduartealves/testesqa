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

Feature: Testes de API Brasilprev. Buscar os cadastros de Pessoa fisica pelo Telefone

 Scenario: buscar as pessoas pelo Telefone
    Given  base para api url "http://localhost:8080" e a base path "/pessoas" 
    When Buscar pessoas conforme tabela de telefones
        | ddd     | telefone  |
        | 12     | 932889572  |
        | 11     | 443253541  |
        | 82     | 984332163  |
    Then Caso encontre retornar os dados conforme busca se nao mostrar qual nao teve exito
     