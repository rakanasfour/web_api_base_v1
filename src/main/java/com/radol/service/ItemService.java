package com.radol.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.radol.dto.ItemDTO;
import com.radol.dto.request.ItemRequestDTO;
import com.radol.model.Item;

public interface ItemService  extends CrudService<ItemDTO, Item, Integer>  {
	
	/*
	
	public List<DisplayPageDTO> getDisplayPage(Long itemId);
	*/
    public ItemDTO saveItemRequest(ItemRequestDTO dto);
    
    
	public ItemDTO findItemByIdAdminUOM(Integer id);
	
    ItemDTO updateItem(Integer id, ItemRequestDTO dto);
    
    public Page<ItemDTO> findAllWithPaging(int page, int size);
    
    public List<ItemDTO> searchItemsByName(String name);
    
    public List<ItemDTO> findAllASC(String field);

		   
	}