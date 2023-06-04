package com.project.boardgames.repositories;

import com.project.boardgames.entities.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    JwtToken findByToken(String token);
    JwtToken findByUser_Id(@Param("user_id") long userId);

}