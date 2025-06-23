
package mx.edu.itses.dgh.MetodosNumericos.web;

import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.dgh.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.dgh.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.dgh.MetodosNumericos.services.UnidadIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Slf4j
@Controller
public class Unit2Controller {

    @Autowired
    private UnidadIIService unidadIIService;

    
    @GetMapping("unit2/formbisection")
    public String formBiseccion(Model model) {
        Biseccion bisection = new Biseccion();
        model.addAttribute("bisection", bisection);
        return "unit2/bisection/formbisection";
    }

    
    @PostMapping("/unit2/solvebisection")
    public String solveBiseccion(Biseccion bisection, Model model) {
        var solveBisection = unidadIIService.AlgoritmoBiseccion(bisection);
        log.info("Resultado Bisección: " + solveBisection);
        model.addAttribute("solveBisection", solveBisection);
        return "unit2/bisection/solvebisection";
    }

    
    @GetMapping("/unidad2/puntofijo")
    public String formPuntoFijo(Model model) {
        PuntoFijo puntoFijo = new PuntoFijo();
        model.addAttribute("puntoFijo", puntoFijo);
        return "templates.unit2.puntofijo.formpuntofijo";
    }

    
    @PostMapping("/unidad2/puntofijo/solucion")
    public String solvePuntoFijo(PuntoFijo puntoFijo, Model model) {
        ArrayList<PuntoFijo> resultado = unidadIIService.AlgoritmoPuntoFijo(puntoFijo);
        log.info("Resultado Punto Fijo: " + resultado);
        model.addAttribute("resultado", resultado);
        return "templates.unit2.puntofijo.solvepuntofijo";
    }
}
