import java.util.*;


public class Main {
    private static boolean bfs(int[][] residualGraph, int start, int goal, int[] parent,int numberOfVertices) {
        boolean[] visited = new boolean[numberOfVertices + 1];
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean pathFound = false;
        int destination, element;
        for (int vertex = 1; vertex <= numberOfVertices; vertex++)
        {
            parent[vertex] = -1;
            visited[vertex] = false;
        }
        queue.add(start);
        parent[start] = -1;
        visited[start] = true;

        while (!queue.isEmpty())
        {
            element = queue.remove();
            destination = 1;
            while (destination <= numberOfVertices)
            {
                if (residualGraph[element][destination] > 0 &&  !visited[destination])
                {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
        }

        if (visited[goal])
        {
            pathFound = true;
        }
        return pathFound;
    }

    private static void dfs(int[][] rGraph, int start,
                            boolean[] visited) {
        visited[start] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[start][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }

    private static void MaxFlowMinCut(int[][] graph, int start, int goal,int numberOfVertices) {
        int u, v;
        int maxFlow = 0;
        int pathFlow = Integer.MAX_VALUE;
        int[][] residualGraph = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }


        int[] parent = new int[graph.length];

        while (bfs(residualGraph, start, goal, parent,numberOfVertices)) {


            for (v = goal; v != start; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (v = goal; v != start; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] = residualGraph[u][v] - pathFlow;
                residualGraph[v][u] = residualGraph[v][u] + pathFlow;
            }
            maxFlow += pathFlow;
        }

        boolean[] isVisited = new boolean[graph.length];
        dfs(residualGraph, start, isVisited);

        System.out.println("MinCut is: ");
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
                    System.out.println(i + " -> " + j);
                }
            }
        }
        System.out.println("MaxFlow is: " + maxFlow);
    }


    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Напишите сколько верщин в вашем графе");
        int NumVertex = sc.nextInt();
        int[][] graph = new int[NumVertex][NumVertex];
        for(int i =0;i<NumVertex;i++){
            for(int j =0;j<NumVertex;j++){
                graph[i][j] = sc.nextInt();
            }
        }

        MaxFlowMinCut(graph, 0, NumVertex-1, NumVertex-1);
    }
}
