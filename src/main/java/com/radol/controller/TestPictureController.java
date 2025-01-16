package com.radol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radol.model.TestPicture;
import com.radol.service.impl.S3Service;
import com.radol.service.impl.TestPictureServiceImpl;

@RestController
@RequestMapping("/api/test-picture")
public class TestPictureController  {

    private final TestPictureServiceImpl testPictureServiceImpl ;

    private final S3Service s3Service;
    
   @Value("${aws.s3.bucket.name}")

    private String bucketName;
    
    



    public TestPictureController(TestPictureServiceImpl testPictureServiceImpl, S3Service s3Service) {
		super();
		this.testPictureServiceImpl = testPictureServiceImpl;
		this.s3Service = s3Service;
	}
    /*
    
 // Get Presigned URL by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<String> getById(@PathVariable int  id) {
        TestPicture testPicture = testPictureServiceImpl.findById(id);

        String filePath = testPicture.getFilePath(); // Get the file path from the database
        String presignedUrl = s3Service.generatePresignedUrl(bucketName, filePath);

        System.out.println("Generated File Path for ID " + id + ": " + filePath);

        return ResponseEntity.ok(presignedUrl);
    }

*/


	@GetMapping("/{encryptedName}")
    public ResponseEntity<String> getPresignedUrl(@PathVariable String encryptedName) {
        String filePath = "uploads/variety_gallery/" + encryptedName; // Adjust based on your storage structure
        String presignedUrl = s3Service.generatePresignedUrl(bucketName, filePath);
        System.out.println("Generated File Path: " + filePath);

        return ResponseEntity.ok(presignedUrl);
    }



	@GetMapping

    public ResponseEntity<List<TestPicture>> getAll() {
        List<TestPicture> testPictures = testPictureServiceImpl.findAll();
        return new ResponseEntity<>(testPictures, HttpStatus.OK);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<List<TestPicture>> getById(@PathVariable Integer id) {
        List<TestPicture> testPictures = testPictureServiceImpl.findAll();
        return new ResponseEntity<>(testPictures, HttpStatus.OK);
    }





   
}
