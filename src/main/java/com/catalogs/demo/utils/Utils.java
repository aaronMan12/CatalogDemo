package com.catalogs.demo.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static List<Map<String, String>> getErrors(BindingResult bindingResult){
        return  bindingResult.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("field", error.getField());
                    errorMap.put("message", error.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());
    }

    public static String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String plaintPassword, String encryptPassword){
        return BCrypt.checkpw(plaintPassword, encryptPassword);
    }
}
