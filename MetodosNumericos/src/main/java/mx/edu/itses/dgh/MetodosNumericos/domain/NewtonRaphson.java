/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.dgh.MetodosNumericos.domain;

import lombok.Data;

@Data
public class NewtonRaphson {
  private String FX;
  private double Xi;
  private double FXi;
  private String DFXi;
  private double Xi1;
  private double Ea;
  private int IteracionesMaximas;
}
