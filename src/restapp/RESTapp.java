/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**Classe central do servidor contendo a main e as funções de inicialização e conexão.
 *
 * @author Afonso H
 * @version 3.0 final
 * @since Sprint 3 - Release final
 */
public class RESTapp {

    private static int port = 8000;
    private static ProductsManager productList = new ProductsManager();
    private static PaymentsManager paymentsList = new PaymentsManager();
    
    /**Função main(String[] args) - Dispara a execução do servidor e configura os handlers.
     * 
     * @param args 
     * @throws java.lang.Exception 
     */
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        //Adiciona os contextos pedidos e seta os handlers
        server.createContext("/plans", new PlansHandler(productList)); 
        server.createContext("/payment", new PaymentHandler(paymentsList, productList));
        server.setExecutor(null); // creates a default executor
        server.start();
        LogService.reportMsgs("RESTapp", "Servidor iniciado - porta "+port);
    }
}