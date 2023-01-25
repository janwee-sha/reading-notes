package chapter2;

import java.util.HashSet;
import java.util.Set;

/**
 * VM Args: -XX:PermSize=6M -XX:MaxPermSize=6M
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        Set<String> set =new HashSet<String>();
        short i =0;
        while (true){
            set.add(String.valueOf(i++).intern());
        }
    }
}
