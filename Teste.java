
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;






import org.json.*;
public class Teste {
	
	private static HttpConnections http;

		   public static void main(String[] args) throws JSONException{
		     
			   http = new HttpConnections();
			   http.get("http://medicoishere.herokuapp.com/cliente/clientes");
			   String resposta =  http.get("http://medicoishere.herokuapp.com/cliente/clientes");
			   System.out.println(resposta);
			  JSONObject json = new JSONObject();
                          
                          json.put("cpf",142536);
                          json.put("password", "1234567");
                          String eu =  json.toString();
            
                          System.out.println("///////////////////////////////////////\n agora o post \n");
                          
            try {
                System.out.println(http.sendPost("http://medicoishere.herokuapp.com/auth/authenticate", json.toString()));
            } catch (HttpConnections.MinhaException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
			   
			
			   
			   
		   }
		

}
