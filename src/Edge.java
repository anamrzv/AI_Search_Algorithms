public class Edge implements Comparable<Edge>{
    private Vertex v;
    private int distance;

    public Edge(Vertex vertex, int distance) {
        this.v = vertex;
        this.distance = distance;
    }

    public Vertex getVertex() {
        return v;
    }

    @Override
    public int compareTo(Edge o) {
        if (o.distance < distance) return 1;
        else return -1;
    }
}
