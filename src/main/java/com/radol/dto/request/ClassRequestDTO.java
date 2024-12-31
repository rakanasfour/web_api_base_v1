package com.radol.dto.request;

import java.util.List;

import com.radol.dto.AttributeDTO;

public class ClassRequestDTO {
	
	
	private Integer classId;
	
	private String className;
	
	private List <AttributeDTO> attributeClasses;

	public ClassRequestDTO(Integer classId, String className, List<AttributeDTO> attributeClasses) {
		super();
		this.classId = classId;
		this.className = className;
		this.attributeClasses = attributeClasses;
	}

	public ClassRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<AttributeDTO> getAttributeClasses() {
		return attributeClasses;
	}

	public void setAttributeClasses(List<AttributeDTO> attributeClasses) {
		this.attributeClasses = attributeClasses;
	}

	
	
	

	
	
	
	

}