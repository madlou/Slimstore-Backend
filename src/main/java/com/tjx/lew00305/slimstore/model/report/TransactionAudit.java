package com.tjx.lew00305.slimstore.model.report;

import lombok.Data;

@Data
public class TransactionAudit {

    Integer store;
    String storeName;
    Integer reg;
    String date;
    String time;
    Integer txn;
    Float txnTotal;
    Float lineTotal;
    Float tenderTotal;
    String check;
    
}
