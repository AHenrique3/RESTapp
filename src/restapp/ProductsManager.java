/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import java.util.ArrayList;

/**ProductsManager: obtém os produtos, os armazena internamente, 
 * e os retorna quando solicitado
 *
 * @author afonso
 */
public class ProductsManager {
    private ArrayList<Product> availableProducts = new ArrayList();

    public ProductsManager() {
        if(availableProducts.isEmpty()){
            setAvailableProducts();
        }
    }

    //Retorna todos os produtos armazenados
    public ArrayList<Product> getAvailableProducts() {
        return availableProducts;
    }

    //Obtém os produtos a serem armazenados, no momento os produtos são hardCoded
    private void setAvailableProducts(){
        availableProducts   = new ArrayList();
        Product p1 = new Product("gold_plan", 59.90, "plano pago gold");
        availableProducts.add(p1);
        Product p2 = new Product("platinum_plan",79.90, "premium platinum");
        availableProducts.add(p2);
        Product p3 = new Product("super_premium_plan", 129.90, "o melhor plano de todos");
        availableProducts.add(p3);
    }    
    
    //Retorna o produto com o nome especificado e null se não existe
    public Product fetchProduct(String productName){
        for(Product p : availableProducts){
            if(productName.equals(p.getProduct())){
                return p;
            }
        }
        return null;
    }
}
