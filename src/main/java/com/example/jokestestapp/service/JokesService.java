package com.example.jokestestapp.service;

import com.example.jokestestapp.domain.Joke;
import java.util.List;

public interface JokesService {
    List<Joke> getJokes(int count);
}
