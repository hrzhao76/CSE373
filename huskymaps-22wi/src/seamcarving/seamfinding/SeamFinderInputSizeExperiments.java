package seamcarving.seamfinding;

import graphs.shortestpaths.DijkstraSolver;
import graphs.shortestpaths.ToposortDAGSolver;
import graphs.shortestpaths.ToposortDAGSolver_Haoran;
import seamcarving.Picture;
import seamcarving.energy.DualGradientEnergyFunction;
import seamcarving.energy.EnergyFunction;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

/**
 * Run timing experiments and save a CSV file for each {@link SeamFinder} implementation.
 */
class SeamFinderInputSizeExperiments {
    /**
     * Maximum image dimensions in pixels. Making this smaller means experiments run faster.
     */
    private static final int MAX_INPUT_SIZE = 500;
    /**
     * Step size increment. Making this smaller means experiments run slower.
     */
    private static final int INPUT_STEP_SIZE = 50;
    /**
     * Number of trials to per implementation run. Making this smaller means experiments run faster.
     */
    private static final int NUM_TRIALS = 50;
    /**
     * Test directory name.
     */
    private static final String TEST = "experiment";

    public static void main(String[] args) throws IOException {
        // Testing implementations.
        Map<String, SeamFinder> implementations = Map.of(
                "AdjDijkstra", new AdjacencyListSeamFinder(DijkstraSolver::new),
                "AdjToposort", new AdjacencyListSeamFinder(ToposortDAGSolver::new),
                "AdjToposort_Haoran", new AdjacencyListSeamFinder(ToposortDAGSolver_Haoran::new),
                "GenDijkstra", new GenerativeSeamFinder(DijkstraSolver::new),
                "GenToposort", new GenerativeSeamFinder(ToposortDAGSolver::new),
                "GenToposort_Haoran", new GenerativeSeamFinder(ToposortDAGSolver_Haoran::new),
                "DynamicProgramming", new DynamicProgrammingSeamFinder(),
                "DynamicProgramming_Haoran", new DynamicProgrammingSeamFinder_Haoran()
        );
        EnergyFunction f = new DualGradientEnergyFunction();

        new File(TEST).mkdir();
        Map<String, PrintStream> printStreams = Map.of(
                "AdjDijkstra", new PrintStream(TEST + "/AdjDijkstra.csv"),
                "AdjToposort", new PrintStream(TEST + "/AdjToposort.csv"),
                "AdjToposort_Haoran", new PrintStream(TEST + "/AdjToposort_Haoran.csv"),
                "GenDijkstra", new PrintStream(TEST + "/GenDijkstra.csv"),
                "GenToposort", new PrintStream(TEST + "/GenToposort.csv"),
                "GenToposort_Haoran", new PrintStream(TEST + "/GenToposort_Haoran.csv"),
                "DynamicProgramming", new PrintStream(TEST + "/DynamicProgramming.csv"),
                "DynamicProgramming_Haoran", new PrintStream(TEST + "/DynamicProgramming_Haoran.csv")
        );
        for (int N = INPUT_STEP_SIZE; N <= MAX_INPUT_SIZE; N += INPUT_STEP_SIZE) {
            System.out.println("N = " + N);
            // Generate a random N-by-N picture.
            Picture picture = Picture.random(N, N);

            for (String name : implementations.keySet()) {
                SeamFinder seamFinder = implementations.get(name);
                PrintStream out = printStreams.get(name);
                out.print(N);
                out.print(',');

                // Record the total runtimes for this picture.
                double totalTime = 0.0;

                for (int i = 0; i < NUM_TRIALS; i += 1) {
                    // Measure findSeam.
                    long start = System.nanoTime();
                    seamFinder.findHorizontal(picture, f);
                    long time = System.nanoTime() - start;

                    // Convert from nanoseconds to seconds and add to total time.
                    totalTime += (double) time / 1_000_000_000;
                }

                // Output the averages to 10 decimal places.
                out.printf("%.10f", totalTime / NUM_TRIALS);
                out.println();
            }
            System.out.println("N = " + N + " done.");
        }
    }
}
