package com.radol.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "packaging")
public class Packaging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packaging_id", columnDefinition = "INT UNSIGNED")
    private Integer packagingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "packaging_type", nullable = false)
    private PackagingType packagingType;
    
    
    

    public enum PackagingType {
        SINGLE,
        TIN,
        BUNDLE, 
        PACK,
        BOX,
        CASE,
        PALLET
    }




	public Packaging(Integer packagingId, PackagingType packagingType) {
		super();
		this.packagingId = packagingId;
		this.packagingType = packagingType;
	}




	public Packaging() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Integer getPackagingId() {
		return packagingId;
	}




	public void setPackagingId(Integer packagingId) {
		this.packagingId = packagingId;
	}




	public PackagingType getPackagingType() {
		return packagingType;
	}




	public void setPackagingType(PackagingType packagingType) {
		this.packagingType = packagingType;
	}
    
    
	
    
}

