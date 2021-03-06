/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

/**Product: classe para armazenar os dados de um produto
 * Contém os campos nome do produto (product) preço (price) e descrição (description)
 *
 * @author afonso
 */
public class Product {
    private final String product;
    private final double price;
    private final String description;

    public Product(String productName, double productPrice, String productDescription) {
        this.product = productName;
        this.price = productPrice;
        this.description = productDescription;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString(){
        return String.format(" product: %s \n price: R$%f\n description: %s\n",product,price,description);
    }
}
