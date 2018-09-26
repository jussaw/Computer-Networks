package server;
import java.net.DatagramPacket;
public class ErrorDetectionImpl implements IErrorDetection {
    // checks the validity of a packet given a hash/checksum
    public boolean detectErrors(DatagramPacket packet) {
        try {
            String data = new String(packet.getData());
            String[] headerLinesAndData = data.split("\r\n\r\n");
            String[] headers = headerLinesAndData[0].split("\r\n");
            String payload = headerLinesAndData[1];
            System.out.println("PAYLOAD: " + payload);
            int checksum = Integer.parseInt(headers[0].split(" ")[1]);
            System.out.println("Parsed checksum: " + checksum);
            int sum = 0;
            for (byte b : payload.getBytes()) {
                sum += (int) b;
            }
            System.out.println("Calculated checksum: " + sum);
            return sum == checksum;
        } catch (Exception e) {
            return false;
        }
    }
}