package com.example.springhorarios.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Model.Role;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String>{
    Optional<Profesor> findById(String professor_cod);
    Optional<Profesor> findByUsername(String username);
    List<Profesor> findByRol(Role rol);
    Optional<Profesor> findByResetToken(String resetToken);
}
