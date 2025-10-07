package com.catalogs.demo.client.service;

import com.catalogs.demo.client.dto.LogingRequestDto;
import com.catalogs.demo.client.dto.LogingResponseDto;
import com.catalogs.demo.client.dto.UserRegisterDto;
import com.catalogs.demo.client.entity.User;
import com.catalogs.demo.client.repository.UserRepository;
import com.catalogs.demo.response.ResponseMessage;
import com.catalogs.demo.utils.JwtTokenProvider;
import com.catalogs.demo.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
        String safePassword = getUserPasswordEncrypt(logingRequestDto.getUserName());
        boolean equalsPassword = Utils.verifyPassword(logingRequestDto.getPassword(), safePassword);

        if (equalsPassword){
            LogingResponseDto logingResponseDto = userRepository.loging(logingRequestDto.getUserName());
            String token = jwtTokenProvider.generateToken(logingRequestDto.getUserName());

            logingResponseDto.setToken(token);

            return new ResponseMessage(200, "Usuario logueado Token", logingResponseDto);
        }else{
            return new ResponseMessage (400 ,"Error de credenciales");
        }
    }

    private String getUserPasswordEncrypt(String userName){
        return userRepository.getUserPassword(userName);
    }

}
