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
import com.radol.dto.request.ItemRequestDTO;
import com.radol.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController implements CrudController<ItemDTO, Integer> {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ItemDTO>> getAll() {
        List<ItemDTO> items = itemService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
   
    
    
    @GetMapping("/all")
    public ResponseEntity<Page<ItemDTO>> getAllPaging(@RequestParam int page, @RequestParam int size){
    	    Page<ItemDTO> items = itemService.findAllWithPaging(page,size);
    	    return new ResponseEntity<>(items, HttpStatus.OK);
    	}
  

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ItemDTO> getById(@PathVariable Integer id) {
        ItemDTO item = itemService.findItemByIdAdminUOM(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    

    


    @PostMapping
    @Override
    public ResponseEntity<ItemDTO> create(@RequestBody ItemDTO dto) {
        ItemDTO item = itemService.save(dto);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ItemDTO> update(@PathVariable Integer id, @RequestBody ItemDTO dto) {
        ItemDTO item = itemService.update(id, dto);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        itemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/request")
    public ResponseEntity<ItemDTO> sendRequest(@RequestBody ItemRequestDTO dto) {
        ItemDTO item = itemService.saveItemRequest(dto);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Integer id, @RequestBody ItemRequestDTO dto) {
        ItemDTO itemDTO = itemService.updateItem(id, dto);
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ItemDTO>> searchItems(@RequestParam("name") String name) {
    	  List<ItemDTO> itemDTO = itemService.searchItemsByName(name);

        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }
    
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<ItemDTO>> findAllASC(@PathVariable String field) {
        List<ItemDTO> items = itemService.findAllASC( field);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
   

}
