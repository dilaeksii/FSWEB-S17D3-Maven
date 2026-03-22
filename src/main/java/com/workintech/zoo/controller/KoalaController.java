package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KoalaController {
    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping("/koalas")
    public List<Koala> getKoalas() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/koalas/{id}")
    public Koala getKoalaById(@PathVariable("id") int id) {
        if (id < 0) {
            throw new ZooException("Id must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist. Id: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PutMapping("/koalas/{id}")
    public Koala putKoala(@PathVariable("id") int id, @RequestBody Koala koala) {
        if (id < 0) {
            throw new ZooException("Id must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist. Id: " + id, HttpStatus.NOT_FOUND);
        }
        koalas.put(id, new Koala(id, koala.getName(), koala.getWeight(), koala.getSleepHour(), koala.getName()));
        return koalas.get(id);
    }

    @PostMapping("/koalas")
    public Koala postKoalas(@RequestBody Koala koala) {
        koalas.put(koala.getId(), new Koala(koala.getId(), koala.getName(), koala.getWeight(), koala.getSleepHour(), koala.getName()));
        return koala;
    }

    @DeleteMapping("/koalas/{id}")
    public Koala deleteKoala(@PathVariable("id") int id) {
        if (id < 0) {
            throw new ZooException("Id must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist. Id: " + id, HttpStatus.NOT_FOUND);
        }
        Koala deletedKoala = koalas.get(id);
        koalas.remove(id);
        return deletedKoala;
    }
}
