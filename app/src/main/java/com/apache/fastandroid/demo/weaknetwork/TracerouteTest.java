package com.apache.fastandroid.demo.weaknetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jerry on 2023/3/31.
 */
public class TracerouteTest implements INetworkTest{
    @Override
    public boolean testNetwork() {
        String url = "www.baidu.com";
        boolean result = false;

        try {
//            traceroute命令需要root或sudo权限才能正常工作。
            Process process = Runtime.getRuntime().exec("traceroute " + url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            System.out.println(stringBuilder.toString());

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Traceroute test succeeded.");
                result = true;
            } else {
                System.out.println("Traceroute test failed with error code: " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
