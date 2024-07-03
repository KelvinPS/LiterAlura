package br.com.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(
                String title,
                Set<AutorDTO> authors,
                Set<String> languages,
                @JsonAlias("download_count") Integer downloadCount) {
}
