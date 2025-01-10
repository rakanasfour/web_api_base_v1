package com.radol.service;

import org.springframework.data.domain.Page;

import com.radol.dto.ManufacturerDTO;
import com.radol.dto.UomPictureDTO;
import com.radol.dto.request.UOMPictureRequestDTO;
import com.radol.model.UomPicture;

public interface UomPictureService  extends CrudService<UomPictureDTO, UomPicture, Integer>  {

	
    UomPictureDTO updateUomPicture(Integer id, UOMPictureRequestDTO dto);
    
    public Page<UomPictureDTO> findAllWithPaging(int page, int size);

		

		   
	}