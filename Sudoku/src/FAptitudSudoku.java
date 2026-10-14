import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import java.util.HashSet;
import java.util.Set;

public class FAptitudSudoku extends FitnessFunction {
    @Override
    public double evaluate(IChromosome cromosoma) {
        int fitness = 0;

        // Evaluar filas
        for (int i = 0; i < 9; i++) {
            Set<Integer> fila = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                int valor = (Integer) cromosoma.getGene(i * 9 + j).getAllele();
                fila.add(valor);
            }
            fitness += fila.size();
        }

        // Evaluar columnas
        for (int j = 0; j < 9; j++) {
            Set<Integer> columna = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                int valor = (Integer) cromosoma.getGene(i * 9 + j).getAllele();
                columna.add(valor);
            }
            fitness += columna.size();
        }

        // Evaluar subcuadros 3x3
        for (int subCuadro = 0; subCuadro < 9; subCuadro++) {
            Set<Integer> subcuadro = new HashSet<>();
            int startRow = (subCuadro / 3) * 3;
            int startCol = (subCuadro % 3) * 3;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int valor = (Integer) cromosoma.getGene((startRow + i) * 9 + (startCol + j)).getAllele();
                    subcuadro.add(valor);
                }
            }
            fitness += subcuadro.size();
        }

        return fitness;
    }
}
