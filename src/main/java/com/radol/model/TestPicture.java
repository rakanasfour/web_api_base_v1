package com.radol.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_picture")
public class TestPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Column(name = "original_name", length = 500)
    private String originalName;
    
    
    
    @Column(name = "encrypted_name", length = 500)
    private String encryptedName;
    
    
    @Column(name = "filePath", length = 500)
    private String filePath;


	public TestPicture(Integer id, String originalName, String encryptedName, String filePath) {
		super();
		this.id = id;
		this.originalName = originalName;
		this.encryptedName = encryptedName;
		this.filePath = filePath;
	}


	public TestPicture() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getOriginalName() {
		return originalName;
	}


	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}


	public String getEncryptedName() {
		return encryptedName;
	}


	public void setEncryptedName(String encryptedName) {
		this.encryptedName = encryptedName;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
   
    
    

  
}