/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

import java.util.Map;
import java.util.Scanner;
import org.darisadesigns.happines.Happi6502.FLAGS6502;

/**
 *
 * @author draque
 */
public class Demo {
    private final Happi6502Bus nes = new Happi6502Bus();
    private Map<Integer, String> mapAsm;
    
    public static void main(String[] Args)
    {
	Demo demo = new Demo();
	demo.load();
        demo.run();
    }
    
    private void run() {
        System.out.print("\n\n\n\n\n\nS = Step Instruction    R = RESET    I = IRQ    N = NMI");
        
        Scanner in = new Scanner(System.in);
        String input = "";
        
        while (true) {    
            input = in.nextLine();
            
            switch (input) {
                case "s" -> {
                    nes.cpu.clock();
                    
                    while(!nes.cpu.complete()) {
                        nes.cpu.clock();
                    }
                }
                case "r" -> {
                    nes.cpu.reset();
                }
                case "i" -> {
                    nes.cpu.irq();
                }
                case "n" -> {
                    nes.cpu.nmi();
                }
                case "x" -> {
                    break;
                }
                default -> {
                    System.out.println("TRY AGAIN LOSER");
                }
            }
            
            // Draw Ram Page 0x00		
            DrawRam(0x0000, 16, 16);
            DrawRam(0x8000, 16, 16);
            DrawCpu();
//            DrawCode(448, 72, 26);

            System.out.print("\n\n\n\n\n\nS = Step Instruction    R = RESET    I = IRQ    N = NMI");
        }
    }
    
//    void DrawCode(int x, int y, int nLines)
//    {
//        auto it_a = mapAsm.find(nes.cpu.pc);
//        int nLineY = (nLines >> 1) * 10 + y;
//        if (it_a != mapAsm.end())
//        {
//            DrawString(x, nLineY, (*it_a).second, olc::CYAN);
//            while (nLineY < (nLines * 10) + y)
//            {
//                nLineY += 10;
//                if (++it_a != mapAsm.end())
//                {
//                    DrawString(x, nLineY, (*it_a).second);
//                }
//            }
//        }
//
//        it_a = mapAsm.find(nes.cpu.pc);
//        nLineY = (nLines >> 1) * 10 + y;
//        if (it_a != mapAsm.end())
//        {
//            while (nLineY > y)
//            {
//                nLineY -= 10;
//                if (--it_a != mapAsm.end())
//                {
//                    DrawString(x, nLineY, (*it_a).second);
//                }
//            }
//        }
//    }
    
    private void DrawCpu()
	{
		System.out.print("\nSTATUS:");
		System.out.print(" N:" + ((nes.cpu.status & FLAGS6502.N.getValue()) > 0 ? "t" : "f"));
		System.out.print(" V:" + ((nes.cpu.status & FLAGS6502.V.getValue()) > 0 ? "t" : "f"));
		System.out.print("-" + ((nes.cpu.status & FLAGS6502.U.getValue()) > 0 ? "t" : "f"));
		System.out.print(" B:" + ((nes.cpu.status & FLAGS6502.B.getValue()) > 0 ? "t" : "f"));
		System.out.print(" D:" + ((nes.cpu.status & FLAGS6502.D.getValue()) > 0 ? "t" : "f"));
		System.out.print(" I:" + ((nes.cpu.status & FLAGS6502.I.getValue()) > 0 ? "t" : "f"));
		System.out.print(" Z:" + ((nes.cpu.status & FLAGS6502.Z.getValue()) > 0 ? "t" : "f"));
		System.out.print(" C:" + ((nes.cpu.status & FLAGS6502.C.getValue()) > 0 ? "t" : "f"));
		System.out.println("PC: $" + Happi6502.hex(nes.cpu.pc, 4));
		System.out.println("A: $" +  Happi6502.hex(nes.cpu.a, 2) + "  [" + nes.cpu.a + "]");
		System.out.println("X: $" +  Happi6502.hex(nes.cpu.x, 2) + "  [" + nes.cpu.x + "]");
		System.out.println("Y: $" +  Happi6502.hex(nes.cpu.y, 2) + "  [" + nes.cpu.y + "]");
		System.out.println("Stack P: $" + Happi6502.hex(nes.cpu.stkp, 4));
	}
    
    private void DrawRam(int nAddr, int nRows, int nColumns)
    {
        System.out.print("\n");
        for (int row = 0; row < nRows; row++)
        {
            String sOffset = "$" + Happi6502.hex(nAddr, 4) + ":";
            for (int col = 0; col < nColumns; col++)
            {
                    sOffset += " " + Happi6502.hex(nes.read(nAddr, true), 2);
                    nAddr += 1;
            }
            System.out.println(sOffset);
        }
    }
    
    private void load() {
        // Load Program (assembled at https://www.masswerk.at/6502/assembler.html)
        /*
                *=$8000
                LDX #10
                STX $0000
                LDX #3
                STX $0001
                LDY $0000
                LDA #0
                CLC
                loop
                ADC $0001
                DEY
                BNE loop
                STA $0002
                NOP
                NOP
                NOP
        */

        String programString = "A2 0A 8E 00 00 A2 03 8E 01 00 AC 00 00 A9 00 18 6D 01 00 88 D0 FA 8D 02 00 EA EA EA";
        String[] programStringBytes = programString.split(" ");
        int[] program = new int[programStringBytes.length];
        
        for (int i = 0; i < programStringBytes.length; i++) {
            program[i] = Integer.decode("0x" + programStringBytes[i]);
        }
        
        var offset = 0x8000;
        
        for (int i = 0; i < program.length; i++) {
            nes.write(offset, program[i]);
            offset++;
        }
        
        // Set Reset Vector
        nes.write(0xFFFC, 0x00);
        nes.write(0xFFFD, 0x80);

        // Dont forget to set IRQ and NMI vectors if you want to play with those

        // Extract dissassembly
        mapAsm = nes.cpu.disassemble(0x0000, 0xFFFF);

        // Reset
        nes.cpu.reset();
    }
}
