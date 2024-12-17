# Cache Cost Calculator

## 📜 Project Overview  
**Cache Cost Calculator** is a Java program that simulates cache memory systems. It calculates and reports memory usage, cache hits, and misses based on user input. The program supports the following cache types:  
1. **Direct-Mapped Cache**  
2. **Set-Associative Cache**  
3. **Fully-Associative Cache**

---

## 🚀 Features  
- Accepts user inputs to define cache configuration:
   - Cache type  
   - Block size  
   - Number of sets and ways  
   - Memory addresses for simulation  
- Calculates:
   - **Offset, Row Index, and Tag Bits** required for addresses  
   - **Block and Cache Storage Requirements**  
   - Total cache hits and misses during memory access simulation  
- Implements **LRU (Least Recently Used)** for Set-Associative and Fully-Associative caches.

---

## 🛠️ Technologies Used  
- **Language**: Java  
- **Tools**: Any IDE that supports Java (e.g., VS Code, IntelliJ IDEA, Eclipse)  

---

## 💻 How to Run the Project  

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Diya-Mandot/cache-cost-calculator.git
   cd cache-cost-calculator

 ## 💻 Sample Output

```
Cache simulator CS 3810
  (D)irect-mapped
  (S)et associative
  (F)ully associative
Enter a letter to select a cache and press enter: D

How many data bytes will be in each cache block? 16
How many sets will there be? 4
Enter a whitespace-separated list of addresses, type 'done' followed by enter at the end:
8 24 32 56 64 done

Number of address bits used as offset bits:        4
Number of address bits used as row index bits:     2
Number of address bits used as tag bits:           26

Number of valid bits needed in each cache block:   1
Number of tag bits stored in each cache block:     26
Number of data bits stored in each cache block:    128
Number of LRU bits needed in each cache block:     0
Total number of storage bits needed in each block: 155

Total number of blocks in the cache:               4
Total number of storage bits needed for the cache: 620

Accessing the addresses gives the following results:
Total number of hits:   2
Total number of misses: 4
```

## 📂 Project Structure
cache-cost-calculator/
├── CacheCostCalculator.java  # Main program file
└── README.md                 # Documentation

