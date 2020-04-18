package br.ce.wcaquino.tasks.apitest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class ApiTest {

    @BeforeClass
    public static void setup(){
        baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        given()
        .when()
            .get("todo")
        .then()
            .statusCode(200);
    }


    @Test
    public void deveCriarTarefas(){
        Map<String, String> requestParams = new HashMap<String, String>();

        requestParams.put("task", "Teste via API");
        requestParams.put("dueDate", "2020-12-29");
        Gson gson = new GsonBuilder().create();
        String body = gson.toJson(requestParams);

        given()
            .body(body)
            .contentType(ContentType.JSON)
        .when()
            .post("todo")
        .then()
            .log().all()
            .statusCode(201);
    }


    @Test
    public void naoDeveCriarTarefasComDaraPassada(){
        Map<String, String> requestParams = new HashMap<String, String>();

        requestParams.put("task", "Teste via API");
        requestParams.put("dueDate", "2010-12-29");
        Gson gson = new GsonBuilder().create();
        String body = gson.toJson(requestParams);

        given()
            .body(body)
            .contentType(ContentType.JSON)
        .when()
            .post("todo")
        .then()
            .log().all()
            .statusCode(400)
            .body("message", is("Due date must not be in past"));
    }
}
