package br.com.rsimplicio.pettech.pet_tech.service;

import br.com.rsimplicio.pettech.pet_tech.controller.exception.ControllerNotFoundException;
import br.com.rsimplicio.pettech.pet_tech.dto.ProdutoDTO;
import br.com.rsimplicio.pettech.pet_tech.entities.Produto;
import br.com.rsimplicio.pettech.pet_tech.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Collection<ProdutoDTO> findAll() {
        var produtos = produtoRepository.findAll();
        return produtos
                .stream()
                .map(this::toProdutoDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO findById(UUID id) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Produto não encontrado"));
        return toProdutoDTO(produto);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Produto produto = toProduto(produtoDTO);
        produto = produtoRepository.save(produto);
        return toProdutoDTO(produto);
    }

    public ProdutoDTO update(UUID id, ProdutoDTO produtoDTO) {
        try {
            Produto buscaProduto = produtoRepository.getReferenceById(id);
            buscaProduto.setNome(produtoDTO.nome());
            buscaProduto.setDescricao(produtoDTO.descricao());
            buscaProduto.setPreco(produtoDTO.preco());
            buscaProduto.setUrlDaImagem(produtoDTO.urlDaImagem());
            buscaProduto = produtoRepository.save(buscaProduto);

            return toProdutoDTO(buscaProduto);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Produto não encontrado");
        }
    }

    public void delete(UUID id) {
        produtoRepository.deleteById(id);
    }

    private ProdutoDTO toProdutoDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getUrlDaImagem()
        );
    }

    private Produto toProduto(ProdutoDTO produtoDTO) {
        return new Produto(
                produtoDTO.id(),
                produtoDTO.nome(),
                produtoDTO.descricao(),
                produtoDTO.preco(),
                produtoDTO.urlDaImagem()
        );
    }
}
