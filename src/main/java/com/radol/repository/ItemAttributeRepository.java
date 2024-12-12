package com.radol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radol.model.ItemAttribute;


@Repository
public interface ItemAttributeRepository extends JpaRepository<ItemAttribute, Integer> {

    
}