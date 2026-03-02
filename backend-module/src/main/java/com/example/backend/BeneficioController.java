package com.example.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.TransferenciaDTO;
import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.ejb.BeneficioEjbService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/api/beneficios")
@Tag(name = "Benefícios", description = "API para gerenciamento e transferência de benefícios")
public class BeneficioController {

    @Autowired
    private BeneficioRepository repository;

    @Autowired
    private BeneficioEjbService ejbService;

    @GetMapping
    @Operation(summary = "Listar todos os benefícios")
    public List<Beneficio> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um benefício por ID")
    public ResponseEntity<Beneficio> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo benefício")
    public ResponseEntity<Beneficio> criar(@RequestBody Beneficio beneficio) {
        Beneficio salvo = repository.save(beneficio);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um benefício existente")
    public ResponseEntity<Beneficio> atualizar(@PathVariable Long id, @RequestBody Beneficio beneficio) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        beneficio.setId(id);
        return ResponseEntity.ok(repository.save(beneficio));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um benefício")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transferir")
    @Operation(summary = "Realizar transferência entre benefícios usando EJB")
    public ResponseEntity<String> transferir(@RequestBody TransferenciaDTO dto) {
        try {
            ejbService.transfer(dto.fromId(), dto.toId(), dto.valor());
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar transferência: " + e.getMessage());
        }
    }
}