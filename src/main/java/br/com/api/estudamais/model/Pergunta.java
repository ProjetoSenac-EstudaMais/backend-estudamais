package br.com.api.estudamais.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Getter
@Setter
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
