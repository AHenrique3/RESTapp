/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;

/**
 *
 * @author afonso
 */
public class PaymentHandler implements HttpHandler {
    GetPayments payments;

    public PaymentHandler(GetPayments payments) {
        this.payments = payments;
    }
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        String method = t.getRequestMethod();
        String response = "Requisicao indisponivel para esse dominio!";
        int HTTPStatusCode = 403;
        
        LogService.reportMsgs("PaymentHandler", "Requisição feita - método: "+method);
        
        if(method.equals("POST")){
            InputStream is = t.getRequestBody();
            String requestContents = getStringFromImputStream(is);
            LogService.reportMsgs("PaymentHandler", "POST recebido : "+requestContents);
            
            Gson gson = new Gson();  
            CrudePayment paymentReceived = gson.fromJson(requestContents, CrudePayment.class);
            LogService.reportMsgs("PaymentHandler", "Pagamento parsed\n" + paymentReceived.toString());
            
            String paymentResponse = payments.setPayment(paymentReceived);
            
            if(paymentResponse == null){
                response = "Pagamento registrado com sucesso!";
                HTTPStatusCode = 200;
            }else{
                response = String.format("Erro! O pagamento nao pode ser registrado!\n Argumento: %s\n",paymentResponse);
                HTTPStatusCode = 400;
            }
        }
        
        LogService.reportMsgs("PaymentHandler", "Respondendo: "+response);
        t.sendResponseHeaders(HTTPStatusCode, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
        LogService.reportMsgs("PaymentHandler", "Respondido - Thread Encerrada :)");
    }
    
    public String getStringFromImputStream(InputStream is) { 
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        String data = "";
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            data = result.toString("UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(PaymentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public Payment loadUserFromJSONGson(String jsonString) {
        Gson gson = new Gson();
        Payment user = gson.fromJson(jsonString, Payment.class);
        return user;
    }
    
    public String paymentToJSON(Payment p) {
        Gson gson = new Gson();
        String userJSONString = gson.toJson(p);

        return userJSONString;
    }
}
