public class Vertex implements Comparable<Vertex> {
    private String name;
    private boolean wasVisited;

    private boolean wasVisitedFromEnd;

    private int heuristicFunction;

    private int estimationFunction;

    public Vertex(final String name, int heuristicFunction) {
        this.name = name;
        this.heuristicFunction = heuristicFunction;
        wasVisited = false;
        wasVisitedFromEnd = false;
    }

    public String getName() {
        return this.name;
    }

    public int getHeuristicF() {
        return heuristicFunction;
    }

    public boolean isWasVisited() {
        return this.wasVisited;
    }

    public boolean isWasVisitedFromEnd() {
        return this.wasVisitedFromEnd;
    }

    public void setWasVisited(final boolean wasVisited) {
        this.wasVisited = wasVisited;
    }

    public void setWasVisitedFromEnd(final boolean wasVisited) {
        this.wasVisitedFromEnd = wasVisited;
    }

    public void setEstimationFunction(int estimationFunction) {
        this.estimationFunction = estimationFunction;
    }

    public int getEstimationFunction() {
        return this.estimationFunction;
    }

    @Override
    public int compareTo(Vertex o) {
        return Integer.compare(heuristicFunction, o.heuristicFunction);
    }
}
