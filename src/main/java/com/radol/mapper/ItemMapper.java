package com.radol.mapper;

import org.mapstruct.*;
import com.radol.dto.ItemDTO;
import com.radol.dto.ItemPictureDTO;
import com.radol.model.Item;
import com.radol.model.ItemPicture;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemAttributeMapper.class, ItemSalesCategoryMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    @Mapping(source = "itemId", target = "itemId")
    @Mapping(source = "itemName", target = "itemName")
    @Mapping(source = "itemAttributes", target = "attributes")
    @Mapping(source = "itemSalesCategories", target = "salesCategories")
    @Mapping(source = "uoms", target = "uoms")
    @Mapping(source = "model.modelId", target = "modelId")
    @Mapping(source = "itemPictures",target = "itemPictures" ) 
    ItemDTO toDTO(Item entity);

    @InheritInverseConfiguration
   // @Mapping(target = "itemPictures", ignore = true) // Temporarily ignore itemPictures
    Item toEntity(ItemDTO dto);

    /*
    @Mapping(source = "itemPictureId", target = "itemPictureId")
    @Mapping(source = "itemPictureMain", target = "itemPictureMain")
    ItemPictureDTO toItemPictureDTO(ItemPicture itemPicture);
    
    */
}

