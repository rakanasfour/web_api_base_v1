package com.radol.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.radol.model.UomPicture;

@Repository
public interface UomPictureRepositoryPaging extends PagingAndSortingRepository<UomPicture, Integer> {
}