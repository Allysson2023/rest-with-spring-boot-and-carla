package br.com.carla.controllers;

import br.com.carla.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreentingController {

    private static final String template = "Hello, %s!";

    private static final String obj = "Carro, %s";

    // Gerar um Id
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "Word") String name){
        return  new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    @RequestMapping("/nova")
    public Greeting gre(@RequestParam(value ="produto", defaultValue = "Valor") String produto ){
        return new Greeting(counter.incrementAndGet(), String.format(obj, produto));
    }
}
