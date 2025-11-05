package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.ReturnOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {

    @Test
    void process_nullInput_throwsRuntimeException() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE, new BalanceOperation())
        );
        FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);

        assertThrows(RuntimeException.class, () ->
                fruitShopService.process(null));
    }

    @Test
    void process_transactionsEmpty_ok() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE, new BalanceOperation())
        );
        FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);

        List<FruitTransaction> emptyList = List.of();
        assertNotNull(emptyList);
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void process_oneTransaction_ok() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE, new BalanceOperation())
        );
        FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);

        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 20);

        Map<String, Integer> actualStorage = fruitShopService.process(List.of(transaction));

        assertNotNull(actualStorage);
        assertEquals(1, actualStorage.size());
        assertEquals(20, actualStorage.get("banana"));
    }

    @Test
    void process_multipleTransactionsMultipleProducts_ok() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE, new BalanceOperation(),
                        Operation.RETURN, new ReturnOperation())
        );
        FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.RETURN, "apple", 3),
                new FruitTransaction(Operation.BALANCE, "banana", 10)
        );

        Map<String, Integer> actualStorage = fruitShopService.process(transactions);

        assertNotNull(actualStorage);
        assertEquals(2, actualStorage.size());
        assertEquals(10, actualStorage.get("banana"));
        assertEquals(3, actualStorage.get("apple"));
    }

    @Test
    void process_multipleTransactionsOneProduct_ok() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE, new BalanceOperation(),
                        Operation.RETURN, new ReturnOperation())
        );
        FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 10),
                new FruitTransaction(Operation.RETURN, "banana", 3)
        );

        Map<String, Integer> actualStorage = fruitShopService.process(transactions);

        assertNotNull(actualStorage);
        assertEquals(1, actualStorage.size()); // jeden produkt
        assertEquals(13, actualStorage.get("banana"));
    }
}
