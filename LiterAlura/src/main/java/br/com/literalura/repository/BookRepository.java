package br.com.literalura.repository;

import br.com.literalura.model.Autor;
import br.com.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT DISTINCT a FROM Book b JOIN b.authors a WHERE YEAR(a.birthYear) <= :year")
    List<Autor> findByAuthorsBirthYearLessThanEqual(int year);

    @Query("SELECT b.authors FROM Book b")
    List<Autor> findAllAuthors();

    @Query("SELECT a FROM Book b JOIN b.authors a WHERE lower(a.name) LIKE lower(concat('%', :name, '%'))")
    List<Autor> findByAuthorsName(String name);
}
