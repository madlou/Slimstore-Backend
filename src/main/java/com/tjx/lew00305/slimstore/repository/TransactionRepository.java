package com.tjx.lew00305.slimstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.tjx.lew00305.slimstore.model.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

}
