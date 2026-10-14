import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class PrincipalReinas {
    public static void main(String[] args) throws Exception {
        DefaultConfiguration config = new DefaultConfiguration();
        config.setPreservFittestIndividual(true);

        // Fitness: maximizar que las reinas NO se maten
        FAptitudReinas fitness = new FAptitudReinas();
        config.setFitnessFunction(fitness);

        // Cromosoma de 4 genes: cada gen es la fila [0..3] para cada columna
        Gene[] genes = new Gene[4];
        for (int i = 0; i < 4; i++) {
            genes[i] = new IntegerGene(config, 0, 3);
        }
        IChromosome sample = new Chromosome(config, genes);
        config.setSampleChromosome(sample);

        config.setPopulationSize(200);

        // Inicialización
        Genotype poblacion = Genotype.randomInitialGenotype(config);

        // Evolución
        for (int generacion = 1; generacion <= 200; generacion++) {
            poblacion.evolve();

            // Si encuentra solución perfecta (fitness = 6), termina
            if (poblacion.getFittestChromosome().getFitnessValue() == 6) {
                break;
            }
        }

        IChromosome mejor = poblacion.getFittestChromosome();

        int[] filas = new int[4];
        for (int i = 0; i < 4; i++) {
            filas[i] = (Integer) mejor.getGene(i).getAllele();
        }

        System.out.println("Mejor cromosoma (fila por columna):");
        for (int c = 0; c < 4; c++) {
            System.out.print("C" + c + "=" + filas[c] + (c < 3 ? "  " : "\n"));
        }
        System.out.println("Fitness (no-conflictos): " + mejor.getFitnessValue());

        // Dibujar tablero con 1 y 0
        System.out.println("\nTablero 4x4 (1 = reina, 0 = vacío):");
        for (int r = 0; r < 4; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < 4; c++) {
                sb.append(filas[c] == r ? "1 " : "0 ");
            }
            System.out.println(sb.toString());
        }
    }
}
