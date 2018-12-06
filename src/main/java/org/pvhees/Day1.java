package org.pvhees;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {

    public List<Integer> readFileOfIntegers(String file) throws URISyntaxException, IOException {
        URI resource = Day1.class.getClassLoader().getResource(file).toURI();
        try (Stream<String> lines = Files.lines(Paths.get(resource))) {
            return lines.map(Integer::parseInt).collect(Collectors.toList());
        }
    }

    public int firstRepeatSum(List<Integer> numbers) {
        int total=0;

        Map<Integer, Integer> totalIndex = new HashMap<>();
        while (true) {
            for (int number : numbers) {
                total += number;
                if (totalIndex.get(total) != null) {
                    return total;
                }
                totalIndex.put(total, 0);
            }
        }
    }

    public int sum(List<Integer> integers) {
        return integers.stream().reduce(Integer::sum).get();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Day1 day1 = new Day1();
        List<Integer> integers = day1.readFileOfIntegers("inputday1.txt");
        System.out.println("Last freq   : "+ day1.sum(integers));
        System.out.println("First repeat: "+ day1.firstRepeatSum(integers));
    }
}
