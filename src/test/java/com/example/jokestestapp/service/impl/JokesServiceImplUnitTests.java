package com.example.jokestestapp.service.impl;

import com.example.jokestestapp.domain.Joke;
import com.example.jokestestapp.integration.JokeGeneratorClient;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JokesServiceImplUnitTests {

    private ExecutorService executorService = mock(ExecutorService.class);
    private JokeGeneratorClient generatorClient = mock(JokeGeneratorClient.class);

    private JokesServiceImpl jokesService = new JokesServiceImpl(generatorClient, executorService);

    @Test
    public void shouldReturnNothingIfInputCountIs0() {
        List<Joke> result = jokesService.getJokes(0);
        assertThat(result).isEmpty();
        verify(generatorClient, never()).getJokeFromAPI();
    }

    @Test
    public void shouldThrowExceptionIfInputCountIsMoreThan100() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> jokesService.getJokes(101));
        verify(generatorClient, never()).getJokeFromAPI();
    }

    @Test
    public void shouldReturnJokes() throws Exception {
        Joke joke1 = new Joke("general", "joke1", "joke1", "1");
        Joke joke2 = new Joke("general", "joke2", "joke2", "2");

        when(generatorClient.getJokeFromAPI()).thenReturn(joke1, joke2);

        Future<Joke> futureMock1 = mock(Future.class);
        Future<Joke> futureMock2 = mock(Future.class);
        
        when(futureMock1.get()).thenReturn(joke1);
        when(futureMock2.get()).thenReturn(joke2);

        when(executorService.submit(any(Callable.class))).thenReturn(futureMock1, futureMock2);

        List<Joke> result = jokesService.getJokes(2);
        assertThat(result).isNotEmpty().contains(joke1, joke2);
    }
}
