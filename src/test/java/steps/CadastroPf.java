package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when; 
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import junit.framework.Assert;


import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class CadastroPf {
	
	
	Response response;
	JsonPath json;
	 private static String requestBody;
	 private static String cpf;
	 private static String[] occurences;
	 private static String status;
	 private static String retornomsg;
	 private static Integer codigo;
	   HashMap<String, List<String>> hash_map = new HashMap<String, List<String>>();
	   
@Given("Inicializa base url {string} e a base path {string}")
public void as_apis_estao_no_servidor(String url, String path) {
    // Write code here that turns the phrase above into concrete actions
	
	//System.out.println( url + " - " + path);
	
	RestAssured.baseURI=url;
	RestAssured.basePath=path;
	

	 /*body("erro", containsString("Já existe pessoa cadastrada com o Telefone")).
	    log().
	     all();
	   */
	
	//throw new io.cucumber.java.PendingException();
}

@When("requisitar os cadastros conforme tabela")
public void execultar_o_endpoint(io.cucumber.datatable.DataTable dataTable) {
	
    // Write code here that turns the phrase above into concrete actions
	List<Map<String,String>> listOfMaps = dataTable.asMaps();
 
    
	for (Map<String, String> map : listOfMaps) {
		
		for (String key : map.keySet()) {
			
			String pessoa = map.get("pessoa");
			  cpf = map.get("cpf");
			String ddd = map.get("ddd");
			String telefone = map.get("telefone");
			
			requestBody = "{\"codigo\": 0, "
					+ "\"nome\": \""+pessoa+ "\", "
					+ "\"cpf\": \""+cpf+"\", "
		  	 		+ "\"enderecos\": [{ \"logradouro\": \"Rua Alexandre Dumas\", \"numero\": 123,\"complemento\": "
			 		+ "\"Empresa\",\"bairro\": \"Chacara Santo Antonio\", \"cidade\": \"São Paulo\",\"estado\": \"SP\"}],"
			 		+ "\"telefones\": "
			 		+ "[{\"ddd\": \""+ ddd+ "\","
			 		+ "\"numero\":\""+telefone+"\"}]}\n"
			 		+ "";
			 
			//System.out.println( key + " - " + requestBody);
		}
		
		  response = RestAssured.given()
	              .header("Content-type", "application/json")
	              .and()
	              .body(requestBody)
	              .when()
	              .post("")
	              .then()
	              .log()
	     	      .all()
	     	      //.statusCode(201).assertThat()
	            //  .body("nome", containsString("Julio testes"))
	     	      // .assertThat().statusCode(201)
	     	      .extract().response();
		
	   
		  // Creating an empty HashMap
	   
		  if(response.getStatusCode()==400) {
			   status = String.valueOf(response.getStatusCode());//get the cookies
			   
			   JsonPath js = new JsonPath(response.asString());
			   retornomsg=js.getString("erro");
			  	
			   occurences =  new String[]{status ,retornomsg };
			   
	        }else 
	           if (response.getStatusCode()==500){
	        	 status = String.valueOf(response.getStatusCode());//get the cookies
	        	 occurences = new String[]{status ,"erro banco de dados" };
	        }else 
		        if (response.getStatusCode()==201){
		            	 codigo =  response.jsonPath().get("codigo");
			        	 status = String.valueOf(response.getStatusCode());//get the cookies
			        	 occurences = new String[]{status ,codigo.toString()};
			        }
		  
	        // Mapping string values to int keys
		  
			
			hash_map.put(cpf, 		Arrays.asList(occurences));
		
			
	}


	

	//throw new io.cucumber.java.PendingException();
}

@Then("validar as requisicoes para chaves duplicadas ou outros erros na insercao do cadastro")
public void validar_as_requisicoes_para_chaves_duplicadas_ou_outros_erros_na_insercao_do_cadastro() {
    // Write code here that turns the phrase above into concrete actions
    
	
	  Set<String> chaves = hash_map.keySet();  
	    for (Iterator<String> it = chaves.iterator(); it.hasNext();){  
	        String chave = it.next();  
	        if(chave != null){ 
	        	List<String> list  =hash_map.get(chave);
	        	Assert.assertEquals("erro no cpf: "+chave+" mensagem erro: "+list.get(1),"201", list.get(0));
	           // System.out.println(chave +" valor"+ hash_map.get(chave));  
	    
	        
	        }
	    
	        
	    }  

}

@Then("caso ocorra corretamente retornar o codigo gerado no banco de dados")
public void caso_ocorra_corretamente_retornar_o_codigo_gerado_no_banco_de_dados() {
	 
	Set<String> chaves = hash_map.keySet();  
	    for (Iterator<String> it = chaves.iterator(); it.hasNext();){  
	        String chave = it.next();  
	        if(chave != null){ 
	        	List<String> list  =hash_map.get(chave);
	        	Assert.assertEquals("Cpf: "+chave+" Codigo gerado de cadastro: "+list.get(1),"201", list.get(0));
	           // System.out.println(chave +" valor"+ hash_map.get(chave));  
	        
	        }
	    
	    }  
}

}
