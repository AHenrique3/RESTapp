/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**PlansHandler: responde aos pedidos no contexto /plans
 * Função atual: rejeita outros pedidos diferentes de GET,
 * e responde um JSON com os produtos existentes
 *
 * @author afonso
 */
public class PlansHandler implements HttpHandler{
    private ProductsManager productList;

    public PlansHandler(ProductsManager productList) {
        this.productList = productList;
    }
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        //Resposta de erro padrão
        String response = "Requisicao indisponivel para esse dominio!";
        int HTTPStatusCode = 403;
        
        //Obtém o método e só executa se for GET
        String method = t.getRequestMethod();
        LogService.reportMsgs("PlansHandler", "Requisição feita - método: "+method);
        if(method.equals("GET")){
            response = getResponseContent();
            HTTPStatusCode = 200;
        }
        
        //Montando resposta
        LogService.reportMsgs("PlansHandler", "Respondendo: "+response);
        
        t.sendResponseHeaders(HTTPStatusCode, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
        
        LogService.reportMsgs("PlansHandler", "Respondido - Thread Encerrada :)");
    }
    
    //Obtem os produtos e retorna a descrição em JSON deles
    private String getResponseContent(){
        Gson gson = new Gson();
        return gson.toJson(productList.getAvailableProducts());
    }
}
