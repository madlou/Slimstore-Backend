package com.tjx.lew00305.slimstore.controller;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tjx.lew00305.slimstore.dto.RegisterRequestDTO;
import com.tjx.lew00305.slimstore.dto.RegisterResponseDTO;
import com.tjx.lew00305.slimstore.dto.UserDTO;
import com.tjx.lew00305.slimstore.model.View;
import com.tjx.lew00305.slimstore.model.FormElement;
import com.tjx.lew00305.slimstore.service.ProductService;
import com.tjx.lew00305.slimstore.service.TransactionService;
import com.tjx.lew00305.slimstore.service.UserService;
import com.tjx.lew00305.slimstore.service.FlowService;
import com.tjx.lew00305.slimstore.service.LocationService;

@RestController
public class RegisterController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @CrossOrigin
    @PostMapping(path = "/slimstore/api/register")
    public @ResponseBody RegisterResponseDTO registerQuery(@RequestBody RegisterRequestDTO request) {
        RegisterResponseDTO response = new RegisterResponseDTO();
        String action = request.getAction();
        View flow = flowService.get(action);
        response.setFlow(flow);
        response.setStore(locationService.getStore(123));
        response.setRegister(locationService.getRegister(123, 1));
        response.setAuditLog(transactionService.getCurrentAuditLog());
        response.setUser(modelMapper.map(userService.getUser(), UserDTO.class));
        switch (action) {
            case "search":
                FormElement[] formElements = request.getFormElements();
                flow.setFormElements(productService.onlineSearch(formElements[0].getValue()));
                break;
        }
        return response;
    }

}
