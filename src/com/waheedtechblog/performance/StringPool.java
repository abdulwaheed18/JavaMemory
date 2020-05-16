package com.waheedtechblog.performance;

import java.util.ArrayList;
import java.util.Date;

/**
 * Performance Improvement : StringPool Statistics
 * Explains the behaviour of StringPool and a bit of its internal working and how can we increase our application performance
 * by twisting VM arguments like -XX:+PrintStringTableStatistics -XX:StringTableSize=120121
 *
 * @Author AbdulWaheed18@gmail.com
 */
public class StringPool {

    public static void main(String[] args) {

        Date startTime = new Date();

        /**
         * Case 1 : Stats without having any iteration
         *
         * Case 2: Adding 530000 String Literals
         * Default bucket size of String is 65536 and we are adding more unique string than the present bucket.
         * So, It will add multiples entries to single bucket as we have already seen in HashMap
         * As per final Stats, we can see that the Maximum bucket size is 14
         * therefore to find string in StringPool will be O(14), total StringPool is close to 35MB and
         * execution time is 303ms
         *
         * Case 3: Increasing String Default  bucket size to 120121 using -XX:StringTableSize params
         * It will reduce the execution time from 303ms to 242ms
         * Average bucket size from 4 to 2
         * and O(n) from 14 to 8
         */
        ArrayList<String> arrayList = new ArrayList<>();
        //Minimize max iteration if you don't below stats on your machine
        for (Integer i = 0; i < 530000; i++) {
            arrayList.add(i.toString().intern());
        }
        Date endTime = new Date();
        System.out.println("Total Time taken : " + (endTime.getTime() - startTime.getTime()) + "ms");
    }
}

/**
 * Case 1:
 * Pass -XX:+PrintStringTableStatistics as VM arguments
 * Output with empty main method
 * =============================================================
 * SymbolTable statistics:
 * Number of buckets       :     32768 =    262144 bytes, each 8
 * Number of entries       :       686 =     10976 bytes, each 16
 * Number of literals      :       686 =     27640 bytes, avg  40.000
 * Total footprint         :           =    300760 bytes
 * Average bucket size     :     0.021
 * Variance of bucket size :     0.021
 * Std. dev. of bucket size:     0.144
 * Maximum bucket size     :         2
 * StringTable statistics:
 * Number of buckets       :     65536 =    524288 bytes, each 8
 * Number of entries       :      2766 =     44256 bytes, each 16
 * Number of literals      :      2766 =    187576 bytes, avg  67.000
 * Total footprint         :           =    756120 bytes
 * Average bucket size     :     0.042
 * Variance of bucket size :     0.042
 * Std. dev. of bucket size:     0.205
 * Maximum bucket size     :         2
 * ==============================================================
 * <p>
 * <p>
 * <p>
 * Case 2:
 * "C:\Program Files\Java\jdk-14.0.1\bin\java.exe" -XX:+PrintStringTableStatistics "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.1.4\lib\idea_rt.jar=61069:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.1.4\bin" -Dfile.encoding=UTF-8 -classpath D:\Workspaces\IntelliJ\JavaMemory\out\production\ExploringJavaMemory com.waheedtechblog.performance.StringPool
 * Having above shown main method
 * =============================================================
 Total Time taken : 303ms
 SymbolTable statistics:
 Number of buckets       :     32768 =    262144 bytes, each 8
 Number of entries       :      1470 =     23520 bytes, each 16
 Number of literals      :      1470 =     54928 bytes, avg  37.000
 Total footprint         :           =    340592 bytes
 Average bucket size     :     0.045
 Variance of bucket size :     0.045
 Std. dev. of bucket size:     0.211
 Maximum bucket size     :         2
 StringTable statistics:
 Number of buckets       :    131072 =   1048576 bytes, each 8
 Number of entries       :    532882 =   8526112 bytes, each 16
 Number of literals      :    532882 =  25634456 bytes, avg  48.000
 Total footprint         :           =  35209144 bytes
 Average bucket size     :     4.066
 Variance of bucket size :     7.803
 Std. dev. of bucket size:     2.793
 Maximum bucket size     :        14
 * =========================================================================
 *
 * Case 3: Updating String bucket size to 120121 by setting VM args : -XX:+PrintStringTableStatistics -XX:StringTableSize=120121
 * ==============
 * "C:\Program Files\Java\jdk-14.0.1\bin\java.exe" -XX:+PrintStringTableStatistics -XX:StringTableSize=120121 "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.1.4\lib\idea_rt.jar=61303:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.1.4\bin" -Dfile.encoding=UTF-8 -classpath D:\Workspaces\IntelliJ\JavaMemory\out\production\ExploringJavaMemory com.waheedtechblog.performance.StringPool
 * Total Time taken : 242ms
 * SymbolTable statistics:
 * Number of buckets       :     32768 =    262144 bytes, each 8
 * Number of entries       :      1470 =     23520 bytes, each 16
 * Number of literals      :      1470 =     54928 bytes, avg  37.000
 * Total footprint         :           =    340592 bytes
 * Average bucket size     :     0.045
 * Variance of bucket size :     0.045
 * Std. dev. of bucket size:     0.211
 * Maximum bucket size     :         2
 * StringTable statistics:
 * Number of buckets       :    262144 =   2097152 bytes, each 8
 * Number of entries       :    532882 =   8526112 bytes, each 16
 * Number of literals      :    532882 =  25634456 bytes, avg  48.000
 * Total footprint         :           =  36257720 bytes
 * Average bucket size     :     2.033
 * Variance of bucket size :     2.381
 * Std. dev. of bucket size:     1.543
 * Maximum bucket size     :         8
 *
 * Process finished with exit code 0
 * ===========================================================================
 *
 */
