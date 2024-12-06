package com.tjx.lew00305.slimstore.model.report;

import java.math.BigDecimal;

public interface TransactionTenderAggregationInterface {
    
    Integer getStore();
    
    String getStoreName();
    
    String getDate();
    
    Integer getRegister();
    
    String getType();
    
    BigDecimal getValue();
    
}
