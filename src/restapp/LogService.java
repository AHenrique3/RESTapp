/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

/**
 *
 * @author afonso
 */
public class LogService {
    /**Função reportMsgs(String source, String msg) - Função de organização de screen logs,
     * identifica a classe que gerou o log.
     * 
     * @param msg mensagem de log a ser exibida na tela.
     */
    static void reportMsgs(String source, String msg){
            System.out.println(String.format("%s - %s",source,msg));
    }
}
