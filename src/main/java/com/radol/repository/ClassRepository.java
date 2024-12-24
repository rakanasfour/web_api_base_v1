package com.radol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radol.model.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {
	
}
