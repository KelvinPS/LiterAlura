package br.com.literalura.model;

import br.com.literalura.dto.LivroDTO;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int downloadCount;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Autor> authors = new HashSet<>();

    public Livro() {
    }

    public Livro(LivroDTO bookDTO) {
        this.title = bookDTO.title();
        this.downloadCount = bookDTO.downloadCount();
        this.authors = bookDTO.authors().stream()
                .map(authorDTO -> new Autor(authorDTO.name(), authorDTO.birthYear(), authorDTO.deathYear()))
                .collect(Collectors.toSet());
    }

    public String getTitle() {
        return title;
    }
}
