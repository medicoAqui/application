

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;


public class HttpConnections {
	
	
	

 //m�todo get
public static String get(String urlString){
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resposta = null;
    try {
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

        reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null){
            buffer.append(line);
        }
        resposta = buffer.toString();
    }catch (Exception e){
        e.printStackTrace();
    }finally {
        if (urlConnection != null){
            urlConnection.disconnect();
        }
        try {
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    return resposta;
  }


public String sendPost(String url, String json) throws MinhaException {

    try {
        // Cria um objeto HttpURLConnection:
        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();

        try {
            // Define que a conex�o pode enviar informa��es e obt�-las de volta:
            request.setDoOutput(true);
            request.setDoInput(true);

            // Define o content-type:
            request.setRequestProperty("Content-Type", "application/json");

            // Define o m�todo da requisi��o:
            request.setRequestMethod("POST");

            // Conecta na URL:
            request.connect();

            // Escreve o objeto JSON usando o OutputStream da requisi��o:
            try (OutputStream outputStream = request.getOutputStream()) {
                outputStream.write(json.getBytes("UTF-8"));
            }

            // Caso voc� queira usar o c�digo HTTP para fazer alguma coisa, descomente esta linha.
            //int response = request.getResponseCode();

            return readResponse(request);
        } finally {
            request.disconnect();
        }
    } catch (IOException ex) {
        throw new MinhaException(ex);
    }
}

private String readResponse(HttpURLConnection request) throws IOException {
    ByteArrayOutputStream os;
    try (InputStream is = request.getInputStream()) {
        os = new ByteArrayOutputStream();
        int b;
        while ((b = is.read()) != -1) {
            os.write(b);
        }
    }
    return new String(os.toByteArray());
}

public static class MinhaException extends Exception {
    private static final long serialVersionUID = 1L;

    public MinhaException(Throwable cause) {
        super(cause);
    }
}


}


//String resposta = HttpConnections.get(" http://rate-exchange-1.appspot.com/currency?from=EUR&to=DKK");
//JSONObject obj = new JSONObject(resposta);
//String to = obj.getString("to");
//Double rate = obj.getDouble("rate");
//String from = obj.getString("from");