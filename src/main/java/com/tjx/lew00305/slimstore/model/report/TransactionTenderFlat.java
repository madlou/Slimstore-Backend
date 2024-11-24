package com.tjx.lew00305.slimstore.model.report;

import lombok.Data;

@Data
public class TransactionTenderFlat {

    private Integer store;
    private String storeName;
    private String date;
    private String time;
    private Integer reg;
    private Integer txn;
    private Integer number;
    private String type;
    private Float value;
    private String reference;
    
}