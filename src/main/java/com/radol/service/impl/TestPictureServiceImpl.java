package com.radol.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.radol.model.TestPicture;
import com.radol.repository.TestPictureRepository;
@Service
public class TestPictureServiceImpl {
	
	
	private final TestPictureRepository testPictureRepository;

	public TestPictureServiceImpl(TestPictureRepository testPictureRepository) {
		super();
		this.testPictureRepository = testPictureRepository;
	}
	
	
	public List<TestPicture> findAll(){
		
		
		return testPictureRepository.findAll();
	}
	
	
	public Optional<TestPicture> findById(int id) {
		
		return testPictureRepository.findById(id);
		
		
		
		
	}
	
	
	
	
	
	
	

}
