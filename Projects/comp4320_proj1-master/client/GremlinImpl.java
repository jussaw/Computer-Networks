package client;
import java.net.DatagramPacket;
import java.util.Random;

public class GremlinImpl implements IGremlin {
   // corrupts a packet if the provided probability is met
   GremlinImpl() {
      
   }
   public DatagramPacket corruptPackets(DatagramPacket packet, float probability) {
      
      float corruptArr[] = new float[pcktSz];      //arr of packet length
                                                   //keeps track of corrupted bits
      Random rand = new Random();               
      int prob = (int) (probability * 100);          //cast float prob to int
      int n_1 = rand.nextInt(101);            //rand # b/w 0 & usr prob
      DatagramPacket corruptedPacket = packet;
      
      /*
      prob .5 = 1 byte change 
      prob .3 = 2 byte change
      prob .2 = 3 byte change
      
      since .5 + .3 + .2 = 1, we take rand # gen and whatever num we get,
      through a series of if statments, determine which num of bytes to
      change
      .5 = 0-50
      .3 = 51-80
      .2 = 81-100
      */

      if(n_1 >= 0 && n_1 <= prob){                 //if rand is b/w 0-prob
         int n_2 = rand.nextInt((100)+1);         
         if(n_2 <= 50){
            recursCorrupt(corruptArr, 1, packet);  //alt 1 byte in packet
            //corruptedPacket = makeCorruption(packet, 1);
         }
         if(n_2 > 50 && n_2 <= 80 ){
            recursCorrupt(corruptArr, 2, packet);  //alt 2 byte in packet
            //corruptedPacket = makeCorruption(packet, 2);
         }
         if(n_2 > 80 && n_2 <= 100){
            recursCorrupt(corruptArr, 3, packet);  //alt 3 byte in packet
            //corruptedPacket = makeCorruption(packet, 3);
         }
      }                
      return packet;
   }
   public boolean recursCorrupt(float[] cArr, float count, DatagramPacket packet){
      if(count <= 0){                        //base case: when count is 0
         return true;
      }
      else{
         Random rand = new Random();
         int n = rand.nextInt((256));      //rand # gen, picks rand byte
         if(cArr[n] == 0){
            //corrupts the bit////
            byte[] bite = packet.getData();  //gets byte[] from packet
            int bite_ = (int)(bite[n] + 1);  //cast byte to int, + 1
            bite[n] = (byte)bite_;           //sets alt byte in arr
            //////////////////////
            cArr[n] = 1;                     //keeps track of corrupted bytes
            count--;                         //param for base case
         }
      }
      return recursCorrupt(cArr, count, packet);
   }

   public DatagramPacket makeCorruption(DatagramPacket packetIn, int numOfPAckets) {
      Random rand = new Random();
      int iter = numOfPAckets;
      int bitToCorrupt;
      int[] alreadyCorrupted = {500, 500, 500};
      byte[] corruptedBytes = packetIn.getData();
      boolean alreadyCorruptedBool = false;
      DatagramPacket corruptedPacket = packetIn;

      while(iter > 0) {
         alreadyCorruptedBool = false;
         for (int num : alreadyCorrupted) {
            if(num < 500){
               alreadyCorruptedBool = true;
            }
         }

         if(!alreadyCorruptedBool){
            bitToCorrupt = rand.nextInt(256);
            alreadyCorrupted[iter - 1] = bitToCorrupt;
            corruptedBytes[bitToCorrupt]++;
            corruptedPacket.setData(corruptedBytes);
            iter--;
         }
      }
      return corruptedPacket;
   }
}