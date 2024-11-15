package com.tjx.lew00305.slimstore.model;

import lombok.Data;

@Data
public class View {

    private String name = "";
    private String title = "";
    private String message = "";
    private String formProcess = "";
    private String formSuccess = "";
    private FormElement[] formElements = new FormElement[0];
    private FunctionButton[] functionButtons = new FunctionButton[0];
    
}