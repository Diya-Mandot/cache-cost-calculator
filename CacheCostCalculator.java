import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CacheCostCalculator {
    /**
     * Helper method to compute the ceiling of log base 2 of a number.
     * This is used to calculate the number of bits needed to represent various cache components.
     * 
     * @param n The number to compute log base 2 of
     * @return The ceiling of log base 2 of n
     */
    public static int logBase2(int n) {
        int x = 0;
        long twoToTheXth = 1;
        while (twoToTheXth < n) {
            x++;
            twoToTheXth *= 2;
        }
        return x;
    }

    /**
     * Main entry point for the cache simulator.
     */
    public static void main(String[] args) {
        new CacheCostCalculator().run();
    }

    /**
     * Constructor for the cache simulator.
     * Currently empty but available for future initialization if needed.
     */
    public CacheCostCalculator() {
        // Constructor left empty for potential future initialization needs
    }

    /**
     * Main execution method that handles the simulation process.
     * This method:
     * 1. Gets user input for cache configuration
     * 2. Calculates cache parameters
     * 3. Simulates memory accesses
     * 4. Reports results
     */
    public void run() {
        Scanner userInput = new Scanner(System.in);

        // Get cache type from user
        System.out.println("Cache simulator CS 3810");
        System.out.println("  (D)irect-mapped");
        System.out.println("  (S)et associative");
        System.out.println("  (F)ully associative");
        System.out.print("Enter a letter to select a cache and press enter: ");

        String choice = userInput.next().toUpperCase();

        // Input validation loop
        while (!choice.equals("D") && !choice.equals("S") && !choice.equals("F")) {
            System.out.print("Invalid choice. Please enter D, S, or F: ");
            choice = userInput.next().toUpperCase();
        }

        // Set flags based on cache type selection
        boolean simulateDirectMapped = choice.startsWith("D");
        boolean simulateSetAssociative = choice.startsWith("S");
        boolean simulateFullyAssociative = choice.startsWith("F");

        // Cache configuration parameters
        int blockDataBytes = 0;  // Size of each block in bytes
        int sets = 0;           // Number of sets in the cache
        int setWays = 0;        // Number of ways (blocks) in each set

        // Get block size from user
        System.out.println();
        System.out.print("How many data bytes will be in each cache block? ");
        blockDataBytes = userInput.nextInt();

        // Get additional parameters based on cache type
        if (simulateDirectMapped || simulateSetAssociative) {
            setWays = 1;
            System.out.print("How many sets will there be? ");
            sets = userInput.nextInt();
        }
        if (simulateSetAssociative) {
            System.out.print("How many 'ways' will there be for each set? ");
            setWays = userInput.nextInt();
        }
        if (simulateFullyAssociative) {
            sets = 1;
            System.out.print("How many blocks will be in this fully associative cache? ");
            setWays = userInput.nextInt();
        }

        // Get memory addresses to simulate
        List<Integer> addressList = new ArrayList<>();
        int[] addresses;

        System.out.println("Enter a whitespace-separated list of addresses, type 'done' followed by enter at the end:");
        while (userInput.hasNextInt())
            addressList.add(userInput.nextInt());

        userInput.close();

        // Convert ArrayList to array for processing
        addresses = new int[addressList.size()];
        for (int i = 0; i < addressList.size(); i++)
            addresses[i] = addressList.get(i);

        // Calculate bit allocations for address components
        int offsetBits = logBase2(blockDataBytes);        // Bits needed for block offset
        int rowIndexBits = logBase2(sets);                // Bits needed for set index
        int tagBits = 32 - offsetBits - rowIndexBits;     // Remaining bits used for tag

        // Display bit allocation information
        System.out.println();
        System.out.println("Number of address bits used as offset bits:        " + offsetBits);
        System.out.println("Number of address bits used as row index bits:     " + rowIndexBits);
        System.out.println("Number of address bits used as tag bits:           " + tagBits);
        System.out.println();

        // Calculate storage requirements for each block
        int validBits = 1;                                // Valid bit per block
        int dataBits = blockDataBytes * 8;                // Data storage in bits
        int lruBits = logBase2(setWays);                  // Bits needed for LRU tracking
        int totalBlockStorageBits = validBits + tagBits + dataBits + lruBits;

        // Display block storage information
        System.out.println("Number of valid bits needed in each cache block:   " + validBits);
        System.out.println("Number of tag bits stored in each cache block:     " + tagBits);
        System.out.println("Number of data bits stored in each cache block:    " + dataBits);
        System.out.println("Number of LRU bits needed in each cache block:     " + lruBits);
        System.out.println("Total number of storage bits needed in each block: " + totalBlockStorageBits);
        System.out.println();

        // Calculate total cache storage requirements
        int totalBlocks = sets * setWays;
        int totalCacheStorageBits = totalBlocks * totalBlockStorageBits;

        System.out.println("Total number of blocks in the cache:               " + totalBlocks);
        System.out.println("Total number of storage bits needed for the cache: " + totalCacheStorageBits);
        System.out.println();

        // Initialize cache simulation structures
        int hits = 0, misses = 0;
        List<List<Integer>> cache = new ArrayList<>();
        for (int i = 0; i < sets; i++) {
            cache.add(new ArrayList<>());
        }

        // Simulate memory accesses
        for (int address : addresses) {
            // Extract address components using bit manipulation
            int offset = address & ((1 << offsetBits) - 1);
            int index = (address >> offsetBits) & ((1 << rowIndexBits) - 1);
            int tag = address >> (offsetBits + rowIndexBits);

            // Check cache for hit/miss and update accordingly
            List<Integer> set = cache.get(index);
            if (set.contains(tag)) {
                // Cache hit: Update LRU order by moving accessed block to end
                hits++;
                set.remove((Integer) tag);
                set.add(tag);
            } else {
                // Cache miss: Add new block, removing least recently used if necessary
                misses++;
                if (set.size() >= setWays) {
                    set.remove(0);  // Remove LRU block (front of list)
                }
                set.add(tag);      // Add new block at end (most recently used)
            }
        }

        // Display simulation results
        System.out.println("Accessing the addresses gives the following results:");
        System.out.println("Total number of hits:   " + hits);
        System.out.println("Total number of misses: " + misses);
    }
}