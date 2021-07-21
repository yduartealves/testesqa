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
public class BuscaCadastrosPf {

	Response response;
	JsonPath json;

	 private static String ddd;
	 private static String telefone;
	 private static String cpf;
	 private static String[] occurences;
	 private static String status;
	 private static String retornomsg;
	 private static Integer codigo;
	 private static String nome;
	 private static String BASE_PATH;
	 
	HashMap<String, List<String>> hash_map = new HashMap<String, List<String>>();
	  

@Given("base para api url {string} e a base path {string}")
public void base_para_api_url_e_a_base_path(String url, String path) {
    // Write code here that turns the phrase above into concrete actions
	RestAssured.baseURI=url;
	RestAssured.basePath=path;
	BASE_PATH = path;
}

@When("Buscar pessoas conforme tabela de telefones")
public void buscar_pessoas_conforme_tabela_de_telefones(io.cucumber.datatable.DataTable dataTable) {
List<Map<String,String>> listOfMaps = dataTable.asMaps();
 
    
	for (Map<String, String> map : listOfMaps) {
		
		for (String key : map.keySet()) {
			
			 ddd = map.get("ddd");
			 telefone = map.get("telefone");
			BASE_PATH = "/"+ddd+"/"+telefone;
			
			//System.out.println( key + " - " + requestBody);2
		}
		
		  response = RestAssured.given()
	              .header("Content-type", "application/json")
	              .and()
	              .when()
	              .get(BASE_PATH)
	              .then()
	              .log()
	     	      .all()
	     	      //.statusCode(201).assertThat()
	            //  .body("nome", containsString("Julio testes"))
	     	     // .assertThat().statusCode(200)
	     	      .extract().response();
		
		  if(response.getStatusCode()==404) {
			   status = String.valueOf(response.getStatusCode());//get the cookies
			   
			   JsonPath js = new JsonPath(response.asString());
			   retornomsg=js.getString("erro");
			  	
			   occurences =  new String[]{status ,retornomsg };
			   
	        }else 
		        if (response.getStatusCode()==200){
		            	 codigo =  response.jsonPath().get("codigo");
		            	 nome =  response.jsonPath().get("nome");
		            	 cpf =  response.jsonPath().get("cpf");
			        	 status = String.valueOf(response.getStatusCode());//get the cookies
			        	 occurences = new String[]{status ,codigo.toString(),nome,cpf};
			        }
		  
			hash_map.put(ddd+" - "+telefone, 		Arrays.asList(occurences));
			
	}
}

@Then("Caso encontre retornar os dados conforme busca se nao mostrar qual nao teve exito")
public void caso_encontre_retornar_os_dados_conforme_busca_se_nao_mostrar_qual_nao_teve_exito() {
    // Write code here that turns the phrase above into concrete actions
	Set<String> chaves = hash_map.keySet();  
	
    for (Iterator<String> it = chaves.iterator(); it.hasNext();){  
        String chave = it.next();  
        List<String> list  =hash_map.get(chave);
        if(chave != null){ 
        	
        	 System.out.println(chave +" antes"+ hash_map.get(chave)); 
        	 Integer stat = Integer.valueOf(list.get(0));
        	if( stat==200) {
        	Assert.assertEquals("Telefone : "+chave+" Codigo: "+list.get(1)
        	+" nome: "+list.get(2)+" cpf: "+list.get(3),"200", list.get(0));
          
        	System.out.println(chave +" certo "+ list.get(0)); 
        }else {
        	 System.out.println(chave +" errado "+ list.get(0)); 
             Assert.assertEquals("erro telefone: "+chave+" mensagem erro: "+list.get(1),"200", "404");
        }
    
     }  
   }
}


}
