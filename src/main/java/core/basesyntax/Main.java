package core.basesyntax;

import core.basesyntax.converter.DataConverter;
import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.db.Operation;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] arg) {
        String pathFile = "reportToRead.csv";
        CsvReader reader = new CsvReaderImpl();
        List<String> lines = reader.read(pathFile);

        DataConverter converter = new DataConverterImpl();
        final List<FruitTransaction> transaction = converter.convert(lines);

        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperation());
        handlers.put(Operation.PURCHASE, new PurchaseOperation());
        handlers.put(Operation.RETURN, new ReturnOperation());
        handlers.put(Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitShopService.process(transaction);

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.generateReport(Storage.getStorage());

        ReportWriter reportWriter = new ReportWriterImpl();
        reportWriter.writeReport(report, "report.txt");
    }
}
