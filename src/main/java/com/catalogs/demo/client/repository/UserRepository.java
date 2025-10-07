package com.catalogs.demo.client.repository;

import com.catalogs.demo.client.dto.LogingResponseDto;
import com.catalogs.demo.client.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        select 
            u.name, 
            u.user_name, 
            concat(u.name, ' ', u.first_last_name, ' ' , u.second_last_name) as full_name, 
            u.first_last_name,
            u.second_last_name
        from dbo.user u 
        where u.user_name = ?1 
        """, nativeQuery = true)
    LogingResponseDto loging(String userName);

    @Query(value = """
            select 1
            from dbo."user" u
            where u.user_name = ?1 or u.password = ?2
            """, nativeQuery = true)
    Integer vefiriqueUser(String userName, String email);

    @Query(value = """
            select
            	u.password
            from dbo.user u
            where u.user_name = ?1
            """, nativeQuery = true)
    String getUserPassword(String userName);
}
