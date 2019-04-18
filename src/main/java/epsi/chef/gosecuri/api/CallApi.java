package epsi.chef.gosecuri.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CallApi {

	private final String key = "7f8cfeb3e86be9b97b65";
	private final String id = "9a9bf18a9af4c4c9bdb6";

	public void request() {
		try {
			URL url = new URL("http://www.facexapi.com/compare_faces");
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("face_det", "1");

			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			out.flush();
			out.close();

			con.setRequestProperty("user_id", id);
			con.setRequestProperty("user_key", key);

			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);

			int status = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
