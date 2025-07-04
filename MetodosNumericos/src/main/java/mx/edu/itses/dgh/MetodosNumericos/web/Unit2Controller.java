package mx.edu.itses.dgh.MetodosNumericos.web;

import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.dgh.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.dgh.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.dgh.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.dgh.MetodosNumericos.services.UnidadIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class Unit2Controller {
//crear nuevo controller dentro unit2controller//
    @Autowired
    private UnidadIIService unidadIIsrv;
    
    @GetMapping("/unit2")
    public String index(Model model){
        return "unit2/index";
    }

    // FORMULARIO BISECCIÓN
    @GetMapping("/unit2/formbisection")
    public String formBiseccion(Model model) {
        Biseccion bisection = new Biseccion();
        model.addAttribute("bisection", bisection);
        return "unit2/bisection/formbisection";
    }

    // SOLUCIÓN BISECCIÓN
    @PostMapping("/unit2/solvebisection")
    public String solveBiseccion(Biseccion bisection, Model model) {
        var solveBisection = unidadIIsrv.AlgoritmoBiseccion(bisection);
        log.info("Resultado Bisección: " + solveBisection);
        model.addAttribute("solveBisection", solveBisection);
        return "unit2/bisection/solvebisection";
    }
    
    // FORMULARIO PUNTO FIJO
    @GetMapping("/unit2/formpuntofijo")
    public String formPuntoFijo(Model model) {
        PuntoFijo puntoFijo = new PuntoFijo();
        model.addAttribute("puntoFijo", puntoFijo);
        return "unit2/puntofijo/formpuntofijo";
    }

// SOLUCIÓN PUNTO FIJO
    @PostMapping("/unit2/solvepuntofijo")
    public String solvePuntoFijo(PuntoFijo puntoFijo, Model model) {
        var solvePuntoFijo = unidadIIsrv.AlgoritmoPuntoFijo(puntoFijo);
        log.info("Resultado Punto Fijo: " + solvePuntoFijo);
        model.addAttribute("solvePuntoFijo", solvePuntoFijo);
        return "unit2/puntofijo/solvepuntofijo";
    }
    
    // FORMULARIO NEWTON-RAPHSON
    @GetMapping("/unit2/formnewtonraphson")
    public String formNewtonRaphson(Model model) {
        NewtonRaphson newtonRaphson = new NewtonRaphson();
        model.addAttribute("newtonRaphson", newtonRaphson);
        return "unit2/newtonraphson/formnewtonraphson";
    }

// SOLUCIÓN NEWTON-RAPHSON
    @PostMapping("/unit2/solvenewtonraphson")
    public String solveNewtonRaphson(NewtonRaphson newtonRaphson, Model model) {
        var solveNewtonRaphson = unidadIIsrv.AlgoritmoNewtonRaphson(newtonRaphson);
        log.info("Resultado Newton-Raphson: " + solveNewtonRaphson);
        model.addAttribute("solveNewtonRaphson", solveNewtonRaphson);
        return "unit2/newtonraphson/solvenewtonraphson";
    }
    
    @GetMapping("/unit2/formsecante")
public String formSecante(Model model) {
    model.addAttribute("secante", new mx.edu.itses.dgh.MetodosNumericos.domain.Secante());
    return "unit2/secante/formsecante";
}

@PostMapping("/unit2/solvesecante")
public String solveSecante(mx.edu.itses.dgh.MetodosNumericos.domain.Secante secante, Model model) {
    var result = unidadIIsrv.AlgoritmoSecante(secante);
    model.addAttribute("resultSecante", result);
    return "unit2/secante/solvesecante";
}

@GetMapping("/unit2/formsecantemodificado")
public String formSecanteModificado(Model model) {
    model.addAttribute("secanteMod", new mx.edu.itses.dgh.MetodosNumericos.domain.SecanteModificado());
    return "unit2/secantemodificado/formsecantemodificado";
}

@PostMapping("/unit2/solvesecantemodificado")
public String solveSecanteModificado(mx.edu.itses.dgh.MetodosNumericos.domain.SecanteModificado secanteMod, Model model) {
    var result = unidadIIsrv.AlgoritmoSecanteModificado(secanteMod);
    model.addAttribute("resultSecanteMod", result);
    return "unit2/secantemodificado/solvesecantemodificado";
}

     
}
