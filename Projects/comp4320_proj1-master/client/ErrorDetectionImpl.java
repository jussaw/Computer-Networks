package client;
import java.net.DatagramPacket;
public class ErrorDetectionImpl implements IErrorDetection {
    // checks the validity of a packet given a hash/checksum
    ErrorDetectionImpl() {
        this.numberOfCorruptedPackets = 0;
    }
    public int numberOfCorruptedPackets;
    public boolean detectErrors(DatagramPacket packet) {
        try {
            String data = new String(packet.getData());
            String[] headers = data.split("\r\n\r\n")[0].split("\r\n");
            String payload = data.substring(data.indexOf("\r\n\r\n") + 4);
            System.out.println("\nPAYLOAD: " + payload);
            int checksum = Integer.parseInt(headers[0].split(" ")[1]);
            System.out.println("Parsed checksum: " + checksum);
            int sum = 0;
            for (byte b : payload.getBytes()) {
                sum += (int) b;
            }
            System.out.println("Calculated checksum: " + sum);
            if(sum == checksum) {
                return true;
            } else {
                numberOfCorruptedPackets++;
                return false;
            }
        } catch (Exception e) {
            numberOfCorruptedPackets++;
            return false;
        }
    }
}