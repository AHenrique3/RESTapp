/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

/**Razão para discount como String: parse JSON não distingue campo numérico vazio de 0.00 -> não permite validação correta
 *
 * @author afonso
 */
public class CrudePayment {
    private String payment_date;
    private String payment_type;
    private String product;
    private double product_price;
    private String discount;

    public String getPayment_date() {
        return payment_date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getProduct() {
        return product;
    }

    public double getProduct_price() {
        return product_price;
    }

    public String getDiscount() {
        return discount;
    }

    public CrudePayment(String payment_date, String payment_type, String product, double product_price, String discount) {
        this.payment_date = payment_date;
        this.payment_type = payment_type;
        this.product = product;
        this.product_price = product_price;
        this.discount = discount;
    }
    
    @Override
    public String toString(){
        return String.format(" payment_date: %s \n payment_type: %s \n product: %s\n product_price: %f\n discount: %s\n",payment_date,payment_type,product,product_price,discount);
    }
}
