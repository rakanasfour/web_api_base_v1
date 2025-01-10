package com.radol.service;

import org.springframework.data.domain.Page;

import com.radol.dto.ManufacturerDTO;
import com.radol.dto.ModelPictureDTO;
import com.radol.model.ModelPicture;

public interface ModelPictureService  extends CrudService<ModelPictureDTO, ModelPicture, Integer>  {

	
	 public Page<ModelPictureDTO> findAllWithPaging(int page, int size);

	   
}