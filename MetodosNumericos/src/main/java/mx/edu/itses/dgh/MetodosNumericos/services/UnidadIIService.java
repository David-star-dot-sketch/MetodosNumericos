package mx.edu.itses.dgh.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.dgh.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.dgh.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.dgh.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.dgh.MetodosNumericos.domain.Secante;
import mx.edu.itses.dgh.MetodosNumericos.domain.SecanteModificado;

public interface UnidadIIService {
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);
    public ArrayList<PuntoFijo> AlgoritmoPuntoFijo(PuntoFijo puntoFijo);
    public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newtonraphson);
    public ArrayList<Secante> AlgoritmoSecante(Secante secante);
    public ArrayList<SecanteModificado> AlgoritmoSecanteModificado(SecanteModificado secanteMod);

}
