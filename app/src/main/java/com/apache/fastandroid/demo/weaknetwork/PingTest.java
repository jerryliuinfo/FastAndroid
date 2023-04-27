package com.apache.fastandroid.demo.weaknetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jerry on 2023/3/31.
 */
public class PingTest implements INetworkTest {
    @Override
    public boolean testNetwork() {
        String url = "www.baidu.com";

        boolean result = false;
        try {
            Process process = Runtime.getRuntime().exec("ping -c 5 " + url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            System.out.println(stringBuilder);

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Ping test succeeded.");
                result = true;
            } else {
                System.out.println("Ping test failed with error code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;

    }
}
