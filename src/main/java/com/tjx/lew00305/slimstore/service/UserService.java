package com.tjx.lew00305.slimstore.service;

import java.util.ArrayList;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tjx.lew00305.slimstore.dto.RegisterRequestDTO;
import com.tjx.lew00305.slimstore.dto.RegisterResponseDTO;
//import com.tjx.lew00305.slimstore.dto.UserDTO;
import com.tjx.lew00305.slimstore.model.common.FormElement;
import com.tjx.lew00305.slimstore.model.entity.User;
import com.tjx.lew00305.slimstore.model.session.UserSession;
import com.tjx.lew00305.slimstore.repository.UserRepository;

@Service
public class UserService {
    
//    @Autowired
//    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserSession userSession;
        
    @Value("${tjx.admin.password}")
    private String adminPassword;

    
    public User getUserFromSession() {
        return userSession.getUser();
    }
    
    public User addUser(String username, String name, String email, String password) throws Exception {
        User user = userRepository.save(new User(null, username, email, name, password));            
        return user;
    }
    
    public RegisterResponseDTO addUserFromRequest(RegisterRequestDTO request, RegisterResponseDTO response) {
        FormElement[] fe = request.getFormElements();
        try {
            addUser(fe[0].getValue(), fe[1].getValue(), fe[2].getValue(), fe[3].getValue());
            return response;
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate entry")) {
                response.setError("Unable to create user: Employee number already being used.");                            
            } else {
                response.setError("Unable to create user: " +  e.getMessage());
            }
            return response;
        }

    }
    
    private User getUser(String username) throws Exception {
        User user = userRepository.findByCode(username);
        if(user == null && username.equals("admin")) {
            return addUser("admin", "Admin Person", "admin@admin.com", adminPassword);
        }
        return user;
    }
    
//    public UserDTO getUserDTO(String username) throws Exception {
//        return modelMapper.map(getUser(username), UserDTO.class);
//    }
    
    public User validateLogin(String username, String password) throws Exception {
        User user = getUser(username);
        if(user != null && user.getPassword().equals(password)) {
            userSession.setUser(user);
            return user;
        }
        return null;
    }
    
    public User validateLoginByRequest(RegisterRequestDTO request) {
        FormElement[] formElements = request.getFormElements();
        if(formElements[0] == null || formElements[1] == null) {
            return null;
        }
        String username = formElements[0].getValue();
        String password = formElements[1].getValue();
        try {
            return validateLogin(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void logout() {
        userSession.setUser(new User());
    }

    public FormElement[] getUsersAsFormElements() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<FormElement> elements = new ArrayList<FormElement>();
        for(User user: users) {
            if(!user.getCode().equals("admin")) {
                FormElement userElement = new FormElement(
                    "button", 
                    user.getCode(), 
                    "Edit", 
                    user.getName(), 
                    null, null, null
                );
                elements.add(userElement);
            }
        }
        return elements.toArray(new FormElement[0]);
    }

    public User getUser() {
        return userSession.getUser();
    }

}
