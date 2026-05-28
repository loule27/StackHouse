import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptFileManager {

    private static final String RECEIPTS_FOLDER = "receipts";

    public static void saveReceipt(Order order) {
        // Create receipts folder if it doesn't exist
        File folder = new File(RECEIPTS_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = generateFileName();
        File receiptFile = new File(RECEIPTS_FOLDER + "/" + fileName);

        try {
            FileWriter writer = new FileWriter(receiptFile);

            writer.write("============================\n");
            writer.write("       DELI-cious Receipt    \n");
            writer.write("============================\n\n");
            writer.write(order.getOrderDetails());
            writer.write("\n\n============================\n");
            writer.write("     Thank you! Come again!  \n");
            writer.write("============================\n");

            writer.close();
            System.out.println("\n  Receipt saved: " + fileName);

        } catch (IOException e) {
            System.out.println("  Problem saving receipt.");
        }
    }

    public static String generateFileName() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return LocalDateTime.now().format(formatter) + ".txt";
    }
}
