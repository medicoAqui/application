
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
			  JSONObject json2 = new JSONObject();
                          
                          json.put("cpf",142536);
                          json.put("password", "1234567");
                          String eu =  json.toString();
                          
                          json2.put("cpf",121348);
                          json2.put("password", "1234567");
                          json2.put("name","bernard lenin");
                          json2.put("sexo", "masculino");
                          json2.put("email", "bernard@gmail.com");
                          json2.put("telefone","9554587");
                          String cadastro =  json.toString();
                          
                          
            
                          System.out.println("///////////////////////////////////////\n agora o post \n");
                          
            try {
                System.out.println(http.sendPost("http://medicoishere.herokuapp.com/auth/authenticate", json.toString()));
                
                System.out.println("/////////////////////// cadastro///////////////// \n");
                System.out.println(json2.toString());
                System.out.println(http.sendPost("http://medicoishere.herokuapp.com/cliente/add", json2.toString()));

            } catch (HttpConnections.MinhaException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
			   
			
			   
			   
		   }
		

}
