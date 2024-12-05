package com.example.primeiroprojeto.controller;

import com.example.primeiroprojeto.model.Aluno;
import com.example.primeiroprojeto.repository.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlunoController {
    private AlunoRepository alunoRepository;

    public AlunoController(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
        alunoRepository.saveAll(List.of(
                new Aluno("Marcelo","12212121","marcelo@gmail.com"),
                new Aluno("João Vitor","12212121","jv@gmail.com"),
                new Aluno("Matheus","12212121","Matheus@gmail.com"),
                new Aluno("Pedro","12212121","Pedro@gmail.com"),
                new Aluno("Bernardo","12212121","Bernardo@gmail.com")
        ));
    }

    @GetMapping("/alunos")//configurando a endpoint
    Iterable<Aluno> getAlunos()
    {
        return alunoRepository.findAll();
    }

    @GetMapping("/alunos/{id}")//configurando a endpoint
    Optional<Aluno> getAlunos(@PathVariable int id)
    {
        return alunoRepository.findById(id);
    }

    @DeleteMapping("/alunos/{id}")
    ResponseEntity<Aluno> deleteAluno(@PathVariable int id){
        if(alunoRepository.existsById(id)){
            alunoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/alunos")
    ResponseEntity<Aluno> adicionarAluno(@RequestBody Aluno aluno){
        alunoRepository.save(aluno);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/alunos/{id}")
    ResponseEntity<Aluno> updateAluno(@PathVariable int id, @RequestBody Aluno aluno){
        return alunoRepository.findById(id)
                .map(existeAluno -> {
                        aluno.setId(existeAluno.getId());
                        return ResponseEntity.ok(alunoRepository.save(aluno));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(alunoRepository.save(aluno)));    }
}
