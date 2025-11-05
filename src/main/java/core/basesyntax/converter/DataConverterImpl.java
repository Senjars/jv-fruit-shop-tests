package core.basesyntax.converter;

import core.basesyntax.db.Operation;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convert(List<String> lines) {
        if (lines.isEmpty()) {
            throw new RuntimeException();
        }

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] oper = lines.get(i).split(",");

            if (!(oper[0].equals("p") || oper[0].equals("b")
                    || oper[0].equals("r") || oper[0].equals("s"))) {
                throw new RuntimeException("Invalid operation");
            }

            if (oper.length < 3) {
                throw new RuntimeException("Invalid operation");
            }

            if (Integer.parseInt(oper[2]) < 0) {
                throw new RuntimeException("Invalid quantity");
            }

            Operation operation1 = Operation.formCode(oper[0]);
            String fruit = oper[1];
            int quantity = Integer.parseInt(oper[2]);

            fruitTransactionList.add(new FruitTransaction(operation1, fruit, quantity));
        }
        return fruitTransactionList;
    }
}
