package com.radol.service;

import org.springframework.data.domain.Page;

import com.radol.dto.ItemDTO;
import com.radol.dto.ManufacturerDTO;
import com.radol.dto.request.ManufacturerRequestDTO;
import com.radol.model.Manufacturer;

public interface ManufacturerService  extends CrudService<ManufacturerDTO, Manufacturer, Integer>  {

    ManufacturerDTO updateManufacturer(Integer id, ManufacturerRequestDTO dto);

	
	public ManufacturerDTO saveManufacturerRequest(ManufacturerRequestDTO dto);

	 public Page<ManufacturerDTO> findAllWithPaging(int page, int size);
}