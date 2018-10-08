package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int x;
    private int y;
    private boolean target = false;
    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        x = maze.xyTo1D(sourceX, sourceY);
        y = maze.xyTo1D(targetX, targetY);
        distTo[x] = 0;
        edgeTo[x] = x;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new ArrayDeque<Integer>();
        marked[this.x] = true;
        q.add(this.x);
        announce();
        if (x == y) {
            target = true;
        }
        if (target) {
            return;
        }

        while(!q.isEmpty()) {
            int v = q.remove();
            for (int x: maze.adj(v)) {
                if (!marked[x]) {
                    if (x != v) {
                        edgeTo[x] = v;
                    }
                    distTo[x] = distTo[v] + 1;
                    marked[x] = true;
                    announce();
                    q.add(x);
                    if (target) {
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

