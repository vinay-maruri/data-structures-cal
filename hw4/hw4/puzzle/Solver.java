package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;
import java.util.Deque;


public class Solver {
    private MinPQ<SearchNode> solvequeue = new MinPQ<SearchNode>();
    private int moves = 0;
    private Deque solution;
    public Solver(WorldState initial) {
        SearchNode initInsert = new SearchNode(initial, 0, null);
        solvequeue.insert(initInsert);
        solution = this.solveHelper();
    }

    private Deque solveHelper() {
        Deque goal = new ArrayDeque();
        SearchNode parent = solvequeue.delMin();
        if (parent.state.estimatedDistanceToGoal() == 0) {
            goal.addFirst(parent.state);
            return goal;
        }
        while (!parent.state.isGoal()) {
            Iterable<WorldState> neighbors = parent.state.neighbors();
            for (WorldState x : neighbors) {
                SearchNode insert = new SearchNode(x, parent.numMoves + 1, parent);
                if (parent.prev != null) {
                    if (!insert.state.equals(parent.prev.state)) {
                        solvequeue.insert(insert);
                    }
                }
                if (parent.prev == null) {
                    solvequeue.insert(insert);
                }
            }
            goal.addFirst(parent.state);
            parent = solvequeue.delMin();
        }
        goal.addFirst(parent.state);
        moves = parent.numMoves;
        Deque toReturn = new ArrayDeque();
        while (parent != null) {
            toReturn.addFirst(parent.state);
            parent = parent.prev;
        }
        return toReturn;
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return this.solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        WorldState state;
        int numMoves;
        SearchNode prev;
        int priority;

        private SearchNode(WorldState w, int num, SearchNode node) {
            state = w;
            numMoves = num;
            prev = node;
            priority = numMoves + state.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode x) {
            if (this.priority < x.priority) {
                return -1;
            } else if (this.priority == x.priority) {
                return 0;
            }
            return 1;
        }
    }
}
