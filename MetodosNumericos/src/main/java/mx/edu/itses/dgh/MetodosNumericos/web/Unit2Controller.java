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

import java.util.ArrayList;

@Slf4j
@Controller
public class Unit2Controller {

    @Autowired
    private UnidadIIService unidadIIService;

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
        var solveBisection = unidadIIService.AlgoritmoBiseccion(bisection);
        log.info("Resultado Bisección: " + solveBisection);
        model.addAttribute("solveBisection", solveBisection);
        return "unit2/bisection/solvebisection";
    }

    // FORMULARIO PUNTO FIJO
    @GetMapping("/unidad2/puntofijo")
    public String formPuntoFijo(Model model) {
        PuntoFijo puntoFijo = new PuntoFijo();
        model.addAttribute("puntoFijo", puntoFijo);
        return "unit2/puntofijo/formpuntofijo";
    }

    // SOLUCIÓN PUNTO FIJO
    @PostMapping("/unidad2/puntofijo/solucion")
    public String solvePuntoFijo(PuntoFijo puntoFijo, Model model) {
        ArrayList<PuntoFijo> resultado = unidadIIService.AlgoritmoPuntoFijo(puntoFijo);
        log.info("Resultado Punto Fijo: " + resultado);
        model.addAttribute("resultado", resultado);
        return "unit2/puntofijo/solvepuntofijo";
    }

    // FORMULARIO NEWTON-RAPHSON
    @GetMapping("/unidad2/newtonraphson")
    public String formNewtonRaphson(Model model) {
        NewtonRaphson newtonRaphson = new NewtonRaphson();
        model.addAttribute("newtonRaphson", newtonRaphson);
        return "unit2/newtonraphson/formnewtonraphson";
    }

    // SOLUCIÓN NEWTON-RAPHSON
    @PostMapping("/unidad2/newtonraphson/solucion")
    public String solveNewtonRaphson(NewtonRaphson newtonRaphson, Model model) {
        ArrayList<NewtonRaphson> resultado = unidadIIService.AlgoritmoNewtonRaphson(newtonRaphson);
        log.info("Resultado Newton-Raphson: " + resultado);
        model.addAttribute("resultado", resultado);
        return "unit2/newtonraphson/solvenewtonraphson";
    }
}
