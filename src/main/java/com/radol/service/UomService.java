package com.radol.service;

import org.springframework.data.domain.Page;

import com.radol.dto.ManufacturerDTO;
import com.radol.dto.UomDTO;
import com.radol.dto.request.UOMRequestDTO;
import com.radol.model.Uom;

public interface UomService  extends CrudService<UomDTO, Uom, Integer>  {

		
    UomDTO updateUom(Integer id, UOMRequestDTO dto);
    
    public Page<UomDTO> findAllWithPaging(int page, int size);

		   
	}