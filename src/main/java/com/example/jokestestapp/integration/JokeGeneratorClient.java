package com.example.jokestestapp.integration;

import com.example.jokestestapp.domain.Joke;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "official-joke-api.appspot.com", url = "https://official-joke-api.appspot.com")
public interface JokeGeneratorClient {
    @GetMapping("/random_joke")
    Joke getJokeFromAPI();
}


