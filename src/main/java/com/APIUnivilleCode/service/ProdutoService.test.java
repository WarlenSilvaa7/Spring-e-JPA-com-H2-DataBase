package com.APIUnivilleCode.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.APIUnivilleCode.model.Produto;
import com.APIUnivilleCode.repository.ProdutoRepository;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void testGetAllProdutos() {
        // Arrange
        List<Produto> expectedProdutos = Arrays.asList(
                new Produto(), new Produto());
        when(produtoRepository.findAll()).thenReturn(expectedProdutos);

        // Act
        List<Produto> actualProdutos = produtoService.getAllProdutos();

        // Assert
        assertEquals(expectedProdutos, actualProdutos);
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    public void shouldCreateNewProductSuccessfully() {
        // Arrange
        Produto produto = new Produto();
        produto.setNome_produto("Test Product");
        produto.setValor(100);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        // Act
        Produto createdProduto = produtoService.criarProduto(produto);

        // Assert
        assertNotNull(createdProduto);
        assertEquals("Test Product", createdProduto.getNome_produto());
        assertEquals(Integer.valueOf(100), createdProduto.getValor());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void testDeletarProdutoWithNonExistentId() {
        // Arrange
        Long nonExistentId = 999L;
        when(produtoRepository.existsById(nonExistentId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.deletarProduto(nonExistentId);
        });

        assertEquals("Produto com ID " + nonExistentId + " não encontrado.", exception.getMessage());
        verify(produtoRepository, times(1)).existsById(nonExistentId);
        verify(produtoRepository, never()).deleteById(any());
    }

    @Test
    void testAtualizarProdutoWithPartialInformation() {
        // Arrange
        Long productId = 1L;
        Produto existingProduto = new Produto();
        existingProduto.setId(productId);
        existingProduto.setNome_produto("Original Name");
        existingProduto.setValor(100);

        Produto partialUpdate = new Produto();
        partialUpdate.setNome_produto("Updated Name");
        // Note: We're not setting the valor, simulating a partial update

        when(produtoRepository.findById(productId)).thenReturn(Optional.of(existingProduto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Produto updatedProduto = produtoService.atualizarProduto(productId, partialUpdate);

        // Assert
        assertNotNull(updatedProduto);
        assertEquals(productId, updatedProduto.getId());
        assertEquals("Updated Name", updatedProduto.getNome_produto());
        assertEquals(Integer.valueOf(100), updatedProduto.getValor());

        verify(produtoRepository, times(1)).findById(productId);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }
}
