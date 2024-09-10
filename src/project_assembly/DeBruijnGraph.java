import java.util.*;

public class DeBruijnGraph {

    public static Map<String, List<String>> buildDeBruijnGraph(String sequence, int k) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> outDegree = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        for (int i = 0; i <= sequence.length() - k; i++) {
            String kMer = sequence.substring(i, i + k);
            String prefix = kMer.substring(0, k - 1);
            String suffix = kMer.substring(1, k);

            graph.putIfAbsent(prefix, new ArrayList<>());
            graph.get(prefix).add(suffix);

            
            outDegree.put(prefix, outDegree.getOrDefault(prefix, 0) + 1);
            inDegree.put(suffix, inDegree.getOrDefault(suffix, 0) + 1);
        }

        return graph;
    }

    public static List<String> findEulerianPath(Map<String, List<String>> graph) {
        Stack<String> stack = new Stack<>();
        List<String> path = new ArrayList<>();
        String startNode = findStartNode(graph);

        stack.push(startNode);

        while (!stack.isEmpty()) {
            String node = stack.peek();
            if (graph.containsKey(node) && !graph.get(node).isEmpty()) {
                String nextNode = graph.get(node).remove(0);
                stack.push(nextNode);
            } else {
                path.add(stack.pop());
            }
        }

        Collections.reverse(path);
        return path;
    }

    public static String findStartNode(Map<String, List<String>> graph) {
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, Integer> outDegree = new HashMap<>();

        for (String node : graph.keySet()) {
            outDegree.put(node, graph.get(node).size());
            for (String neighbor : graph.get(node)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        String startNode = null;
        for (String node : outDegree.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);

            if (out > in) {
                startNode = node;
                break;
            }
        }

        if (startNode == null) {
            startNode = graph.keySet().iterator().next();
        }

        return startNode;
    }
    
    public static String reconstructGenome(List<String> path) {
        StringBuilder genome = new StringBuilder(path.get(0));

        for (int i = 1; i < path.size(); i++) {
            genome.append(path.get(i).charAt(path.get(i).length() - 1));
        }

        return genome.toString();
    }

    public static void main(String[] args) {
        String sequence = "GATCACAGGTCTATCACCCTATTAACCACTCACGGGAGCTCTCCATGCATTTGGTATTTTCGTCTGGGGGGTGTGCACGCGATAGCATTGCGAGACGCTGGAGCCGGAGCACCCTATGTCGCAGTATCTGTCTTTGATTCCTGCCTCATTCTATTATTTATCGCACCTACGTTCAATATTACAGGCGAACATACCTACTAAAGTGTGTTAATTAATTAATGCTTGTAGGACATAATAATAACAATTGAAT";
        int k = 4;
        Map<String, List<String>> graph = buildDeBruijnGraph(sequence, k);
        List<String> eulerianPath = findEulerianPath(graph);
        String reconstructedGenome = reconstructGenome(eulerianPath);
        System.out.println("Reconstructed Genome: " + reconstructedGenome);
    }
}
