import java.util.Scanner;
import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class PrincipalSudoku {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Parámetros de entrada
        System.out.print("Ingrese el numero de individuos en la poblacion: ");
        int poblacionInicial = scanner.nextInt();

        System.out.print("Ingrese el numero de generaciones: ");
        int generaciones = scanner.nextInt();

        // Configuración del algoritmo genético
        DefaultConfiguration config = new DefaultConfiguration();
        config.setPreservFittestIndividual(true);

        // Función de aptitud
        FAptitudSudoku fAptitud = new FAptitudSudoku();
        config.setFitnessFunction(fAptitud);

        // Definir cromosoma con 81 genes (Sudoku 9x9)
        Gene[] genes = new Gene[81];
        for (int i = 0; i < 81; i++) {
            genes[i] = new IntegerGene(config, 1, 9); // valores entre 1 y 9
        }

        // Crear cromosoma de muestra
        IChromosome cromosomaMuestra = new Chromosome(config, genes);
        config.setSampleChromosome(cromosomaMuestra);

        // Tamaño de población
        config.setPopulationSize(poblacionInicial);

        // Crear población inicial
        Genotype poblacion = Genotype.randomInitialGenotype(config);

        int mejorAptitud = 0;
        IChromosome mejorIndividuo = null;

        // Evolución
        for (int generacion = 1; generacion <= generaciones; generacion++) {
            poblacion.evolve();
            IChromosome masApto = poblacion.getFittestChromosome();
            int aptitud = (int) masApto.getFitnessValue();

            if (aptitud > mejorAptitud) {
                mejorAptitud = aptitud;
                mejorIndividuo = masApto;
            }

            // Mostrar progreso cada 100 generaciones
            if (generacion % 100 == 0 || generacion == generaciones) {
                System.out.println("Generación " + generacion +
                        " | Mejor aptitud hasta ahora: " + mejorAptitud);
            }
        }

        // Mostrar mejor solución
        System.out.println("\nMejor individuo encontrado con aptitud: " + mejorAptitud);
        imprimirSudoku(mejorIndividuo);
    }

    // Método para imprimir el Sudoku en formato 9x9 con separadores
public static void imprimirSudoku(IChromosome cromosoma) {
    for (int i = 0; i < 9; i++) {
        if (i % 3 == 0 && i != 0) {
            System.out.println("------+-------+------");
        }
        for (int j = 0; j < 9; j++) {
            if (j % 3 == 0 && j != 0) {
                System.out.print("| ");
            }
            int valor = (Integer) cromosoma.getGene(i * 9 + j).getAllele();
            System.out.print(valor + " ");
        }
        System.out.println();
    }
}

}
