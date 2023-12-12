package problem12;

import java.util.*;

public class SolutionPart1 {

    private static final String OPERATIONAL = ".";
    private static final String DAMAGED = "#";
    private static final String UNKNOWN = "?";
    private static final String SPACE = " ";
    private static final String COMMA = ",";

    public static void main(String... args) throws java.lang.Exception {
        Integer totalArrangements = 0;

        final List<String> inputList = getInputList();

        for(String input : inputList) {
            totalArrangements += calculateArrangements(input);
        }

        System.out.println("Total Arrangements = " + totalArrangements);
    }

    private static Integer calculateArrangements(final String input) {
        Integer arrangements = 0;

        final String codex = input.split(SPACE)[0];
        final String groups = input.split(SPACE)[1];
        final String reducedCodex = codex.replaceAll("\\.+", ".");
        final Integer codexLength = codex.length();
        final Integer reducedCodexLength = reducedCodex.length();
        final Integer groupsCount = groups.split(COMMA).length;
        final Integer totalDamaged = List.of(groups.split(COMMA)).stream()
            .map(s -> Integer.valueOf(s))
            .mapToInt(Integer::intValue)
            .sum();

        System.out.println("input: " + input);
        System.out.println("codex: " + codex);
        System.out.println("reducedCodex: " + reducedCodex);
        System.out.println("groups: " + groups);
        System.out.println("codexLength: " + codexLength);
        System.out.println("reducedCodexLength: " + reducedCodexLength);
        System.out.println("groupsCount: " + groupsCount);
        System.out.println("totalDamaged: " + totalDamaged);

        //If the difference between the reduced codex length and the number of damaged elements is
        //less than or equal to the the total number of groups, then they can only be separated by
        //operational elements. So there can only be 1 arrangement possible.
        if((reducedCodexLength - totalDamaged) <= groupsCount) {
            //Then there must only be 1 possible arrangement.
            arrangements++;
        }

        //TODO: Extend this to find the number of arrangements where it's more than 1.
        //      Expecting my solution here to involve generating all the possible permutations based on
        //      the groups and then comparing those to the codex and seeing which ones would match

        return arrangements;
    }

    private static List<String> getInputList() {
        final List<String> inputList = new ArrayList<>();

        inputList.add("???.### 1,1,3");
        inputList.add(".??..??...?##. 1,1,3");
        inputList.add("?#?#?#?#?#?#?#? 1,3,1,6");
        inputList.add("????.#...#... 4,1,1");
        inputList.add("????.######..#####. 1,6,5");
        inputList.add("?###???????? 3,2,1");

        return inputList;
    }
}