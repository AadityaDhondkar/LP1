# Design suitable Data structures and implement Pass-I and Pass-II of a two-pass assembler for pseudo
# machine. Implementation should consist of a few instructions from each category and few assembler 
# directives. The output of Pass-I (intermediate code file and symbol table) should be input for Pass-II.

class Assembler:
    def __init__(self):
        self.symbol_table = {}
        self.intermediate_code = []
        self.location_counter = 0

    def pass_one(self, source_code):
        for line in source_code:
            line = line.strip()
            if not line or line.startswith(';'):  # Skip empty lines and comments
                continue

            parts = line.split()
            instruction = parts[0]

            if instruction == 'START':
                self.location_counter = int(parts[1])
            elif instruction == 'END':
                continue
            elif instruction == 'WORD':
                self.intermediate_code.append((self.location_counter, 'WORD', parts[1]))
                self.location_counter += 1
            elif instruction == 'RESW':
                size = int(parts[1])
                self.intermediate_code.append((self.location_counter, 'RESW', size))
                self.location_counter += size
            else:
                # Assume it's a label or an instruction
                if ':' in instruction:
                    label = instruction[:-1]
                    self.symbol_table[label] = self.location_counter
                    instruction = parts[1]  # The next part is the actual instruction

                self.intermediate_code.append((self.location_counter, instruction, parts[1:] if len(parts) > 1 else []))
                self.location_counter += 1

    def pass_two(self):
        final_output = []
        for loc, instruction, operands in self.intermediate_code:
            if instruction == 'WORD':
                final_output.append(f"{loc}: {instruction} {operands[0]}")
            elif instruction == 'RESW':
                final_output.append(f"{loc}: {instruction} {operands[0]}")
            else:
                operand_addresses = []
                for operand in operands:
                    if operand in self.symbol_table:
                        operand_addresses.append(self.symbol_table[operand])
                    else:
                        operand_addresses.append(operand)  # For immediate values, etc.
                final_output.append(f"{loc}: {instruction} {' '.join(map(str, operand_addresses))}")

        return final_output

# Example source code
source_code = [
    "START 100",
    "LABEL1: LOAD VALUE",
    "ADD LABEL2",
    "STORE RESULT",
    "VALUE WORD 5",
    "LABEL2: WORD 10",
    "RESULT RESW 1",
    "END"
]

# Create assembler instance
assembler = Assembler()
assembler.pass_one(source_code)
final_output = assembler.pass_two()

# Display the symbol table and final output
print("Symbol Table:")
for label, address in assembler.symbol_table.items():
    print(f"{label}: {address}")

print("\nIntermediate Code / Final Output:")
for line in final_output:
    print(line)
