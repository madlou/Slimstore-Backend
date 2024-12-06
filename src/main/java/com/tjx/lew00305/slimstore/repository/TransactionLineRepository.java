package com.tjx.lew00305.slimstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.tjx.lew00305.slimstore.model.entity.TransactionLine;

public interface TransactionLineRepository extends CrudRepository<TransactionLine, Integer> {
    
}
