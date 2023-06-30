package com.example.jokestestapp.rest;

import com.example.jokestestapp.domain.Joke;
import com.example.jokestestapp.domain.input.JokesInput;
import com.example.jokestestapp.service.JokesService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokesController {
    private JokesService generator;

    public JokesController(JokesService generator) {
        this.generator = generator;
    }

    @GetMapping("/jokes")
    public List<Joke> getJokes(JokesInput input) {
        return generator.getJokes(input.getCount());
    }
}
