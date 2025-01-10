package com.radol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radol.model.TestPicture;
@Repository
public interface TestPictureRepository extends JpaRepository<TestPicture, Integer> {

}
