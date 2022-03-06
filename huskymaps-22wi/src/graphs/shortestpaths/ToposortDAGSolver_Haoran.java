package graphs.shortestpaths;

import graphs.Edge;
import graphs.Graph;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver_Haoran<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver_Haoran(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");

        Set<V> visited = new HashSet<>();
        List<V> result = new ArrayList<>();

        dfsPostOrder(graph, start, visited, result);

        // Reverse the DFS postorder.
        Collections.reverse(result);
        edgeTo.put(start, null);
        distTo.put(start, 0.0);

        for (V vertex : result) {
            for (Edge<V> edge : graph.neighbors(vertex)) {
                V to_vtx = edge.to;
                // oldDist is the weight of the best-known path not using this edge.
                double oldDist = distTo.getOrDefault(to_vtx, Double.POSITIVE_INFINITY);
                // newDist is the weight of the shortest path using this edge.
                double newDist = distTo.get(vertex) + edge.weight;
                // Check that we haven't added the vertex to the SPT
                //double newDist = distTo.getOrDefault(vertex, Double.POSITIVE_INFINITY);
                //newDist += edge.weight;
                // AND the path using this edge is better than the best-known path.
                if (newDist < oldDist) {
                    edgeTo.put(to_vtx, edge);
                    // Store the weight of the path using this edge.
                    distTo.put(to_vtx, newDist);
                }
            }
        }
    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        visited.add(start);
        for (Edge<V> edge : graph.neighbors(start)) {
            if (!visited.contains(edge.to)) {
                dfsPostOrder(graph, edge.to, visited, result);
            }
        }
        result.add(start);
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
