package com.radol.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.radol.dto.ItemPictureDTO;
import com.radol.dto.request.ItemPictureRequestDTO;
import com.radol.mapper.ItemPictureMapper;
import com.radol.model.ItemPicture;
import com.radol.repository.ItemPictureRepository;
import com.radol.repository.ItemPictureRepositoryPaging;
import com.radol.service.ItemPictureService;

@Service
public class ItemPictureServiceImpl implements ItemPictureService {
    @Autowired
    private final ItemPictureRepository itemPictureRepository;
    private final ItemPictureRepositoryPaging itemPictureRepositoryPaging;
    private final ItemPictureMapper itemPictureMapper;
    
    private final S3Service s3Service;
    

  

   @Value("${aws.s3.bucket.name}")
    private String bucketName;
   

	public ItemPictureServiceImpl(ItemPictureRepository itemPictureRepository,
		ItemPictureRepositoryPaging itemPictureRepositoryPaging, ItemPictureMapper itemPictureMapper,
		S3Service s3Service) {
	super();
	this.itemPictureRepository = itemPictureRepository;
	this.itemPictureRepositoryPaging = itemPictureRepositoryPaging;
	this.itemPictureMapper = itemPictureMapper;
	this.s3Service = s3Service;
}

	@Override
    public ItemPictureDTO save(ItemPictureDTO dto) {
        ItemPicture itemPicture = itemPictureMapper.toEntity(dto);
        return itemPictureMapper.toDTO(itemPictureRepository.save(itemPicture));
    }

    @Override
    public ItemPictureDTO findById(Integer id) {
        return itemPictureRepository.findById(id)
                .map(itemPictureMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("ItemPicture not found"));
    }

    @Override
    public List<ItemPictureDTO> findAll() {
        return itemPictureRepository.findAll().stream()
                .map(itemPicture -> {
                    String presignedUrl = s3Service.generatePresignedUrl(bucketName, itemPicture.getItemPictureMain());
                    return new ItemPicture(itemPicture.getItemPictureId(),presignedUrl,itemPicture.getItemPItemId());
                })
                .map(itemPictureMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItemPictureDTO update(Integer id, ItemPictureDTO dto) {
        ItemPicture itemPicture = itemPictureMapper.toEntity(dto);
        itemPicture.setItemPictureId(id);
        return itemPictureMapper.toDTO(itemPictureRepository.save(itemPicture));
    }

    @Override
    public void deleteById(Integer id) {
        itemPictureRepository.deleteById(id);
    }
    
    @Override
    public ItemPictureDTO updateItemPicture(Integer id, ItemPictureRequestDTO dto) {
        ItemPicture itemPicture = new ItemPicture();
        itemPicture.setItemPictureId(id);
        itemPicture.setItemPictureMain(dto.getItemPictureMain());
        return itemPictureMapper.toDTO(itemPictureRepository.save(itemPicture));
    }
    
    public Page<ItemPictureDTO> findAllWithPaging(int page, int size) {
        // Fetch paginated data
        PageRequest pr = PageRequest.of(page, size);
        Page<ItemPicture> pagedItemPictures = itemPictureRepositoryPaging.findAll(pr);

        // Map entities to DTOs while generating presigned URLs
        return pagedItemPictures.map(itemPicture -> {
            String presignedUrl = s3Service.generatePresignedUrl(bucketName, itemPicture.getItemPictureMain());
            ItemPicture itemPictureWithUrl = new ItemPicture(
                itemPicture.getItemPictureId(),
                presignedUrl,
                itemPicture.getItemPItemId()
            );
            return itemPictureMapper.toDTO(itemPictureWithUrl);
        });
    }
}
