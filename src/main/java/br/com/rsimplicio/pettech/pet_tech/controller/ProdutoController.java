package br.com.rsimplicio.pettech.pet_tech.controller;

import br.com.rsimplicio.pettech.pet_tech.dto.ProdutoDTO;
import br.com.rsimplicio.pettech.pet_tech.entities.Produto;
import br.com.rsimplicio.pettech.pet_tech.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Collection<ProdutoDTO>> findAll() {
        var produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable UUID id) {
        var produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> save(@RequestBody ProdutoDTO produtoDTO) {
        produtoDTO = produtoService.save(produtoDTO);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> update(
            @PathVariable UUID id,
            @RequestBody ProdutoDTO produtoDTO) {
        produtoDTO = produtoService.update(id, produtoDTO);
        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
