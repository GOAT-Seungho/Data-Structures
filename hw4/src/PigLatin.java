import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PigLatin {
    static List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

    public static String toPigLatin(String input) {
        
        if (input.isEmpty()) {
            return null;
        }

        int firstVowelIndex = 0;

        outerloop: 
            for (int j = 0 ; j < input.length() ; j++) {
                for (int k = 0 ; k < vowels.size() ; k++) {
                    if (vowels.get(k).equals(input.charAt(j))) {
                        firstVowelIndex = j;
                        break outerloop;
                    }
                }
            }

        return input.substring(firstVowelIndex) + input.substring(0, firstVowelIndex) + "ay";
    }

    public static void main(String[] args) {
        List<String> words = List.of("pig", "latin", "smile", "string", "eat");

        System.out.println(words.stream()
                                .map(PigLatin::toPigLatin)
                                .collect(Collectors.toList()));
    }
}

