package com.tjx.lew00305.slimstore.model.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTender {

    @Id
    @GeneratedValue()
    private int id;
    @JoinColumn(name = "transaction.id")
    private int transactionId;
    private int number;
    private String type;
    private float value;
    private String reference;

}
