package client;
import java.net.DatagramPacket;
import java.util.Comparator;
public class PacketComparator implements Comparator<DatagramPacket> {
	public PacketComparator() {}
	public int compare(DatagramPacket a, DatagramPacket b) {
		return this.getSequenceNumber(a) - this.getSequenceNumber(b);
	}
	public boolean equals(DatagramPacket a, DatagramPacket b) {
		return this.getSequenceNumber(a) == this.getSequenceNumber(b);
	}
	public int getSequenceNumber(DatagramPacket packet) {
		String data = new String(packet.getData());
		return Integer.parseInt(data.split("\r\n\r\n")[0].split("\r\n")[1].split(" ")[2]);
	}
}