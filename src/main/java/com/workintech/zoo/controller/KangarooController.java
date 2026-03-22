package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KangarooController {
    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping("/kangaroos")
    public List<Kangaroo> getKangaroos() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/kangaroos/{id}")
    public Kangaroo getKangarooById(@PathVariable("id") int id) {
        if (id < 0) {
            throw new ZooException("Id must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist. Id: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping("/kangaroos")
    public Kangaroo postKangaroo(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getId() < 0 || kangaroo.getId() == 0) {
            throw new ZooException("Id must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), new Kangaroo(kangaroo.getId(), kangaroo.getName(), kangaroo.getHeight(), kangaroo.getWeight(), kangaroo.getGender(), kangaroo.getIsAggressive()));
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("/kangaroos/{id}")
    public Kangaroo putKangaroo(@PathVariable("id") int id, @RequestBody Kangaroo kangaroo) {
        if (id < 0) {
            throw new ZooException("Id must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist. Id: " + id, HttpStatus.NOT_FOUND);
        }
        kangaroos.put(id, new Kangaroo(id, kangaroo.getName(), kangaroo.getHeight(), kangaroo.getWeight(), kangaroo.getGender(), kangaroo.getIsAggressive()));
        return kangaroos.get(id);
    }

    @DeleteMapping("/kangaroos/{id}")
    public Kangaroo deleteKangaroo(@PathVariable("id") int id) {
        if (id < 0) {
            throw new ZooException("Id must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist. Id: " + id, HttpStatus.NOT_FOUND);
        }
        Kangaroo deletedKangaroo = kangaroos.get(id);
        kangaroos.remove(id);
        return deletedKangaroo;
    }
}
