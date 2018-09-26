package client;
import java.net.DatagramPacket;

public interface IGremlin {
	// corrupts a packet if the provided probability is met
   DatagramPacket corruptPackets(DatagramPacket packet, float probability);
   int pcktSz = 256;
   //int corruptsLeft = -1;
}