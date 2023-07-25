package halq.misericordia.fun.core.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Halq
 * @since 24/07/2023 at 19:09
 */

public class MainMenuBackground {

    static String urel = "https://pastebin.com/raw/rubRdBqR";

    public static String getBackgroundUrl() throws Exception {
        URL url = new URL(urel);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            return line;
        }
        return null;
    }
}
