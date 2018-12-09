package org.pvhees;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Day5Test {
    @Test
    public void part1Example() {
        String polymer = "dabAcCaCBAcCcaDA";
        String expectedResult = "dabCBAcaDA";
        assertEquals(expectedResult, fullyReact(polymer));
    }

    @Test
    public void part1ForReal() {
        String polymer = readFileOfOneString("inputday5.txt");
        String result = fullyReact(polymer);
        System.out.println("remaining units = " + result.length());
     }

    private String fullyReact(String polymer) {
        String beforeReacting = polymer;
        String afterReacting = "";
        while (true) {
            afterReacting = singleReact(beforeReacting);
            if (beforeReacting.equals(afterReacting)) break;
            beforeReacting = afterReacting;
        }

        return afterReacting;
    }

    private String singleReact(String beforeReacting) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < beforeReacting.length(); i++) {
            char currentChar = beforeReacting.charAt(i);
            if (notLastCharacter(i, beforeReacting)) {
                char nextCharacter = beforeReacting.charAt(i + 1);
                if (unitsDestroyEachother(currentChar, nextCharacter)) {
                    s.append(removeUnits(i, beforeReacting));
                    break;
                }
            }
            s.append(currentChar);
        }

        return s.toString();
    }

    private String removeUnits(int i, String s) {
        return s.substring(i + 2);
    }

    private boolean notLastCharacter(int i, String s) {
        return i < s.length() - 1;
    }

    private boolean unitsDestroyEachother(char c1, char c2) {
        return c1 != c2 && (Character.toLowerCase(c1) == Character.toLowerCase(c2));
    }

    public String readFileOfOneString(String file) {
        URI resource = null;
        try {
            resource = Day1.class.getClassLoader().getResource(file).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try (Stream<String> lines = Files.lines(Paths.get(resource))) {
            return lines.collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
