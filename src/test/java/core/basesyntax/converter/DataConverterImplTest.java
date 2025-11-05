package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Operation;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convert_nullInput_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () ->
                dataConverter.convert(null));
    }

    @Test
    void convert_invalidLine_throwsRuntimeException() {
        List<String> list = List.of("operation,fruit,quantity", "invalid", "list");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convert(list));
    }

    @Test
    void convert_validList_ok() {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,12");
        List<FruitTransaction> result = dataConverter.convert(list);

        assertNotNull(result);
        assertEquals(1, result.size());

        FruitTransaction actual = result.get(0);
        assertEquals("banana", actual.getProduct());
        assertEquals(12, actual.getQuantity());
        assertEquals(Operation.BALANCE, actual.getOperation());
    }

    @Test
    void convert_emptyList_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () ->
                dataConverter.convert(List.of()));
    }

    @Test
    void convert_invalidLineLength_throwsRuntimeException() {
        List<String> list = List.of("operation,fruit,quantity", "p,banana");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convert(list));
    }

    @Test
    void convert_invalidQuantity_throwsRuntimeException() {
        List<String> list = List.of("operation,fruit,quantity", "p,apple,-23");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convert(list));
    }

    @Test
    void convert_invalidOperation_throwsRuntimeException() {
        List<String> list = List.of("operation,fruit,quantity", "x,banana,23");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convert(list));
    }

    @Test
    void convert_multipleValidLines_ok() {
        List<String> list = List.of("operation,fruit,quantity", "p,banana,12", "b,apple,4");
        List<FruitTransaction> actual = dataConverter.convert(list);

        assertEquals(2, actual.size());

        assertEquals("banana", actual.get(0).getProduct());
        assertEquals(12, actual.get(0).getQuantity());
        assertEquals(Operation.PURCHASE, actual.get(0).getOperation());

        assertEquals("apple", actual.get(1).getProduct());
        assertEquals(4, actual.get(1).getQuantity());
        assertEquals(Operation.BALANCE, actual.get(1).getOperation());
    }
}
