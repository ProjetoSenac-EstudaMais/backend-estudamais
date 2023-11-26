package br.com.api.estudamais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.estudamais.model.Comentario;
import br.com.api.estudamais.service.ComentarioService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/posts/{postId}/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<Comentario>> obterComentariosPorPostId(@PathVariable Long postId) {
        List<Comentario> comentarios = comentarioService.obterComentariosPorPostId(postId);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping
    public ResponseEntity<Comentario> criarComentario(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody Comentario comentario) {
        Comentario novoComentario = comentarioService.criarComentario(postId, userId, comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoComentario);
    }
}

