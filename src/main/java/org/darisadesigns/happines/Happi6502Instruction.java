/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

import java.util.function.IntSupplier;

/**
 *
 * @author draque
 */
public class Happi6502Instruction {
    public final String name;
    public final IntSupplier addrmode;
    public final IntSupplier operation;
    public final int cycles;
    
    public Happi6502Instruction(String _name, IntSupplier _addrmode, IntSupplier _operation, int _cycles) {
        name = _name;
        operation = _operation;
        addrmode = _addrmode;
        cycles = _cycles;
    } 
}
