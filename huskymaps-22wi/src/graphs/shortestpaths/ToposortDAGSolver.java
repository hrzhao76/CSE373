package graphs.shortestpaths;

import graphs.Edge;
import graphs.Graph;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 *
 * Implemented by Sneh.
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo; // map nodes to edges
    private final Map<V, Double> distTo; // map nodes to the distance

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        Set<V> visited = new HashSet<>();
        List<V> result = new ArrayList<>();
        dfsPostOrder(graph, start, visited, result);
        Collections.reverse(result); // Reverse the DFS postorder.
        edgeTo.put(start, null);
        distTo.put(start, 0.0);
        for (V vertex : result) {
            for (Edge<V> edge : graph.neighbors(vertex)) {
                Double goingTo = distTo.getOrDefault(edge.to, Double.POSITIVE_INFINITY);
                Double curr = distTo.getOrDefault(vertex, Double.POSITIVE_INFINITY);
                curr += edge.weight;
                if (goingTo > curr) {
                    distTo.put(edge.to, curr);
                    edgeTo.put(edge.to, edge);
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
        visited.add(start);
        for (Edge<V> neighbor : graph.neighbors(start)) { // how to do this??????
            if (!visited.contains(neighbor.to)) {
                dfsPostOrder(graph, neighbor.to, visited, result);
            }
        }
        // Postorder: Add start after visiting all the neighbors.
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
