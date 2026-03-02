package com.example.backend.ejb;

import com.example.backend.model.Beneficio;
import com.example.ejb.BeneficioEjbService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioEjbServiceTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private BeneficioEjbService ejbService;

    private Beneficio contaOrigem;
    private Beneficio contaDestino;

    @BeforeEach
    void setUp() {
        contaOrigem = new Beneficio();
        contaOrigem.setId(1L);
        contaOrigem.setValor(new BigDecimal("1000.00"));

        contaDestino = new Beneficio();
        contaDestino.setId(2L);
        contaDestino.setValor(new BigDecimal("500.00"));
    }

    @Test
    @DisplayName("Deve transferir o valor com sucesso e subtrair/adicionar os saldos")
    void deveTransferirComSucesso() {
        when(em.find(Beneficio.class, 1L)).thenReturn(contaOrigem);
        when(em.find(Beneficio.class, 2L)).thenReturn(contaDestino);

        ejbService.transfer(1L, 2L, new BigDecimal("200.00"));

        assertEquals(new BigDecimal("800.00"), contaOrigem.getValor());
        assertEquals(new BigDecimal("700.00"), contaDestino.getValor());
        verify(em, times(1)).merge(contaOrigem);
        verify(em, times(1)).merge(contaDestino);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o saldo for insuficiente e não alterar o banco")
    void deveLancarExcecaoQuandoSaldoInsuficiente() {
        when(em.find(Beneficio.class, 1L)).thenReturn(contaOrigem);
        when(em.find(Beneficio.class, 2L)).thenReturn(contaDestino);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            ejbService.transfer(1L, 2L, new BigDecimal("1500.00"));
        });

        assertEquals("Saldo insuficiente para transferência na conta de origem.", exception.getMessage());
        verify(em, never()).merge(any());
    }

    @Test
    @DisplayName("Deve lançar exceção para valores de transferência negativos ou zero")
    void deveLancarExcecaoParaValorInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ejbService.transfer(1L, 2L, BigDecimal.ZERO);
        });

        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());
    }
}