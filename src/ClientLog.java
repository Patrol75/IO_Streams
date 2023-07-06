import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ClientLog {
    private List<int[]> list = new ArrayList();

    public void log(int productNum, int amount) {
        list.add(new int[]{productNum, amount});

    }

    public void exportAsCSV(FileWriter file) {
        try (CSVWriter writer = new CSVWriter(file)) {
            for (int[] x : list) {
                String[] contactArrayForCsv = new String[x.length];
                for (int i = 0; i < x.length; i++) {
                    contactArrayForCsv[i] = Integer.toString(x[i]);
                }
                writer.writeNext(contactArrayForCsv);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
