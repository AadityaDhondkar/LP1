# Design suitable data structures and implement Pass-I and Pass-II of a two-pass macroprocessor. The output 
# of Pass-I (MNT, MDT and intermediate code file without any macro definitions) should be input for Pass-II.

class MacroProcessor:
    def __init__(self):
        self.mnt = []  # Macro Name Table
        self.mdt = []  # Macro Definition Table
        self.intermediate_code = []

    def pass_one(self, source_code):
        in_macro = False
        macro_name = ""

        for line in source_code:
            stripped_line = line.strip()
            if stripped_line.startswith("MACRO"):
                in_macro = True
                macro_name = stripped_line.split()[1]
                self.mnt.append((macro_name, len(self.mdt)))  # Add to MNT
            elif stripped_line == "END":
                in_macro = False
                macro_name = ""
            elif in_macro:
                self.mdt.append(stripped_line)  # Add to MDT
            else:
                self.intermediate_code.append(stripped_line)  # Normal code

    def pass_two(self):
        output_code = []

        for line in self.intermediate_code:
            if line in dict(self.mnt):
                mdt_index = dict(self.mnt)[line]
                output_code.extend(self.mdt[mdt_index:])
            else:
                output_code.append(line)

        return output_code

    def display_tables(self):
        print("MNT (Macro Name Table):")
        for name, index in self.mnt:
            print(f"{name}: {index}")
        print("MDT (Macro Definition Table):")
        for index, definition in enumerate(self.mdt):
            print(f"{index}: {definition}")


# Example usage
source_code = [
    "MACRO ADD",
    "  A = A + B",
    "END",
    "MOV A, B",
    "ADD",
    "SUB A, C"
]

macro_processor = MacroProcessor()
macro_processor.pass_one(source_code)
macro_processor.display_tables()
output_code = macro_processor.pass_two()

print("Output Code after Pass-II:")
for line in output_code:
    print(line)
