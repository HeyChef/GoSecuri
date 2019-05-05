package epsi.chef.gosecuri.controller.site;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import epsi.chef.gosecuri.entity.Equipment;
import epsi.chef.gosecuri.entity.User;
import epsi.chef.gosecuri.services.LoginServices;

@Controller
public class AccueilController {

    private LoginServices loginServices = new LoginServices();

    @GetMapping( "/" )
    public ModelAndView displayAccueil( @RequestParam( value = "error", required = false ) Boolean error )
            throws Exception {
        ModelAndView mv = new ModelAndView( "index" );
        if ( error != null ) {
            if ( error ) {
                mv.addObject( "error", " Vous ne correspondez à aucune personne autorisé" );
            }
        }
        return mv;
    }

    @GetMapping( "/saisieMateriel" )
    public ModelAndView displaySaisie( HttpServletRequest request, HttpServletResponse response ) {
        ModelAndView mv = new ModelAndView( "saisie" );
        if ( request.getSession().getAttribute( "user" ) == null ) {
            try {
                response.sendRedirect( request.getContextPath() );
            } catch ( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            User user = (User) request.getSession().getAttribute( "user" );
            List<Equipment> equipments = loginServices.getEquipments( user.getUserId() );
            mv.addObject( "equipments", equipments );
        }
        return mv;
    }

    @RequestMapping( value = "/addEquipment", method = RequestMethod.POST )
    public void addEquipment( HttpServletRequest request, HttpServletResponse response ) {
        try {
            if ( request.getSession().getAttribute( "user" ) == null ) {
                response.sendRedirect( request.getContextPath() );

            } else {
                Map<String, Object> equipments = new HashMap<String, Object>();
                String[] labelList = request.getParameterValues( "label" );
                for ( String label : labelList ) {
                    equipments.put( label, request.getParameter( label ) );
                }
                User user = (User) request.getSession().getAttribute( "user" );
                equipments.values().removeIf( Objects::isNull );
                loginServices.addEquipment( equipments, user.getUserId() );
                request.getSession().invalidate();
                response.sendRedirect( request.getContextPath() );

            }
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping( value = "/login", method = RequestMethod.POST )
    public void login( HttpServletRequest request, HttpServletResponse response ) {
        try {
            String[] value = request.getParameter( "imageInput" ).split( "," );
            User user = loginServices.testLogin( value[1] );
            if ( user.getUserId() != null ) {
                HttpSession session = request.getSession();
                session.setAttribute( "user", user );
                response.sendRedirect( request.getContextPath() + "/saisieMateriel" );
            } else {
                response.sendRedirect( request.getContextPath() + "?error=true" );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
