package com.restResource.StockTrader.repository;

import com.restResource.StockTrader.entity.BuyTrigger;
import com.restResource.StockTrader.entity.TriggerKey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BuyTriggerRepository extends CrudRepository<BuyTrigger, TriggerKey> {
    @Query(value=
            "Select * from buy_trigger where user_id = ?1 and stock_symbol=?2 ",
            nativeQuery = true)
    Optional<BuyTrigger> findByUserIdAndStockSymbol(String userId, String stockSymbol);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE buy_trigger " +
            "SET stock_amount = buy_trigger.stock_amount + ?2 " +
            "WHERE buy_trigger.user_id = ?1 AND buy_trigger.stock_symbol = ?3 ",
            nativeQuery = true)
    Integer incrementStockAmount(String userId, Integer amount, String stockSymbol);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE buy_trigger " +
             "SET stock_cost = ?2 " +
             "WHERE buy_trigger.user_id = ?1 AND buy_trigger.stock_symbol = ?3 AND buy_trigger.stock_cost IS NULL ",
            nativeQuery = true)
    Integer addCostAmount(String userId, Integer amount, String stockSymbol);
}
