package com.example.NexusOS.repository;

import com.example.NexusOS.entity.RefreshToken;
import com.example.NexusOS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepositiry extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
}
