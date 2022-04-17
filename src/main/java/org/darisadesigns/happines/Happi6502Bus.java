/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

/**
 *
 * @author draque
 */
public class Happi6502Bus {
    
    public static int RAM_SIZE = 0x10000;
    public final Happi6502 cpu; 
    private final int[] ram;
    
    public Happi6502Bus() {
        cpu = new Happi6502();
        ram = new int[RAM_SIZE];
        
        cpu.connectBus(this);
        
        for (int i = 0; i < RAM_SIZE; i++) {
            ram[i] = 0;
        }
    }
    
    /**
     * 
     * @param addr 16 bit
     * @param data 8 bit
     */
    public void write(int addr, int data) {
        if (addr >= 0x0 && addr <= 0xFFFF) {
            ram[addr] = data;
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
        if (addr >= 0x0 && addr <= 0xFFFF) {
            return ram[addr];
        }
        
        return -1; // TODO: throw appropriate error here instead
    }
}
