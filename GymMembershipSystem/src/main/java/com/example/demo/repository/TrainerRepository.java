package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.*;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Trainer findByEmailAndPassword(String email, String password);
}