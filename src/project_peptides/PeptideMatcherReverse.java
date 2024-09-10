import java.util.*;

public class PeptideMatcherReverse {

    public static Map<String, List<Integer>> findPeptidesPositions(String protein, List<String> peptideLibrary) {
        int k = 8;
        Map<String, List<Integer>> peptideMap = new HashMap<>();

        for (String peptide : peptideLibrary) {
            peptideMap.put(peptide, new ArrayList<>());
        }

        for (int i = 0; i <= protein.length() - k; i++) {
            String kmer = protein.substring(i, i + k);

            if (peptideMap.containsKey(kmer)) {
                peptideMap.get(kmer).add(i);
            }
        }

        return peptideMap;
    }
}
