package org.example.Benchmarking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BenchMarkCSV {

    private static final Logger log = LoggerFactory.getLogger(BenchMarkCSV.class);

    private final String filePath;
    private PrintWriter writer;

    public BenchMarkCSV(String filePath) {
        this.filePath = filePath;
    }


    public void open() throws IOException {
        writer = new PrintWriter(new FileWriter(filePath, false));
        writer.println(
                "operation,distribution,structure," +
                        "run1_ms,run2_ms,run3_ms,run4_ms,run5_ms," +
                        "mean_ms,median_ms,stddev_ms"
        );
        log.info("CSV file opened: {}", filePath);
    }


    public void close() {
        if (writer != null) {
            writer.flush();
            writer.close();
            log.info("CSV file closed: {}", filePath);
        }
    }

    public void writeSection(String operation, String distribution,
                             long[] rbtTimes, long[] bstTimes) {
        log.debug("Writing section: {} / {}", operation, distribution);
        writeRow(operation, distribution, "RBT", rbtTimes);
        writeRow(operation, distribution, "BST", bstTimes);
    }


    public void writeSort(String distribution,
                          long[] rbtTimes, long[] bstTimes, long[] qsTimes) {
        log.debug("Writing SORT section: {}", distribution);
        writeRow("SORT", distribution, "RBT",       rbtTimes);
        writeRow("SORT", distribution, "BST",       bstTimes);
        writeRow("SORT", distribution, "QuickSort", qsTimes);
    }

    private void writeRow(String operation, String distribution,
                          String structure, long[] nsTimesRaw) {
        double[] stats = BenchMarkData.makeAnalsis(nsTimesRaw); // [mean, median, stddev] in ns

        double mean   = toMs(stats[0]);
        double median = toMs(stats[1]);
        double stddev = toMs(stats[2]);

        // individual runs in ms (nsTimesRaw is already the 5 warm-up-stripped samples)
        double r1 = toMs(nsTimesRaw[0]);
        double r2 = toMs(nsTimesRaw[1]);
        double r3 = toMs(nsTimesRaw[2]);
        double r4 = toMs(nsTimesRaw[3]);
        double r5 = toMs(nsTimesRaw[4]);

        writer.printf("%s,%s,%s,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f%n",
                operation, distribution, structure,
                r1, r2, r3, r4, r5,
                mean, median, stddev);

        log.debug("{}/{}/{} → mean={:.3f}ms median={:.3f}ms sd={:.3f}ms",
                operation, distribution, structure, mean, median, stddev);
    }

    // ---------------------------------------------------------------
    private static double toMs(double nanos) {
        return nanos / 1_000_000.0;
    }

    public static String timestampedPath(String dir) {
        String ts = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return dir + "/benchmark_" + ts + ".csv";
    }
}