package com.LojadeGames.controller;



import com.LojadeGames.Service.ClienteService;
import com.LojadeGames.model.Clientes;
import com.LojadeGames.model.ClienteLogin;
import com.LojadeGames.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/all")
    public ResponseEntity <List<Clientes>> getAll(){
        return ResponseEntity.ok( clienteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clientes> getId (@PathVariable Long id){
        return clienteRepository.findById(id)
                .map(respota -> ResponseEntity.ok(respota))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/logar")
    public  ResponseEntity<ClienteLogin> login(@Valid @RequestBody Optional<ClienteLogin> clienteLogin){
        return clienteService.autenticarCliente(clienteLogin)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse (ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Clientes> postCliente(@Valid @RequestBody Clientes clientes){
        return clienteService.cadastrarCliente( clientes )
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
                        .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());


    }

    @PutMapping("/atualizar")
    public ResponseEntity<Clientes> putCliente (@Valid @RequestBody Clientes clientes){
        return clienteService.atualizarCliente( clientes )
                .map(respota -> ResponseEntity.status(HttpStatus.OK).body(respota))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
