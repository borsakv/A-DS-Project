/*
 * This class is essentially an edge weighted directed graph design to store the
 * bus network - Created by Cian Jinks 26 Apr 22:39
 */

import com.sun.source.tree.NewArrayTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class BusNetwork
{
    // Class is essentially a pair used for nodes in the network
    static class NetworkNode
    {
        public double weight;
        public int stopID;

        public NetworkNode(double weight, int stopID)
        {
            this.weight = weight;
            this.stopID = stopID;
        }
    }

    private HashMap<Integer, ArrayList<NetworkNode>> adjacencyList;     // Maps stopID to adjacent nodes
    private HashMap<Integer, BusStop> lookupTable;                      // Maps stopID to corresponding BusStop

    public BusNetwork()
    {
        adjacencyList = new HashMap<>();
        lookupTable = new HashMap<>();
    }

    // Initialise using a list of stops
    public BusNetwork(ArrayList<BusStop> stops)
    {
        adjacencyList = new HashMap<>();
        lookupTable = new HashMap<>();
        for(BusStop stop : stops)
        {
            addStop(stop);
        }
    }

    void addStop(BusStop stop)
    {
        lookupTable.put(stop.stopId, stop);
        adjacencyList.put(stop.stopId, new ArrayList<>());
    }

    void addEdge(int fromStopID, int toStopID, double weight)
    {
        // If the from stop was not added to the graph before we will add it here
        adjacencyList.computeIfAbsent(fromStopID, k -> {
            System.out.println("[DEBUG] Edge was added with stopID that doesn't exist in the network yet: " + fromStopID);
            return new ArrayList<>();
        });
        // If the to stop was not added to the graph before we will add it here
        adjacencyList.computeIfAbsent(toStopID, k -> {
            System.out.println("[DEBUG] Edge was added with stopID that doesn't exist in the network yet: " + toStopID);
            return new ArrayList<>();
        });

        adjacencyList.get(fromStopID).add(new NetworkNode(weight, toStopID));
    }

    double getConnection(int fromStopID, int toStopID)
    {
        return adjacencyList.get(fromStopID).get(toStopID).weight;
    }

    ArrayList<NetworkNode> getConnections(int stopID)
    {
        return adjacencyList.get(stopID);
    }

    // Can return null
    BusStop lookup(int stopID)
    {
        return lookupTable.get(stopID);
    }

    // Uses dijkstra since we have no information with which to make an heuristic for something like A*
    ArrayList<Integer> getShortestPath(int fromStopID, int toStopID, double[] r_Distance)
    {
        HashMap<Integer, Double> distTo = new HashMap<>(adjacencyList.size());
        HashMap<Integer, Integer> prev = new HashMap<>(adjacencyList.size());
        HashSet<Integer> nodeSet = new HashSet<>(adjacencyList.size());

        for(int key : adjacencyList.keySet())
        {
            distTo.put(key, Double.POSITIVE_INFINITY);
            prev.put(key, Integer.MAX_VALUE);
            nodeSet.add(key);
        }
        distTo.put(fromStopID, 0.0);

        while(!nodeSet.isEmpty())
        {
            // Find vertex u in nodeSet with minimum distTo[u]
            int u = Integer.MAX_VALUE;
            double minVal = Double.POSITIVE_INFINITY;
            for(int v : nodeSet)
            {
                double newVal = distTo.get(v);
                if(newVal < minVal)
                {
                    minVal = newVal;
                    u = v;
                }
            }
            if(u == Integer.MAX_VALUE) { break; } // No more available nodes to check

            nodeSet.remove(u);
            if(u == toStopID) { break; } // Terminate search if we have found the target

            // For each neighbour v of u
            ArrayList<NetworkNode> neighbours = adjacencyList.get(u);
            if(neighbours != null) {
                for (NetworkNode v : neighbours) {
                    if (v.weight != Double.POSITIVE_INFINITY) {
                        // Relax
                        double alt = distTo.get(u) + v.weight;
                        if (distTo.get(v.stopID) > alt) {
                            distTo.put(v.stopID, alt);
                            prev.put(v.stopID, u);
                        }
                    }
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        int u = toStopID;
        if(prev.get(u) != Integer.MAX_VALUE || u == fromStopID)
        {
            while(u != Integer.MAX_VALUE)
            {
                result.add(0, u);
                u = prev.get(u);
            }
        }

        r_Distance[0] = distTo.get(toStopID);
        return result;
    }

    // For testing
    public static void main(String[] args)
    {
        // Using first 5 stops from the first route in stop_times.txt
        ArrayList<BusStop> testStops = new ArrayList<>();
        testStops.add(new BusStop(646,50640, "DUNBAR LOOP","DUNBAR @ LOOP",
                49.234598,-123.185765,"ZN 99", "",0, ""));
        testStops.add(new BusStop(378,50374,"EB W 41 AVE FS COLLINGWOOD ST","W 41 AVE @ COLLINGWOOD ST",
                49.234692,-123.181654,"ZN 99", "",0, ""));
        testStops.add(new BusStop(379,50375,"EB W 41 AVE FS BLENHEIM ST","W 41 AVE @ BLENHEIM ST",
                49.234696,-123.178303,"ZN 99", "",0, ""));
        testStops.add(new BusStop(381,50377,"EB W 41 AVE FS CARNARVON ST","W 41 AVE @ CARNARVON ST",
                49.234677,-123.172505,"ZN 99", "",0, ""));
        testStops.add(new BusStop(1269,51259,"NB MACKENZIE ST FS W 38 AVE","MACKENZIE ST @ W 38 AVE",
                49.237919,-123.170182,"ZN 99", "",0, ""));
        BusNetwork network = new BusNetwork(testStops);

        /* Adding edges based on:
        9017927, 5:25:00, 5:25:00,646,1,,0,0,
        9017927, 5:25:50, 5:25:50,378,2,,0,0,0.3300
        9017927, 5:26:28, 5:26:28,379,3,,0,0,0.5780
        9017927, 5:27:33, 5:27:33,381,4,,0,0,1.0061
        9017927, 5:28:52, 5:28:52,1269,5,,0,0,1.5221
        */
        network.addEdge(646, 378, 1);
        network.addEdge(378, 379, 1);
        network.addEdge(379, 381, 1);
        network.addEdge(381, 1269, 1);

        /* Adding some transfers
        646,1907,0,
        646,647,0,
        1477,1394,2,300
        */
        network.addEdge(646, 1907, 2);
        network.addEdge(646, 647, 2);
        network.addEdge(1477, 1394, 3);

        // Testing shortest path finding
        double[] returnedDistance = new double[1];
        ArrayList<Integer> pathTaken = network.getShortestPath(646, 1269, returnedDistance);
        if(returnedDistance[0] == Double.POSITIVE_INFINITY) {
            System.out.println("No route from from 646 to 1269");
        }
        else {
            System.out.println("Distance from 646 to 1269 is: " + returnedDistance[0]);
            System.out.print("Path Taken: ");
            for(Integer i : pathTaken)
            {
                System.out.print(i + ", ");
            }
            System.out.println();
        }
    }
}
