import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Collections;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug (project skeleton/frontend)
 */
/**
 * @author Vinay Maruri, vmaruri1@berkeley.edu (back end)
 * @version: 3.1
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    private final ArrayList<Long> ID = new ArrayList<Long>();
    final HashMap<Long, GraphDB.Node> nodes = new HashMap<Long, GraphDB.Node>();
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     *  @source: https://stackoverflow.com/questions/18448671/
     *  how-to-avoid-concurrentmodificationexception-while-removing-elements-from-arr
     *  NOTE: source is broken into two lines to avoid CS61B style checker wrath
     *  Purpose: The source was used to help solve the concurrentmodificationexception
     *  i experienced when trying to mutate the graph while iterating directly over it
     *  Instead, I took the top suggestion from this source and created an indirect
     *  way to iterate over the graph, which i have stored in a hashmap variable
     *  called nodes.
     */
    private void clean() {
        Iterator<Long> iter = this.nodes.keySet().iterator();
        while (iter.hasNext()) {
            long x = iter.next();
            if (this.nodes.get(x).adjacent.size() == 0) {
                iter.remove();
            }
        }
    }

    /**
     * private helper method to add nodes
     * void addlat(Long id, Double lat) {
     this.latitude.put(id, lat);
     }
     void addlon(Long id, Double lon) {
     this.longitude.put(id, lon);
     }
     */
    void addID(Long id) {
        this.ID.add(id);
    }
    void addNode(GraphDB.Node node) {
        this.nodes.put(node.id, node);
    }
    boolean exist(Long id) {
        if (this.ID.indexOf(id) != -1) {
            return true;
        }
        return false;
    }
    GraphDB.Node getNode(long id) {
        GraphDB.Node x;
        x = this.nodes.get(id);
        return x;
    }
    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return this.nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        GraphDB.Node x = getNode(v);
        return x.adjacent;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        Set<Long> keys = this.nodes.keySet();
        HashMap<Long, Double> distances = new HashMap<Long, Double>();
        for (Long x: keys) {
            GraphDB.Node n = getNode(x);
            double lon1 = n.lon;
            double lat1 = n.lat;
            double distance = distance(lon1, lat1, lon, lat);
            distances.put(x, distance);
        }
        double mindist = Collections.min(distances.values());
        long r = 0;
        for (Long x: distances.keySet()) {
            if (distances.get(x) == mindist) {
                r = x;
            }
        }
        return r;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        GraphDB.Node temp = getNode(v);
        return temp.lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        GraphDB.Node temp = getNode(v);
        return temp.lat;
    }

    static class Node implements Comparable<Node> {
        long id;
        double lat;
        double lon;
        ArrayList<Long> adjacent;
        GraphDB.Node prev;
        double priority;
        double pathDistance;
        int times;
        Node(long id, double lat, double lon, ArrayList<Long> adjacent,
             GraphDB.Node prev, double priority, double pathdist, int times) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.adjacent = new ArrayList<Long>();
            this.prev = prev;
            this.priority = priority;
            this.pathDistance = pathdist;
            this.times = 0;
        }
        public int compareTo(Node o) {
            return Double.compare(this.priority, o.priority);
        }
    }
}
