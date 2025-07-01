package mx.edu.itses.dgh.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.dgh.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.dgh.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.dgh.MetodosNumericos.domain.NewtonRaphson;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIIServiceImpl implements UnidadIIService {

    @Override
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion) {
        ArrayList<Biseccion> respuesta = new ArrayList<>();
        double XL, XU, XRa, XRn, FXL, FXU, FXR, Ea;

        XL = biseccion.getXl();
        XU = biseccion.getXu();
        XRa = 0;
        Ea = 100;

        FXL = Funciones.Ecuacion(biseccion.getFx(), XL);
        FXU = Funciones.Ecuacion(biseccion.getFx(), XU);
        if (FXL * FXU < 0) {
            for (int i = 1; i <= biseccion.getIteracionesMaximas(); i++) {
                XRn = (XL + XU) / 2;
                FXL = Funciones.Ecuacion(biseccion.getFx(), XL);
                FXU = Funciones.Ecuacion(biseccion.getFx(), XU);
                FXR = Funciones.Ecuacion(biseccion.getFx(), XRn);
                if (i != 1) {
                    Ea = Funciones.ErrorRelativo(XRn, XRa);
                }

                Biseccion renglon = new Biseccion();
                renglon.setXl(XL);
                renglon.setXu(XU);
                renglon.setXr(XRn);
                renglon.setFXl(FXL);
                renglon.setFXu(FXU);
                renglon.setFXr(FXR);
                renglon.setEa(Ea);

                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else if (FXL * FXR == 0) {
                    break;
                }

                XRa = XRn;
                respuesta.add(renglon);

                if (Ea <= biseccion.getEa()) {
                    break;
                }
            }
        } else {
            Biseccion renglon = new Biseccion();
            // renglon.setIntervaloInvalido(true);
            respuesta.add(renglon);
        }

        return respuesta;
    }

    @Override
    public ArrayList<PuntoFijo> AlgoritmoPuntoFijo(PuntoFijo puntoFijo) {
        ArrayList<PuntoFijo> respuesta = new ArrayList<>();
        double xi = puntoFijo.getXi();
        double error = 1;
        int iteracionesMax = puntoFijo.getIteraciones();
        double errorDeseado = puntoFijo.getError();
        int contador = 1;

        while (contador <= iteracionesMax && error > errorDeseado) {
            double gxi = funcionG(xi);
            if (contador > 1) {
                error = Funciones.ErrorRelativo(gxi, xi);
            }

            PuntoFijo renglon = new PuntoFijo();
            renglon.setIteracion(contador);
            renglon.setXi(xi);
            renglon.setGxi(gxi);
            renglon.setError(error);

            respuesta.add(renglon);

            xi = gxi;
            contador++;
        }

        return respuesta;
    }

    @Override
    public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newtonraphson) {
        ArrayList<NewtonRaphson> respuesta = new ArrayList<>();

        double Xi = newtonraphson.getXi(); // valor inicial
        double Xi1 = 0;           // siguiente Xi
        double Ea = 100;            // error inicial
        double h = 0.0001; // paso pequeño para aproximar derivada

        int maxIteraciones = newtonraphson.getIteracionesMaximas();

        for (int i = 1; i <= maxIteraciones; i++) {
            double FXi = Funciones.Ecuacion(newtonraphson.getFX(), Xi);
            double FdXi = (Funciones.Ecuacion(newtonraphson.getFX(), Xi + h) - FXi) / h;

            if (FdXi == 0) {
                log.warn("Derivada cercana a cero en iteración {}, deteniendo proceso.", i);
                break;
            }

            Xi1 = Xi - (FXi / FdXi);
            Ea = Funciones.ErrorRelativo(Xi1, Xi);

            NewtonRaphson iteracion = new NewtonRaphson();
            iteracion.setXi(Xi);
            iteracion.setFXi(FXi);
            iteracion.setDFXi(String.valueOf(FdXi));
            iteracion.setXi1(Xi1);
            iteracion.setEa(Ea);
            iteracion.setIteracionesMaximas(i);

            respuesta.add(iteracion);
            Xi = Xi1;
        }

        return respuesta;
    }

    // G(x) = sin(x) - x^2 + x
    private double funcionG(double x) {
        return Math.sin(x) - Math.pow(x, 2) + x;
    }
}
