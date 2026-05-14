package com.marcelo.habit_tracker.service;

import com.marcelo.habit_tracker.model.Quote;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuoteServiceTest {

    @Test
    void shouldReturnValidQuoteFromApi() {
        QuoteService service = new QuoteService();

        Quote quote = service.getRandomQuote();

        assertNotNull(quote, "A resposta da API não deve ser nula");
        assertNotNull(quote.getQ(), "A frase não deve ser nula");
        assertFalse(quote.getQ().isBlank(), "A frase não deve ser vazia");
        assertNotNull(quote.getA(), "O autor não deve ser nulo");
    }
}