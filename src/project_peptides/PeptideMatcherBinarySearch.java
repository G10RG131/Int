import java.util.*;

public class PeptideMatcherBinarySearch {

    public static long convertToLong(String sequence) {
        long value = 0;
        for (int i = 0; i < sequence.length(); i++) {
            value = value * 26 + (sequence.charAt(i) - 'A');
        }
        return value;
    }

    // Binary search helper function
    public static int binarySearch(long[] array, long target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

        public static Map<String, List<Integer>> findPeptidesPositions(String protein, List<String> peptideLibrary) {
        int k = 8;
        long[] peptideLongs = new long[peptideLibrary.size()];
        Map<Long, String> peptideLongToStrMap = new HashMap<>();

        for (int i = 0; i < peptideLibrary.size(); i++) {
            long peptideLong = convertToLong(peptideLibrary.get(i));
            peptideLongs[i] = peptideLong;
            peptideLongToStrMap.put(peptideLong, peptideLibrary.get(i));
        }
        Arrays.sort(peptideLongs);

        Map<String, List<Integer>> result = new HashMap<>();
        for (String peptide : peptideLibrary) {
            result.put(peptide, new ArrayList<>());
        }

        for (int i = 0; i <= protein.length() - k; i++) {
            String kmer = protein.substring(i, i + k);
            long kmerLong = convertToLong(kmer);

            int index = binarySearch(peptideLongs, kmerLong);
            if (index != -1) {
                String matchedPeptide = peptideLongToStrMap.get(kmerLong);
                result.get(matchedPeptide).add(i);
            }
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
