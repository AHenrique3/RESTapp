/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restapp;

import com.google.gson.Gson;
import java.util.ArrayList;

/**Obtém os produtos
 *
 * @author afonso
 */
public class GetProducts {
    private ArrayList<Product> availableProducts = new ArrayList();

    public GetProducts() {
        if(availableProducts.isEmpty()){
            setProducts();
        }
    }

    private void setProducts(){
        availableProducts   = new ArrayList();
        Product p1 = new Product("gold_plan", 59.90, "plano pago gold");
        availableProducts.add(p1);
        Product p2 = new Product("platinum_plan",79.90, "premium platinum");
        availableProducts.add(p2);
        Product p3 = new Product("super_premium_plan", 129.90, "o melhor plano de todos");
        availableProducts.add(p3);
    }

    public String getProductsAsJSON(){
        if(availableProducts.isEmpty()){
            setProducts();
        }
        Gson gson = new Gson();
        return gson.toJson(availableProducts);
    }
    
    public Product fetchProduct(String productName){
        for(Product p : availableProducts){
            if(productName.equals(p.getProduct())){
                return p;
            }
        }
        return null;
    }
    
    
}
