package com.restResource.StockTrader.repository;

import com.restResource.StockTrader.entity.SellTrigger;
import com.restResource.StockTrader.entity.TriggerKey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SellTriggerRepository extends CrudRepository<SellTrigger, TriggerKey> {
    @Query(value=
            "Select * from sell_trigger where user_id = ?1 and stock_symbol=?2 ",
            nativeQuery = true)
    Optional<SellTrigger> findByUserIdAndStockSymbol(String userId, String stockSymbol);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE sell_trigger " +
            "SET stock_amount = sell_trigger.stock_amount + ?2 " +
            "WHERE sell_trigger.user_id = ?1 AND sell_trigger.stock_symbol = ?3 ",
            nativeQuery = true)
    Integer incrementStockAmount(String userId, Integer funds, String stockSymbol);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE sell_trigger " +
            "SET stock_cost = ?2 " +
            "WHERE sell_trigger.user_id = ?1 AND sell_trigger.stock_symbol = ?3 AND sell_trigger.stock_cost IS NULL ",
            nativeQuery = true)
    Integer addCostAmount(String userId, Integer cost, String stockSymbol);
}
