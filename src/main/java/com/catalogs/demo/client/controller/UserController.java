package com.catalogs.demo.client.controller;

import com.catalogs.demo.client.dto.LogingRequestDto;
import com.catalogs.demo.client.dto.UserRegisterDto;
import com.catalogs.demo.client.service.UserService;
import com.catalogs.demo.response.ResponseMessage;
import com.catalogs.demo.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LogingRequestDto logingRequest){
        try {
            ResponseMessage result = userService.logingUser(logingRequest);
            return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body( new ResponseMessage("Error en servidor"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody UserRegisterDto user, BindingResult bindingResult){
        try{
          if (bindingResult.hasErrors()){
              List <Map<String, String>> errorList = Utils.getErrors(bindingResult);
              return ResponseEntity.status(400).body(new ResponseMessage("Error de validación", errorList));
          }
          ResponseMessage result = userService.registerUser(user);
          return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error en servidor: " + e.getMessage()));
        }
    }
}