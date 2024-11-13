package com.tjx.lew00305.slimstore.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.tjx.lew00305.slimstore.model.BasketLine;
import com.tjx.lew00305.slimstore.model.FormElement;

@Service
public class BasketService {
    
    private ArrayList<BasketLine> basket = new ArrayList<BasketLine>();
    
    public void addFormElements(FormElement[] elements) {
        for(FormElement element : elements) {
            addFormElement(element);
        }
    }
    
    public void addFormElement(FormElement element) {
        int quantity = Integer.parseInt(element.getValue());
        if(quantity > 0) {
            basket.add(new BasketLine(element.getKey(), element.getLabel(), element.getType(), quantity, element.getPrice()));
        }
    }
    
    public ArrayList<BasketLine> getBasket() {
        return basket;
    }

    public BasketLine[] getBasketArray() {
        return basket.toArray(new BasketLine[0]);
    }

    public void empty() {
        basket = new ArrayList<BasketLine>();
    }

}
