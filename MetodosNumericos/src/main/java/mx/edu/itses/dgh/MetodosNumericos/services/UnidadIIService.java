package mx.edu.itses.dgh.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.dgh.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.dgh.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.dgh.MetodosNumericos.domain.NewtonRaphson;

public interface UnidadIIService {
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);
    public ArrayList<PuntoFijo> AlgoritmoPuntoFijo(PuntoFijo puntoFijo);
    public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newtonraphson);
}
