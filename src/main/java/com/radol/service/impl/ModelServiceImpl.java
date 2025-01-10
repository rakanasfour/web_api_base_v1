package com.radol.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.radol.dto.ModelDTO;
import com.radol.dto.request.ModelRequestDTO;
import com.radol.mapper.ModelMapper;
import com.radol.model.Brand;
import com.radol.model.Model;
import com.radol.repository.BrandRepository;
import com.radol.repository.ModelRepository;
import com.radol.repository.ModelRepositoryPaging;
import com.radol.service.ModelService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private final ModelRepository modelRepository;
    private final ModelRepositoryPaging modelRepositoryPaging;
    private final ModelMapper modelMapper;
    private BrandRepository brandRepository;
    

    

	public ModelServiceImpl(ModelRepository modelRepository, ModelRepositoryPaging modelRepositoryPaging,
			ModelMapper modelMapper, BrandRepository brandRepository) {
		super();
		this.modelRepository = modelRepository;
		this.modelRepositoryPaging = modelRepositoryPaging;
		this.modelMapper = modelMapper;
		this.brandRepository = brandRepository;
	}

	@Override
    public ModelDTO save(ModelDTO dto) {
        Model model = modelMapper.toEntity(dto);
        return modelMapper.toDTO(modelRepository.save(model));
    }

    @Override
    public ModelDTO findById(Integer id) {
        return modelRepository.findById(id)
                .map(modelMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Model not found"));
    }

    @Override
    public List<ModelDTO> findAll() {
        return modelRepository.findAll().stream()
                .map(modelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModelDTO update(Integer id, ModelDTO dto) {
        Model model = modelMapper.toEntity(dto);
        model.setModelId(id);
        return modelMapper.toDTO(modelRepository.save(model));
    }

    @Override
    public void deleteById(Integer id) {
        modelRepository.deleteById(id);
    }

	@Override
	public ModelDTO saveModelRequest(ModelRequestDTO dto) {
	    // Fetch the Brand entity
	    Brand brand = brandRepository.findById(dto.getBrandId())
	            .orElseThrow(() -> new EntityNotFoundException("Brand not found with ID: " + dto.getBrandId()));

	    // Create and save the Model entity
	    Model model = new Model();
	    model.setModelName(dto.getModelName());
	    model.setModelDescription(dto.getModelDescription());
	    model.setBrand(brand);
	    modelRepository.save(model);

	    // Convert and return the DTO
	    return modelMapper.toDTO(model);
	}
	
	@Override
    public ModelDTO updateModel(Integer id, ModelRequestDTO dto) {
	    Brand brand = brandRepository.findById(dto.getBrandId())
	            .orElseThrow(() -> new EntityNotFoundException("Brand not found with ID: " + dto.getBrandId()));
		 
        Model modelEntity = new Model();
        modelEntity.setModelId(id);
        modelEntity.setModelName(dto.getModelName());
        modelEntity.setModelDescription(dto.getModelDescription());
        modelEntity.setBrand(brand);
        return modelMapper.toDTO(modelRepository.save(modelEntity));
    }
	
	
	
    public Page<ModelDTO> findAllWithPaging(int page, int size) {
        // Fetch paginated data
    	PageRequest pr = PageRequest.of(page, size);
        Page<Model> pagedModels = modelRepositoryPaging.findAll(pr);
        // Convert entities to DTOs and return the Page<ItemDTO>
        return pagedModels.map(modelMapper::toDTO);
    }
	
	
}
