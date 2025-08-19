package com.joseignacio.literalura;

import com.joseignacio.literalura.dto.RespuestaLibros;
import com.joseignacio.literalura.modelo.Autor;
import com.joseignacio.literalura.modelo.Libro;
import com.joseignacio.literalura.repositorio.AutorRepositorio;
import com.joseignacio.literalura.repositorio.LibroRepositorio;
import com.joseignacio.literalura.servicio.ConsumoAPI;
import com.joseignacio.literalura.servicio.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Optional;
import java.util.Scanner;
import java.util.List;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    --- Bienvenido a LiterAlura,
                    tu catálogo de libros.
                    Opciones:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo(teclado);
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEnAnio(teclado);
                        break;
                    case 5:
                        listarLibrosPorIdioma(teclado);
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        System.exit(0); // Salimos del método run() para detener la aplicación
                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Opción inválida. Por favor, ingrese un número.");
                teclado.nextLine();
                opcion = -1;
            }
        }
        teclado.close();
    }

    private void buscarLibroPorTitulo(Scanner teclado) {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var tituloLibro = teclado.nextLine();


        Optional<Libro> libroExistente = libroRepositorio.findByTituloIgnoreCase(tituloLibro);
        if (libroExistente.isPresent()) {
            System.out.println("El libro '" + tituloLibro + "' ya está registrado en la base de datos.");
            return;
        }

        var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.replace(" ", "%20"));
        var datos = conversor.obtenerDatos(json, RespuestaLibros.class);

        if (datos.resultados().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        } else {
            var libroEncontrado = datos.resultados().get(0);

            Autor autor = null;
            if (!libroEncontrado.autor().isEmpty()) {
                var autorDTO = libroEncontrado.autor().get(0);
                Optional<Autor> autorExistente = autorRepositorio.findByNombre(autorDTO.nombre());
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    Integer añoNacimiento = (autorDTO.añoDeNacimiento() != null) ? Integer.valueOf(autorDTO.añoDeNacimiento()) : null;
                    Integer añoFallecimiento = (autorDTO.añoDeFallecimiento() != null) ? Integer.valueOf(autorDTO.añoDeFallecimiento()) : null;

                    autor = new Autor(autorDTO.nombre(), añoNacimiento, añoFallecimiento);
                    autorRepositorio.save(autor);
                }
            }

            Libro libro = new Libro(libroEncontrado.titulo(), libroEncontrado.idiomas().get(0), libroEncontrado.numeroDeDescargas());
            libro.setAutor(autor);

            libroRepositorio.save(libro);

            System.out.println("Libro guardado con éxito: ");
            System.out.println("--------------------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
            System.out.println("Idiomas: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
            System.out.println("--------------------\n");
        }
    }

    private void listarLibrosRegistrados() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarAutoresVivosEnAnio(Scanner teclado) {
    }

    private void listarLibrosPorIdioma(Scanner teclado) {
    }
}
