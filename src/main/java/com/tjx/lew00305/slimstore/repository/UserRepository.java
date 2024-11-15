package com.tjx.lew00305.slimstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.tjx.lew00305.slimstore.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    
    User findByCode(String code);

}
