
package mx.edu.itses.dgh.MetodosNumericos.domain;

import lombok.Data;

@Data
public class Biseccion {
 
    private String Fx; //Funcion a Evaluar
    private double Xl;
    private double Xu;
    private double Xr;
    private double FXl;
    private double FXu;
    private double FXr;
    private double Ea;
    private int IteracionesMaximas;
    
    
}
