/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

/** Payment : Classe para armazenar os dados de pagamentos
 * Sintaxes especiais:
 * payment_date : dd/mm/yyyy
 * discount     : 0 <= discount <= 0.5
 * valores numéricos positivos
 *
 * @author afonso
 */
public class Payment {
    private String payment_date;
    private String payment_type;
    private String product;
    private double product_price;
    private double discount;
    private double price;
    private int    transaction_id;

    public Payment(String payment_date, String payment_type, String product, double product_price, double discount, double price, int transaction_id) {
        this.payment_date = payment_date;
        this.payment_type = payment_type;
        this.product = product;
        this.product_price = product_price;
        this.discount = discount;
        this.price = price;
        this.transaction_id = transaction_id;
    }
}
