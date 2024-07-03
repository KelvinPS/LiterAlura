package br.com.literalura.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    private LocalDate anoAniversario;
    private LocalDate AnoMorte;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Livro> livro = new HashSet<>();

    public Autor() {
    }

    public Autor(String name, String birthYear, String deathYear) {
        this.nome = name;
        try {
            this.anoAniversario = LocalDate.parse(birthYear);
            this.AnoMorte = LocalDate.parse(deathYear);
        } catch (DateTimeParseException ex) {
            this.anoAniversario = null;
            this.AnoMorte = null;
        }
    }

    @Override
    public String toString() {
        String livroStr = livro.stream().map(Livro::getTitle).collect(Collectors.joining(", "));

        return "Author{" +
                "name='" + nome + '\'' +
                ", birthYear=" + anoAniversario +
                ", deathYear=" + AnoMorte +
                ", books=" + livroStr +
                '}';
    }

    public String getNome() {
        return nome;
    }
}
