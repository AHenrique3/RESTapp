/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import java.util.ArrayList;

/**GetPayments : Gerencia os pagamentos, recebendo e armazenando eles
 *
 * @author afonso
 */
public class PaymentsManager {
    private final ArrayList<Payment> paymentsList;

    public PaymentsManager() {
        paymentsList = new ArrayList();
    }

    public void addPayment(Payment p) {
        paymentsList.add(p);
    }
    
    public ArrayList<Payment> getPaymentsList() {
        return paymentsList;
    }
    
    public int getPaymentsListSize(){
        return paymentsList.size();
    }
}
