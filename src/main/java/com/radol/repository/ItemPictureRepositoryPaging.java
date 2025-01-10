package com.radol.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.radol.model.ItemPicture;

@Repository
public interface ItemPictureRepositoryPaging extends PagingAndSortingRepository<ItemPicture, Integer> {
    // Custom query methods can be added here if needed
}