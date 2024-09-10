import java.util.*;

public class PeptideMatcherLongInt {

    public static long convertToLong(String sequence) {
        long value = 0;
        for (int i = 0; i < sequence.length(); i++) {
            value = value * 26 + (sequence.charAt(i) - 'A');
        }
        return value;
    }

    public static Map<String, List<Integer>> findPeptidesPositions(String protein, List<String> peptideLibrary) {
        int k = 8;
        Map<Long, String> peptideLongToStrMap = new HashMap<>();
        Map<Long, List<Integer>> peptidePositionsMap = new HashMap<>();

        for (String peptide : peptideLibrary) {
            long peptideLong = convertToLong(peptide);
            peptideLongToStrMap.put(peptideLong, peptide);
            peptidePositionsMap.put(peptideLong, new ArrayList<>());
        }

        for (int i = 0; i <= protein.length() - k; i++) {
            String kmer = protein.substring(i, i + k);
            long kmerLong = convertToLong(kmer);

            if (peptidePositionsMap.containsKey(kmerLong)) {
                peptidePositionsMap.get(kmerLong).add(i);
            }
        }

        Map<String, List<Integer>> result = new HashMap<>();
        for (Map.Entry<Long, List<Integer>> entry : peptidePositionsMap.entrySet()) {
            String peptide = peptideLongToStrMap.get(entry.getKey());
            result.put(peptide, entry.getValue());
        }

        return result;
    }

    public static void main(String[] args) {
        String protein = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<String> peptideLibrary = Arrays.asList("ABCDEFGH", "IJKLMNOP", "QRSTUVWX", "XYZABCDE");

        Map<String, List<Integer>> peptidePositions = findPeptidesPositions(protein, peptideLibrary);

        for (Map.Entry<String, List<Integer>> entry : peptidePositions.entrySet()) {
            System.out.println("Peptide: " + entry.getKey() + " -> Positions: " + entry.getValue());
        }
    }
}
