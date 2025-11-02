package com.sisgea.sisgea.TesteAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Request {
    
    private static final String BASE_URL = "http://localhost:8080/";
    private static final int TIMEOUT_SECONDS = 10;
    
    public String requisicao(Object obj, String url, String method) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        
        try {
            if (obj != null) {
                json = mapper.writeValueAsString(obj);
                System.out.println("JSON enviado (" + method + "): " + json);
            }
        } catch (Exception e) {
            System.err.println("Erro ao serializar objeto para JSON:");
            e.printStackTrace();
            return null;
        }
        
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + url))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS));
        
        switch (method.toUpperCase()) {
            case "POST":
                builder.POST(HttpRequest.BodyPublishers.ofString(json));
                break;
            case "PUT":
                builder.PUT(HttpRequest.BodyPublishers.ofString(json));
                break;
            case "DELETE":
                builder.DELETE();
                break;
            case "GET":
                builder.GET();
                break;
            default:
                throw new IllegalArgumentException("Método HTTP inválido: " + method);
        }
        
        HttpRequest request = builder.build();
        
        System.out.println("Executando requisição: " + method + " " + BASE_URL + url);
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Resposta recebida: " + (response.body() != null && !response.body().isEmpty() 
                    ? response.body() 
                    : "(corpo vazio)"));
            
            // Verificar se a resposta foi bem-sucedida
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                System.err.println("Erro na requisição. Status: " + response.statusCode());
                return response.body(); // Retorna o corpo mesmo com erro para análise
            }
            
        } catch (IOException e) {
            System.err.println("Erro de I/O ao fazer requisição:");
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            System.err.println("Requisição interrompida:");
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception e) {
            System.err.println("Erro inesperado ao fazer requisição:");
            e.printStackTrace();
            return null;
        }
    }
}