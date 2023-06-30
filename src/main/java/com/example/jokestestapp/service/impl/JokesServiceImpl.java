package com.example.jokestestapp.service.impl;

import com.example.jokestestapp.domain.Joke;
import com.example.jokestestapp.integration.JokeGeneratorClient;
import com.example.jokestestapp.service.JokesService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class JokesServiceImpl implements JokesService {

    private final JokeGeneratorClient generatorClient;
    private final ExecutorService executorService;

    public JokesServiceImpl(JokeGeneratorClient generatorClient, ExecutorService executorService) {
        this.generatorClient = generatorClient;
        this.executorService = executorService;
    }

    @Override
    public List<Joke> getJokes(int count) {
        if (count > 100) {
            throw new IllegalArgumentException("You can get 100 jokes at most per one request");
        }

        List<Joke> result = new ArrayList<>();

        while (count > 0) {
            int pathSize = count / 10 >= 1 ? 10 : count % 10;
            result.addAll(getJokesPatch(pathSize));
            count -= pathSize;
        }
        return result;
    }

    private List<Joke> getJokesPatch(int count) {
        List<Future<Joke>> futures = IntStream.range(0, count)
                .mapToObj((i) -> executorService.submit(generatorClient::getJokeFromAPI))
                .toList();
        
        return futures.stream().map((f) -> {
            try {
                return f.get();
            } catch (Exception e) {
                throw new ServerErrorException("Failed to get jokes due to: ", e);
            }
        }).toList();
    }
}
