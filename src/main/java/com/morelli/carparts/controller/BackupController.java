package com.morelli.carparts.controller;

import com.morelli.carparts.model.entity.Backup;
import com.morelli.carparts.service.BackupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/storage/backup")
@AllArgsConstructor
public class BackupController {

    private BackupService backupService;

    // Creazione di un backup
    @PostMapping("/")
    public ResponseEntity<String> createDatabaseBackup() {
        try {
            String result = backupService.createDatabaseBackup();
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>("Errore durante il backup del database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Recupero di un backup
    @GetMapping("/{id}")
    public ResponseEntity<Backup> getBackup(@PathVariable Long id) {
        Backup backup = backupService.getBackup(id);
        if (backup != null) {
            return new ResponseEntity<>(backup, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Elenco dei backup
    @GetMapping("/")
    public ResponseEntity<List<Backup>> getAllBackups() {
        List<Backup> backups = backupService.getAllBackups();
        return new ResponseEntity<>(backups, HttpStatus.OK);
    }

    // Cancellazione di un backup
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBackup(@PathVariable Long id) {
        backupService.deleteBackup(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
