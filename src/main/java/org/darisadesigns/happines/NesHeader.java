/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

/**
 *
 * @author draque
 */
public class NesHeader {
    public static final int PRG_BLOCK_SIZE = 16384;
    public static final int CHR_BLOCK_SIZE = 8192;
    
    private final boolean isINes; // whether this is the original iNES header type (true) or 2.0 (false)
    private final int prgSize; // program data in 16384 byte units
    private final int chrSize; // media data in 8192 byt units (0 means board uses CHR RAM)
    private final int pgRAMSize; //
    private final Mirroring mirroring;
    private final boolean batteryPresent;
    private final boolean trainerPresent; // 512-byte trainer at $7000-$71FF (stored before PRG data)
    private final boolean forceFourscreen; // ignore mirroring and provide four screen VRAM
    private final int lowMapperNybble;
    private final int upperMapperNybble;
    private final int superMapperNybble; // used with 2.0 only
    private final int subMapperNybble; // used with 2.0 only
    
    private NesHeader(byte[] headerData, boolean _isINes) {        
        isINes = _isINes;
        prgSize = headerData[4] & 0xFF; // account for LSB
        chrSize = headerData[5] & 0xFF; // account for LSB
        
        mirroring = ((headerData[6]) & 0x01) == 0x01 ? Mirroring.VERTICAL : Mirroring.HORIZONTAL;
        batteryPresent = ((headerData[6] >> 1) & 0x01) == 0x01;
        trainerPresent = ((headerData[6] >> 2) & 0x01) == 0x01;
        forceFourscreen = ((headerData[6] >> 3) & 0x01) == 0x01;
        lowMapperNybble = headerData[6] >> 4;
        
        if (isINes) {
            upperMapperNybble = headerData[7] >> 4;
            pgRAMSize = headerData[8] == 0 ? 8192 : headerData[8]; // 0 implies 8KB
            superMapperNybble = 0;
            subMapperNybble = 0;
            // headerData[9] ignored
            // headerData[10] ignored
            // headerData[11] ignored
            // headerData[12] ignored
            // headerData[13] ignored
            // headerData[14] ignored
            // headerData[15] ignored
        } else {
            upperMapperNybble = 0;
            pgRAMSize = 0;
            superMapperNybble = 0;
            subMapperNybble = 0;
            throw new RuntimeException("NES 2.0 header not yet handled");
        }
    }
    
    public static NesHeader CreateHeader(byte[] headerData) throws Exception {
        boolean iNesFormat=false;
        if (headerData[0]=='N' && headerData[1]=='E' && headerData[2]=='S' && headerData[3]==0x1A) {
            iNesFormat=true;
        }

        if (!iNesFormat) {
            throw new Exception("Unrecognized header format!");
        }
        
        // bits that dictate whether flag 8+ should be read in the 2.0 manner
        if ((headerData[7] & 0x0C) == 0x08) {
            iNesFormat = false;
        }
        
        if (headerData.length != HappiCore.HEADER_SIZE) {
            throw new Exception("Header data wrong size!");
        }
        
        return new NesHeader(headerData, iNesFormat);
    }
    
    public int getPrgBlocks() {
        return prgSize;
    }
    
    public int getPrgSizeBytes() {
        return prgSize * PRG_BLOCK_SIZE;
    }
    
    public enum Mirroring {
        HORIZONTAL,
        VERTICAL
    }

    public int getChrSizeBytes() {
        return chrSize * CHR_BLOCK_SIZE;
    }
}
