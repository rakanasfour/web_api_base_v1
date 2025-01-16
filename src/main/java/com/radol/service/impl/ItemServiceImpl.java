package com.radol.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.radol.dto.ItemDTO;
import com.radol.dto.request.ItemRequestDTO;
import com.radol.mapper.ItemMapper;
import com.radol.model.Attribute;
import com.radol.model.Item;
import com.radol.model.ItemAttribute;
import com.radol.model.ItemPicture;
import com.radol.model.ItemSalesCategory;
import com.radol.model.Model;
import com.radol.model.SalesCategory;
import com.radol.repository.AttributeRepository;
import com.radol.repository.DocumentStorageRepository;
import com.radol.repository.ItemAttributeRepository;
import com.radol.repository.ItemRepository;
import com.radol.repository.ItemRepositoryPaging;
import com.radol.repository.ItemSalesCategoryRepository;
import com.radol.repository.ModelRepository;
import com.radol.repository.SalesCategoryRepository;
import com.radol.repository.UomRepository;
import com.radol.service.ItemService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ItemServiceImpl implements ItemService {
	
	
    @Autowired
    private final ItemRepository itemRepository;
    private final ItemAttributeRepository itemAttributeRepository;
    private final ModelRepository modelRepository;
    private final ItemMapper itemMapper;
    private final ItemSalesCategoryRepository itemSalesCategoryRepository;
    private final AttributeRepository attributeRepository;
    private final SalesCategoryRepository salesCategoryRepository;
    private final UomRepository uomRepository;
    private final DocumentStorageRepository documentStorageRepository;
    private final ItemRepositoryPaging itemRepositoryPaging;
    
    private final S3Service s3Service;
    
    @Value("${aws.s3.bucket.name}")
    private  String bucketName;
    



	public ItemServiceImpl(ItemRepository itemRepository, ItemAttributeRepository itemAttributeRepository,
			ModelRepository modelRepository, ItemMapper itemMapper,
			ItemSalesCategoryRepository itemSalesCategoryRepository, AttributeRepository attributeRepository,
			SalesCategoryRepository salesCategoryRepository, UomRepository uomRepository,
			DocumentStorageRepository documentStorageRepository, ItemRepositoryPaging itemRepositoryPaging,
			S3Service s3Service) {
		super();
		this.itemRepository = itemRepository;
		this.itemAttributeRepository = itemAttributeRepository;
		this.modelRepository = modelRepository;
		this.itemMapper = itemMapper;
		this.itemSalesCategoryRepository = itemSalesCategoryRepository;
		this.attributeRepository = attributeRepository;
		this.salesCategoryRepository = salesCategoryRepository;
		this.uomRepository = uomRepository;
		this.documentStorageRepository = documentStorageRepository;
		this.itemRepositoryPaging = itemRepositoryPaging;
		this.s3Service = s3Service;
	}



	@Override
    public ItemDTO save(ItemDTO dto) {
        Item item = itemMapper.toEntity(dto);
        return itemMapper.toDTO(itemRepository.save(item));
    }
    
    

	  @Override
	    public ItemDTO findItemByIdAdminUOM(Integer id) {
	        return itemRepository.findById(id)
	                .map(item -> {
	                    List<ItemPicture> updatedPictures = item.getItemPictures().stream()
	                            .map(itemPicture -> {
	                                String presignedUrl = s3Service.generatePresignedUrl(bucketName, itemPicture.getItemPictureMain());
	                                return new ItemPicture(itemPicture.getItemPictureId(), presignedUrl, itemPicture.getItemPItemId());
	                            })
	                            .collect(Collectors.toList());
	                    item.setItemPictures(updatedPictures);
	                    return item;
	                })
	                .map(itemMapper::toDTO)
	                .orElseThrow(() -> new EntityNotFoundException("Item with ID " + id + " not found"));
	    }
	

    
    
    /*
    .map(itemDTO -> {
        if (itemDTO.getUoms() != null && !itemDTO.getUoms().isEmpty()) {
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (UomDTO uom : itemDTO.getUoms()) {
                BigDecimal uomQuantity = BigDecimal.valueOf(uom.getUomQuantity());
                ManufacturerPricingDTO manufacturerPricing = uom.getManufacturerPricing();

                if (manufacturerPricing != null && manufacturerPricing.getPricingList() != null) {
                    // Multiply uomQuantity by the pricingList field
                    totalPrice = totalPrice.add(uomQuantity.multiply(manufacturerPricing.getPricingList()));
                }
            }

            // Set the total calculated price as the itemBasePrice
            itemDTO.setItemBasePrice(totalPrice);
        }
        return itemDTO;
    })
    */



    @Override
    public List<ItemDTO> findAll() {
        return itemRepository.findAllItemsAdmin().stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDTO update(Integer id, ItemDTO dto) {
        Item item = itemMapper.toEntity(dto);
        item.setItemId(id);
        return itemMapper.toDTO(itemRepository.save(item));
    }

    @Override
    public void deleteById(Integer id) {
        itemRepository.deleteById(id);
    }

    /*
    public List<DisplayPageDTO> getDisplayPage(Long itemId) {
        return itemRepository.getDisplayPage(itemId).stream()
                .map(displayPage -> {
                    // Calculate total price
                    BigDecimal totalPrice = displayPage.getItemBasePrice()
                            .multiply(BigDecimal.valueOf(displayPage.getUomQuantity()));
                    displayPage.setItemBasePrice(totalPrice); // Update the base price
                    
                    
                    // Calculate total weight
                    BigDecimal totalWeight = displayPage.getItemWeight()
                            .multiply(BigDecimal.valueOf(displayPage.getUomQuantity()));
                    displayPage.setItemWeight(totalWeight); // Update the weight

                    return displayPage;
                })
                .collect(Collectors.toList());
                
               
    }

*/
    
 
    public ItemDTO saveItemRequest(ItemRequestDTO dto) {
    	
    	Item item = new Item();
    	item.setItemName(dto.getItemName());
    	item.setItemSku(dto.getItemSku());
    	item.setItemDescription(dto.getItemDescription());
    	item.setItemStatus(dto.getItemStatus());
        Item savedItem = itemRepository.save(item);
        
        // Associate attributes with the saved item
        for (Integer attributeId : dto.getAttributes()) {
            Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new RuntimeException("Attribute not found with ID: " + attributeId));
            ItemAttribute itemAttribute = new ItemAttribute();
            itemAttribute.setMappedItemAttribute(savedItem);
            itemAttribute.setMappedAttribute(attribute);
            itemAttributeRepository.save(itemAttribute);
        }

        
        for (Integer salesCategoryId : dto.getSalesCategories()) {
        	SalesCategory salesCategory = salesCategoryRepository.findById(salesCategoryId)
                .orElseThrow(() -> new RuntimeException("SalesCategory not found with ID: " + salesCategoryId));
        	ItemSalesCategory itemSalesCategory = new ItemSalesCategory();
        	itemSalesCategory.setMappedItemSalesCategory(savedItem);
        	itemSalesCategory.setMappedSalesCategory(salesCategory);
            itemSalesCategoryRepository.save(itemSalesCategory);
        }
        
        
     // Associate the item with a model
        Model model = modelRepository.findById(dto.getModelId())
            .orElseThrow(() -> new RuntimeException("Model not found with ID: " + dto.getModelId()));
        model.getItems().add(savedItem);
        modelRepository.save(model);
        
        item.setModel(model);

        // Map saved entity back to DTO and return
        return itemMapper.toDTO(savedItem);
    }




	@Override
	public ItemDTO findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
    public ItemDTO updateItem(Integer id, ItemRequestDTO dto) {
        Item item = new Item();
        item.setItemId(id);
        item.setItemName(dto.getItemName());
        item.setItemSku(dto.getItemSku());
        item.setItemStatus(dto.getItemStatus());
        item.setItemDescription(dto.getItemDescription());       
        
        
        // Associate the item with a model
        Model model = modelRepository.findById(dto.getModelId())
            .orElseThrow(() -> new RuntimeException("Model not found with ID: " + dto.getModelId()));
        model.getItems().add(item);
        modelRepository.save(model);
        
        item.setModel(model);

        return itemMapper.toDTO(itemRepository.save(item));
    }




    public Page<ItemDTO> findAllWithPaging(int page, int size) {
        // Fetch paginated data
    	
    	PageRequest pr = PageRequest.of(page, size);
        Page<Item> pagedItems = itemRepositoryPaging.findAllItemsAdmin(pr);

        // Convert entities to DTOs and return the Page<ItemDTO>
        return pagedItems.map(itemMapper::toDTO);
    }
    
    public List<ItemDTO> searchItemsByName(String name) {
       
        return itemRepository.findByNameContaining(name).stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<ItemDTO> findAllASC(String field) {
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, field))
                             .stream()
                             .map(itemMapper::toDTO)
                             .collect(Collectors.toList());
    }
	
    
}
