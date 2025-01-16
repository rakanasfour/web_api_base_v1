package com.radol.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.radol.dto.ItemDTO;
import com.radol.dto.ItemPictureDTO;
import com.radol.dto.ManufacturerDTO;
import com.radol.dto.request.ItemPictureRequestDTO;
import com.radol.service.ItemPictureService;

@RestController
@RequestMapping("/api/item-pictures")
public class ItemPictureController implements CrudController<ItemPictureDTO, Integer> {

    private final ItemPictureService itemPictureService;

    public ItemPictureController(ItemPictureService itemPictureService) {
        this.itemPictureService = itemPictureService;
    }

    
    @GetMapping
    @Override
    public ResponseEntity<List<ItemPictureDTO>> getAll() {
        List<ItemPictureDTO> itemPictures = itemPictureService.findAll();
        return new ResponseEntity<>(itemPictures, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<Page<ItemPictureDTO>> getAllPaging(@RequestParam int page, @RequestParam int size){
    	    Page<ItemPictureDTO> itemPictures = itemPictureService.findAllWithPaging(page,size);
    	    return new ResponseEntity<>(itemPictures, HttpStatus.OK);
    	}

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ItemPictureDTO> getById(@PathVariable Integer id) {
        ItemPictureDTO itemPicture = itemPictureService.findById(id);
        return new ResponseEntity<>(itemPicture, HttpStatus.OK);
    }
    

    @PostMapping
    @Override
    public ResponseEntity<ItemPictureDTO> create(@RequestBody ItemPictureDTO dto) {
        ItemPictureDTO itemPicture = itemPictureService.save(dto);
        return new ResponseEntity<>(itemPicture, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ItemPictureDTO> update(@PathVariable Integer id, @RequestBody ItemPictureDTO dto) {
        ItemPictureDTO itemPicture = itemPictureService.update(id, dto);
        return new ResponseEntity<>(itemPicture, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        itemPictureService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ItemPictureDTO> updateItemPicture(@PathVariable Integer id, @RequestBody ItemPictureRequestDTO dto) {
        ItemPictureDTO itemPictureDTO = itemPictureService.updateItemPicture(id, dto);
        return new ResponseEntity<>(itemPictureDTO, HttpStatus.OK);
    }
    
    @GetMapping("/item/{id}")

    public ResponseEntity<ItemPictureDTO> getByItemId(@PathVariable int id) {
    	ItemPictureDTO itemPicture = itemPictureService.findByItemId(id);
        return new ResponseEntity<>(itemPicture, HttpStatus.OK);
    }
}
