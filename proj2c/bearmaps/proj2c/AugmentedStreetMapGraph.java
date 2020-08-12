package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private KDTree pointsHasNeighborKDTree;
    private Map<Point, Node> pointsToNodes = new HashMap<>();

    private MyTrieSet cleanedNames = new MyTrieSet();
    private Map<String, List<Node>> cleanNameToNodes = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath); //Constructor of StreetMapGraph (String filename)
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> pointsHasNeighbor = new ArrayList<>();
        for (Node node : nodes) {
            Point point = new Point(node.lon(), node.lat());
            pointsToNodes.put(point, node);
            if (!neighbors(node.id()).isEmpty()) {
                pointsHasNeighbor.add(point);
            }

            // MyTrieSet and Map
            String cleanedName = cleanName(node.name());
            List<Node> cleanedNameNodes = new LinkedList<>();
            cleanedNames.add(cleanedName);
            if (cleanNameToNodes.containsKey(cleanedName)) {
                cleanedNameNodes = cleanNameToNodes.get(cleanedName);
                cleanedNameNodes.add(node);
                cleanNameToNodes.replace(cleanedName, cleanedNameNodes);
            } else {
                cleanedNameNodes.add(node);
                cleanNameToNodes.put(cleanedName, cleanedNameNodes);
            }

        }
        pointsHasNeighborKDTree= new KDTree(pointsHasNeighbor);
    }

    private String cleanName(String fullName) {
        String cleanedName = new String();
        if(fullName != null) {
            String lowerFullName = fullName.toLowerCase();
            for (int i = 0; i < lowerFullName.length(); i++) {
                char s = lowerFullName.toLowerCase().charAt(i);
                if ((s >= 'a' && s <= 'z') || s == ' ' || s == '\t') {
                    cleanedName = cleanedName + s;
                }
            }
        }
        return cleanedName;
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point closestPoint = pointsHasNeighborKDTree.nearest(lon, lat);
        Node closetNode = pointsToNodes.get(closestPoint);
        return closetNode.id();
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
        Set<String> fullNamesByPrefix = new HashSet<>();
        for (String cleanedName : cleanedNames.keysWithPrefix(prefix)) {
            for (Node node : cleanNameToNodes.get(cleanedName)) {
                fullNamesByPrefix.add(node.name());
            }
        }
        List<String> FullNamesByPrefix = new ArrayList<>();
        FullNamesByPrefix.addAll(fullNamesByPrefix);
        return FullNamesByPrefix;
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
        List<Map<String, Object>> locations = new LinkedList<>();

        if (cleanNameToNodes.containsKey(cleanName(locationName))) {
            for (Node node : cleanNameToNodes.get(cleanName(locationName))) {
                Map<String, Object> location = new HashMap<>();
                location.put("lat", node.lat());
                location.put("lon", node.lon());
                location.put("name", node.name());
                location.put("id", node.id());
                locations.add(location);
            }
        }
        return locations;
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
