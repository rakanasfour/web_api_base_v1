package com.radol.service;

import org.springframework.data.domain.Page;

import com.radol.dto.ManufacturerDTO;
import com.radol.dto.UserDTO;
import com.radol.dto.request.UserRequestDTO;
import com.radol.model.User;

public interface UserService  extends CrudService<UserDTO, User, Integer>  {

		

    UserDTO updateUser(Integer id, UserRequestDTO dto);
    

	 public Page<UserDTO> findAllWithPaging(int page, int size);

	}