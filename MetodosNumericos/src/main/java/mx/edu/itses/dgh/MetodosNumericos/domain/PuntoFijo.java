package mx.edu.itses.dgh.MetodosNumericos.domain;

import lombok.Data;

@Data
public class PuntoFijo {

private int iteracion;
private double xi;
private double gxi;
private double error;
private int iteraciones;
private double errorDeseado;

}
