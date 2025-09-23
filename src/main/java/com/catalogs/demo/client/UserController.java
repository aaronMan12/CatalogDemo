package com.catalogs.demo.client;

import com.catalogs.demo.client.User;
import com.catalogs.demo.client.dto.LogingRequestDto;
import com.catalogs.demo.client.dto.LogingResponseDto;
import com.catalogs.demo.client.dto.RegisterDto;
import com.catalogs.demo.response.ResponseMessage;
import com.catalogs.demo.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository clientRepository;

    @PostMapping("/loging")
    public ResponseEntity<LogingResponseDto> loging(@RequestBody LogingRequestDto logingRequest){
        try {
            UserLoginProjection userLoginProjection = clientRepository.loging(logingRequest.getUserName(), logingRequest.getPassword());
            if (userLoginProjection != null){
                LogingResponseDto logingResponseDto = new LogingResponseDto(
                        userLoginProjection.getName(),
                        userLoginProjection.getUserName(),
                        userLoginProjection.getFullName(),
                        userLoginProjection.getFirstLastName(),
                        userLoginProjection.getSecondLastName(),
                        true, "exitoso");
                return  ResponseEntity.ok(logingResponseDto);
            }else{
                return ResponseEntity.status(401).body(
                        new LogingResponseDto(false, "Error en credenciales")
                );
            }
        }catch (Exception e) {
            return ResponseEntity.status(500).body( new LogingResponseDto(false, "Error en servidor"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody User user, BindingResult bindingResult){
        try{
          if (bindingResult.hasErrors()){
              List <Map<String, String>> errorList = Utils.getErrors(bindingResult);
              return ResponseEntity.status(400).body(new ResponseMessage("Error de validación", errorList));
          }else{
          User newUser = clientRepository.save(user);
          if (newUser != null){
              return ResponseEntity.status(201).body(new ResponseMessage("Usuario creado exitosamente"));
          }else {
              return ResponseEntity.status(406).body(new ResponseMessage("Usuario creado exitosamente"));
          }
          }
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error en servidor"));
        }
    }

}