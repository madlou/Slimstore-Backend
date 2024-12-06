package com.tjx.lew00305.slimstore.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tjx.lew00305.slimstore.dto.TjxComSearchDTO;
import com.tjx.lew00305.slimstore.model.common.FormElement;

@Service
public class ProductService {
    
    private RestTemplate restTemplate;
    private String onlineSearchUrl;

    public ProductService(
        RestTemplate restTemplate,
        @Value("${tjx.online.search}")
        String onlineSearchUrl
    ) {
        this.restTemplate = restTemplate;
        this.onlineSearchUrl = onlineSearchUrl;
    }

    public FormElement[] onlineSearch(
        String query
    ) {
        TjxComSearchDTO search = restTemplate.getForObject(onlineSearchUrl +
            query, TjxComSearchDTO.class);
        int productCount = search.response.docs.length;
        FormElement[] products = new FormElement[productCount];
        for (int i = 0; i < productCount; i++) {
            TjxComSearchDTO.Response.Doc doc = search.response.docs[i];
            FormElement element = new FormElement();
            element.setType(FormElement.Type.PRODUCT_WEB);
            element.setKey(doc.pid);
            element.setLabel(doc.title);
            element.setQuantity(0);
            element.setPrice(new BigDecimal(doc.price));
            element.setImage(doc.thumbImage);
            products[i] = element;
        }
        return products;
    }
    
    public FormElement[] search(
        String query
    ) {
        return onlineSearch(query);
    }
    
}
