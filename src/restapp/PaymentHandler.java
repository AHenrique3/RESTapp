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
import com.google.gson.Gson;
import java.util.ArrayList;

/**PaymentHandler: responde aos pedidos no contexto /payment
 * Função atual: rejeita outros pedidos diferentes de POST,
 * e responde um texto confirmando ou rejeitando o pagamento,
 * e mostrando o argumento de rejeição
 *
 * @author afonso
 */
public class PaymentHandler implements HttpHandler {
    PaymentsManager payments;
    ProductsManager products;

    public PaymentHandler(PaymentsManager payments, ProductsManager products) {
        this.payments = payments;
        this.products = products;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        //Resposta de erro padrão
        String response = "Requisicao indisponivel para esse dominio!";
        int HTTPStatusCode = 403;
        
        //Obtém o método e só executa se for POST
        String method = t.getRequestMethod();
        LogService.reportMsgs("PaymentHandler", "Requisição feita - método: "+method);
        if(method.equals("POST")){
            //Obtendo dados da requisição
            InputStream is = t.getRequestBody();
            String requestContents = getStringFromImputStream(is);
            if(requestContents != null){
                LogService.reportMsgs("PaymentHandler", "POST recebido : "+requestContents);

                //Gera CrudePayment para a validação
                Gson gson = new Gson();  
                CrudePayment paymentReceived = gson.fromJson(requestContents, CrudePayment.class);
                LogService.reportMsgs("PaymentHandler", "Pagamento parsed\n" + paymentReceived.toString());

                //Gerando validação
                ValidatePayments validation = new ValidatePayments(payments, products);
                String paymentResponse = validation.validateAndCreatePayment(paymentReceived);
                
                //Verificando lista de pagamentos
                LogService.reportMsgs("GetPayments","Listagem de pagamentos: \n" + getPaymentsListAsJSON(payments.getPaymentsList()));

                //Verificando status da validação e montando resposta
                if(paymentResponse == null){
                    response = "Pagamento registrado com sucesso!";
                    HTTPStatusCode = 200;
                }else{
                    response = String.format("Erro! O pagamento nao pode ser registrado!\n Argumento: %s\n",paymentResponse);
                    HTTPStatusCode = 400;
                }
            }else{
                response = "Erro! O pagamento nao pode ser registrado!\n Falha no processamento\n";
                HTTPStatusCode = 500;
            }
        }
        
        //Montando resposta
        LogService.reportMsgs("PaymentHandler", "Respondendo: "+response);
        
        t.sendResponseHeaders(HTTPStatusCode, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
        
        LogService.reportMsgs("PaymentHandler", "Respondido - Thread Encerrada :)");
    }
    
    //Coleta os dados enviados pelo usuário e os retorna em uma String
    public String getStringFromImputStream(InputStream is) { 
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        String data = null;
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            data = result.toString("UTF-8");
        } catch (IOException ex) {
            LogService.reportMsgs("PaymentHandler", "ERRO! - Falha na obtencao dos dados do usuario");
        }
        return data;
    }
    
    public Payment loadUserFromJSONGson(String jsonString) {
        Gson gson = new Gson();
        Payment user = gson.fromJson(jsonString, Payment.class);
        return user;
    }
    
    //Obtem o pagamento e retorna a sua descrição em JSON
    public String paymentToJSON(Payment p) {
        Gson gson = new Gson();
        String paymentJSONString = gson.toJson(p);
        return paymentJSONString;
    }
    
    public String getPaymentsListAsJSON(ArrayList<Payment> paymentsList){
        Gson gson = new Gson();
        String paymentsListJSONString = gson.toJson(paymentsList);
        return paymentsListJSONString;
    }
}
