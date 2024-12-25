package com.APIUnivilleCode.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.APIUnivilleCode.model.Produto;
import com.APIUnivilleCode.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> getAllProdutos() {

        return produtoRepository.findAll();
    }

    public Produto criarProduto(Produto produto) {
        if (produtoRepository.existsById(produto.getId())) {
            throw new RuntimeException("Produto com ID " + produto.getId() + " já existe.");
        }

        if (produto.getNome_produto() == null || produto.getNome_produto().trim().isEmpty()) {
            throw new RuntimeException("Nome do produto não pode ser vazio.");
        }
        if (produto.getValor() == null || produto.getValor() <= 0) {
            throw new RuntimeException("Valor do produto deve ser maior que zero.");
        }
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {

            throw new RuntimeException("Produto com ID " + id + " não encontrado.");
        }
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome_produto(produtoAtualizado.getNome_produto());
            produto.setValor(produtoAtualizado.getValor());
            return produtoRepository.save(produto);
        }).orElseThrow(() -> new RuntimeException("Produto com ID " + id + " não encontrado."));
    }

    public Produto buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto == null) {
            throw new RuntimeException("Produto com ID " + id + " não encontrado.");
        }
        return produto;
    }
}
