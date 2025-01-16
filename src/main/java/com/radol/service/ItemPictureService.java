package com.radol.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.radol.dto.ItemPictureDTO;
import com.radol.dto.request.ItemPictureRequestDTO;
import com.radol.model.ItemPicture;
public interface ItemPictureService  extends CrudService<ItemPictureDTO, ItemPicture, Integer>  {

	

    ItemPictureDTO updateItemPicture(Integer id, ItemPictureRequestDTO dto);
    
	 public Page<ItemPictureDTO> findAllWithPaging(int page, int size);
	 
	 
	 public ItemPictureDTO findByItemId(int id);

	   
}