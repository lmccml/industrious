package concurrent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommonTest {
    public static void main(String[] args) throws Exception{
        BufferedReader burd_second = new BufferedReader(new InputStreamReader(System.in));

        String cmd = null;
        while ((cmd = burd_second.readLine()) != null){
            System.out.println(cmd);

        }
    }

}
