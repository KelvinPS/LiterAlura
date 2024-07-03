package br.com.literalura;

import br.com.literalura.dto.LivroDTO;
import br.com.literalura.model.Autor;
import br.com.literalura.model.Livro;
import br.com.literalura.repository.BookRepository;
import br.com.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	private GutendexService gutendexService;

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int opcao = -1;

		while (opcao != 0) {
			menu();
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
				case 1:
					encontraLivroPorTitulo(scanner);
					break;

				case 2:
					listarLivros();
					break;

				case 3:
					listarAutores();
					break;

				case 4:
					buscarAutorPorNome(scanner);
					break;

				case 5:
					listarAutorPorAnoVivido(scanner);
					break;

				case 0:
					System.out.println("Saindo...");
					break;

				default:
					System.out.println("Opção inválida :(");
			}
		}

		scanner.close();
	}

	public void menu() {
		System.out.println("1 - Buscar livro pelo título");
		System.out.println("2 - Listar livros salvos");
		System.out.println("3 - Listar autores salvos");
		System.out.println("4 - Buscar autor pelo nome");
		System.out.println("5 - Listar autores vivos em determinado ano");
		System.out.println("0 - Sair");
		System.out.println("Escolha uma opção:");
	}

	private void encontraLivroPorTitulo(Scanner scanner) {
		System.out.println("Digite o título do livro:");
		String title = scanner.nextLine();

		try {
			List<LivroDTO> books = gutendexService.searchBooks(title);

			if (books.isEmpty()) {
				System.out.println("Nenhum livro encontrado");
			} else {
				for (int i = 0; i < books.size(); i++) {
					System.out.println(i + " - " + books.get(i).title());
				}

				System.out.println("Digite o número do livro que deseja salvar:");
				int index = scanner.nextInt();
				scanner.nextLine();

				Livro book = new Livro(books.get(index));
				bookRepository.save(book);
				System.out.println("Livro salvo com sucesso!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void listarLivros() {
		List<Livro> books = bookRepository.findAll();

		if (books.isEmpty()) {
			System.out.println("Nenhum livro salvo");
		} else {
			System.out.println("Lista de livros salvos:");
			for (Livro book : books) {
				System.out.println("- " + book.getTitle());
			}
		}
	}

	public void listarAutores() {
		List<Autor> authors = bookRepository.findAllAuthors();

		if (authors.isEmpty()) {
			System.out.println("Nenhum autor salvo");
		} else {
			System.out.println("Lista de autores salvos:");
			for (Autor author : authors) {
				System.out.println("- " + author.getNome());
			}
		}
	}

	public void buscarAutorPorNome(Scanner scanner) {
		System.out.println("Digite o nome do autor:");
		String name = scanner.nextLine();

		List<Autor> authors = bookRepository.findByAuthorsName(name);

		if (authors.isEmpty()) {
			System.out.println("Nenhum autor encontrado com esse nome");
		} else {
			System.out.println("Autores encontrados:");
			for (Autor author : authors) {
				System.out.println("- " + author.getNome());
			}
		}
	}

	public void listarAutorPorAnoVivido(Scanner scanner) {
		System.out.println("Digite o ano:");
		int year = scanner.nextInt();
		scanner.nextLine();

		List<Autor> authors = bookRepository.findByAuthorsBirthYearLessThanEqual(year);

		if (authors.isEmpty()) {
			System.out.println("Nenhum autor encontrado vivo em " + year);
		} else {
			System.out.println("Autores vivos em " + year + ":");
			for (Autor author : authors) {
				System.out.println("- " + author.getNome());
			}
		}
	}
}
