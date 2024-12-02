package com.tjx.lew00305.slimstore.model.report;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionAudit {

    Integer store;
    String storeName;
    Integer reg;
    String date;
    String time;
    Integer txn;
    BigDecimal txnTotal;
    BigDecimal lineTotal;
    BigDecimal tenderTotal;
    String check;
    
}
