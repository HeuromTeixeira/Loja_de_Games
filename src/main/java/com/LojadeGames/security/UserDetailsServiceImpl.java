package com.LojadeGames.security;

import com.LojadeGames.model.Clientes;
import com.LojadeGames.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<Clientes> clientes = clienteRepository.findAllByEmail(userName);

        if (clientes.isPresent())
            return new UserDetailsImpl(clientes.get());
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
