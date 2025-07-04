
package mx.edu.itses.dgh.MetodosNumericos.domain;

import lombok.Data;

@Data
public class NewtonRaphson {
    
    private double Error;
    private String FX;
    private double Xi;
    private double FXi;
    private String DFXi;
    private double Xi1;
    private double Ea;
    private int IteracionesMaximas;
    private int Iteracion; 

    
}
