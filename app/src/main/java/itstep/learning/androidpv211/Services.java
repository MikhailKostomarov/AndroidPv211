package itstep.learning.androidpv211;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Services {
    public static String readAllText(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
        byte[] buffer=new byte[8192];
        int len;
        while ((len=inputStream.read(buffer))>0){
            byteBuilder.write(buffer,0,len);
        }
        String charsetName= StandardCharsets.UTF_8.name();
        String data = byteBuilder.toString(charsetName);
        byteBuilder.close();
        return data;
    }
    public static String fetchUrl(String href){
        try{
            URL url=new URL(href);
            InputStream urlStream=url.openStream();
            String data = readAllText(urlStream);
            urlStream.close();
            return data;
        } catch (MalformedURLException ex) {
            Log.d("Services::fetchUrl","MalformedURLException"+ex.getMessage());
        } catch (IOException ex) {
            Log.d("Services::fetchUrl","IOException"+ex.getMessage());
        }
        return null;
    }
}
