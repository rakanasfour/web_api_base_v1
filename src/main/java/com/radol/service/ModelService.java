package com.radol.service;

import org.springframework.data.domain.Page;

import com.radol.dto.ManufacturerDTO;
import com.radol.dto.ModelDTO;
import com.radol.dto.request.ModelRequestDTO;
import com.radol.model.Model;

public interface ModelService  extends CrudService<ModelDTO, Model, Integer>  {

	
	public ModelDTO saveModelRequest(ModelRequestDTO dto);
    ModelDTO updateModel(Integer id, ModelRequestDTO dto);
	 public Page<ModelDTO> findAllWithPaging(int page, int size);

	

	   
}