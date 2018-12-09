package org.pvhees;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Day2Test {

    @Test
    public void part1Example() {
        List<String> boxIds = Arrays.asList("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab");
        assertEquals(12, checksum(boxIds));
    }

    @Test
    public void part1() {
        List<String> boxIds = readFileOfStrings("inputday2.txt");
        System.out.println("Part 1, checksum = " + checksum(boxIds));
    }

    private long checksum(List<String> input) {
        int twoes = 0;
        int threes = 0;
        for (String line : input) {
            boolean[] checksumString = checksumString(line);
            if (checksumString[0]) twoes++;
            if (checksumString[1]) threes++;
        }
        return twoes * threes;
    }

    private boolean[] checksumString(String s) {
        Map<String, Long> checksumParts = checksumEntries(s);
        return new boolean[]{
                checksumParts.values().stream().filter(v -> v == 2).count() > 0,
                checksumParts.values().stream().filter(v -> v == 3).count() > 0
        };
    }

    private Map<String, Long> checksumEntries(String s) {
        return Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public List<String> readFileOfStrings(String file) {
        URI resource = null;
        try {
            resource = Day1.class.getClassLoader().getResource(file).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try (Stream<String> lines = Files.lines(Paths.get(resource))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Test
    public void part2Example() {
        String expected = "fgij";
        List<String> boxIds = Arrays.asList("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz");
        String commonLetters = findCommonLetters(boxIds);
        assertEquals(expected, commonLetters);
    }

    @Test
    public void part2() {
        List<String> boxIds = readFileOfStrings("inputday2.txt");
        String commonLetters = findCommonLetters(boxIds);
        System.out.println("Part2, common letters = " + commonLetters);
    }

    private String findCommonLetters(List<String> boxIds) {
        List<String> commonBoxIds = findCommonBoxIds(boxIds);
        return matchingLetters(commonBoxIds);
    }

    private List<String> findCommonBoxIds(List<String> boxIds) {
        for (int i = 0; i < boxIds.size(); i++) {
            for (int j = 0; j < boxIds.size(); j++) {
                if (i != j) {
                    if (matches(boxIds.get(i), boxIds.get(j))) {
                        return Arrays.asList(boxIds.get(i), boxIds.get(j));
                    }
                }
            }

        }
        return null;
    }

    private boolean matches(String s1, String s2) {
        if (s1.length() != s2.length())
            throw new IllegalArgumentException(String.format("Strings moeten gelijke lengte hebben (%s, %s)", s1, s2));
        int matchingLetterCount = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                matchingLetterCount++;
            }
        }

        return matchingLetterCount == (s1.length() - 1);
    }

    private String matchingLetters(List<String> commonBoxIds) {
        String id1 = commonBoxIds.get(0);
        String id2 = commonBoxIds.get(1);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < id1.length(); i++) {
            if (id1.charAt(i) == id2.charAt(i)) {
                result.append(id1.charAt(i));
            }
        }

        return result.toString();
    }
}