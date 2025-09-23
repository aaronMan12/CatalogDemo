package com.catalogs.demo.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        select 
            u.user_name, 
            u.name, 
            u.first_last_name,
            u.second_last_name, 
            concat(u.name, ' ', u.first_last_name, ' ' , u.second_last_name) as full_name 
        from dbo.user u 
        where u.user_name = ?1 and u.password = ?2 
        """, nativeQuery = true)
    UserLoginProjection loging(String userName, String password);



}
