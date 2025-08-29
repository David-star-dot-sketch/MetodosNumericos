package mx.edu.itses.dgh.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.dgh.MetodosNumericos.domain.Gauss;
import mx.edu.itses.dgh.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.dgh.MetodosNumericos.domain.GaussSeidel;
import mx.edu.itses.dgh.MetodosNumericos.domain.Jacobi;
import mx.edu.itses.dgh.MetodosNumericos.domain.ReglaCramer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIIIServiceImpl implements UnidadIIIService {

    @Override
    public ReglaCramer AlgoritmoReglaCramer(ReglaCramer modelCramer) {
    //Aqui se almacenan los determinantes de la matriz
     ArrayList<Double> determinantes = new ArrayList<>();
     
   //TAMAÑO DEL SISTEMA LINEAL
    switch(modelCramer.getMN()){
        case 2 -> {
            ArrayList<Double> A = modelCramer.getMatrizA();
            ArrayList<Double> b = modelCramer.getVectorB();
            
            double[][] MatrizA = {
                {A.get(0), A.get(1)},
                 {A.get(2), A.get(3)}
            };
            determinantes.add(Det2(MatrizA));
            log.info("Det A: " + determinantes.get(0));

            //calculamos determinantes para X1
            double[][] MatrizX1 = {
                {b.get(0), A.get(1)},
                 {b.get(1), A.get(3)}
            };
            determinantes.add(Det2(MatrizX1));
            log.info("Det X1: " + determinantes.get(1));
            
             //calculamos determinantes para X2
            double[][] MatrizX2 = {
                {A.get(0), b.get(0)},
                 {A.get(2), b.get(1)}
            };
            determinantes.add(Det2(MatrizX2));
            log.info("Det X2: " + determinantes.get(2));
            }
            
            
        
        
        case 3 -> {
            
            }
        
    }
    return modelCramer;
    }
   
    private double Det2(double[][] A){
    return A[0][0]* A[1][1]-A[0][1]*A[1][0];
    
    }

   @Override
    public Gauss AlgoritmoGauss(Gauss modelGauss) {
        if (modelGauss == null) {
            return null;
        }

        Integer n = modelGauss.getMN();
        var Aflat = modelGauss.getMatrizA();
        var bList = modelGauss.getVectorB();

        Gauss out = new Gauss();
        out.setMN(n);
        out.setPasos(new ArrayList<>());
        out.setSingular(false);

        if (n == null || Aflat == null || bList == null) {
            out.setSingular(true);
            out.setMensaje("Entradas nulas: MN/A/b");
            return out;
        }
        if (n < 2 || n > 4) {
            out.setSingular(true);
            out.setMensaje("Solo se admiten tamaños 2, 3 o 4");
            return out;
        }
        if (Aflat.size() != n * n || bList.size() != n) {
            out.setSingular(true);
            out.setMensaje("Dimensiones inválidas: A=" + Aflat.size() + ", b=" + bList.size() + ", n=" + n);
            return out;
        }

        double[][] A = toMatrix(Aflat, n);
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = bList.get(i);
        }

        for (int k = 0; k < n - 1; k++) {
            int piv = k;
            double maxAbs = Math.abs(A[k][k]);
            for (int r = k + 1; r < n; r++) {
                double val = Math.abs(A[r][k]);
                if (val > maxAbs) {
                    maxAbs = val;
                    piv = r;
                }
            }
            if (Math.abs(A[piv][k]) < 1e-12) {
                out.setSingular(true);
                out.setMensaje("Sistema singular o casi singular en columna " + (k + 1));
                out.setMatrizUEscalonada(flatten(A));
                return out;
            }
            if (piv != k) {
                swapRows(A, k, piv);
                double tmp = b[k];
                b[k] = b[piv];
                b[piv] = tmp;
                out.getPasos().add("Swap fila " + (k + 1) + " <-> " + (piv + 1));
            }

            for (int i = k + 1; i < n; i++) {
                double factor = A[i][k] / A[k][k];
                A[i][k] = 0.0; 
                for (int j = k + 1; j < n; j++) {
                    A[i][j] -= factor * A[k][j];
                }
                b[i] -= factor * b[k];
                out.getPasos().add(String.format("R%d := R%d - (%.6f)*R%d", i + 1, i + 1, factor, k + 1));
            }
        }

        if (Math.abs(A[n - 1][n - 1]) < 1e-12) {
            out.setSingular(true);
            out.setMensaje("Sistema singular (último pivote nulo)");
            out.setMatrizUEscalonada(flatten(A));
            return out;
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }

        out.setVectorX(toList(x));
        out.setMatrizUEscalonada(flatten(A)); 
        out.setMatrizA(new ArrayList<>(Aflat));
        out.setVectorB(new ArrayList<>(bList)); 
        out.setMensaje("Todo bien");
        return out;
    }

    private static double[][] toMatrix(ArrayList<Double> flat, int n) {
        double[][] M = new double[n][n];
        for (int i = 0, p = 0; i < n; i++) {
            for (int j = 0; j < n; j++, p++) {
                M[i][j] = flat.get(p);
            }
        }
        return M;
    }

    private static ArrayList<Double> flatten(double[][] M) {
        int n = M.length;
        ArrayList<Double> out = new ArrayList<>(n * n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.add(M[i][j]);
            }
        }
        return out;
    }

    private static ArrayList<Double> toList(double[] v) {
        ArrayList<Double> out = new ArrayList<>(v.length);
        for (double x : v) {
            out.add(x);
        }
        return out;
    }

    private static void swapRows(double[][] A, int r1, int r2) {
        double[] tmp = A[r1];
        A[r1] = A[r2];
        A[r2] = tmp;
    }

    
    @Override
    public GaussJordan AlgoritmoGaussJordan(GaussJordan modelGJ) {
        if (modelGJ == null) {
            return null;
        }

        Integer n = modelGJ.getMN();
        var Aflat = modelGJ.getMatrizA();
        var bList = modelGJ.getVectorB();

        GaussJordan out = new GaussJordan();
        out.setMN(n);
        out.setSingular(false);

        if (n == null || Aflat == null || bList == null) {
            out.setSingular(true);
            return out;
        }
        if (n < 2 || n > 4) {
            out.setSingular(true);
            return out;
        }
        if (Aflat.size() != n * n || bList.size() != n) {
            out.setSingular(true);
            return out;
        }

        double[][] M = new double[n][n + 1];
        for (int i = 0, p = 0; i < n; i++) {
            for (int j = 0; j < n; j++, p++) {
                M[i][j] = Aflat.get(p);
            }
        }
        for (int i = 0; i < n; i++) {
            M[i][n] = bList.get(i);
        }

        for (int col = 0; col < n; col++) {

            int piv = col;
            double maxAbs = Math.abs(M[col][col]);
            for (int r = col + 1; r < n; r++) {
                double v = Math.abs(M[r][col]);
                if (v > maxAbs) {
                    maxAbs = v;
                    piv = r;
                }
            }
            if (Math.abs(M[piv][col]) < 1e-12) {
                out.setSingular(true);
                out.setMatrizRrefAumentada(flattenAug(M));
                return out;
            }
            if (piv != col) {
                swapRowsAug(M, piv, col);
            }

            double diag = M[col][col];
            for (int j = col; j <= n; j++) {
                M[col][j] /= diag;
            }

            for (int r = 0; r < n; r++) {
                if (r == col) {
                    continue;
                }
                double factor = M[r][col];
                if (Math.abs(factor) < 1e-15) {
                    continue;
                }
                for (int j = col; j <= n; j++) {
                    M[r][j] -= factor * M[col][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = M[i][n];
        }

        out.setVectorX(toList(x));
        out.setMatrizRrefAumentada(flattenAug(M));
        out.setMatrizA(new java.util.ArrayList<>(Aflat));
        out.setVectorB(new java.util.ArrayList<>(bList));
        return out;
    }

    private static ArrayList<Double> flattenAug(double[][] M) {
        int n = M.length, m = M[0].length;
        var out = new java.util.ArrayList<Double>(n * m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out.add(M[i][j]);
            }
        }
        return out;
    }

    private static void swapRowsAug(double[][] M, int r1, int r2) {
        double[] tmp = M[r1];
        M[r1] = M[r2];
        M[r2] = tmp;
    }

    
    @Override
    public Jacobi AlgoritmoJacobi(Jacobi modelJacobi) {
        Integer n = modelJacobi.getMN();
        var Aflat = modelJacobi.getMatrizA();
        var bList = modelJacobi.getVectorB();

        Jacobi out = new Jacobi();
        out.setMN(n);
        out.setMatrizA(Aflat);
        out.setVectorB(bList);

        if (n == null || Aflat == null || bList == null) {
            out.setConvergente(false);
            return out;
        }

        double[][] A = new double[n][n];
        double[] b = new double[n];
        for (int i = 0, p = 0; i < n; i++) {
            for (int j = 0; j < n; j++, p++) {
                A[i][j] = Aflat.get(p);
            }
            b[i] = bList.get(i);
        }

        int maxIter = modelJacobi.getIteraciones() > 0 ? modelJacobi.getIteraciones() : 50;
        double tol = modelJacobi.getTolerancia() > 0 ? modelJacobi.getTolerancia() : 1e-6;

        double[] xOld = new double[n];
        double[] xNew = new double[n];

        boolean convergio = false;
        for (int k = 0; k < maxIter; k++) {
            for (int i = 0; i < n; i++) {
                double suma = 0.0;
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        suma += A[i][j] * xOld[j];
                    }
                }
                xNew[i] = (b[i] - suma) / A[i][i];
            }

            double error = 0.0;
            for (int i = 0; i < n; i++) {
                error = Math.max(error, Math.abs(xNew[i] - xOld[i]));
                xOld[i] = xNew[i];
            }

            if (error < tol) {
                convergio = true;
                out.setIteraciones(k + 1);
                break;
            }
        }

        out.setVectorX(toList(xNew));
        out.setTolerancia(tol);
        out.setConvergente(convergio);
        return out;
    }

    @Override
    public GaussSeidel AlgoritmoGaussSeidel(GaussSeidel modelGS) {
        var out = new GaussSeidel();
        if (modelGS == null) {
            return out;
        }

        Integer n = modelGS.getMN();
        var Aflat = modelGS.getMatrizA();
        var bList = modelGS.getVectorB();

        int maxIter = (modelGS.getIteraciones() != null && modelGS.getIteraciones() > 0) ? modelGS.getIteraciones() : 50;
        double tol = (modelGS.getTolerancia() != null && modelGS.getTolerancia() > 0.0) ? modelGS.getTolerancia() : 1e-6;

        out.setMN(n);
        out.setIteraciones(0);
        out.setTolerancia(tol);
        out.setConvergente(false);

        if (n == null || Aflat == null || bList == null) {
            return out;
        }
        if (n < 2 || n > 4) {
            return out;
        }
        if (Aflat.size() != n * n || bList.size() != n) {
            return out;
        }

        double[][] A = new double[n][n];
        double[] b = new double[n];
        for (int i = 0, p = 0; i < n; i++) {
            for (int j = 0; j < n; j++, p++) {
                A[i][j] = Aflat.get(p);
            }
            b[i] = bList.get(i);
            if (Math.abs(A[i][i]) < 1e-12) {
                return out;
            }
        }

        double[] x = new double[n];

        boolean ok = false;
        for (int k = 0; k < maxIter; k++) {
            double maxRelErr = 0.0;

            for (int i = 0; i < n; i++) {
                double suma = 0.0;
                for (int j = 0; j < n; j++) {
                    if (j == i) {
                        continue;
                    }
                    suma += A[i][j] * x[j];
                }
                double nuevo = (b[i] - suma) / A[i][i];

                double denom = Math.abs(nuevo) < 1e-12 ? 1.0 : Math.abs(nuevo);
                double rel = Math.abs(nuevo - x[i]) / denom;
                if (rel > maxRelErr) {
                    maxRelErr = rel;
                }

                x[i] = nuevo;
            }

            if (maxRelErr < tol) {
                ok = true;
                out.setIteraciones(k + 1);
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            x[i] = pretty(x[i]);
        }

        out.setVectorX(toList(x));
        out.setConvergente(ok);
        return out;
    }

    private static double pretty(double v) {
        if (Math.abs(v) < 1e-12) {
            return 0.0;
        }
        double r = Math.rint(v);
        if (Math.abs(v - r) < 1e-9) {
            return r;
        }
        return Math.round(v * 1e6) / 1e6;
    }

}   
