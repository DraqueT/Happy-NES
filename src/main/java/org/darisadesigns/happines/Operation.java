/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

/**
 *
 * @author draque
 */
public class Operation {
    public final String opCode;
    public final Mode mode;
    public final String syntax;
    public final int opValue;
    public final int length;
    public final int time;
    public final boolean orMoreTime; // if true, add 1 cycle if page boundary crossed
    private int target;
    private int opLocation;
    
    public Operation(String _opCode, Mode _mode, String _syntax, int _opValue, int _length, int _time, boolean _orMoreTime) {
        opCode = _opCode;
        mode = _mode;
        syntax = _syntax;
        opValue = _opValue;
        length = _length;
        time = _time;
        orMoreTime = _orMoreTime;
        target = 0;
        opLocation = 0;
    }
    
    public enum Mode {
        Relative, // relocate somewhere from -128 to 127 bytes from current execution (can use labels)
        Implied,
        Accumulator, // no params! executes on accumulator
        Immediate, // parameters are literals: # before means decimal, #$ before means hex #% mens binary
        ZeroPage, // $XX value is single byte. 0x00 prefixed to this automatically, as zero page is addresses from 0x0000 - 0x00FF
        ZeroPageX, // $XX value is single bye. Offset by X - Example: "LDA $44,X" loads into the accumulator whatever is in $44 + X memory position
        ZeroPageY, // Same but with Y
        Absolute, // $XXXX full address, 2 bytes
        AbsoluteX, // Example: "$4444,X" used for looping and indexing
        AbsoluteY, // Same as above, but with Y
        AbsoluteIndirect, // uses little endian to read one (two byte) address from the address targetted EX: JMP($2000) <- jumps to addresse STORED in location $2000 (onky used with JMP)
        PreindexedIndirectX, // uses little endian to read one (two byte) address from Zero Page at offset in X register Ex: LDA ($80, X) <- finds address at $0080 + X, then loads whatever that points to into A
        PostindexedIndirectY, // same as above, but with y, and formatting "($40),Y
        Indirect, // (65c02 only)
        Missing_Error // reserved for unrecognized commands
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int _target) {
        this.target = _target;
    }

    public int getOpLocation() {
        return opLocation;
    }

    public void setOpLocation(int opLocation) {
        this.opLocation = opLocation;
    }
}
