package lab11.graphs;
import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int[] secretEdgeTo;
    private Stack<Integer> cycle;
    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        secretEdgeTo = new int[maze.V()];
        dfs(1,1);
        int lastv = -1;
        if (cycle == null) {
            return;
        }
    }

    private void dfs(int z, int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (cycle != null) {
                return;
            }
            if (!marked[w]) {
                secretEdgeTo[w] = z;
                dfs(z, w);
            } else if (z != v) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = secretEdgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(v);
                cycle.push(w);
            }
        }
    }
    // Helper methods go here
}

