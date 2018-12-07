package org.pvhees;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2Test {

    @Test
    public void givenInAssignment() {
        List<String> testcases = Arrays.asList("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab");
        System.out.println("Checksum on list = " + checksum(testcases));
    }

    @Test
    public void partOne() {
        List<String> testcases = readFileOfStrings("inputday2.txt");
        System.out.println("Checksum on list = " + checksum(testcases));

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
        return new boolean [] {
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
}