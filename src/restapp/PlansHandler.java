/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author afonso
 */
public class PlansHandler implements HttpHandler{
    private GetProducts productList;

    public PlansHandler(GetProducts productList) {
        this.productList = productList;
    }
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        String method = t.getRequestMethod();
        String response = "Requisicao indisponivel para esse dominio!";
        int HTTPStatusCode = 403;
        
        LogService.reportMsgs("PlansHandler", "Requisição feita - método: "+method);
        
        if(method.equals("GET")){
            response = productList.getProductsAsJSON();
            HTTPStatusCode = 200;
        }
        
        LogService.reportMsgs("PlansHandler", "Respondendo: "+response);
        t.sendResponseHeaders(HTTPStatusCode, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
        LogService.reportMsgs("PlansHandler", "Respondido - Thread Encerrada :)");
    }
}
