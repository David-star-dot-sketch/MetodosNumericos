/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.itses.dgh.MetodosNumericos.services;

import mx.edu.itses.dgh.MetodosNumericos.domain.Gauss;
import mx.edu.itses.dgh.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.dgh.MetodosNumericos.domain.GaussSeidel;
import mx.edu.itses.dgh.MetodosNumericos.domain.Jacobi;
import mx.edu.itses.dgh.MetodosNumericos.domain.ReglaCramer;

/**
 *
 * @author david
 */

public interface UnidadIIIService {
    
    public ReglaCramer AlgoritmoReglaCramer(ReglaCramer modelCramer);
    
    public Gauss AlgoritmoGauss(Gauss modelGauss);
    
    public GaussJordan AlgoritmoGaussJordan(GaussJordan modelGJ);
    
    public Jacobi AlgoritmoJacobi(Jacobi modelJacobi);
    
    public GaussSeidel AlgoritmoGaussSeidel(GaussSeidel modelGS);
}
