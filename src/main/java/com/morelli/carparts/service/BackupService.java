package com.morelli.carparts.service;

import com.morelli.carparts.model.entity.Backup;
import com.morelli.carparts.repository.BackupRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BackupService {

    @Autowired
    private BackupRepository backupRepository;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUser;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.database.name}")
    private String databaseName;

    @Transactional
    public String createDatabaseBackup() throws IOException, InterruptedException {
        String BACKUP_DIRECTORY = "C:/path/to/carparts/backup/";

        File directory = new File(BACKUP_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFilePath = BACKUP_DIRECTORY + "backup_carparts_" + timestamp + ".sql";

        String command = String.format("\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe\" -u %s -p%s %s > %s",
                databaseUser,
                databasePassword,
                databaseName,
                backupFilePath);


        Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", command});
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            Backup backup = new Backup();
            backup.setName("Backup del database " + databaseName);
            backup.setBackupPath(backupFilePath);
            backup.setCreatedAt(timestamp);
            backupRepository.save(backup);
            return "Backup creato con successo: " + backupFilePath;
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorMessage = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                errorMessage.append(line);
            }
            throw new IOException("Errore durante il backup del database: " + errorMessage.toString());
        }
    }

    public Backup getBackup(Long id) {
        return backupRepository.findById(id).orElse(null);
    }

    // Elenco dei backup
    public List<Backup> getAllBackups() {
        return backupRepository.findAll();
    }

    // Cancellazione di un backup
    public void deleteBackup(Long id) {
        backupRepository.deleteById(id);
    }
}
