package com.LojadeGames.repository;

import com.LojadeGames.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends  JpaRepository<Clientes, Long> {

    Optional<Clientes> findAllByEmail(String email);



}