package com.joseignacio.literalura.repositorio;
import com.joseignacio.literalura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    List<Libro> findByIdioma(String idioma);
}