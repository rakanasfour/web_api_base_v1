package com.radol.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.radol.model.ModelPicture;

@Repository
public interface ModelPictureRepositoryPaging extends PagingAndSortingRepository<ModelPicture, Integer> {
    // Custom query methods can be added here if needed
}