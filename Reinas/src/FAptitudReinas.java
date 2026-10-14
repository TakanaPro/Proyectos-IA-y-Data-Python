import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class FAptitudReinas extends FitnessFunction {
    @Override
    protected double evaluate(IChromosome cromosoma) {
        int n = 4;
        int[] filas = new int[n];
        for (int i = 0; i < n; i++) {
            filas[i] = (Integer) cromosoma.getGene(i).getAllele();
        }

        // Contar pares en conflicto (misma fila o misma diagonal)
        int conflictos = 0;
        for (int c1 = 0; c1 < n; c1++) {
            for (int c2 = c1 + 1; c2 < n; c2++) {
                int r1 = filas[c1];
                int r2 = filas[c2];
                if (r1 == r2) conflictos++; // misma fila
                if (Math.abs(r1 - r2) == Math.abs(c1 - c2)) conflictos++; // misma diagonal
            }
        }

        // Número total de pares posibles = C(4,2) = 6
        int maxPares = 6;
        int noConflictos = maxPares - conflictos;

        // Mientras más no-conflictos, mejor
        return noConflictos;
    }
}
