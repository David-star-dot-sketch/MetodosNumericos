package mx.edu.itses.dgh.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.dgh.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.dgh.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.dgh.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.dgh.MetodosNumericos.domain.Secante;
import mx.edu.itses.dgh.MetodosNumericos.domain.SecanteModificado;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIIServiceImpl implements UnidadIIService {

    @Override
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion) {
        ArrayList<Biseccion> respuesta = new ArrayList<>();
        double XL = biseccion.getXl();
        double XU = biseccion.getXu();
        double XRa = 0;
        double Ea = 100;

        double FXL = Funciones.Ecuacion(biseccion.getFx(), XL);
        double FXU = Funciones.Ecuacion(biseccion.getFx(), XU);

        if (FXL * FXU < 0) {
            for (int i = 1; i <= biseccion.getIteracionesMaximas(); i++) {
                double XRn = (XL + XU) / 2;
                FXL = Funciones.Ecuacion(biseccion.getFx(), XL);
                FXU = Funciones.Ecuacion(biseccion.getFx(), XU);
                double FXR = Funciones.Ecuacion(biseccion.getFx(), XRn);

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

                // Actualizar límites del intervalo
                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else {
                    break; // Raíz exacta
                }

                XRa = XRn;
                respuesta.add(renglon);

                // Criterio de parada por tolerancia
                if (Ea <= biseccion.getEa()) {
                    break;
                }
            }
        } else {
            // Intervalo inválido: no hay cambio de signo
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
            // Evaluamos g(x) ingresada por el usuario
            double gxi = Funciones.Ecuacion(puntoFijo.getGx(), xi);

            if (contador > 1) {
                error = Funciones.ErrorRelativo(gxi, xi);
            }

            PuntoFijo renglon = new PuntoFijo();
            renglon.setIteracion(contador);
            renglon.setXi(xi);
            renglon.setGxi(gxi);
            renglon.setError(error);

            respuesta.add(renglon);

            // Preparamos la siguiente iteración
            xi = gxi;
            contador++;
        }

        return respuesta;
    }

    @Override
    public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newtonraphson) {
        ArrayList<NewtonRaphson> respuesta = new ArrayList<>();

        double Xi = newtonraphson.getXi(); // valor inicial
        double Ea = 100;                   // error inicial grande
        double h = 0.0001;                // paso para derivada numérica

        int maxIteraciones = newtonraphson.getIteracionesMaximas();
        double errorDeseado = newtonraphson.getError();

        for (int i = 1; i <= maxIteraciones; i++) {
            double FXi = Funciones.Ecuacion(newtonraphson.getFX(), Xi);
            double FdXi = (Funciones.Ecuacion(newtonraphson.getFX(), Xi + h) - FXi) / h;

            if (FdXi == 0) {
                log.warn("Derivada cercana a cero en iteración {}, deteniendo proceso.", i);
                break;
            }

            double Xi1 = Xi - (FXi / FdXi);
            Ea = Funciones.ErrorRelativo(Xi1, Xi);

            NewtonRaphson iteracion = new NewtonRaphson();
            iteracion.setXi(Xi);
            iteracion.setFXi(FXi);
            iteracion.setDFXi(String.valueOf(FdXi));
            iteracion.setXi1(Xi1);
            iteracion.setEa(Ea);
            iteracion.setIteracion(i);

            respuesta.add(iteracion);

            // Criterio de parada por tolerancia
            if (Ea <= errorDeseado) {
                break;
            }

            Xi = Xi1;
        }

        return respuesta;
    }
    
    @Override
public ArrayList<Secante> AlgoritmoSecante(Secante secante) {
    ArrayList<Secante> resultado = new ArrayList<>();
    double x0 = secante.getX0();
    double x1 = secante.getX1();
    double ea = 100;
    double xr = 0;
    int iteracion = 1;

    while (iteracion <= secante.getIteracionesMaximas() && ea > secante.getErrorDeseado()) {
        double fx0 = Funciones.Ecuacion(secante.getFx(), x0);
        double fx1 = Funciones.Ecuacion(secante.getFx(), x1);

        if ((fx1 - fx0) == 0) break;

        xr = x1 - fx1 * (x1 - x0) / (fx1 - fx0);

        if (iteracion > 1)
            ea = Funciones.ErrorRelativo(xr, x1);

        Secante paso = new Secante();
        paso.setFx(secante.getFx());
        paso.setX0(x0);
        paso.setX1(x1);
        paso.setXr(xr);
        paso.setFxr(Funciones.Ecuacion(secante.getFx(), xr));
        paso.setEa(ea);
        paso.setIteracion(iteracion);

        resultado.add(paso);
        x0 = x1;
        x1 = xr;
        iteracion++;
    }

    return resultado;
}

@Override
public ArrayList<SecanteModificado> AlgoritmoSecanteModificado(SecanteModificado secanteMod) {
    ArrayList<SecanteModificado> resultado = new ArrayList<>();
    double h = 0.0001;
    double x0 = secanteMod.getX0();
    double ea = 100;
    int iteracion = 1;

    while (iteracion <= secanteMod.getIteracionesMaximas() && ea > secanteMod.getErrorDeseado()) {
        double fx = Funciones.Ecuacion(secanteMod.getFx(), x0);
        double fxh = Funciones.Ecuacion(secanteMod.getFx(), x0 + h);
        double derivada = (fxh - fx) / h;

        if (derivada == 0) break;

        double xr = x0 - fx / derivada;

        if (iteracion > 1)
            ea = Funciones.ErrorRelativo(xr, x0);

        SecanteModificado paso = new SecanteModificado();
        paso.setFx(secanteMod.getFx());
        paso.setX0(x0);
        paso.setXr(xr);
        paso.setFxr(Funciones.Ecuacion(secanteMod.getFx(), xr));
        paso.setEa(ea);
        paso.setIteracion(iteracion);

        resultado.add(paso);
        x0 = xr;
        iteracion++;
    }

    return resultado;
   }

}
