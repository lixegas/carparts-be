package com.morelli.carparts.repository;


import com.morelli.carparts.model.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    void deleteByFileName(String fileName);
}


