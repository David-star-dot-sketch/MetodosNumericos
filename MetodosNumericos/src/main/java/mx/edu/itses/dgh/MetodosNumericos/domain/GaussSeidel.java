/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.dgh.MetodosNumericos.domain;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author david
 */
@Data
public class GaussSeidel {
    private Integer MN;                
    private ArrayList<Double> matrizA;  
    private ArrayList<Double> vectorB;  
    private ArrayList<Double> vectorX;  
    private Integer iteraciones;        
    private Double tolerancia;         
    private boolean convergente;
}
