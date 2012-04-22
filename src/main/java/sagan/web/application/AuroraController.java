package sagan.web.application;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sagan.Converter;
import sagan.model.AuroraData;

@Controller
@RequestMapping("/aurora")
public class AuroraController {

	private Converter converter = new Converter();

	public AuroraController() {
	}

	@RequestMapping(value = "/now")
	public @ResponseBody AuroraData now(HttpServletResponse response) throws IOException {
		return converter.LoadAndConvert();
	}

	@RequestMapping(value="/weather") 
	public void weather(HttpServletResponse response) throws IOException {
		String url = "http://www.spaceweather.gc.ca/apps/conditions/php/ajax/get_current_conditions.php";
		HttpMethod method = new GetMethod(url);

		HttpClient client = new HttpClient();
		client.executeMethod(method);
		
		response.getOutputStream().write(method.getResponseBody());
	}
}
