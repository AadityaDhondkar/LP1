// Design suitable Data structures and implement Pass-I and Pass-II of a two-pass assembler for pseudo
// machine. Implementation should consist of a few instructions from each category and few assembler 
// directives. The output of Pass-I (intermediate code file and symbol table) should be input for Pass-II.

import java.util.*;

class TwoPassAssembler {
    private Map<String, Integer> symbolTable = new HashMap<>();
    private List<String> intermediateCode = new ArrayList<>();
    private int locationCounter = 0;

    public void passOne(List<String> sourceCode) {
        for (String line : sourceCode) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith(";")) {
                continue; // Skip empty lines and comments
            }

            String[] parts = line.split("\\s+");
            String instruction = parts[0];

            switch (instruction) {
                case "START":
                    locationCounter = Integer.parseInt(parts[1]);
                    break;
                case "END":
                    break;
                case "WORD":
                    intermediateCode.add(locationCounter + ": WORD " + parts[1]);
                    locationCounter++;
                    break;
                case "RESW":
                    int size = Integer.parseInt(parts[1]);
                    intermediateCode.add(locationCounter + ": RESW " + size);
                    locationCounter += size;
                    break;
                default:
                    if (instruction.endsWith(":")) {
                        String label = instruction.substring(0, instruction.length() - 1);
                        symbolTable.put(label, locationCounter);
                        instruction = parts[1]; // The next part is the actual instruction
                    }
                    intermediateCode.add(locationCounter + ": " + instruction + " " + Arrays.toString(Arrays.copyOfRange(parts, 1, parts.length)));
                    locationCounter++;
            }
        }
    }

    public List<String> passTwo() {
        List<String> finalOutput = new ArrayList<>();
        for (String code : intermediateCode) {
            String[] parts = code.split(": ");
            String loc = parts[0];
            String instruction = parts[1];
            String[] operands = instruction.split(" ", 2);
            String opcode = operands[0];
            String operandPart = operands.length > 1 ? operands[1] : "";

            if (opcode.equals("WORD") || opcode.equals("RESW")) {
                finalOutput.add(code); // No change for directives
            } else {
                String[] operandArray = operandPart.split(" ");
                StringBuilder resolvedOperands = new StringBuilder();
                for (String operand : operandArray) {
                    if (symbolTable.containsKey(operand)) {
                        resolvedOperands.append(symbolTable.get(operand)).append(" ");
                    } else {
                        resolvedOperands.append(operand).append(" "); // For immediate values, etc.
                    }
                }
                finalOutput.add(loc + ": " + opcode + " " + resolvedOperands.toString().trim());
            }
        }
        return finalOutput;
    }

    public void printSymbolTable() {
        System.out.println("Symbol Table:");
        for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        List<String> sourceCode = Arrays.asList(
            "START 100",
            "LABEL1: LOAD VALUE",
            "ADD LABEL2",
            "STORE RESULT",
            "VALUE WORD 5",
            "LABEL2: WORD 10",
            "RESULT RESW 1",
            "END"
        );

        TwoPassAssembler assembler = new TwoPassAssembler();
        assembler.passOne(sourceCode);
        List<String> finalOutput = assembler.passTwo();

        assembler.printSymbolTable();
        System.out.println("\nIntermediate Code / Final Output:");
        for (String line : finalOutput) {
            System.out.println(line);
        }
    }
}
