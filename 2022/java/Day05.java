/**
 * Script to find final stack arrangement after shifting values between stacks.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Day05Data;
import utils.Day05Instruction;
import utils.Day;

public class Day05 extends Day {
    int numStacks;

    Day05() {
        this.numStacks = 9;
    }

    private Day05Data parseData() {
        Day05Data data;
        String line;
        List<Deque<Character>> stacks;
        Deque<Character> stack;
        int stackCount;
        char stackChar;
        Pattern pattern;
        Matcher matcher;
        List<Day05Instruction> instructions;
        Day05Instruction instruction;

        stacks = new ArrayList<>();
        for (int i = 0; i < this.numStacks; i++) {
            stack = new ArrayDeque<>();
            stacks.add(stack);
        }

        instructions = new ArrayList<>();

        pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        while (this.scanner.hasNextLine()) {
            line = this.scanner.nextLine();
            // Parse columns into stack arrangement
            if (line.contains("[")) {
                stackCount = 0;
                for (int i = 1; i < (this.numStacks * 4) - 1; i += 4) {
                    if (i >= line.length()) {
                        break;
                    }

                    stackChar = line.charAt(i);
                    if (Character.isLetter(stackChar)) {
                        stacks.get(stackCount).addFirst(stackChar);
                    }
                    stackCount++;
                }

            }

            // Parse moving instructions
            else {
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    instruction = new Day05Instruction(matcher.group(1), matcher.group(2), matcher.group(3));
                    instructions.add(instruction);
                }
            }
        }
        data = new Day05Data(stacks, instructions);

        return data;
    }

    private String solveOne(Day05Data data) {
        List<Deque<Character>> stacks;
        char stackChar;

        // Deep copy stacks data
        stacks = new ArrayList<>();
        for (Deque<Character> stack : data.stacks()) {
            stacks.add(new ArrayDeque<>(stack));
        }

        for (Day05Instruction instruction : data.instructions()) {
            // Move value from stacks one at a time per instruction
            for (int i = 0; i < instruction.getAmount(); i++) {
                stackChar = stacks.get(instruction.getStart() - 1).removeLast();
                stacks.get(instruction.getEnd() - 1).add(stackChar);
            }
        }

        // Find values at the top of each stack
        StringBuilder stringBuilder = new StringBuilder();
        for (Deque<Character> stack : stacks) {
            stringBuilder.append(stack.getLast());
        }

        return stringBuilder.toString();
    }

    private String solveTwo(Day05Data data) {
        List<Deque<Character>> stacks;
        List<Character> movingStack;
        char stackChar;

        // Deep copy stacks data
        stacks = new ArrayList<>();
        for (Deque<Character> stack : data.stacks()) {
            stacks.add(new ArrayDeque<>(stack));
        }

        for (Day05Instruction instruction : data.instructions()) {
            // Move multiple values from stacks at a time per instruction, maintaining order
            movingStack = new ArrayList<>();
            for (int i = 0; i < instruction.getAmount(); i++) {
                stackChar = stacks.get(instruction.getStart() - 1).removeLast();
                movingStack.add(stackChar);
            }
            for (int i = 0; i < instruction.getAmount(); i++) {
                stackChar = movingStack.remove(movingStack.size() - 1);
                stacks.get(instruction.getEnd() - 1).add(stackChar);
            }
        }

        // Find values at the top of each stack
        StringBuilder stringBuilder = new StringBuilder();
        for (Deque<Character> stack : stacks) {
            stringBuilder.append(stack.getLast());
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Day05 day05;
        Day05Data data;
        String topStacksSingleMove;
        String topStacksMultipleMove;

        day05 = new Day05();
        data = day05.parseData();

        // Top stacks from shifting single value per move
        topStacksSingleMove = day05.solveOne(data);
        System.out.println("part one solution: " + topStacksSingleMove);

        // Top stacks from shifting multiple values per move
        topStacksMultipleMove = day05.solveTwo(data);
        System.out.println("part two solution: " + topStacksMultipleMove);
    }
}
