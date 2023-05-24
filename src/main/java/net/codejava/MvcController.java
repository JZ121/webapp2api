package net.codejava;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MvcController {
	private static HttpURLConnection connection; 
	
	@RequestMapping("/")
	public String home() {
		System.out.println("Going home...");
		return "index";
	}
	
	@GetMapping("/register")
	public String showForm(Model model) {
		User user = new User();
		//user.setName("Nam Ha Minh");
		model.addAttribute("user", user);
		
		//List<String> professionList = Arrays.asList("Developer", "Designer", "Tester", "Architect");
		//model.addAttribute("professionList", professionList);
		
		return "register_form";
	}
	
	@PostMapping("/register")
	public String submitForm(@ModelAttribute("user") User user) throws URISyntaxException, IOException {
	//	System.out.println("in success");
	//	System.out.println(user);
	//	HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://postman-echo.com/get")).GET().build();
	//	HttpClient client = new HttpClient();
	//	HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		int i=0;
		try {
			URL url = new URL("https://postman-echo.com/get");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			int status = connection.getResponseCode();
			System.out.println(status);
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null ) {
				responseContent.append(line);
				i++;
				if (i == 3) {
					user.setName(responseContent.append(line).toString());
					break;
					
				}
			}
			reader.close();
			System.out.println(responseContent.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			connection.disconnect();
		}
		
		return "register_success";
	}
}
