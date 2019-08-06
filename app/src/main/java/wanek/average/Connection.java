package wanek.average;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Connection {

    public String url;

    public Connection() {
        this.url = "https://wroadbot.000webhostapp.com/";
    }
    public String queryGET(String url) throws IOException {
        HttpURLConnection connection = null;
        URL obj = null;

        {
            try {
                obj = new URL(this.url + url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        {
            try {
                connection = (HttpURLConnection) obj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        try {
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Host","wroadbot.000webhostapp.com");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
