package epsi.chef.gosecuri.controller.site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import epsi.chef.gosecuri.entity.Materiel;

@Controller
public class AccueilController {

	@GetMapping("/")
	public ModelAndView displayAccueil() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@GetMapping("/saisieMateriel")
	public ModelAndView displaySaisie() {
		ModelAndView mv = new ModelAndView("saisie");
		Materiel materiel1 = new Materiel(Long.valueOf(1), "Mousqueton");
		Materiel materiel2 = new Materiel(Long.valueOf(2), "Gant d'intervention");
		Materiel materiel3 = new Materiel(Long.valueOf(3), "Ceinture de sécurite tactique");
		Materiel materiel4 = new Materiel(Long.valueOf(3), "Détecteur de métaux");
		Materiel materiel5 = new Materiel(Long.valueOf(4), "Brassard de sécurité");
		Materiel materiel6 = new Materiel(Long.valueOf(5), "Lampe de poche");
		Materiel materiel7 = new Materiel(Long.valueOf(6), "Gilet par balle");

		List<Materiel> matList = new ArrayList<>();
		matList.add(materiel1);
		matList.add(materiel2);
		matList.add(materiel3);
		matList.add(materiel4);
		matList.add(materiel5);
		matList.add(materiel6);
		matList.add(materiel7);
		mv.addObject("matList",matList);
		return mv;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate(); 
		try {
			response.sendRedirect(request.getContextPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + "/saisieMateriel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
