package bearmaps.proj2d;

import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2ab.Point;

import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;
import bearmaps.proj2d.Trie;


import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Point> vertices;
    private HashMap<Point, Long> map;
    private WeirdPointSet pointSet;
    private Trie trieSet;
    private HashMap<String, List<Node>> cleanMap; // maps cleaned name to list of nodes



    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        vertices = new ArrayList<>();

        List<Node> nodes = this.getNodes();
        map = new HashMap<>();
        cleanMap = new HashMap<>();
        trieSet = new Trie();


        for (Node n : nodes) {
            if (n.name() != null) {
                String cleanName = cleanString(n.name());
                trieSet.add(cleanName);
                if (!cleanMap.containsKey(cleanName)) {
                    cleanMap.put(cleanName, new ArrayList<>());
                }
                List<Node> listOfNodes = cleanMap.get(cleanName);
                listOfNodes.add(n);
            }

            if (!this.neighbors(n.id()).isEmpty()) {
                Long nodeid = n.id();
                Point current = new Point(n.lon(), n.lat());
                map.put(current, nodeid);
                vertices.add(current);

            }



        }
        pointSet = new WeirdPointSet(vertices);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point point = pointSet.nearest(lon, lat);
        return map.get(point);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {

        List<String> found = new LinkedList<>();
        List<String> keys = trieSet.keysWithPrefix(cleanString(prefix));
        // List of all keys matching prefix
        for (String k : keys) {
            for (Node n : cleanMap.get(k)) {
                String nodeName = n.name();
                if (!found.contains(nodeName)) {
                    found.add(nodeName);
                }
            }
        }
        return found;
//        List<String> found = new LinkedList<>();
//        Iterable<String> key = trieSet.keysWithPrefix(cleanString(prefix));
//        List<Iterable<String>> keys = new LinkedList<>();
//        keys.add(key);
//        // List of all keys matching prefix
//        for (Iterable<String> k : keys) {
//            List<Node> full = cleanMap.get(k);
//            if (full != null) {
//                for (Node f : full) {
//                    found.add(f.name());
//                }
//            }
//        }
//        return found;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Node> nodes = cleanMap.get(cleanString(locationName));
        ArrayList<Map<String, Object>> loc = new ArrayList<>(nodes.size());
        for (Node n : nodes) {
            Map<String, Object> l = new HashMap<>(6);
            l.put("lat", n.lat());
            l.put("lon", n.lon());
            l.put("name", n.name());
            l.put("id", n.id());
            loc.add(l);
        }
        return loc;

    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
