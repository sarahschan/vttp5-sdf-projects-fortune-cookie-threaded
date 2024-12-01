package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    
    public List<String> getCookieList(File cookieFile) throws IOException{

        // Open reader
        FileReader fr = new FileReader(cookieFile);
        BufferedReader br = new BufferedReader(fr);
        
        // Create cookieList ArrayList to store lines read from file
        List<String> cookieList = new ArrayList<>();
        String line = "";

        // Read lines and add to ArrrayList
        while ((line = br.readLine()) != null) {
            cookieList.add(line);
        }

        br.close();

        return cookieList;

    }


    public String getFortune(List<String> cookieList) {
        
        // Get random index number
        Random random = new Random();
        int yourFortuneIndex = random.nextInt(cookieList.size());
        String yourFortune = cookieList.get(yourFortuneIndex);
        
        return yourFortune;
    }
}
