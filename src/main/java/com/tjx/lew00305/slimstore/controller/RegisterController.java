package com.tjx.lew00305.slimstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tjx.lew00305.slimstore.dto.RegisterRequestDTO;
import com.tjx.lew00305.slimstore.dto.RegisterResponseDTO;
import com.tjx.lew00305.slimstore.dto.UserDTO;
import com.tjx.lew00305.slimstore.model.common.Barcode;
import com.tjx.lew00305.slimstore.model.common.View;
import com.tjx.lew00305.slimstore.model.entity.Store;
import com.tjx.lew00305.slimstore.model.session.LocationSession;
import com.tjx.lew00305.slimstore.service.UserService;
import com.tjx.lew00305.slimstore.service.ViewService;
import com.tjx.lew00305.slimstore.service.BarcodeService;
import com.tjx.lew00305.slimstore.service.BasketService;
import com.tjx.lew00305.slimstore.service.GiftCardService;

import com.tjx.lew00305.slimstore.service.LocationService;
import com.tjx.lew00305.slimstore.service.TenderService;

@RestController
public class RegisterController {

    @Autowired
    private BasketService basketService;
    @Autowired
    private GiftCardService giftCardService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private TenderService tenderService;
    @Autowired
    private BarcodeService barcodeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ViewService viewService;

    @PostMapping(path = "/api/register")
    public @ResponseBody RegisterResponseDTO registerQuery(
            @RequestBody RegisterRequestDTO request,
            @CookieValue(name = "store-register", required = false) String storeRegCookie
    ) {
        RegisterResponseDTO response = new RegisterResponseDTO();
        UserDTO user = userService.getUserFromSession();
        if((user == null || user.getCode() == null) && !request.getFormProcess().equals("Login")) {
            response.setView(viewService.getViewByName("login"));
            return response;
        }
        if(!request.getFormProcess().isEmpty()) {
            switch (request.getFormProcess()) {
                case "Login":
                    user = userService.validateLoginByRequest(request);
                    if(user == null) {
                        response.setView(viewService.getViewByName("login"));
                        response.setError("Invalid login attempt.");
                        return response;
                    }
                    break;
                case "Logout":
                    userService.logout();
                    response.setView(viewService.getViewByName("login"));
                    return response;
                case "ChangeRegister":
                    LocationSession location = locationService.validateLocationByRequest(request);
                    if(location == null) {
                        response.setView(viewService.getViewByName("register-setup"));
                        response.setError("Invalid location details.");
                        return response;
                    }
                    break;
                case "Search":
                    Barcode barcode = barcodeService.getBarcodeByRequest(request);
                    if(barcode != null) {
                        basketService.addFormElement(barcode.getFormElement());
                        request.setAction("home");
                    }
                    break;
                case "NewUser":
                    response = userService.addUserFromRequest(request, response);
                    if(!response.getError().isEmpty()) {
                        response.setView(viewService.getViewByName("user-new"));
                    }
                    break;
                case "AddToBasket":
                    basketService.addBasketByRequest(request);
                    break;
                case "EmptyBasket":
                    basketService.empty();
                    break;
                case "Tender":
                    tenderService.addTenderByRequest(request);
                    break;
                case "ProcessGiftcard":
                    basketService.addFormElement(giftCardService.topupByRequest(request));
                    break;
                case "TransactionComplete":
                    basketService.empty();
                    tenderService.empty();
                    break;
            }
        }
        Store store = locationService.getStore();
        if(store == null && storeRegCookie != null) {
            String[] storeRegCookieSplit = storeRegCookie.split("-");
            locationService.validateLocation(storeRegCookieSplit[0], storeRegCookieSplit[1]);
            store = locationService.getStore();
        }
        if(store == null && !request.getFormProcess().equals("ChangeRegister")) {
            response.setView(viewService.getViewByName("register-setup"));
            response.setError("Store and register setup required.");
            return response;
        }
        View view = viewService.getViewByRequest(request);
        response.setView(view);
        response.setStore(locationService.getStore());
        response.setRegister(locationService.getStoreRegister());
        response.setBasket(basketService.getBasketArray());
        response.setTender(tenderService.getTenderArray());
        response.setUser(user);
        if(tenderService.isComplete()) {
            response.setView(viewService.getViewByName("complete"));
        }
        return response;
    }
    
}
