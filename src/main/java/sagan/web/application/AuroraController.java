package sagan.web.application;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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

}
