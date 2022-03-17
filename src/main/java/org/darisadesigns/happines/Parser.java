/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

import org.darisadesigns.happines.Operation.Mode;

/**
 *
 * @author draque
 */
public class Parser {
    public static Operation getOperation(int opVal) {
        switch(opVal) {
            // ADC        MODE           SYNTAX       HEX LEN TIM
            case 0x69: // Immediate     ADC #$44      $69  2   2
                return new Operation("ADC", Mode.Immediate, "ADC #$44", 0x69, 2, 2, false);
            case 0x65: // Zero Page     ADC $44       $65  2   3
                return new Operation("ADC", Mode.ZeroPage, "ADC $44", 0x65, 2, 3, false);
            case 0x75: // Zero Page,X   ADC $44,X     $75  2   4
                return new Operation("ADC", Mode.ZeroPageX, "ADC $44,X", 0x75, 2, 4, false);
            case 0x6D: // Absolute      ADC $4400     $6D  3   4
                return new Operation("ADC", Mode.Absolute, "ADC $4400", 0x6D, 3, 4, false);
            case 0x7D: // Absolute,X    ADC $4400,X   $7D  3   4+
                return new Operation("ADC", Mode.AbsoluteX, "ADC $4400,X", 0x7D, 3, 4, true);
            case 0x79: // Absolute,Y    ADC $4400,Y   $79  3   4+
                return new Operation("ADC", Mode.AbsoluteY, "ADC $4400,Y", 0x79, 3, 4, true);
            case 0x61: // Indirect,X    ADC ($44,X)   $61  2   6
                return new Operation("ADC", Mode.PreindexedIndirectX, "ADC ($44,X)", 0x61, 2, 6, false);
            case 0x71: // Indirect,Y    ADC ($44),Y   $71  2   5+
                return new Operation("ADC", Mode.PostindexedIndirectY, "ADC ($44),Y", 0x71, 2, 5, true);
                
            // AND        MODE           SYNTAX       HEX LEN TIM
            case 0x29: // Immediate     AND #$44      $29  2   2
                return new Operation("AND", Mode.Immediate, "AND #$44", 0x29, 2, 2, false);
            case 0x25: // Zero Page     AND $44       $25  2   3
                return new Operation("AND", Mode.ZeroPage, "AND $44", 0x25, 2, 3, false);
            case 0x35: // Zero Page,X   AND $44,X     $35  2   4
                return new Operation("AND", Mode.ZeroPageX, "AND $44,X", 0x35, 2, 4, false);
            case 0x2D: // Absolute      AND $4400     $2D  3   4
                return new Operation("AND", Mode.Absolute, "AND $4400", 0x2D, 3, 4, false);
            case 0x3D: // Absolute,X    AND $4400,X   $3D  3   4+
                return new Operation("AND", Mode.AbsoluteX, "AND $4400,X", 0x3D, 3, 4, true);
            case 0x39: // Absolute,Y    AND $4400,Y   $39  3   4+
                return new Operation("AND", Mode.AbsoluteY, "AND $4400,Y", 0x39, 3, 4, true);
            case 0x21: // Indirect,X    AND ($44,X)   $21  2   6
                return new Operation("AND", Mode.PreindexedIndirectX, "AND ($44,X)", 0x21, 2, 6, false);
            case 0x31: // Indirect,Y    AND ($44),Y   $31  2   5+
                return new Operation("AND", Mode.PostindexedIndirectY, "AND ($44),Y", 0x31, 2, 5, true);

            // ASL        MODE           SYNTAX       HEX LEN TIM
            case 0x0A: // Accumulator   ASL A         $0A  1   2
                return new Operation("ASL", Mode.Accumulator, "ASL A", 0x0A, 1, 2, false);
            case 0x06: // Zero Page     ASL $44       $06  2   5
                return new Operation("ASL", Mode.ZeroPage, "ASL $44", 0x06, 2, 5, false);
            case 0x16: // Zero Page,X   ASL $44,X     $16  2   6
                return new Operation("ASL", Mode.ZeroPageX, "ASL $44,X", 0x16, 2, 6, false);
            case 0x0E: // Absolute      ASL $4400     $0E  3   6
                return new Operation("ASL", Mode.Absolute, "ASL $4400", 0x0E, 3, 6, false);
            case 0x1E: // Absolute,X    ASL $4400,X   $1E  3   7
                return new Operation("ASL", Mode.AbsoluteX, "ASL $4400,X", 0x1E, 3, 7, false);

            // BIT        MODE           SYNTAX       HEX LEN TIM
            case 0x24: // Zero Page     BIT $44       $24  2   3
                return new Operation("BIT", Mode.ZeroPage, "BIT $44", 0x24, 2, 3, false);
            case 0x2C: // Absolute      BIT $4400     $2C  3   4
                return new Operation("BIT", Mode.Absolute, "BIT $4400", 0x2C, 3, 4, false);

            // CMP        MODE           SYNTAX       HEX LEN TIM
            case 201: // Immediate     CMP #$44      $C9  2   2
                return new Operation("CMP", Mode.Immediate, "CMP #$44", 201, 2, 2, false);
            case 197: // Zero Page     CMP $44       $C5  2   3
                return new Operation("CMP", Mode.ZeroPage, "CMP $44", 197, 2, 3, false);
            case 213: // Zero Page,X   CMP $44,X     $D5  2   4
                return new Operation("CMP", Mode.ZeroPageX, "CMP $44,X", 213, 2, 4, false);
            case 205: // Absolute      CMP $4400     $CD  3   4
                return new Operation("CMP", Mode.Absolute, "CMP $4400", 205, 3, 4, false);
            case 221: // Absolute,X    CMP $4400,X   $DD  3   4+
                return new Operation("CMP", Mode.AbsoluteX, "CMP $4400,X", 221, 3, 4, true);
            case 217: // Absolute,Y    CMP $4400,Y   $D9  3   4+
                return new Operation("CMP", Mode.AbsoluteY, "CMP $4400,Y", 217, 3, 4, true);
            case 193: // Indirect,X    CMP ($44,X)   $C1  2   6
                return new Operation("CMP", Mode.PreindexedIndirectX, "CMP ($44,X)", 193, 2, 6, false);
            case 209: // Indirect,Y    CMP ($44),Y   $D1  2   5+
                return new Operation("CMP", Mode.PostindexedIndirectY, "CMP ($44),Y", 209, 2, 5, true);

            // CPX        MODE           SYNTAX       HEX LEN TIM
            case 224: // Immediate     CPX #$44      $E0  2   2
                return new Operation("CPX", Mode.Immediate, "CPX #$44", 224, 2, 2, false);
            case 228: // Zero Page     CPX $44       $E4  2   3
                return new Operation("CPX", Mode.ZeroPage, "CPX $44", 228, 2, 3, false);
            case 236: // Absolute      CPX $4400     $EC  3   4
                return new Operation("CPX", Mode.Absolute, "CPX $4400", 236, 3, 4, false);

            // CPY        MODE           SYNTAX       HEX LEN TIM
            case 192: // Immediate     CPY #$44      $C0  2   2
                return new Operation("CPY", Mode.Immediate, "CPY #$44", 192, 2, 2, false);
            case 196: // Zero Page     CPY $44       $C4  2   3
                return new Operation("CPY", Mode.ZeroPage, "CPY $44", 196, 2, 3, false);
            case 204: // Absolute      CPY $4400     $CC  3   4
                return new Operation("CPY", Mode.Absolute, "CPY $4400", 204, 3, 4, false);

            // DEC        MODE           SYNTAX       HEX LEN TIM
            case 198: // Zero Page     DEC $44       $C6  2   5
                return new Operation("DEC", Mode.ZeroPage, "DEC $44", 198, 2, 5, false);
            case 214: // Zero Page,X   DEC $44,X     $D6  2   6
                return new Operation("DEC", Mode.ZeroPageX, "DEC $44,X", 214, 2, 6, false);
            case 206: // Absolute      DEC $4400     $CE  3   6
                return new Operation("DEC", Mode.Absolute, "DEC $4400", 206, 3, 6, false);
            case 222: // Absolute,X    DEC $4400,X   $DE  3   7
                return new Operation("DEC", Mode.AbsoluteX, "DEC $4400,X", 222, 3, 7, false);

            // EOR        MODE           SYNTAX       HEX LEN TIM
            case 73: // Immediate     EOR #$44      $49  2   2
                return new Operation("EOR", Mode.Immediate, "EOR #$44", 73, 2, 2, false);
            case 69: // Zero Page     EOR $44       $45  2   3
                return new Operation("EOR", Mode.ZeroPage, "EOR $44", 69, 2, 3, false);
            case 85: // Zero Page,X   EOR $44,X     $55  2   4
                return new Operation("EOR", Mode.ZeroPageX, "EOR $44,X", 85, 2, 4, false);
            case 77: // Absolute      EOR $4400     $4D  3   4
                return new Operation("EOR", Mode.Absolute, "EOR $4400", 77, 3, 4, false);
            case 93: // Absolute,X    EOR $4400,X   $5D  3   4+
                return new Operation("EOR", Mode.AbsoluteX, "EOR $4400,X", 93, 3, 4, true);
            case 89: // Absolute,Y    EOR $4400,Y   $59  3   4+
                return new Operation("EOR", Mode.AbsoluteY, "EOR $4400,Y", 89, 3, 4, true);
            case 65: // Indirect,X    EOR ($44,X)   $41  2   6
                return new Operation("EOR", Mode.PreindexedIndirectX, "EOR ($44,X)", 65, 2, 6, false);
            case 81: // Indirect,Y    EOR ($44),Y   $51  2   5+
                return new Operation("EOR", Mode.PostindexedIndirectY, "EOR ($44),Y", 81, 2, 5, true);

            // INC        MODE           SYNTAX       HEX LEN TIM
            case 230: // Zero Page     INC $44       $E6  2   5
                return new Operation("INC", Mode.ZeroPage, "INC $44", 230, 2, 5, false);
            case 246: // Zero Page,X   INC $44,X     $F6  2   6
                return new Operation("INC", Mode.ZeroPageX, "INC $44,X", 246, 2, 6, false);
            case 238: // Absolute      INC $4400     $EE  3   6
                return new Operation("INC", Mode.Absolute, "INC $4400", 238, 3, 6, false);
            case 254: // Absolute,X    INC $4400,X   $FE  3   7
                return new Operation("INC", Mode.AbsoluteX, "INC $4400,X", 254, 3, 7, false);
            case 0x1A:
                return new Operation("INA", Mode.Implied, "INA", 254, 1, 6, false);

            // JMP        MODE           SYNTAX       HEX LEN TIM
            case 76: // Absolute      JMP $5597     $4C  3   3
                return new Operation("JMP", Mode.Absolute, "JMP $5597", 76, 3, 3, false);
            case 108: // Indirect      JMP ($5597)   $6C  3   5
                return new Operation("JMP", Mode.AbsoluteIndirect, "JMP ($5597)", 108, 3, 5, false);

            // JSR        MODE           SYNTAX       HEX LEN TIM
            case 32: // Absolute      JSR $5597     $20  3   6
                return new Operation("JSR", Mode.Absolute, "JSR $5597", 32, 3, 6, false);

            // LDA        MODE           SYNTAX       HEX LEN TIM
            case 169: // Immediate     LDA #$44      $A9  2   2
                return new Operation("LDA", Mode.Immediate, "LDA #$44", 169, 2, 2, false);
            case 165: // Zero Page     LDA $44       $A5  2   3
                return new Operation("LDA", Mode.ZeroPage, "LDA $44", 165, 2, 3, false);
            case 181: // Zero Page,X   LDA $44,X     $B5  2   4
                return new Operation("LDA", Mode.ZeroPageX, "LDA $44,X", 181, 2, 4, false);
            case 173: // Absolute      LDA $4400     $AD  3   4
                return new Operation("LDA", Mode.Absolute, "LDA $4400", 173, 3, 4, false);
            case 189: // Absolute,X    LDA $4400,X   $BD  3   4+
                return new Operation("LDA", Mode.AbsoluteX, "LDA $4400,X", 189, 3, 4, true);
            case 185: // Absolute,Y    LDA $4400,Y   $B9  3   4+
                return new Operation("LDA", Mode.AbsoluteY, "LDA $4400,Y", 185, 3, 4, true);
            case 161: // Indirect,X    LDA ($44,X)   $A1  2   6
                return new Operation("LDA", Mode.PreindexedIndirectX, "LDA ($44,X)", 161, 2, 6, false);
            case 177: // Indirect,Y    LDA ($44),Y   $B1  2   5+
                return new Operation("LDA", Mode.PostindexedIndirectY, "LDA ($44),Y", 177, 2, 5, true);

            // LDX        MODE           SYNTAX       HEX LEN TIM
            case 162: // Immediate     LDX #$44      $A2  2   2
                return new Operation("LDX", Mode.Immediate, "LDX #$44", 162, 2, 2, false);
            case 166: // Zero Page     LDX $44       $A6  2   3
                return new Operation("LDX", Mode.ZeroPage, "LDX $44", 166, 2, 3, false);
            case 182: // Zero Page,Y   LDX $44,Y     $B6  2   4
                return new Operation("LDX", Mode.ZeroPageY, "LDX $44,Y", 182, 2, 4, false);
            case 174: // Absolute      LDX $4400     $AE  3   4
                return new Operation("LDX", Mode.Absolute, "LDX $4400", 174, 3, 4, false);
            case 190: // Absolute,Y    LDX $4400,Y   $BE  3   4+
                return new Operation("LDX", Mode.AbsoluteY, "LDX $4400,Y", 190, 3, 4, true);

            // LDY        MODE           SYNTAX       HEX LEN TIM
            case 160: // Immediate     LDY #$44      $A0  2   2
                return new Operation("LDY", Mode.Immediate, "LDY #$44", 160, 2, 2, false);
            case 164: // Zero Page     LDY $44       $A4  2   3
                return new Operation("LDY", Mode.ZeroPage, "LDY $44", 164, 2, 3, false);
            case 180: // Zero Page,X   LDY $44,X     $B4  2   4
                return new Operation("LDY", Mode.ZeroPageX, "LDY $44,X", 180, 2, 4, false);
            case 172: // Absolute      LDY $4400     $AC  3   4
                return new Operation("LDY", Mode.Absolute, "LDY $4400", 172, 3, 4, false);
            case 188: // Absolute,X    LDY $4400,X   $BC  3   4+
                return new Operation("LDY", Mode.AbsoluteX, "LDY $4400,X", 188, 3, 4, true);

            // LSR        MODE           SYNTAX       HEX LEN TIM
            case 74: // Accumulator   LSR A         $4A  1   2
                return new Operation("LSR", Mode.Accumulator, "LSR A", 74, 1, 2, false);
            case 70: // Zero Page     LSR $44       $46  2   5
                return new Operation("LSR", Mode.ZeroPage, "LSR $44", 70, 2, 5, false);
            case 86: // Zero Page,X   LSR $44,X     $56  2   6
                return new Operation("LSR", Mode.ZeroPageX, "LSR $44,X", 86, 2, 6, false);
            case 78: // Absolute      LSR $4400     $4E  3   6
                return new Operation("LSR", Mode.Absolute, "LSR $4400", 78, 3, 6, false);
            case 94: // Absolute,X    LSR $4400,X   $5E  3   7
                return new Operation("LSR", Mode.AbsoluteX, "LSR $4400,X", 94, 3, 7, false);

            // NOP        MODE           SYNTAX       HEX LEN TIM
            case 234: // Implied       NOP           $EA  1   2
                return new Operation("NOP", Mode.Implied, "NOP", 234, 1, 2, false);

            // ORA        MODE           SYNTAX       HEX LEN TIM
            case 9: // Immediate     ORA #$44      $09  2   2
                return new Operation("ORA", Mode.Immediate, "ORA #$44", 9, 2, 2, false);
            case 5: // Zero Page     ORA $44       $05  2   3
                return new Operation("ORA", Mode.ZeroPage, "ORA $44", 5, 2, 3, false);
            case 21: // Zero Page,X   ORA $44,X     $15  2   4
                return new Operation("ORA", Mode.ZeroPageX, "ORA $44,X", 21, 2, 4, false);
            case 13: // Absolute      ORA $4400     $0D  3   4
                return new Operation("ORA", Mode.Absolute, "ORA $4400", 13, 3, 4, false);
            case 29: // Absolute,X    ORA $4400,X   $1D  3   4+
                return new Operation("ORA", Mode.AbsoluteX, "ORA $4400,X", 29, 3, 4, true);
            case 0x19: // Absolute,Y    ORA $4400,Y   $19  3   4+
                return new Operation("ORA", Mode.AbsoluteY, "ORA $4400,Y", 25, 3, 4, true);
            case 1: // Indirect,X    ORA ($44,X)   $01  2   6
                return new Operation("ORA", Mode.PreindexedIndirectX, "ORA ($44,X)", 1, 2, 6, false);
            case 17: // Indirect,Y    ORA ($44),Y   $11  2   5+
                return new Operation("ORA", Mode.PostindexedIndirectY, "ORA ($44),Y", 17, 2, 5, true);

            // ROL        MODE           SYNTAX       HEX LEN TIM
            case 42: // Accumulator   ROL A         $2A  1   2
                return new Operation("ROL", Mode.Accumulator, "ROL A", 42, 1, 2, false);
            case 38: // Zero Page     ROL $44       $26  2   5
                return new Operation("ROL", Mode.ZeroPage, "ROL $44", 38, 2, 5, false);
            case 54: // Zero Page,X   ROL $44,X     $36  2   6
                return new Operation("ROL", Mode.ZeroPageX, "ROL $44,X", 54, 2, 6, false);
            case 46: // Absolute      ROL $4400     $2E  3   6
                return new Operation("ROL", Mode.Absolute, "ROL $4400", 46, 3, 6, false);
            case 62: // Absolute,X    ROL $4400,X   $3E  3   7
                return new Operation("ROL", Mode.AbsoluteX, "ROL $4400,X", 62, 3, 7, false);

            // ROR        MODE           SYNTAX       HEX LEN TIM
            case 106: // Accumulator   ROR A         $6A  1   2
                return new Operation("ROR", Mode.Accumulator, "ROR A", 106, 1, 2, false);
            case 102: // Zero Page     ROR $44       $66  2   5
                return new Operation("ROR", Mode.ZeroPage, "ROR $44", 102, 2, 5, false);
            case 118: // Zero Page,X   ROR $44,X     $76  2   6
                return new Operation("ROR", Mode.ZeroPageX, "ROR $44,X", 118, 2, 6, false);
            case 110: // Absolute      ROR $4400     $6E  3   6
                return new Operation("ROR", Mode.Absolute, "ROR $4400", 110, 3, 6, false);
            case 126: // Absolute,X    ROR $4400,X   $7E  3   7
                return new Operation("ROR", Mode.AbsoluteX, "ROR $4400,X", 126, 3, 7, false);

            // RTI        MODE           SYNTAX       HEX LEN TIM
            case 0x40: // Implied       RTI           $40  1   6
                return new Operation("RTI", Mode.Implied, "RTI", 64, 1, 6, false);

            // RTS        MODE           SYNTAX       HEX LEN TIM
            case 0x60: // Implied       RTS           $60  1   6
                return new Operation("RTS", Mode.Implied, "RTS", 96, 1, 6, false);

            // SBC        MODE           SYNTAX       HEX LEN TIM
            case 233: // Immediate     SBC #$44      $E9  2   2
                return new Operation("SBC", Mode.Immediate, "SBC #$44", 233, 2, 2, false);
            case 229: // Zero Page     SBC $44       $E5  2   3
                return new Operation("SBC", Mode.ZeroPage, "SBC $44", 229, 2, 3, false);
            case 245: // Zero Page,X   SBC $44,X     $F5  2   4
                return new Operation("SBC", Mode.ZeroPageX, "SBC $44,X", 245, 2, 4, false);
            case 237: // Absolute      SBC $4400     $ED  3   4
                return new Operation("SBC", Mode.Absolute, "SBC $4400", 237, 3, 4, false);
            case 253: // Absolute,X    SBC $4400,X   $FD  3   4+
                return new Operation("SBC", Mode.AbsoluteX, "SBC $4400,X", 253, 3, 4, true);
            case 249: // Absolute,Y    SBC $4400,Y   $F9  3   4+
                return new Operation("SBC", Mode.AbsoluteY, "SBC $4400,Y", 249, 3, 4, true);
            case 225: // Indirect,X    SBC ($44,X)   $E1  2   6
                return new Operation("SBC", Mode.PreindexedIndirectX, "SBC ($44,X)", 225, 2, 6, false);
            case 241: // Indirect,Y    SBC ($44),Y   $F1  2   5+
                return new Operation("SBC", Mode.PostindexedIndirectY, "SBC ($44),Y", 241, 2, 5, true);

            // STA        MODE           SYNTAX       HEX LEN TIM
            case 133: // Zero Page     STA $44       $85  2   3
                return new Operation("STA", Mode.ZeroPage, "STA $44", 133, 2, 3, false);
            case 149: // Zero Page,X   STA $44,X     $95  2   4
                return new Operation("STA", Mode.ZeroPageX, "STA $44,X", 149, 2, 4, false);
            case 141: // Absolute      STA $4400     $8D  3   4
                return new Operation("STA", Mode.Absolute, "STA $4400", 141, 3, 4, false);
            case 157: // Absolute,X    STA $4400,X   $9D  3   5
                return new Operation("STA", Mode.AbsoluteX, "STA $4400,X", 157, 3, 5, false);
            case 153: // Absolute,Y    STA $4400,Y   $99  3   5
                return new Operation("STA", Mode.AbsoluteY, "STA $4400,Y", 153, 3, 5, false);
            case 129: // Indirect,X    STA ($44,X)   $81  2   6
                return new Operation("STA", Mode.PreindexedIndirectX, "STA ($44,X)", 129, 2, 6, false);
            case 145: // Indirect,Y    STA ($44),Y   $91  2   6
                return new Operation("STA", Mode.PostindexedIndirectY, "STA ($44),Y", 145, 2, 6, false);

            // STX        MODE           SYNTAX       HEX LEN TIM
            case 134: // Zero Page     STX $44       $86  2   3
                return new Operation("STX", Mode.ZeroPage, "STX $44", 134, 2, 3, false);
            case 150: // Zero Page,Y   STX $44,Y     $96  2   4
                return new Operation("STX", Mode.ZeroPageY, "STX $44,Y", 150, 2, 4, false);
            case 142: // Absolute      STX $4400     $8E  3   4
                return new Operation("STX", Mode.Absolute, "STX $4400", 142, 3, 4, false);

            // STY        MODE           SYNTAX       HEX LEN TIM
            case 132: // Zero Page     STY $44       $84  2   3
                return new Operation("STY", Mode.ZeroPage, "STY $44", 132, 2, 3, false);
            case 148: // Zero Page,X   STY $44,X     $94  2   4
                return new Operation("STY", Mode.ZeroPageX, "STY $44,X", 148, 2, 4, false);
            case 140: // Absolute      STY $4400     $8C  3   4
                return new Operation("STY", Mode.Absolute, "STY $4400", 140, 3, 4, false);

            // BRK        MODE           SYNTAX       HEX LEN TIM
            case 0: // Implied       BRK           $00  1   7
                return new Operation("BRK", Mode.Implied, "BRK", 0, 1, 7, false);
                
            // Flag (Processor Status) Instructions 
            //These instructions are implied mode, have a length of one byte and require two machine cycles. Implied mode
            case 24: // CLC (CLear Carry)              $18
                return new Operation("CLC", Mode.Implied, "CLC", opVal, 1, 2, false);
            case 56: // SEC (SEt Carry)                $38
                return new Operation("SEC", Mode.Implied, "SEC", opVal, 1, 2, false);
            case 88: // CLI (CLear Interrupt)          $58
                return new Operation("CLI", Mode.Implied, "CLI", opVal, 1, 2, false);
            case 120: // SEI (SEt Interrupt)            $78
                return new Operation("SEI", Mode.Implied, "SEI", opVal, 1, 2, false);
            case 184: // CLV (CLear oVerflow)           $B8
                return new Operation("CLV", Mode.Implied, "CLV", opVal, 1, 2, false);
            case 216: // CLD (CLear Decimal)            $D8
                return new Operation("CLD", Mode.Implied, "CLD", opVal, 1, 2, false);
            case 248: // SED (SEt Decimal)              $F8
                return new Operation("SED", Mode.Implied, "SED", opVal, 1, 2, false);
                
            // Branch Instructions
            // All branches are relative mode and have a length of two bytes. Syntax is "Bxx Displacement" or (better) "Bxx Label" Relative mode
            case 16: // BPL (Branch on PLus)           $10
                return new Operation("BPL", Mode.Relative, "BPL #DISPLACEMENT", opVal, 2, 2, false);
            case 48: // BMI (Branch on MInus)          $30
                return new Operation("BMI", Mode.Relative, "BMI #DISPLACEMENT", opVal, 2, 2, false);
            case 80: // BVC (Branch on oVerflow Clear) $50
                return new Operation("BVC", Mode.Relative, "BVC #DISPLACEMENT", opVal, 2, 2, false);
            case 112: // BVS (Branch on oVerflow Set)   $70
                return new Operation("BVS", Mode.Relative, "BVS #DISPLACEMENT", opVal, 2, 2, false);
            case 144: // BCC (Branch on Carry Clear)    $90
                return new Operation("BCC", Mode.Relative, "BCC #DISPLACEMENT", opVal, 2, 2, false);
            case 176: // BCS (Branch on Carry Set)      $B0
                return new Operation("BCS", Mode.Relative, "BCS #DISPLACEMENT", opVal, 2, 2, false);
            case 0xD0: // BNE (Branch on Not Equal)      $D0
                return new Operation("BNE", Mode.Relative, "BNE #DISPLACEMENT", opVal, 2, 2, false);
            case 240: // BEQ (Branch on EQual)          $F0
                return new Operation("BEQ", Mode.Relative, "BEQ #DISPLACEMENT", opVal, 2, 2, false);
                
            // Stack Instructions
            // These instructions are implied mode, have a length of one byte and require machine cycles as indicated. Relative Mode
            case 154: // TXS (Transfer X to Stack ptr)   $9A  2
                return new Operation("TXS", Mode.Relative, "TXS", opVal, 1, 2, false);
            case 186: // TSX (Transfer Stack ptr to X)   $BA  2
                return new Operation("TSX", Mode.Relative, "TSX", opVal, 1, 2, false);
            case 72: // PHA (PusH Accumulator)          $48  3
                return new Operation("PHA", Mode.Relative, "PHA", opVal, 1, 3, false);
            case 104: // PLA (PuLl Accumulator)          $68  4
                return new Operation("PLA", Mode.Relative, "PLA", opVal, 1, 4, false);
            case 8: // PHP (PusH Processor status)     $08  3
                return new Operation("PHP", Mode.Relative, "PHP", opVal, 1, 3, false);
            case 40: // PLP (PuLl Processor status)     $28  4
                return new Operation("PLP", Mode.Relative, "PLP", opVal, 1, 4, false);
                
            //Register Instructions
            // These instructions are implied mode, have a length of one byte and require two machine cycles. Implied mode
            case 170: // TAX (Transfer A to X)    $AA
                return new Operation("TAX", Mode.Implied, "TAX", opVal, 1, 2, false);
            case 138: // TXA (Transfer X to A)    $8A
                return new Operation("TXA", Mode.Implied, "TXA", opVal, 1, 2, false);
            case 202: // DEX (DEcrement X)        $CA
                return new Operation("DEX", Mode.Implied, "DEX", opVal, 1, 2, false);
            case 232: // INX (INcrement X)        $E8
                return new Operation("INX", Mode.Implied, "INX", opVal, 1, 2, false);
            case 168: // TAY (Transfer A to Y)    $A8
                return new Operation("TAY", Mode.Implied, "TAY", opVal, 1, 2, false);
            case 152: // TYA (Transfer Y to A)    $98
                return new Operation("TYA", Mode.Implied, "TYA", opVal, 1, 2, false);
            case 136: // DEY (DEcrement Y)        $88
                return new Operation("DEY", Mode.Implied, "DEY", opVal, 1, 2, false);
            case 200: // INY (INcrement Y)        $C8
                return new Operation("INY", Mode.Implied, "INY", opVal, 1, 2, false);
                
            default:
                return new Operation("", Mode.Missing_Error, "", opVal, 1, 0, false);
        }
    }
    
    public static boolean isCommandBranch(int opVal) {
        return opVal == 0x10
                || opVal == 0x30
                || opVal == 0x50
                || opVal == 0x70
                || opVal == 0x90
                || opVal == 0xB0
                || opVal == 0xD0
                || opVal == 0xF0;
    }
    
    public static boolean isCommandReturn(int opVal) {
        return opVal == 0x60
                || opVal == 0x40;
    }
    
    /**
     * Identifies jump commands
     * (does NOT identify jump to subroutine)
     * @param opVal
     * @return 
     */
    public static boolean isJump(int opVal) {
        return opVal == 0x4C
                || opVal == 0x6c;
    }
    
    public static boolean isJumpSubroutine(int opVal) {
        return opVal == 0x20;
    }
    
    
    
    public static Operation getCommand(int[] sysMem, int location) throws Exception {
        var op = Parser.getOperation(sysMem[location]);
        
        String command = "(" + Integer.toHexString(op.opValue) + ")\t" + op.opCode;
        
        switch (op.mode) {
            case Relative:
            case ZeroPage:
                command += "\t$" + Integer.toHexString(sysMem[location + 1]);
                break;
            case Implied: // whole lotta nothing to do here
                break;
            case Accumulator:
                command += "\tA";
                break;
            case Immediate:
                command += "\t#$" + Integer.toHexString(sysMem[location + 1]);
                break;
            case ZeroPageX:
                command += "\t$" + Integer.toHexString(sysMem[location + 1]) + ",X";
                break;
            case ZeroPageY:
                command += "\t$" + Integer.toHexString(sysMem[location + 1]) + ",Y";
                break;
            case Absolute:
                var parameter = (sysMem[location+2] * 256) + sysMem[location + 1]; // little endian
                // if JMP command jumping to self, it is an infinite loop
                switch (op.opValue) {
                    case 0x4C: // JMP
                        // test if this is infinite loop
                        if(parameter == location) {
                            command += "\t*\t;\tInfinite Loop";
                        } else {

                            command += "\t#$" + Integer.toHexString(parameter) + "\t;\tJump to : $" + Integer.toHexString(parameter); 
                        }

                        break;
                    case 0x20: // JSR
                        command += "\t#$" + Integer.toHexString(parameter) + "\t;\tJump to sub: $" + Integer.toHexString(parameter);
                        break;
                    default:
                        command += "\t#$" + Integer.toHexString(parameter);
                        break;
                }
                break;

            case AbsoluteX:
                command += "\t$" + Integer.toHexString(sysMem[location + 2]) + Integer.toHexString(sysMem[location + 1]) + ",X";  // little endian
                break;
            case AbsoluteY:
                command += "\t$" + Integer.toHexString(sysMem[location + 2]) + Integer.toHexString(sysMem[location + 1]) + ",Y";  // little endian
                break;
            case AbsoluteIndirect:
                command += "\t(" + Integer.toHexString(sysMem[location + 2]) + Integer.toHexString(sysMem[location + 1]) + ")";  // little endian
                break;
            case PreindexedIndirectX:
                command += "\t(" + Integer.toHexString(sysMem[location + 1]) + ",X)";  // little endian
                break;
            case PostindexedIndirectY:
                command += "\t(" + Integer.toHexString(sysMem[location + 1]) + "),Y";  // little endian
                break;
            case Missing_Error:
                command += "????:\t0x" + Integer.toHexString(sysMem[location]) + " : " + Integer.toString(sysMem[location]);
                break;
            case Indirect: // TODO: Implement if I'm ever going to do SNES stufd
            default:
                command = "UNREC MODE:\t" + op.mode;
        }
        
        if (isCommandBranch(op.opValue) || isJump(op.opValue) || isJumpSubroutine(op.opValue)) {
            switch (op.mode) {
                case Absolute:
                    op.setTarget((sysMem[location + 2] * 256) + sysMem[location + 1]); // little endian
                    break;
                case Relative:
                    // branches to program location plus offset plus length of branch command plus branch value
                    op.setTarget(op.length + location + (int)(byte)sysMem[location +  1]);
                    break;
                case AbsoluteIndirect:
                    // Impossible to predict target (based on X or Y values)
                    break;
                default:
                    throw new Exception("Unhandled addressing mode: " + op.mode);
            }
        }
        
        if (isCommandBranch(op.opValue)) {
            command += "\t;\tBranches to $" + Integer.toHexString(op.getTarget());
        }
        
        if (op.opValue == 0x6C) { // Indirect JMP
            command += "\t;\t Indirect jump to unpredictable location.";
        }
        
        var retOp = new Operation(op.opCode, op.mode, command, op.opValue, op.length, op.time, op.orMoreTime);
        retOp.setOpLocation(location);
        retOp.setTarget(op.getTarget());
        
        return retOp;
    }
}
