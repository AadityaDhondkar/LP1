// Design suitable data structures and implement Pass-I and Pass-II of a two-pass macroprocessor. The output 
// of Pass-I (MNT, MDT and intermediate code file without any macro definitions) should be input for Pass-II.

import java.util.*;
class MacroProcessor {
    private List<String[]> mnt;  // Macro Name Table
    private List<String> mdt;    // Macro Definition Table
    private List<String> intermediateCode;

    public MacroProcessor() {
        mnt = new ArrayList<>();
        mdt = new ArrayList<>();
        intermediateCode = new ArrayList<>();
    }

    public void passOne(List<String> sourceCode) {
        boolean inMacro = false;
        String macroName = "";

        for (String line : sourceCode) {
            String strippedLine = line.trim();
            if (strippedLine.startsWith("MACRO")) {
                inMacro = true;
                macroName = strippedLine.split(" ")[1];
                mnt.add(new String[]{macroName, String.valueOf(mdt.size())}); // Add to MNT
            } else if (strippedLine.equals("END")) {
                inMacro = false;
                macroName = "";
            } else if (inMacro) {
                mdt.add(strippedLine); // Add to MDT
            } else {
                intermediateCode.add(strippedLine); // Normal code
            }
        }
    }

    public List<String> passTwo() {
        List<String> outputCode = new ArrayList<>();

        for (String line : intermediateCode) {
            boolean isMacro = false;
            for (String[] mntEntry : mnt) {
                if (mntEntry[0].equals(line)) {
                    int mdtIndex = Integer.parseInt(mntEntry[1]);
                    outputCode.addAll(mdt.subList(mdtIndex, mdt.size()));
                    isMacro = true;
                    break;
                }
            }
            if (!isMacro) {
                outputCode.add(line);
            }
        }
        return outputCode;
    }

    public void displayTables() {
        System.out.println("MNT (Macro Name Table):");
        for (String[] entry : mnt) {
            System.out.println(entry[0] + ": " + entry[1]);
        }
        System.out.println("MDT (Macro Definition Table):");
        for (int i = 0; i < mdt.size(); i++) {
            System.out.println(i + ": " + mdt.get(i));
        }
    }

    public static void main(String[] args) {
        List<String> sourceCode = Arrays.asList(
                "MACRO ADD",
                "  A = A + B",
                "END",
                "MOV A, B",
                "ADD",
                "SUB A, C"
        );

        MacroProcessor macroProcessor = new MacroProcessor();
        macroProcessor.passOne(sourceCode);
        macroProcessor.displayTables();
        List<String> outputCode = macroProcessor.passTwo();

        System.out.println("Output Code after Pass-II:");
        for (String line : outputCode) {
            System.out.println(line);
        }
    }
}
