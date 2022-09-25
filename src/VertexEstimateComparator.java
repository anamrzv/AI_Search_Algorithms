import java.util.Comparator;

public class VertexEstimateComparator implements Comparator<Vertex> {
    public int compare (Vertex v1, Vertex v2) {
        return Integer.compare(v1.getEstimationFunction(), v2.getEstimationFunction());
    }
}
