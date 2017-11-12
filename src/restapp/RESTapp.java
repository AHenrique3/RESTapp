/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Classe central do servidor contendo a main e as funções de inicialização e conexão.
 *
 * @author Afonso H
 * @version 3.0 final
 * @since Sprint 3 - Release final
 */
public class RESTapp {

    private static int port = 8000;
    private static GetProducts productList = new GetProducts();
    private static GetPayments paymentsList = new GetPayments(productList);
    
    /**Função main(String[] args) - Dispara a execução do sistema e configura os handlers.
     * 
     * @param args 
     */
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/plans", new PlansHandler(productList));
        server.createContext("/payments", new PaymentHandler(paymentsList));
        server.setExecutor(null); // creates a default executor
        server.start();
        LogService.reportMsgs("RESTapp", "Servidor iniciado - porta "+port);
    }
}