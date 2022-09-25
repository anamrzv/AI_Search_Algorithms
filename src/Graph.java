import java.util.*;

public class Graph {
    private final int MAX_VERTS;
    private final ArrayList<Vertex> vertex;
    private final int[][] connections;

    public Graph(int vertexNum) {
        MAX_VERTS = vertexNum;
        vertex = new ArrayList<>();
        connections = new int[MAX_VERTS][MAX_VERTS];
        for (int j = 0; j < MAX_VERTS; j++) {
            for (int k = 0; k < MAX_VERTS; k++) {
                connections[j][k] = 0;
            }
        }
    }

    public void addEdge(Vertex from, Vertex to, int weight) {
        int start = vertex.indexOf(from);
        int end = vertex.indexOf(to);
        connections[start][end] = weight;
        connections[end][start] = weight;
    }

    public void displayVertex(int v) {
        System.out.println(vertex.get(v).getName());
    }

    public Vertex addVertex(String name, int heuristic_f) {
        Vertex v = new Vertex(name, heuristic_f);
        vertex.add(v);
        return v;
    }

    public void setAllUnvisited() {
        vertex.forEach((v) -> v.setWasVisited(false));
    }

    public void dfs(Vertex from, Vertex to) {
        int root = vertex.indexOf(from);
        vertex.get(root).setWasVisited(true);
        displayVertex(root);

        for (int v = 0; v < MAX_VERTS && !to.isWasVisited(); v++) {
            if (v != root && connections[root][v] != 0 && !vertex.get(v).isWasVisited()) {
                Vertex newRootVertex = vertex.get(v);
                if (newRootVertex.getName().equals(to.getName())) {
                    displayVertex(v);
                    to.setWasVisited(true);
                    break;
                } else dfs(newRootVertex, to);
            }
        }
    }

    public void dls(Vertex from, Vertex to, int current_depth, int depth_limit) {
        int root = vertex.indexOf(from);
        vertex.get(root).setWasVisited(true);
        displayVertex(root);

        if (current_depth < depth_limit) {
            for (int v = 0; v < MAX_VERTS && !to.isWasVisited(); v++) {
                if (v != root && connections[root][v] != 0 && !vertex.get(v).isWasVisited()) {
                    Vertex newRootVertex = vertex.get(v);
                    if (newRootVertex.getName().equals(to.getName())) {
                        displayVertex(v);
                        to.setWasVisited(true);
                        break;
                    } else dls(newRootVertex, to, current_depth + 1, depth_limit);
                }
            }
        }
    }

    public void iddfs(Vertex from, Vertex to) {
        int depth_limit = 2;
        dls(from, to, 1, depth_limit);
        while (!to.isWasVisited()) {
            System.out.print("\nЦель не найдена. Предел глубины увеличивается до ");
            depth_limit += 1;
            System.out.println(depth_limit);
            setAllUnvisited();
            System.out.println("===");
            dls(from, to, 1, depth_limit);
        }
    }

    public void bfs(Queue<Vertex> vertexInLayer, Vertex to) {
        while (!to.isWasVisited() && !vertexInLayer.isEmpty()) {
            Vertex currentVertex = vertexInLayer.poll();
            int currentVertexIndex = vertex.indexOf(currentVertex);

            vertex.get(currentVertexIndex).setWasVisited(true);
            displayVertex(currentVertexIndex);

            if (currentVertex.getName().equals(to.getName())) {
                vertexInLayer.clear();
                break;
            } else {
                for (int v = 0; v < MAX_VERTS && !to.isWasVisited(); v++) {
                    if (v != currentVertexIndex && connections[currentVertexIndex][v] != 0 && !vertex.get(v).isWasVisited()) {
                        Vertex neighbourVertex = vertex.get(v);
                        if (!vertexInLayer.contains(neighbourVertex)) {
                            vertexInLayer.add(neighbourVertex);
                        }
                    }
                }

                bfs(vertexInLayer, to);
            }
        }
    }

    private void step_toward(Queue<Vertex> queue, boolean isFromStart) {
        Vertex currentVertex = queue.poll();
        int currentVertexIndex = vertex.indexOf(currentVertex);

        if (isFromStart) {
            vertex.get(currentVertexIndex).setWasVisited(true);
            System.out.print("Передний ход: ");
            displayVertex(currentVertexIndex);
        } else {
            vertex.get(currentVertexIndex).setWasVisitedFromEnd(true);
            System.out.print("Задний ход: ");
            displayVertex(currentVertexIndex);
            System.out.println("@@");
        }

        for (int v = 0; v < MAX_VERTS; v++) {
            if (isFromStart && v != currentVertexIndex && connections[currentVertexIndex][v] != 0 && !vertex.get(v).isWasVisited()) {
                Vertex neighbourVertex = vertex.get(v);
                if (!queue.contains(neighbourVertex)) {
                    queue.add(neighbourVertex);
                }
            } else if (!isFromStart && v != currentVertexIndex && connections[currentVertexIndex][v] != 0 && !vertex.get(v).isWasVisitedFromEnd()) {
                Vertex neighbourVertex = vertex.get(v);
                if (!queue.contains(neighbourVertex)) {
                    queue.add(neighbourVertex);
                }
            }

        }
    }

    public void bidirectional_search(Queue<Vertex> startQueue, Queue<Vertex> endQueue) {
        startQueue.peek().setWasVisited(true);
        endQueue.peek().setWasVisitedFromEnd(true);
        int meeting = -1;

        while (meeting == -1 && !startQueue.isEmpty() && !endQueue.isEmpty()) {
            step_toward(startQueue, true);
            step_toward(endQueue, false);

            for (int v = 0; v < MAX_VERTS; v++) {
                if (vertex.get(v).isWasVisited() && vertex.get(v).isWasVisitedFromEnd()) {
                    meeting = v;
                    break;
                }
            }
        }

        if (meeting == -1) System.out.println("Не существует пути между заданными элементами");
        else System.out.println("Встреча в " + vertex.get(meeting).getName());
    }

    public void best_first_search(PriorityQueue<Edge> priorityQueue, Vertex to) {
        while (!to.isWasVisited() && !priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            Vertex currentVertex = edge.getVertex();
            if (currentVertex.isWasVisited()) continue;
            int currentVertexIndex = vertex.indexOf(currentVertex);

            vertex.get(currentVertexIndex).setWasVisited(true);
            displayVertex(currentVertexIndex);

            if (currentVertex.getName().equals(to.getName())) {
                priorityQueue.clear();
                break;
            } else {
                for (int v = 0; v < MAX_VERTS && !to.isWasVisited(); v++) {
                    if (v != currentVertexIndex && connections[currentVertexIndex][v] != 0 && !vertex.get(v).isWasVisited()) {
                        Vertex neighbourVertex = vertex.get(v);
                        Edge neighbourEdge = new Edge(neighbourVertex, connections[currentVertexIndex][v]);
                        if (!priorityQueue.contains(neighbourEdge)) {
                            priorityQueue.add(neighbourEdge);
                        }
                    }
                }
                best_first_search(priorityQueue, to);
            }
        }
    }

    public void gfbs(PriorityQueue<Vertex> priorityQueue, Vertex to) {
        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.poll();
            int currentVertexIndex = vertex.indexOf(currentVertex);
            System.out.println(currentVertex.getName()+" | Расстояние до цели: "+currentVertex.getHeuristicF());

            vertex.get(currentVertexIndex).setWasVisited(true);

            if (currentVertex.getName().equals(to.getName())) {
                priorityQueue.clear();
                break;
            } else {
                for (int v = 0; v < MAX_VERTS && !to.isWasVisited(); v++) {
                    if (v != currentVertexIndex && connections[currentVertexIndex][v] != 0 && !vertex.get(v).isWasVisited()) {
                        Vertex neighbourVertex = vertex.get(v);
                        if (!priorityQueue.contains(neighbourVertex)) {
                            priorityQueue.add(neighbourVertex);
                        }
                    }
                }
                gfbs(priorityQueue, to);
            }
        }
    }

    public void a_star(PriorityQueue<Vertex> priorityQueue, Vertex to) {
        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.poll();
            int currentVertexIndex = vertex.indexOf(currentVertex);
            System.out.println(currentVertex.getName()+" | Расстояние до цели: "+currentVertex.getHeuristicF()+" | Функция оценки: "+currentVertex.getEstimationFunction());

            vertex.get(currentVertexIndex).setWasVisited(true);

            if (currentVertex.getName().equals(to.getName())) {
                priorityQueue.clear();
                break;
            } else {
                for (int v = 0; v < MAX_VERTS && !to.isWasVisited(); v++) {
                    if (v != currentVertexIndex && connections[currentVertexIndex][v] != 0 && !vertex.get(v).isWasVisited()) {
                        Vertex neighbourVertex = vertex.get(v);
                        neighbourVertex.setEstimationFunction(connections[currentVertexIndex][v]+neighbourVertex.getHeuristicF());
                        if (!priorityQueue.contains(neighbourVertex)) {
                            priorityQueue.add(neighbourVertex);
                        }
                    }
                }
                a_star(priorityQueue, to);
            }
        }
    }


}
