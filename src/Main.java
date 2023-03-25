/*
 * User: levon
 * Date: 25.03.2023
 * Time: 07:55
 */

import java.util.*;

public class Main {
  private static final Map<String, Integer> costs = new HashMap<>();
  private static final Set<String> processed = new HashSet<>();

  public static void main(String[] args) {
    // This graph I have got from book Grokking Algorithms
    final Map<String, Map<String, Integer>> graph = new HashMap<>();
    graph.put("book", new HashMap<>(){{
      put("plate", 5);
      put("poster", 0);
    }});
    graph.put("plate", new HashMap<>(){{
      put("guitar", 15);
      put("drum", 20);
    }});
    graph.put("poster", new HashMap<>(){{
      put("guitar", 30);
      put("drum", 35);
    }});
    graph.put("guitar", new HashMap<>(){{
      put("piano", 20);
    }});
    graph.put("drum", new HashMap<>(){{
      put("piano", 10);
    }});

    // Let's fill costs as default values
    for (String p : graph.keySet()) {
      costs.put(p, Integer.MAX_VALUE);

      Map<String, Integer> c = graph.get(p);
      for (String v : c.keySet()) {
        costs.put(v, Integer.MAX_VALUE);
      }
    }
    costs.put("book", 0); // Start from here
    System.out.println("Costs: " + costs);

    // This needs to construct path
    final Map<String, String> parents = new HashMap<>();

    // Main loop
    String node;
    while ((node = findLowerCostNode()) != null) {
      Integer cost = costs.get(node);
      Map<String, Integer> neighbors = graph.get(node);
      if (neighbors != null) {
        for (String neighbor : neighbors.keySet()) {
          int newCost = cost + neighbors.get(neighbor);
          if (costs.get(neighbor) > newCost) {
            costs.put(neighbor, newCost);
            parents.put(neighbor, node);
          }
        }
      }

      processed.add(node);
    }

    // Let's construct path
    Deque<String> list = new LinkedList<>();
    String n = "piano";
    list.offerFirst(n);
    while ((n = parents.get(n)) != null) {
      list.offerFirst(n);
    }
    System.out.println("Path: " + list);

    // Let's show additional info
    for (String e : list) {
      Integer price = 0;
      String p = parents.get(e);
      if (p != null) {
        Map<String, Integer> c = graph.get(p);
        price = c.get(e);
      }

      System.out.println("Node: " + e + ", Price: " + price + ", Cost: " + costs.get(e));
    }
  }

  /**
   * Let's find a lower cost node!
   * @return node
   */
  public static String findLowerCostNode() {
    String minNode = null;
    Integer minCost = Integer.MAX_VALUE;
    for (String node : costs.keySet()) {
      if (processed.contains(node))
        continue;

      if (minCost > costs.get(node)) {
        minNode = node;
        minCost = costs.get(node);
      }
    }
    System.out.println("MinNode: " + minNode + ", MinCost: " + minCost);

    return minNode;
  }
}