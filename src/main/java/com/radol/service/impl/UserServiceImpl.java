package com.radol.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.radol.dto.UserDTO;
import com.radol.dto.request.UserRequestDTO;
import com.radol.mapper.UserMapper;
import com.radol.model.User;
import com.radol.repository.UserRepository;
import com.radol.repository.UserRepositoryPaging;
import com.radol.service.UserService;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    private final UserRepositoryPaging userRepositoryPaging;
    private final UserMapper userMapper;


    
    
    public UserServiceImpl(UserRepository userRepository, UserRepositoryPaging userRepositoryPaging,
			UserMapper userMapper) {
		super();
		this.userRepository = userRepository;
		this.userRepositoryPaging = userRepositoryPaging;
		this.userMapper = userMapper;
	}

	@Override
    public UserDTO save(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(Integer id, UserDTO dto) {
        User user = userMapper.toEntity(dto);
        user.setUserId(id);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
    @Override
    public UserDTO updateUser(Integer id, UserRequestDTO dto) {
        User user = new User();
        user.setUserId(id);
        user.setUserName(dto.getUserName());
        user.setUserPassword(null);
        user.setUserEmail(dto.getUserEmail());
        user.setUserFirstName(null);
        user.setUserLastName(null);
        user.setRole(dto.getRole());
        user.setStatus(null);
        
        return userMapper.toDTO(userRepository.save(user));
    }
    
    public Page<UserDTO> findAllWithPaging(int page, int size) {
        // Fetch paginated data
    	PageRequest pr = PageRequest.of(page, size);
        Page<User> pagedUsers= userRepositoryPaging.findAll(pr);
        // Convert entities to DTOs and return the Page<ItemDTO>
        return pagedUsers.map(userMapper::toDTO);
    }
}
