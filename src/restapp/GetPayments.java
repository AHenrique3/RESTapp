/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**GetPayments : Gerencia os pagamentos (Protótipo de banco - Guarda os dados em um array de objetos)
 *
 * @author afonso
 */
public class GetPayments {
    private ArrayList<Payment> paymentsList = new ArrayList();
    private GetProducts productsList;

    public GetPayments(GetProducts productsList) {
        this.productsList = productsList;
    }
    
    public String setPayment(CrudePayment paymentToStore){
        String returnedError = validPayment(paymentToStore);
        if (returnedError == null){
            LogService.reportMsgs("GetPayments", "Pagamento Validado");
            
            //compondo o resto do pagamento
            int transaction_id = paymentsList.size();
            double discount = Double.parseDouble(paymentToStore.getDiscount());
            double price = paymentToStore.getProduct_price()*(1-discount);
            
            Payment p = new Payment(paymentToStore.getPayment_date(), paymentToStore.getPayment_type(), paymentToStore.getProduct(), paymentToStore.getProduct_price(), discount, price, transaction_id);
            
            paymentsList.add(p);
            LogService.reportMsgs("GetPayments","Listagem de pagamentos: \n" + getPaymentsAsJSON());
            LogService.reportMsgs("GetPayments", "Pagamento Adicionado");
        }
        return returnedError;
    }
    
    public String getPaymentsAsJSON(){
        Gson gson = new Gson();
        return gson.toJson(paymentsList);
    }
    
    /**validPayment : faz a validação dos pagamentos recebidos
     * Regras: 
     *      nenhum campo vazio  (payment_date, payment_type, product, product_price, discount)
     *      payment_date com formato dd/mm/yyyy
     *      product fornecido existe e product_price corresponde ao produto
     *      discount fornecido entre 0 e 0.5
     *      
     * @return String erro : null se não há erro, senão mensagem de erro 
     */
    private String validPayment(CrudePayment paymentToValidate){
        String erro = null;
        
        if(paymentToValidate.getPayment_date() == null){
            return "Campo payment_date vazio";
        }
        
        if(paymentToValidate.getPayment_type() == null){
            return "Campo payment_type vazio";
        }
        
        if(paymentToValidate.getProduct() == null){
            return "Campo product vazio";
        }
        
        if(paymentToValidate.getDiscount() == null){
            return "Campo discount vazio";
        }
        
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        sourceFormat.setLenient(false);
        try {
            Date data = sourceFormat.parse(paymentToValidate.getPayment_date());
            if(data == null){
                erro = "Data Invalida";
            }
        } catch (ParseException ex) {
            erro = "Data invalida";
        }
        
        Product p = productsList.fetchProduct(paymentToValidate.getProduct());
        if(p == null){
            erro = "Produto nao encontrado";
        }else{
            if(p.getPrice() != paymentToValidate.getProduct_price()){
                erro = "Preco invalido";
            }
        }
        double discount = Double.parseDouble(paymentToValidate.getDiscount());
        if(discount > 0.5 || discount < 0){
            erro = "Valor de desconto inadequado";
        }
        LogService.reportMsgs("GetPayments", erro);
        return erro;
    }
}
