package com.sisgea.sisgea.API.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sisgea.BancoDados.Controllers.AeronaveController;
import com.sisgea.Entidades.Aeronave;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/aeronaves")
public class AeronaveAPI {

    @GetMapping("/{matricula}")
    public Aeronave buscar(@PathVariable String matricula) {
        Aeronave aer = AeronaveController.buscarId(matricula);
        if (aer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeronave não encontrada");
        }
        return aer;
    }

    @GetMapping
    public List<Aeronave> listar() {
        return AeronaveController.listarAeronaves();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Aeronave aer) {
        // Limitações de Cadastro das aeronaves
        // Conflito -> Bloqueia o cadastro
        String conflito_str = "";
        boolean conflito = false;

        //
        // DADOS OBRIGATÓRIOS
        //
        String erro = validarAeronave(aer);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // CONFLITOS
        //
        // Matrícula é única
        if (AeronaveController.buscarId(aer.getMatricula()) != null) {
            conflito = true;
            conflito_str += "Já existe uma aeronave com esta matrícula.\n";
        }

        if (conflito) {
            return ResponseEntity.badRequest().body(Map.of("error", conflito_str));
        }

        //
        // CADASTRO DA AERONAVE
        //
        AeronaveController.salvarAeronave(aer);
        return ResponseEntity.ok(Map.of("status", "sucesso", "matricula", aer.getMatricula()));
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<?> atualizar(@PathVariable String matricula, @RequestBody Aeronave aer) {
        // Busca aeronave existente
        Aeronave existente = AeronaveController.buscarId(matricula);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Aeronave não encontrada"));
        }

        // DADOS OBRIGATÓRIOS
        String erro = validarAeronave(aer);
        if (!erro.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", erro));
        }

        //
        // ATUALIZAÇÃO DA AERONAVE
        //
        aer.setMatricula(matricula);
        AeronaveController.atualizarAeronave(aer);
        return ResponseEntity.ok(Map.of("status", "sucesso", "matricula", aer.getMatricula()));
    }

    @DeleteMapping("/{matricula}")
    public void deletar(@PathVariable String matricula) {
        Aeronave aer = AeronaveController.buscarId(matricula);
        if (aer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeronave não encontrada");
        }
        AeronaveController.deletarAeronave(aer);
    }

    private String validarAeronave(Aeronave aer) {
        String msg = "";
        if (aer.getMatricula() == null || aer.getMatricula().isBlank()) msg += "Matrícula é obrigatória. ";
        if (aer.getModelo() == null || aer.getModelo().isBlank()) msg += "Modelo é obrigatório. ";
        if (aer.getFabricante() == null || aer.getFabricante().isBlank()) msg += "Fabricante é obrigatório.";
        return msg;
    }
}
