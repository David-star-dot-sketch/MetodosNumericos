package mx.edu.itses.dgh.MetodosNumericos.domain;

import lombok.Data;

@Data
public class SecanteModificado {
    private String fx;
    private double x0;
    private double xr;
    private double fxr;
    private double ea;
    private int iteracion;
    private int iteracionesMaximas;
    private double errorDeseado;
}
