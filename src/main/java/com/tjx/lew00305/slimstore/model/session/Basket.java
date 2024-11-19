package com.tjx.lew00305.slimstore.model.session;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SuppressWarnings("serial")
@Component
@SessionScope
public class Basket implements Serializable {

    private ArrayList<BasketLine> basket = new ArrayList<BasketLine>();
    
    public void add(BasketLine basketLine) {
        basket.add(basketLine);
    }
    
    public void empty() {
        basket = new ArrayList<BasketLine>();        
    }
    
    public ArrayList<BasketLine> getArrayList(){
        return basket;
    }
    
    public BasketLine[] getArray() {
        return basket.toArray(new BasketLine[0]);
    }
    
    public float getTotal() {
        float total = 0;
        for(BasketLine line : basket) {
            total += line.getQuantity() * line.getUnitValue();
        }
        return total;
    }
   
}