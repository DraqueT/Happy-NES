/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;

/**
 *
 * @author draque
 */
public class Happi6502 {

    public int a; // accumulator 8 bit
    public int x; // x register 8 bit
    public int y; // y register 8 biy
    public int pc; // program counter 16 bit
    public int stkp; // stack pointer 8 bit
    public int status; // status register flags 8 bit
    private Happi6502Bus bus;

    private int addr_abs; // absolute address 16 bit
    private int addr_rel; // relative address
    private int opCode; // operation code 8 bit
    private int cycles; // cycles left for current operation (just an int)
    private int fetched; // fetched value from address 8 bit
    private int temp; // convenience value 16 bit

    private Happi6502Instruction[] lookup = null;

    public Happi6502() {
        a = 0;
        x = 0;
        y = 0;
        pc = 0;
        stkp = 0;
        addr_abs = 0;
        addr_rel = 0;
        opCode = 0;
        cycles = 0;
        status = 0;
        fetched = 0;
        temp = 0;

        lookup = new Happi6502Instruction[]{
            new Happi6502Instruction("BRK", BRK, IMM, 7), new Happi6502Instruction("ORA", ORA, IZX, 6), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 3), new Happi6502Instruction("ORA", ORA, ZP0, 3), new Happi6502Instruction("ASL", ASL, ZP0, 5), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("PHP", PHP, IMP, 3), new Happi6502Instruction("ORA", ORA, IMM, 2), new Happi6502Instruction("ASL", ASL, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("ORA", ORA, ABS, 4), new Happi6502Instruction("ASL", ASL, ABS, 6), new Happi6502Instruction("???", XXX, IMP, 6),
            new Happi6502Instruction("BPL", BPL, REL, 2), new Happi6502Instruction("ORA", ORA, IZY, 5), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("ORA", ORA, ZPX, 4), new Happi6502Instruction("ASL", ASL, ZPX, 6), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("CLC", CLC, IMP, 2), new Happi6502Instruction("ORA", ORA, ABY, 4), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 7), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("ORA", ORA, ABX, 4), new Happi6502Instruction("ASL", ASL, ABX, 7), new Happi6502Instruction("???", XXX, IMP, 7),
            new Happi6502Instruction("JSR", JSR, ABS, 6), new Happi6502Instruction("AND", AND, IZX, 6), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("BIT", BIT, ZP0, 3), new Happi6502Instruction("AND", AND, ZP0, 3), new Happi6502Instruction("ROL", ROL, ZP0, 5), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("PLP", PLP, IMP, 4), new Happi6502Instruction("AND", AND, IMM, 2), new Happi6502Instruction("ROL", ROL, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("BIT", BIT, ABS, 4), new Happi6502Instruction("AND", AND, ABS, 4), new Happi6502Instruction("ROL", ROL, ABS, 6), new Happi6502Instruction("???", XXX, IMP, 6),
            new Happi6502Instruction("BMI", BMI, REL, 2), new Happi6502Instruction("AND", AND, IZY, 5), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("AND", AND, ZPX, 4), new Happi6502Instruction("ROL", ROL, ZPX, 6), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("SEC", SEC, IMP, 2), new Happi6502Instruction("AND", AND, ABY, 4), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 7), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("AND", AND, ABX, 4), new Happi6502Instruction("ROL", ROL, ABX, 7), new Happi6502Instruction("???", XXX, IMP, 7),
            new Happi6502Instruction("RTI", RTI, IMP, 6), new Happi6502Instruction("EOR", EOR, IZX, 6), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 3), new Happi6502Instruction("EOR", EOR, ZP0, 3), new Happi6502Instruction("LSR", LSR, ZP0, 5), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("PHA", PHA, IMP, 3), new Happi6502Instruction("EOR", EOR, IMM, 2), new Happi6502Instruction("LSR", LSR, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("JMP", JMP, ABS, 3), new Happi6502Instruction("EOR", EOR, ABS, 4), new Happi6502Instruction("LSR", LSR, ABS, 6), new Happi6502Instruction("???", XXX, IMP, 6),
            new Happi6502Instruction("BVC", BVC, REL, 2), new Happi6502Instruction("EOR", EOR, IZY, 5), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("EOR", EOR, ZPX, 4), new Happi6502Instruction("LSR", LSR, ZPX, 6), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("CLI", CLI, IMP, 2), new Happi6502Instruction("EOR", EOR, ABY, 4), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 7), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("EOR", EOR, ABX, 4), new Happi6502Instruction("LSR", LSR, ABX, 7), new Happi6502Instruction("???", XXX, IMP, 7),
            new Happi6502Instruction("RTS", RTS, IMP, 6), new Happi6502Instruction("ADC", ADC, IZX, 6), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 3), new Happi6502Instruction("ADC", ADC, ZP0, 3), new Happi6502Instruction("ROR", ROR, ZP0, 5), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("PLA", PLA, IMP, 4), new Happi6502Instruction("ADC", ADC, IMM, 2), new Happi6502Instruction("ROR", ROR, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("JMP", JMP, IND, 5), new Happi6502Instruction("ADC", ADC, ABS, 4), new Happi6502Instruction("ROR", ROR, ABS, 6), new Happi6502Instruction("???", XXX, IMP, 6),
            new Happi6502Instruction("BVS", BVS, REL, 2), new Happi6502Instruction("ADC", ADC, IZY, 5), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("ADC", ADC, ZPX, 4), new Happi6502Instruction("ROR", ROR, ZPX, 6), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("SEI", SEI, IMP, 2), new Happi6502Instruction("ADC", ADC, ABY, 4), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 7), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("ADC", ADC, ABX, 4), new Happi6502Instruction("ROR", ROR, ABX, 7), new Happi6502Instruction("???", XXX, IMP, 7),
            new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("STA", STA, IZX, 6), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("STY", STY, ZP0, 3), new Happi6502Instruction("STA", STA, ZP0, 3), new Happi6502Instruction("STX", STX, ZP0, 3), new Happi6502Instruction("???", XXX, IMP, 3), new Happi6502Instruction("DEY", DEY, IMP, 2), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("TXA", TXA, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("STY", STY, ABS, 4), new Happi6502Instruction("STA", STA, ABS, 4), new Happi6502Instruction("STX", STX, ABS, 4), new Happi6502Instruction("???", XXX, IMP, 4),
            new Happi6502Instruction("BCC", BCC, REL, 2), new Happi6502Instruction("STA", STA, IZY, 6), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("STY", STY, ZPX, 4), new Happi6502Instruction("STA", STA, ZPX, 4), new Happi6502Instruction("STX", STX, ZPY, 4), new Happi6502Instruction("???", XXX, IMP, 4), new Happi6502Instruction("TYA", TYA, IMP, 2), new Happi6502Instruction("STA", STA, ABY, 5), new Happi6502Instruction("TXS", TXS, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("???", NOP, IMP, 5), new Happi6502Instruction("STA", STA, ABX, 5), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("???", XXX, IMP, 5),
            new Happi6502Instruction("LDY", LDY, IMM, 2), new Happi6502Instruction("LDA", LDA, IZX, 6), new Happi6502Instruction("LDX", LDX, IMM, 2), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("LDY", LDY, ZP0, 3), new Happi6502Instruction("LDA", LDA, ZP0, 3), new Happi6502Instruction("LDX", LDX, ZP0, 3), new Happi6502Instruction("???", XXX, IMP, 3), new Happi6502Instruction("TAY", TAY, IMP, 2), new Happi6502Instruction("LDA", LDA, IMM, 2), new Happi6502Instruction("TAX", TAX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("LDY", LDY, ABS, 4), new Happi6502Instruction("LDA", LDA, ABS, 4), new Happi6502Instruction("LDX", LDX, ABS, 4), new Happi6502Instruction("???", XXX, IMP, 4),
            new Happi6502Instruction("BCS", BCS, REL, 2), new Happi6502Instruction("LDA", LDA, IZY, 5), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("LDY", LDY, ZPX, 4), new Happi6502Instruction("LDA", LDA, ZPX, 4), new Happi6502Instruction("LDX", LDX, ZPY, 4), new Happi6502Instruction("???", XXX, IMP, 4), new Happi6502Instruction("CLV", CLV, IMP, 2), new Happi6502Instruction("LDA", LDA, ABY, 4), new Happi6502Instruction("TSX", TSX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 4), new Happi6502Instruction("LDY", LDY, ABX, 4), new Happi6502Instruction("LDA", LDA, ABX, 4), new Happi6502Instruction("LDX", LDX, ABY, 4), new Happi6502Instruction("???", XXX, IMP, 4),
            new Happi6502Instruction("CPY", CPY, IMM, 2), new Happi6502Instruction("CMP", CMP, IZX, 6), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("CPY", CPY, ZP0, 3), new Happi6502Instruction("CMP", CMP, ZP0, 3), new Happi6502Instruction("DEC", DEC, ZP0, 5), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("INY", INY, IMP, 2), new Happi6502Instruction("CMP", CMP, IMM, 2), new Happi6502Instruction("DEX", DEX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("CPY", CPY, ABS, 4), new Happi6502Instruction("CMP", CMP, ABS, 4), new Happi6502Instruction("DEC", DEC, ABS, 6), new Happi6502Instruction("???", XXX, IMP, 6),
            new Happi6502Instruction("BNE", BNE, REL, 2), new Happi6502Instruction("CMP", CMP, IZY, 5), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("CMP", CMP, ZPX, 4), new Happi6502Instruction("DEC", DEC, ZPX, 6), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("CLD", CLD, IMP, 2), new Happi6502Instruction("CMP", CMP, ABY, 4), new Happi6502Instruction("NOP", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 7), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("CMP", CMP, ABX, 4), new Happi6502Instruction("DEC", DEC, ABX, 7), new Happi6502Instruction("???", XXX, IMP, 7),
            new Happi6502Instruction("CPX", CPX, IMM, 2), new Happi6502Instruction("SBC", SBC, IZX, 6), new Happi6502Instruction("???", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("CPX", CPX, ZP0, 3), new Happi6502Instruction("SBC", SBC, ZP0, 3), new Happi6502Instruction("INC", INC, ZP0, 5), new Happi6502Instruction("???", XXX, IMP, 5), new Happi6502Instruction("INX", INX, IMP, 2), new Happi6502Instruction("SBC", SBC, IMM, 2), new Happi6502Instruction("NOP", NOP, IMP, 2), new Happi6502Instruction("???", SBC, IMP, 2), new Happi6502Instruction("CPX", CPX, ABS, 4), new Happi6502Instruction("SBC", SBC, ABS, 4), new Happi6502Instruction("INC", INC, ABS, 6), new Happi6502Instruction("???", XXX, IMP, 6),
            new Happi6502Instruction("BEQ", BEQ, REL, 2), new Happi6502Instruction("SBC", SBC, IZY, 5), new Happi6502Instruction("???", XXX, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 8), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("SBC", SBC, ZPX, 4), new Happi6502Instruction("INC", INC, ZPX, 6), new Happi6502Instruction("???", XXX, IMP, 6), new Happi6502Instruction("SED", SED, IMP, 2), new Happi6502Instruction("SBC", SBC, ABY, 4), new Happi6502Instruction("NOP", NOP, IMP, 2), new Happi6502Instruction("???", XXX, IMP, 7), new Happi6502Instruction("???", NOP, IMP, 4), new Happi6502Instruction("SBC", SBC, ABX, 4), new Happi6502Instruction("INC", INC, ABX, 7), new Happi6502Instruction("???", XXX, IMP, 7),};
    }

    public void clock() {
        if (cycles == 0) {
            opCode = read(pc);
            pc++;

            var instruction = lookup[opCode];

            cycles = instruction.cycles;

            var extraCycle1 = instruction.addrmode.getAsInt();
            var extraCycle2 = instruction.operation.getAsInt();

            cycles += (extraCycle1 & extraCycle2);
        }

        cycles--;
    }

    public void reset() {
        a = 0;
        x = 0;
        y = 0;
        stkp = 0xFD;
        status = 0;
        addr_abs = 0xFFFC;
        var lo = read(addr_abs);
        var hi = read(addr_abs + 1);

        pc = (hi << 8) | lo;

        addr_abs = 0;
        addr_rel = 0;
        fetched = 0;

        cycles = 8;
    }

    // Interrupt requests are a complex operation and only happen if the
// "disable interrupt" flag is 0. IRQs can happen at any time, but
// you dont want them to be destructive to the operation of the running 
// program. Therefore the current instruction is allowed to finish
// (which I facilitate by doing the whole thing when cycles == 0) and 
// then the current program counter is stored on the stack. Then the
// current status register is stored on the stack. When the routine
// that services the interrupt has finished, the status register
// and program counter can be restored to how they where before it 
// occurred. This is impemented by the "RTI" instruction. Once the IRQ
// has happened, in a similar way to a reset, a programmable address
// is read form hard coded location 0xFFFE, which is subsequently
// set to the program counter.
    public void irq() {
        // If interrupts are allowed
        if (!GetFlag(FLAGS6502.I)) {
            // Push the program counter to the stack. It's 16-bits dont
            // forget so that takes two pushes
            write(0x0100 + stkp, (pc >> 8) & 0x00FF);
            stkp--;
            write(0x0100 + stkp, pc & 0x00FF);
            stkp--;

            // Then Push the status register to the stack
            SetFlag(FLAGS6502.B, false);
            SetFlag(FLAGS6502.U, true);
            SetFlag(FLAGS6502.I, true);
            write(0x0100 + stkp, status);
            stkp--;

            // Read new program counter location from fixed address
            addr_abs = 0xFFFE;
            var lo = read(addr_abs);
            var hi = read(addr_abs + 1);
            pc = (hi << 8) | lo;

            // IRQs take time
            cycles = 7;
        }
    }

// A Non-Maskable Interrupt cannot be ignored. It behaves in exactly the
// same way as a regular IRQ, but reads the new program counter address
// form location 0xFFFA.
    public void nmi() {
        write(0x0100 + stkp, (pc >> 8) & 0x00FF);
        stkp--;
        write(0x0100 + stkp, pc & 0x00FF);
        stkp--;

        SetFlag(FLAGS6502.B, false);
        SetFlag(FLAGS6502.U, true);
        SetFlag(FLAGS6502.I, true);
        write(0x0100 + stkp, status);
        stkp--;

        addr_abs = 0xFFFA;
        var lo = read(addr_abs);
        var hi = read(addr_abs + 1);
        pc = (hi << 8) | lo;

        cycles = 8;
    }

    public void connectBus(Happi6502Bus _bus) {
        bus = _bus;
    }

    /**
     *
     * @param addr 16 bit
     * @param data 8 bit
     */
    public void write(int addr, int data) {
        if (addr >= 0x0 && addr <= 0xFFFF) {
            bus.write(addr, data);
        } // TODO: else throw appropriate error
    }

    /**
     *
     * @param addr 16 bit
     * @return 8 bit
     */
    public int read(int addr) {
        return read(addr, false);
    }

    /**
     *
     * @param addr 16 bit
     * @param readOnly
     * @return 8 bit
     */
    public int read(int addr, boolean readOnly) {
        return bus.read(addr, false);
    }

    /*
    1) Reat byte for instruction
    2) extract addressing mode/cycles (addressing mode specifies bytes to read)
    3) read 0, 1, or 2 additional bytes
    4) execute
    5) wait appropriate number of additional cycles before reporting complete
     */
    public void SetFlag(FLAGS6502 flag, boolean value) {
        if (value) {
            status |= flag.getValue();
        } else {
            status &= ~flag.getValue();
        }
    }

    public boolean GetFlag(FLAGS6502 flag) {
        return ((status & flag.getValue()) > 0);
    }

    public enum FLAGS6502 {
        C(1), // carry
        Z(1 << 1), // Zero
        I(1 << 2), // Disable Interrupts
        D(1 << 3), // Decimal Mode (not used in NES)
        B(1 << 4), // Break
        U(1 << 5), // Unused
        V(1 << 6), // Overflow
        N(1 << 7);  // Negative

        private final int flagVal;

        FLAGS6502(int _flagVal) {
            flagVal = _flagVal;
        }

        public int getValue() {
            return flagVal;
        }
    }
    
    boolean complete()
    {
	return cycles == 0;
    }

    ///////////////////////////////////////////////////////////////////////////////
// ADDRESSING MODES
// The 6502 can address between 0x0000 - 0xFFFF. The high byte is often referred
// to as the "page", and the low byte is the offset into that page. This implies
// there are 256 pages, each containing 256 bytes.
//
// Several addressing modes have the potential to require an additional clock
// cycle if they cross a page boundary. This is combined with several instructions
// that enable this additional clock cycle. So each addressing function returns
// a flag saying it has potential, as does each instruction. If both instruction
// and address function return 1, then an additional clock cycle is required.
// Address Mode: Implied
// There is no additional data required for this instruction. The instruction
// does something very simple like like sets a status bit. However, we will
// target the accumulator, for instructions like PHA
    public IntSupplier IMP = () -> {
        fetched = a;
        return 0;
    };

// Address Mode: Immediate
// The instruction expects the next byte to be used as a value, so we'll prep
// the read address to point to the next byte
    public IntSupplier IMM = () -> {
        addr_abs = pc++;
        return 0;
    };

// Address Mode: Zero Page
// To save program bytes, zero page addressing allows you to absolutely address
// a location in first 0xFF bytes of address range. Clearly this only requires
// one byte instead of the usual two.
    public IntSupplier ZP0 = () -> {
        addr_abs = read(pc);
        pc++;
        addr_abs &= 0x00FF;
        return 0;
    };

// Address Mode: Zero Page with X Offset
// Fundamentally the same as Zero Page addressing, but the contents of the X Register
// is added to the supplied single byte address. This is useful for iterating through
// ranges within the first page.
    public IntSupplier ZPX = () -> {
        addr_abs = (read(pc) + x);
        pc++;
        addr_abs &= 0x00FF;
        return 0;
    };

// Address Mode: Zero Page with Y Offset
// Same as above but uses Y Register for offset
    public IntSupplier ZPY = () -> {
        addr_abs = (read(pc) + y);
        pc++;
        addr_abs &= 0x00FF;
        return 0;
    };

// Address Mode: Relative
// This address mode is exclusive to branch instructions. The address
// must reside within -128 to +127 of the branch instruction, i.e.
// you cant directly branch to any address in the addressable range.
    public IntSupplier REL = () -> {
        addr_rel = read(pc);
        pc++;
        if ((addr_rel & 0x80) > 0) {
            addr_rel |= 0xFF00;
        }
        return 0;
    };

// Address Mode: Absolute 
// A full 16-bit address is loaded and used
    public IntSupplier ABS = () -> {
        int lo = read(pc);
        pc++;
        int hi = read(pc);
        pc++;

        addr_abs = (hi << 8) | lo;

        return 0;
    };

// Address Mode: Absolute with X Offset
// Fundamentally the same as absolute addressing, but the contents of the X Register
// is added to the supplied two byte address. If the resulting address changes
// the page, an additional clock cycle is required
    public IntSupplier ABX = () -> {
        int lo = read(pc);
        pc++;
        int hi = read(pc);
        pc++;

        addr_abs = (hi << 8) | lo;
        addr_abs += x;

        if ((addr_abs & 0xFF00) != (hi << 8)) {
            return 1;
        } else {
            return 0;
        }
    };

// Address Mode: Absolute with Y Offset
// Fundamentally the same as absolute addressing, but the contents of the Y Register
// is added to the supplied two byte address. If the resulting address changes
// the page, an additional clock cycle is required
    public IntSupplier ABY = () -> {
        int lo = read(pc);
        pc++;
        int hi = read(pc);
        pc++;

        addr_abs = (hi << 8) | lo;
        addr_abs += y;

        if ((addr_abs & 0xFF00) != (hi << 8)) {
            return 1;
        } else {
            return 0;
        }
    };

// Note: The next 3 address modes use indirection (aka Pointers!)
// Address Mode: Indirect
// The supplied 16-bit address is read to get the actual 16-bit address. This is
// instruction is unusual in that it has a bug in the hardware! To emulate its
// function accurately, we also need to emulate this bug. If the low byte of the
// supplied address is 0xFF, then to read the high byte of the actual address
// we need to cross a page boundary. This doesnt actually work on the chip as 
// designed, instead it wraps back around in the same page, yielding an 
// invalid actual address
    public IntSupplier IND = () -> {
        int ptr_lo = read(pc);
        pc++;
        int ptr_hi = read(pc);
        pc++;

        int ptr = (ptr_hi << 8) | ptr_lo;

        if (ptr_lo == 0x00FF) // Simulate page boundary hardware bug
        {
            addr_abs = (read(ptr & 0xFF00) << 8) | read(ptr + 0);
        } else // Behave normally
        {
            addr_abs = (read(ptr + 1) << 8) | read(ptr + 0);
        }

        return 0;
    };

// Address Mode: Indirect X
// The supplied 8-bit address is offset by X Register to index
// a location in page 0x00. The actual 16-bit address is read 
// from this location
    public IntSupplier IZX = () -> {
        int t = read(pc);
        pc++;

        int lo = read((int) (t + (int) x) & 0x00FF);
        int hi = read((int) (t + (int) x + 1) & 0x00FF);

        addr_abs = (hi << 8) | lo;

        return 0;
    };

// Address Mode: Indirect Y
// The supplied 8-bit address indexes a location in page 0x00. From 
// here the actual 16-bit address is read, and the contents of
// Y Register is added to it to offset it. If the offset causes a
// change in page then an additional clock cycle is required.
    public IntSupplier IZY = () -> {
        int t = read(pc);
        pc++;

        int lo = read(t & 0x00FF);
        int hi = read((t + 1) & 0x00FF);

        addr_abs = (hi << 8) | lo;
        addr_abs += y;

        if ((addr_abs & 0xFF00) != (hi << 8)) {
            return 1;
        } else {
            return 0;
        }
    };

    // This function sources the data used by the instruction into 
// a convenient numeric variable. Some instructions dont have to 
// fetch data as the source is implied by the instruction. For example
// "INX" increments the X register. There is no additional data
// required. For all other addressing modes, the data resides at 
// the location held within addr_abs, so it is read from there. 
// Immediate adress mode exploits this slightly, as that has
// set addr_abs = pc + 1, so it fetches the data from the
// next byte for example "LDA $FF" just loads the accumulator with
// 256, i.e. no far reaching memory fetch is required. "fetched"
// is a variable global to the CPU, and is set by calling this 
// function. It also returns it for convenience.
    public int fetch() {
        if (!(lookup[opCode].addrmode == IMP)) {
            fetched = read(addr_abs);
        }
        return fetched;
    }

    ///////////////////////////////////////////////////////////////////////////////
// INSTRUCTION IMPLEMENTATIONS
// Note: Ive started with the two most complicated instructions to emulate, which
// ironically is addition and subtraction! Ive tried to include a detailed 
// explanation as to why they are so complex, yet so fundamental. Im also NOT
// going to do this through the explanation of 1 and 2's complement.
// Instruction: Add with Carry In
// Function:    A = A + M + C
// Flags Out:   C, V, N, Z
//
// Explanation:
// The purpose of this function is to add a value to the accumulator and a carry bit. If
// the result is > 255 there is an overflow setting the carry bit. Ths allows you to
// chain together ADC instructions to add numbers larger than 8-bits. This in itself is
// simple, however the 6502 supports the concepts of Negativity/Positivity and Signed Overflow.
//
// 10000100 = 128 + 4 = 132 in normal circumstances, we know this as unsigned and it allows
// us to represent numbers between 0 and 255 (given 8 bits). The 6502 can also interpret 
// this word as something else if we assume those 8 bits represent the range -128 to +127,
// i.e. it has become signed.
//
// Since 132 > 127, it effectively wraps around, through -128, to -124. This wraparound is
// called overflow, and this is a useful to know as it indicates that the calculation has
// gone outside the permissable range, and therefore no longer makes numeric sense.
//
// Note the implementation of ADD is the same in binary, this is just about how the numbers
// are represented, so the word 10000100 can be both -124 and 132 depending upon the 
// context the programming is using it in. We can prove this!
//
//  10000100 =  132  or  -124
// +00010001 = + 17      + 17
//  ========    ===       ===     See, both are valid additions, but our interpretation of
//  10010101 =  149  or  -107     the context changes the value, not the hardware!
//
// In principle under the -128 to 127 range:
// 10000000 = -128, 11111111 = -1, 00000000 = 0, 00000000 = +1, 01111111 = +127
// therefore negative numbers have the most significant set, positive numbers do not
//
// To assist us, the 6502 can set the overflow flag, if the result of the addition has
// wrapped around. V <- ~(A^M) & A^(A+M+C) :D lol, let's work out why!
//
// Let's suppose we have A = 30, M = 10 and C = 0
//          A = 30 = 00011110
//          M = 10 = 00001010+
//     RESULT = 40 = 00101000
//
// Here we have not gone out of range. The resulting significant bit has not changed.
// So let's make a truth table to understand when overflow has occurred. Here I take
// the MSB of each component, where R is RESULT.
//
// A  M  R | V | A^R | A^M |~(A^M) | 
// 0  0  0 | 0 |  0  |  0  |   1   |
// 0  0  1 | 1 |  1  |  0  |   1   |
// 0  1  0 | 0 |  0  |  1  |   0   |
// 0  1  1 | 0 |  1  |  1  |   0   |  so V = ~(A^M) & (A^R)
// 1  0  0 | 0 |  1  |  1  |   0   |
// 1  0  1 | 0 |  0  |  1  |   0   |
// 1  1  0 | 1 |  1  |  0  |   1   |
// 1  1  1 | 0 |  0  |  0  |   1   |
//
// We can see how the above equation calculates V, based on A, M and R. V was chosen
// based on the following hypothesis:
//       Positive Number + Positive Number = Negative Result -> Overflow
//       Negative Number + Negative Number = Positive Result -> Overflow
//       Positive Number + Negative Number = Either Result -> Cannot Overflow
//       Positive Number + Positive Number = Positive Result -> OK! No Overflow
//       Negative Number + Negative Number = Negative Result -> OK! NO Overflow
    public IntSupplier ADC = () -> {
        // Grab the data that we are adding to the accumulator
        fetch();

        // Add is performed in 16-bit domain for emulation to capture any
        // carry bit, which will exist in bit 8 of the 16-bit word
        temp = a + fetched + (GetFlag(FLAGS6502.C) ? 1 : 0);

        // The carry flag out exists in the high byte bit 0
        SetFlag(FLAGS6502.C, temp > 255);

        // The Zero flag is set if the result is 0
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0);

        // The signed Overflow flag is set based on all that up there! :D
        SetFlag(FLAGS6502.V, ((~(a ^ fetched) & (a ^ temp)) & 0x0080) > 0);

        // The negative flag is set to the most significant bit of the result
        SetFlag(FLAGS6502.N, (temp & 0x80) > 0);

        // Load the result into the accumulator (it's 8-bit dont forget!)
        a = temp & 0x00FF;

        // This instruction has the potential to require an additional clock cycle
        return 1;
    };

// Instruction: Subtraction with Borrow In
// Function:    A = A - M - (1 - C)
// Flags Out:   C, V, N, Z
//
// Explanation:
// Given the explanation for ADC above, we can reorganise our data
// to use the same computation for addition, for subtraction by multiplying
// the data by -1, i.e. make it negative
//
// A = A - M - (1 - C)  ->  A = A + -1 * (M - (1 - C))  ->  A = A + (-M + 1 + C)
//
// To make a signed positive number negative, we can invert the bits and add 1
// (OK, I lied, a little bit of 1 and 2s complement :P)
//
//  5 = 00000101
// -5 = 11111010 + 00000001 = 11111011 (or 251 in our 0 to 255 range)
//
// The range is actually unimportant, because if I take the value 15, and add 251
// to it, given we wrap around at 256, the result is 10, so it has effectively 
// subtracted 5, which was the original intention. (15 + 251) % 256 = 10
//
// Note that the equation above used (1-C), but this got converted to + 1 + C.
// This means we already have the +1, so all we need to do is invert the bits
// of M, the data(!) therfore we can simply add, exactly the same way we did 
// before.
    public IntSupplier SBC = () -> {
        fetch();

        // Operating in 16-bit domain to capture carry out
        // We can invert the bottom 8 bits with bitwise xor
        int value = ((int) fetched) ^ 0x00FF;

        // Notice this is exactly the same as addition from here!
        temp = (int) a + value + (GetFlag(FLAGS6502.C) ? 1 : 0);
        SetFlag(FLAGS6502.C, (temp & 0xFF00) > 0);
        SetFlag(FLAGS6502.Z, ((temp & 0x00FF) == 0));
        SetFlag(FLAGS6502.V, ((temp ^ (int) a) & (temp ^ value) & 0x0080) > 0);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        a = temp & 0x00FF;
        return 1;
    };

// OK! Complicated operations are done! the following are much simpler
// and conventional. The typical order of events is:
// 1) Fetch the data you are working with
// 2) Perform calculation
// 3) Store the result in desired place
// 4) Set Flags of the status register
// 5) Return if instruction has potential to require additional 
//    clock cycle
// Instruction: Bitwise Logic AND
// Function:    A = A & M
// Flags Out:   N, Z
    public IntSupplier AND = () -> {
        fetch();
        a = a & fetched;
        SetFlag(FLAGS6502.Z, a == 0x00);
        SetFlag(FLAGS6502.N, (a & 0x80) > 0);
        return 1;
    };

// Instruction: Arithmetic Shift Left
// Function:    A = C <- (A << 1) <- 0
// Flags Out:   N, Z, C
    public IntSupplier ASL = () -> {
        fetch();
        temp = (int) fetched << 1;
        SetFlag(FLAGS6502.C, (temp & 0xFF00) > 0);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x00);
        SetFlag(FLAGS6502.N, (temp & 0x80) > 0);
        if (lookup[opCode].addrmode == IMP) {
            a = temp & 0x00FF;
        } else {
            write(addr_abs, temp & 0x00FF);
        }
        return 0;
    };

// Instruction: Branch if Carry Clear
// Function:    if(C == 0) pc = address 
    public IntSupplier BCC = () -> {
        if (!GetFlag(FLAGS6502.C)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }

        return 0;
    };

// Instruction: Branch if Carry Set
// Function:    if(C == 1) pc = address
    public IntSupplier BCS = () -> {
        if (GetFlag(FLAGS6502.C)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }
        return 0;
    };

// Instruction: Branch if Equal
// Function:    if(Z == 1) pc = address
    public IntSupplier BEQ = () -> {
        if (GetFlag(FLAGS6502.Z)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }
        return 0;
    };

    public IntSupplier BIT = () -> {
        fetch();
        temp = a & fetched;
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x00);
        SetFlag(FLAGS6502.N, (fetched & (1 << 7)) > 0);
        SetFlag(FLAGS6502.V, (fetched & (1 << 6)) > 0);
        return 0;
    };

// Instruction: Branch if Negative
// Function:    if(N == 1) pc = address
    public IntSupplier BMI = () -> {
        if (GetFlag(FLAGS6502.N)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }
        return 0;
    };

// Instruction: Branch if Not Equal
// Function:    if(Z == 0) pc = address
    public IntSupplier BNE = () -> {
        if (!GetFlag(FLAGS6502.Z)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }
        return 0;
    };

// Instruction: Branch if Positive
// Function:    if(N == 0) pc = address
    public IntSupplier BPL = () -> {
        if (!GetFlag(FLAGS6502.N)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }
        return 0;
    };

// Instruction: Break
// Function:    Program Sourced Interrupt
    public IntSupplier BRK = () -> {
        pc++;

        SetFlag(FLAGS6502.I, true);
        write(0x0100 + stkp, (pc >> 8) & 0x00FF);
        stkp--;
        write(0x0100 + stkp, pc & 0x00FF);
        stkp--;

        SetFlag(FLAGS6502.B, true);
        write(0x0100 + stkp, status);
        stkp--;
        SetFlag(FLAGS6502.B, false);

        pc = (int) read(0xFFFE) | ((int) read(0xFFFF) << 8);
        return 0;
    };

// Instruction: Branch if Overflow Clear
// Function:    if(V == 0) pc = address
    public IntSupplier BVC = () -> {
        if (!GetFlag(FLAGS6502.V)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }
        return 0;
    };

// Instruction: Branch if Overflow Set
// Function:    if(V == 1) pc = address
    public IntSupplier BVS = () -> {
        if (GetFlag(FLAGS6502.V)) {
            cycles++;
            addr_abs = pc + addr_rel;

            if ((addr_abs & 0xFF00) != (pc & 0xFF00)) {
                cycles++;
            }

            pc = addr_abs;
        }
        return 0;
    };

// Instruction: Clear Carry Flag
// Function:    C = 0
    public IntSupplier CLC = () -> {
        SetFlag(FLAGS6502.C, false);
        return 0;
    };

// Instruction: Clear Decimal Flag
// Function:    D = 0
    public IntSupplier CLD = () -> {
        SetFlag(FLAGS6502.D, false);
        return 0;
    };

// Instruction: Disable Interrupts / Clear Interrupt Flag
// Function:    I = 0
    public IntSupplier CLI = () -> {
        SetFlag(FLAGS6502.I, false);
        return 0;
    };

// Instruction: Clear Overflow Flag
// Function:    V = 0
    public IntSupplier CLV = () -> {
        SetFlag(FLAGS6502.V, false);
        return 0;
    };

// Instruction: Compare Accumulator
// Function:    C <- A >= M      Z <- (A - M) == 0
// Flags Out:   N, C, Z
    public IntSupplier CMP = () -> {
        fetch();
        temp = (int) a - (int) fetched;
        SetFlag(FLAGS6502.C, a >= fetched);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x0000);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        return 1;
    };

// Instruction: Compare X Register
// Function:    C <- X >= M      Z <- (X - M) == 0
// Flags Out:   N, C, Z
    public IntSupplier CPX = () -> {
        fetch();
        temp = (int) x - (int) fetched;
        SetFlag(FLAGS6502.C, x >= fetched);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x0000);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        return 0;
    };

// Instruction: Compare Y Register
// Function:    C <- Y >= M      Z <- (Y - M) == 0
// Flags Out:   N, C, Z
    public IntSupplier CPY = () -> {
        fetch();
        temp = (int) y - (int) fetched;
        SetFlag(FLAGS6502.C, y >= fetched);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x0000);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        return 0;
    };

// Instruction: Decrement Value at Memory Location
// Function:    M = M - 1
// Flags Out:   N, Z
    public IntSupplier DEC = () -> {
        fetch();
        temp = fetched - 1;
        write(addr_abs, temp & 0x00FF);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x0000);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        return 0;
    };

// Instruction: Decrement X Register
// Function:    X = X - 1
// Flags Out:   N, Z
    public IntSupplier DEX = () -> {
        x--;
        SetFlag(FLAGS6502.Z, x == 0x00);
        SetFlag(FLAGS6502.N, (x & 0x80) > 0);
        return 0;
    };

// Instruction: Decrement Y Register
// Function:    Y = Y - 1
// Flags Out:   N, Z
    public IntSupplier DEY = () -> {
        y--;
        SetFlag(FLAGS6502.Z, y == 0x00);
        SetFlag(FLAGS6502.N, (y & 0x80) > 0);
        return 0;
    };

// Instruction: Bitwise Logic XOR
// Function:    A = A xor M
// Flags Out:   N, Z
    public IntSupplier EOR = () -> {
        fetch();
        a = a ^ fetched;
        SetFlag(FLAGS6502.Z, a == 0x00);
        SetFlag(FLAGS6502.N, (a & 0x80) > 0);
        return 1;
    };

// Instruction: Increment Value at Memory Location
// Function:    M = M + 1
// Flags Out:   N, Z
    public IntSupplier INC = () -> {
        fetch();
        temp = fetched + 1;
        write(addr_abs, temp & 0x00FF);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x0000);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        return 0;
    };

// Instruction: Increment X Register
// Function:    X = X + 1
// Flags Out:   N, Z
    public IntSupplier INX = () -> {
        x++;
        SetFlag(FLAGS6502.Z, x == 0x00);
        SetFlag(FLAGS6502.N, (x & 0x80) > 0);
        return 0;
    };

// Instruction: Increment Y Register
// Function:    Y = Y + 1
// Flags Out:   N, Z
    public IntSupplier INY = () -> {
        y++;
        SetFlag(FLAGS6502.Z, y == 0x00);
        SetFlag(FLAGS6502.N, (y & 0x80) > 0);
        return 0;
    };

// Instruction: Jump To Location
// Function:    pc = address
    public IntSupplier JMP = () -> {
        pc = addr_abs;
        return 0;
    };

// Instruction: Jump To Sub-Routine
// Function:    Push current pc to stack, pc = address
    public IntSupplier JSR = () -> {
        pc--;

        write(0x0100 + stkp, (pc >> 8) & 0x00FF);
        stkp--;
        write(0x0100 + stkp, pc & 0x00FF);
        stkp--;

        pc = addr_abs;
        return 0;
    };

// Instruction: Load The Accumulator
// Function:    A = M
// Flags Out:   N, Z
    public IntSupplier LDA = () -> {
        fetch();
        a = fetched;
        SetFlag(FLAGS6502.Z, a == 0x00);
        SetFlag(FLAGS6502.N, (a & 0x80) > 0);
        return 1;
    };

// Instruction: Load The X Register
// Function:    X = M
// Flags Out:   N, Z
    public IntSupplier LDX = () -> {
        fetch();
        x = fetched;
        SetFlag(FLAGS6502.Z, x == 0x00);
        SetFlag(FLAGS6502.N, (x & 0x80) > 0);
        return 1;
    };

// Instruction: Load The Y Register
// Function:    Y = M
// Flags Out:   N, Z
    public IntSupplier LDY = () -> {
        fetch();
        y = fetched;
        SetFlag(FLAGS6502.Z, y == 0x00);
        SetFlag(FLAGS6502.N, (y & 0x80) > 0);
        return 1;
    };

    public IntSupplier LSR = () -> {
        fetch();
        SetFlag(FLAGS6502.C, (fetched & 0x0001) > 0);
        temp = fetched >> 1;
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x0000);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        if (lookup[opCode].addrmode == IMP) {
            a = temp & 0x00FF;
        } else {
            write(addr_abs, temp & 0x00FF);
        }
        return 0;
    };

    public IntSupplier NOP = () -> {
        // Sadly not all NOPs are equal, Ive added a few here
        // based on https://wiki.nesdev.com/w/index.php/CPU_unofficial_opcodes
        // and will add more based on game compatibility, and ultimately
        // I'd like to cover all illegal opcodes too
        switch (opCode) {

            case 0x1C, 0x3C, 0x5C, 0x7C, 0xDC, 0xFC -> {
                return 1;
            }
        }
        return 0;
    };

// Instruction: Bitwise Logic OR
// Function:    A = A | M
// Flags Out:   N, Z
    public IntSupplier ORA = () -> {
        fetch();
        a = a | fetched;
        SetFlag(FLAGS6502.Z, a == 0x00);
        SetFlag(FLAGS6502.N, (a & 0x80) > 0);
        return 1;
    };

// Instruction: Push Accumulator to Stack
// Function:    A -> stack
    public IntSupplier PHA = () -> {
        write(0x0100 + stkp, a);
        stkp--;
        return 0;
    };

// Instruction: Push Status Register to Stack
// Function:    status -> stack
// Note:        Break flag is set to 1 before push
    public IntSupplier PHP = () -> {
        write(0x0100 + stkp, status | FLAGS6502.B.getValue() | FLAGS6502.U.getValue());
        SetFlag(FLAGS6502.B, false);
        SetFlag(FLAGS6502.U, false);
        stkp--;
        return 0;
    };

// Instruction: Pop Accumulator off Stack
// Function:    A <- stack
// Flags Out:   N, Z
    public IntSupplier PLA = () -> {
        stkp++;
        a = read(0x0100 + stkp);
        SetFlag(FLAGS6502.Z, a == 0x00);
        SetFlag(FLAGS6502.N, (a & 0x80) > 0);
        return 0;
    };

// Instruction: Pop Status Register off Stack
// Function:    Status <- stack
    public IntSupplier PLP = () -> {
        stkp++;
        status = read(0x0100 + stkp);
        SetFlag(FLAGS6502.U, true);
        return 0;
    };

    public IntSupplier ROL = () -> {
        fetch();
        temp = (int) (fetched << 1) | (GetFlag(FLAGS6502.C) ? 1 : 0);
        SetFlag(FLAGS6502.C, (temp & 0xFF00) > 0);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x0000);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        if (lookup[opCode].addrmode == IMP) {
            a = temp & 0x00FF;
        } else {
            write(addr_abs, temp & 0x00FF);
        }
        return 0;
    };

    public IntSupplier ROR = () -> {
        fetch();
        temp = (int) ((GetFlag(FLAGS6502.C) ? 1 : 0) << 7) | (fetched >> 1);
        SetFlag(FLAGS6502.C, (fetched & 0x01) > 0);
        SetFlag(FLAGS6502.Z, (temp & 0x00FF) == 0x00);
        SetFlag(FLAGS6502.N, (temp & 0x0080) > 0);
        if (lookup[opCode].addrmode == IMP) {
            a = temp & 0x00FF;
        } else {
            write(addr_abs, temp & 0x00FF);
        }
        return 0;
    };

    public IntSupplier RTI = () -> {
        stkp++;
        status = read(0x0100 + stkp);
        status &= ~FLAGS6502.B.getValue();
        status &= ~FLAGS6502.U.getValue();

        stkp++;
        pc = (int) read(0x0100 + stkp);
        stkp++;
        pc |= (int) read(0x0100 + stkp) << 8;
        return 0;
    };

    public IntSupplier RTS = () -> {
        stkp++;
        pc = (int) read(0x0100 + stkp);
        stkp++;
        pc |= (int) read(0x0100 + stkp) << 8;

        pc++;
        return 0;
    };

// Instruction: Set Carry Flag
// Function:    C = 1
    public IntSupplier SEC = () -> {
        SetFlag(FLAGS6502.C, true);
        return 0;
    };

// Instruction: Set Decimal Flag
// Function:    D = 1
    public IntSupplier SED = () -> {
        SetFlag(FLAGS6502.D, true);
        return 0;
    };

// Instruction: Set Interrupt Flag / Enable Interrupts
// Function:    I = 1
    public IntSupplier SEI = () -> {
        SetFlag(FLAGS6502.I, true);
        return 0;
    };

// Instruction: Store Accumulator at Address
// Function:    M = A
    public IntSupplier STA = () -> {
        write(addr_abs, a);
        return 0;
    };

// Instruction: Store X Register at Address
// Function:    M = X
    public IntSupplier STX = () -> {
        write(addr_abs, x);
        return 0;
    };

// Instruction: Store Y Register at Address
// Function:    M = Y
    public IntSupplier STY = () -> {
        write(addr_abs, y);
        return 0;
    };

// Instruction: Transfer Accumulator to X Register
// Function:    X = A
// Flags Out:   N, Z
    public IntSupplier TAX = () -> {
        x = a;
        SetFlag(FLAGS6502.Z, x == 0x00);
        SetFlag(FLAGS6502.N, (x & 0x80) > 0);
        return 0;
    };

// Instruction: Transfer Accumulator to Y Register
// Function:    Y = A
// Flags Out:   N, Z
    public IntSupplier TAY = () -> {
        y = a;
        SetFlag(FLAGS6502.Z, y == 0x00);
        SetFlag(FLAGS6502.N, (y & 0x80) > 0);
        return 0;
    };

// Instruction: Transfer Stack Pointer to X Register
// Function:    X = stack pointer
// Flags Out:   N, Z
    public IntSupplier TSX = () -> {
        x = stkp;
        SetFlag(FLAGS6502.Z, x == 0x00);
        SetFlag(FLAGS6502.N, (x & 0x80) > 0);
        return 0;
    };

// Instruction: Transfer X Register to Accumulator
// Function:    A = X
// Flags Out:   N, Z
    public IntSupplier TXA = () -> {
        a = x;
        SetFlag(FLAGS6502.Z, a == 0x00);
        SetFlag(FLAGS6502.N, (a & 0x80) > 0);
        return 0;
    };

// Instruction: Transfer X Register to Stack Pointer
// Function:    stack pointer = X
    public IntSupplier TXS = () -> {
        stkp = x;
        return 0;
    };

// Instruction: Transfer Y Register to Accumulator
// Function:    A = Y
// Flags Out:   N, Z
    public IntSupplier TYA = () -> {
        a = y;
        SetFlag(FLAGS6502.Z, a == 0x00);
        SetFlag(FLAGS6502.N, (a & 0x80) > 0);
        return 0;
    };

// This function captures illegal opcodes
    public IntSupplier XXX = () -> {
        return 0;
    };

// A convenient utility to convert variables into
// hex strings because "modern C++"'s method with 
// streams is atrocious
    public static String hex(int n, int d) {
        String hexString = Integer.toHexString(n);

        while (hexString.length() < d) {
            hexString = "0" + hexString;
        }

        return hexString;
    }

    // This is the disassembly function. Its workings are not required for emulation.
// It is merely a convenience function to turn the binary instruction code into
// human readable form. Its included as part of the emulator because it can take
// advantage of many of the CPUs internal operations to do this.
    public Map<Integer, String> disassemble(int nStart, int nStop) {
        int addr = nStart;
        int value = 0x00, lo = 0x00, hi = 0x00;
        var mapLines = new HashMap<Integer, String>();
        int line_addr = 0;

        // Starting at the specified address we read an instruction
        // byte, which in turn yields information from the lookup table
        // as to how many additional bytes we need to read and what the
        // addressing mode is. I need this info to assemble human readable
        // syntax, which is different depending upon the addressing mode
        // As the instruction is decoded, a std::string is assembled
        // with the readable output
        while (addr <= nStop) {
            line_addr = addr;

            // Prefix line with instruction address
            String sInst = "$" + hex(addr, 4) + ": ";

            // Read instruction, and get its readable name
            int opcode = bus.read(addr, true);
            addr++;
            sInst += lookup[opcode].name + " ";

            // Get oprands from desired locations, and form the
            // instruction based upon its addressing mode. These
            // routines mimmick the actual fetch routine of the
            // 6502 in order to get accurate data as part of the
            // instruction
            if (lookup[opcode].addrmode == IMP) {
                sInst += " {IMP}";
            } else if (lookup[opcode].addrmode == IMM) {
                value = bus.read(addr, true);
                addr++;
                sInst += "#$" + hex(value, 2) + " {IMM}";
            } else if (lookup[opcode].addrmode == ZP0) {
                lo = bus.read(addr, true);
                addr++;
                hi = 0x00;
                sInst += "$" + hex(lo, 2) + " {ZP0}";
            } else if (lookup[opcode].addrmode == ZPX) {
                lo = bus.read(addr, true);
                addr++;
                hi = 0x00;
                sInst += "$" + hex(lo, 2) + ", X {ZPX}";
            } else if (lookup[opcode].addrmode == ZPY) {
                lo = bus.read(addr, true);
                addr++;
                hi = 0x00;
                sInst += "$" + hex(lo, 2) + ", Y {ZPY}";
            } else if (lookup[opcode].addrmode == IZX) {
                lo = bus.read(addr, true);
                addr++;
                hi = 0x00;
                sInst += "($" + hex(lo, 2) + ", X) {IZX}";
            } else if (lookup[opcode].addrmode == IZY) {
                lo = bus.read(addr, true);
                addr++;
                hi = 0x00;
                sInst += "($" + hex(lo, 2) + "), Y {IZY}";
            } else if (lookup[opcode].addrmode == ABS) {
                lo = bus.read(addr, true);
                addr++;
                hi = bus.read(addr, true);
                addr++;
                sInst += "$" + hex((hi << 8) | lo, 4) + " {ABS}";
            } else if (lookup[opcode].addrmode == ABX) {
                lo = bus.read(addr, true);
                addr++;
                hi = bus.read(addr, true);
                addr++;
                sInst += "$" + hex((hi << 8) | lo, 4) + ", X {ABX}";
            } else if (lookup[opcode].addrmode == ABY) {
                lo = bus.read(addr, true);
                addr++;
                hi = bus.read(addr, true);
                addr++;
                sInst += "$" + hex((hi << 8) | lo, 4) + ", Y {ABY}";
            } else if (lookup[opcode].addrmode == IND) {
                lo = bus.read(addr, true);
                addr++;
                hi = bus.read(addr, true);
                addr++;
                sInst += "($" + hex((hi << 8) | lo, 4) + ") {IND}";
            } else if (lookup[opcode].addrmode == REL) {
                value = bus.read(addr, true);
                addr++;
                sInst += "$" + hex(value, 2) + " [$" + hex(addr + value, 4) + "] {REL}";
            }

            // Add the formed string to a std::map, using the instruction's
            // address as the key. This makes it convenient to look for later
            // as the instructions are variable in length, so a straight up
            // incremental index is not sufficient.
            mapLines.put(line_addr, sInst);
        }

        return mapLines;
    }
}
