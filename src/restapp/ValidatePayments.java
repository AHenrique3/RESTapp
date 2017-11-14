/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**ValidatePayments: Valida um CrudePayment e gera um Payment adequado,
 * se a validação falha, retorna uma mensagem de erro
 *
 * @author afonso
 */
public class ValidatePayments {
    private PaymentsManager paymentsList;
    private ProductsManager productsList;

    public ValidatePayments(PaymentsManager paymentsList, ProductsManager productsList) {
        this.paymentsList = paymentsList;
        this.productsList = productsList;
    }
    
    //Faz a validação e cria o pagamento se ele é válido
    public String validateAndCreatePayment(CrudePayment paymentToValidate){
        LogService.reportMsgs("ValidatePayments", "Iniciando Validação");
        String returnedError = validPayment(paymentToValidate);
        if (returnedError == null){
            LogService.reportMsgs("ValidatePayments", "Pagamento Validado");
            
            //compondo o novo pagamento
            int transaction_id = paymentsList.getPaymentsListSize();
            double discount = Double.parseDouble(paymentToValidate.getDiscount());
            double price = paymentToValidate.getProduct_price()*(1-discount);
            
            Payment p = new Payment(paymentToValidate.getPayment_date(), paymentToValidate.getPayment_type(), paymentToValidate.getProduct(), paymentToValidate.getProduct_price(), discount, price, transaction_id);
            
            paymentsList.addPayment(p);
            LogService.reportMsgs("ValidatePayments", "Pagamento Adicionado");
        }else{
            LogService.reportMsgs("ValidatePayments", "Falha de Validação");
        }
        return returnedError;
    }

    
    /**validPayment : faz a validação dos pagamentos recebidos
     * Regras: 
     *      nenhum campo vazio  (payment_date, payment_type, product, product_price, discount)
     *      payment_date com formato dd/mm/yyyy
     *      product fornecido existe e product_price corresponde ao produto
     *      discount fornecido entre 0 e 0.5
     *      
     * @param paymentToValidate
     * @return String erro : null se não há erro, senão mensagem de erro 
     */
    private String validPayment(CrudePayment paymentToValidate){
        String erro = null;
        
        //Verificando campos vazios
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
        
        //Verificando data, existência do produto e preço correto
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
        LogService.reportMsgs("ValidatePayments", "Erro: " + erro);
        return erro;
    }
}
