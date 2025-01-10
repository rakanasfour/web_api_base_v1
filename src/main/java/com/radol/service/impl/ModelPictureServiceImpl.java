package com.radol.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.radol.dto.ModelPictureDTO;
import com.radol.mapper.ModelPictureMapper;
import com.radol.model.ModelPicture;
import com.radol.repository.ModelPictureRepository;
import com.radol.repository.ModelPictureRepositoryPaging;
import com.radol.service.ModelPictureService;

@Service
public class ModelPictureServiceImpl implements ModelPictureService {
    @Autowired
    private final ModelPictureRepository modelPictureRepository;
    private final ModelPictureRepositoryPaging modelPictureRepositoryPaging;
    private final ModelPictureMapper modelPictureMapper;


  

    public ModelPictureServiceImpl(ModelPictureRepository modelPictureRepository,
			ModelPictureRepositoryPaging modelPictureRepositoryPaging, ModelPictureMapper modelPictureMapper) {
		super();
		this.modelPictureRepository = modelPictureRepository;
		this.modelPictureRepositoryPaging = modelPictureRepositoryPaging;
		this.modelPictureMapper = modelPictureMapper;
	}

	@Override
    public ModelPictureDTO save(ModelPictureDTO dto) {
        ModelPicture modelPicture = modelPictureMapper.toEntity(dto);
        return modelPictureMapper.toDTO(modelPictureRepository.save(modelPicture));
    }

    @Override
    public ModelPictureDTO findById(Integer id) {
        return modelPictureRepository.findById(id)
                .map(modelPictureMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("ModelPicture not found"));
    }

    @Override
    public List<ModelPictureDTO> findAll() {
        return modelPictureRepository.findAll().stream()
                .map(modelPictureMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModelPictureDTO update(Integer id, ModelPictureDTO dto) {
        ModelPicture modelPicture = modelPictureMapper.toEntity(dto);
        modelPicture.setModelPictureId(id);
        return modelPictureMapper.toDTO(modelPictureRepository.save(modelPicture));
    }

    @Override
    public void deleteById(Integer id) {
        modelPictureRepository.deleteById(id);
    }
    
    public Page<ModelPictureDTO> findAllWithPaging(int page, int size) {
        // Fetch paginated data
    	PageRequest pr = PageRequest.of(page, size);
        Page<ModelPicture> pagedManufacturers = modelPictureRepositoryPaging.findAll(pr);
        // Convert entities to DTOs and return the Page<ItemDTO>
        return pagedManufacturers.map(modelPictureMapper::toDTO);
    }

    
    
}