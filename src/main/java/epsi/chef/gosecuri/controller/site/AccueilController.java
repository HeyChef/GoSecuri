package epsi.chef.gosecuri.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccueilController {
	
	@RequestMapping("/accueil")
	public ModelAndView displayAccueil() {
		ModelAndView mv = new ModelAndView("accueil");
		return mv;
	}
}
