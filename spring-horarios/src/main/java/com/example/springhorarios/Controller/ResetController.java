package com.example.springhorarios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.springhorarios.SpringHorariosApplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/reset")
public class ResetController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ConfigurableApplicationContext context;

    @DeleteMapping("/database")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> resetDatabase() {
        try {
            // Eliminar todas las tablas de la base de datos
            entityManager.createNativeQuery("DROP TABLE IF EXISTS asignatura, aula, ausencia, franja, grupo, horario, periodo, profesor").executeUpdate();

            // Reiniciar la aplicación
            restartApplication();

            return new ResponseEntity<>("Database reset successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to reset database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para reiniciar la aplicación
    private void restartApplication() {
        Thread restartThread = new Thread(() -> {
            try {
                // Detener la aplicación
                Thread.sleep(1000); // Esperar un segundo antes de reiniciar
                context.close();

                // Reiniciar la aplicación
                SpringApplication.run(SpringHorariosApplication.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        restartThread.setDaemon(false);
        restartThread.start();
    }
}