import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.BitSet;

public class UniqueIPCounter {

    private static final long BITSET_SIZE = 1L << 32;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // change the filePath to the location of the file containing the IP addresses
        String filePath = "PATH/TO/FILE";

        long uniqueIpCount = countUniqueIPs(filePath);

        long end = System.currentTimeMillis();

        System.out.println("Number of unique IP addresses: " + uniqueIpCount);
        System.out.println("Time taken: " + (end - start) + "ms");
    }

    public static long countUniqueIPs(String filePath){
        BitSet posBitSet = new BitSet((int) BITSET_SIZE);
        BitSet negBitSet = new BitSet((int) BITSET_SIZE);

        long uniqueCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int ip = ipToInt(line.trim());
                if (ip >= 0) {
                    if (!posBitSet.get(ip)) {
                        posBitSet.set(ip);
                        uniqueCount++;
                    }
                } else {
                    ip = -ip;
                    if (!negBitSet.get(ip)) {
                        negBitSet.set(ip);
                        uniqueCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniqueCount;
    }

    private static int ipToInt(String ipAddress) {
        int ip = 0;
        try {
            ip = InetAddress.getByName(ipAddress).hashCode();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}