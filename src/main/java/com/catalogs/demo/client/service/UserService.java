package com.catalogs.demo.client.service;

import com.catalogs.demo.client.UserLoginProjection;
import com.catalogs.demo.client.dto.LogingRequestDto;
import com.catalogs.demo.client.dto.LogingResponseDto;
import com.catalogs.demo.client.dto.UserRegisterDto;
import com.catalogs.demo.client.entity.User;
import com.catalogs.demo.client.repository.UserRepository;
import com.catalogs.demo.response.ResponseMessage;
import com.catalogs.demo.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseMessage registerUser(UserRegisterDto user){
        Integer userExist = userRepository.vefiriqueUser(user.getUserName(), user.getEmail());
            if (userExist == null){
                User newUser = new User();
                newUser.setIdStatus(user.getIdStatus());
                newUser.setName(user.getName());
                newUser.setFirstLastName(user.getFirstLastName());
                newUser.setSecondLastName(user.getSecondLastName());
                newUser.setUserName(user.getUserName());
                newUser.setPassword(Utils.encryptPassword(user.getPassword()));
                newUser.setEmail(user.getEmail());
                User userCreated = userRepository.save(newUser);
                if (userCreated != null){
                    //Mandar el código de verificación al usuario al correo
                    return new ResponseMessage(201, "Usuario creado exitosamente");
                }else{
                    return new ResponseMessage(406, "Error al crear usuario");
                }
            }else{
                return new ResponseMessage(406, "El nombre de usuario o Email ya fueron usados, ingrese uno diferente");
            }
    }

    public ResponseMessage logingUser(LogingRequestDto logingRequestDto){
        UserLoginProjection userLoginProjection = userRepository.loging(logingRequestDto.getUserName(), logingRequestDto.getPassword());
        if (userLoginProjection != null){
            LogingResponseDto logingResponseDto = new LogingResponseDto(
                    userLoginProjection.getName(),
                    userLoginProjection.getUserName(),
                    userLoginProjection.getFullName(),
                    userLoginProjection.getFirstLastName(),
                    userLoginProjection.getSecondLastName(),
                    true, "exitoso");

                    ResponseMessage msj = new ResponseMessage(200, "Usuario logueado Token");
                    msj.setData(logingResponseDto);
                    return msj;
        }else{
            return new ResponseMessage (400 ,"Error de credenciales");
        }
    }

}
