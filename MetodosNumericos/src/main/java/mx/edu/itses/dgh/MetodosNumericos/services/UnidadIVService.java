/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.itses.dgh.MetodosNumericos.services;

import mx.edu.itses.dgh.MetodosNumericos.domain.DDNewton;
import mx.edu.itses.dgh.MetodosNumericos.domain.Lagrange;

/**
 *
 * @author david
 */
public interface UnidadIVService {
    
    DDNewton AlgoritmoDDNewton(DDNewton model);
    Lagrange AlgoritmoLagrange(Lagrange model);
    
    
}
