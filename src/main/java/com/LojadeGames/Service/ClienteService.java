package com.LojadeGames.Service;

import com.LojadeGames.model.Clientes;
import com.LojadeGames.model.ClienteLogin;
import com.LojadeGames.repository.ClienteRepository;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Optional;
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Clientes> cadastrarCliente(Clientes clientes){

        if(clienteRepository.findAllByEmail( clientes.getEmail()).isPresent())
            return Optional.empty();

        clientes.setSenha( criptografarSenha( clientes.getSenha() ) );

        return Optional.of( clienteRepository.save( clientes ));
    }

    public Optional<Clientes> atualizarCliente(Clientes clientes) {

        if(clienteRepository.findAllByEmail( clientes.getEmail()).isPresent() ) {

            Optional<Clientes> buscaCliente = clienteRepository.findAllByEmail( clientes.getEmail() );

            if ((buscaCliente.isPresent()) && ( buscaCliente.get().getId() != clientes.getId()))
                throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Usuário já existe", null );

            clientes.setSenha(criptografarSenha(clientes.getSenha()));
            return Optional.ofNullable( clienteRepository.save( clientes ) );

        }
        return Optional.empty();
    }


    public Optional<ClienteLogin> autenticarCliente(Optional<ClienteLogin> clienteLogin) {

        Optional<Clientes> clientes = clienteRepository.findAllByEmail( clienteLogin.get().getEmail() );

        if (clientes.isPresent()) {
            if (compararSenhas(clienteLogin.get().getSenha(), clientes.get().getSenha())) {

                clienteLogin.get().setId(clientes.get().getId());
                clienteLogin.get().setEmail(clientes.get().getEmail());
                clienteLogin.get().setToken(gerarBasicToken(clienteLogin.get().getEmail(),
                clienteLogin.get().getSenha()));
                clienteLogin.get().setSenha(clientes.get().getSenha());

                return clienteLogin;

            }
        }

        return Optional.empty();

    }

    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String clientes, String senha) {

        String token = clientes + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }


    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }

}