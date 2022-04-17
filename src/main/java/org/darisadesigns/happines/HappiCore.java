/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.darisadesigns.happines.Operation.Mode;

/**
 *
 * @author draque
 */
public class HappiCore {
    public static final int HEADER_SIZE = 0x10;
    public static final int PRG_START_LOC = 0x8000;
    public static final int SINGLE_BANK_MIRROR_LOC = 0xC000;
    private final NesHeader header;
    private final int[] rawFile;
    private int[] systemMemory;
    private int[] chrMemory;
    private final int resetLocation;
    private final int nmiAddress;
    private final int irqAddress;
    private boolean printData = false;
    
    public static void main(String[] Args) {
        try {
            var core = new HappiCore("/Users/draque/NetBeansProjects/DWIV.nes");
            core.parseData();
        } catch (Exception e) {
            System.out.println("What a butt you are.");
            e.printStackTrace();
        }
    }
    
    public HappiCore(String path) throws Exception {
        File file = new File(path);
        var rawBytes = new byte[(int)file.length()];
        
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            dis.readFully(rawBytes);
        }
        
        rawFile = new int[rawBytes.length];
        for (int i = 0; i < rawBytes.length; i++) {
            rawFile[i] = rawBytes[i] & 0xff;
        }
        
        header = NesHeader.CreateHeader(Arrays.copyOf(rawBytes, HEADER_SIZE));
        int vectorLocation = header.getPrgSizeBytes() + 10; // vectors begin 10 bytes after PRG Data ends

        nmiAddress = (rawFile[vectorLocation + 1] * 0x100) + rawFile[vectorLocation ];
        resetLocation = (rawFile[vectorLocation + 3] * 0x100) + rawFile[vectorLocation + 2];
        irqAddress = (rawFile[vectorLocation + 5] * 0x100) + rawFile[vectorLocation + 4];
        
        loadSystemMemory(rawFile);
    }
    
    private void loadSystemMemory(int[] rawData) {
        var systemMemorySize = 0x10000 + header.getPrgSizeBytes();
        systemMemory = new int[systemMemorySize];
        chrMemory = new int[header.getChrSizeBytes()];
        
        int prgEnd = HEADER_SIZE + header.getPrgSizeBytes();
        int locationOffset = -HEADER_SIZE + PRG_START_LOC;
        
        // TODO: Handle when there are more than 2 banks (need to switch in and out)
        for (int location = HEADER_SIZE; location < prgEnd; location++) {
            systemMemory[location + locationOffset] = rawData[location];
        }
        
        // single bank memory is mirrored for some damned reason
        if (header.getPrgBlocks() == 1) {
            locationOffset = -HEADER_SIZE + SINGLE_BANK_MIRROR_LOC;
            for (int location = HEADER_SIZE; location < prgEnd; location++) {
                systemMemory[location + locationOffset] = rawData[location];
            }
        }
        
        for (int location = 0; location < + header.getChrSizeBytes(); location++) {
            chrMemory[location] = rawData[location + prgEnd];
        }
    }
    
    public int[] getChrMemory() {
        return chrMemory;
    }
    
    public void parseData() throws Exception {
        var executionLocations = new ArrayList<Integer>();
        gatherExecutionLocations(resetLocation, executionLocations);
        gatherExecutionLocations(nmiAddress, executionLocations);
        gatherExecutionLocations(irqAddress, executionLocations);
        
        Collections.sort(executionLocations);
        
        var bad = 0;
        
        while (!executionLocations.isEmpty()) {
            var location = executionLocations.get(0);
            
            while (location < 0xFFFF) {
                executionLocations.remove((Object)location);
                var nextOp = Parser.getCommand(systemMemory, location);
                
                System.out.println(Integer.toHexString(location) + "\t" + nextOp.syntax);
                
                if (nextOp.mode == Mode.Missing_Error) {                    
                    
                    break;
                }
                
                if (Parser.isJump(nextOp.opValue) 
                        || Parser.isCommandReturn(nextOp.opValue) 
                        || nextOp.mode == Mode.Missing_Error) {
                    printData(location, executionLocations);
                    break;
                }
                
                location += nextOp.length;
            }
        }
        
        System.out.print("\nBad lines: " + bad);
    }
    
    private void printData(int startLocation, List<Integer> executionLocations) {
        System.out.println("\nDATA\n"); // TODO: change this to .db once I figure it out
        
        if (!printData || executionLocations.contains(startLocation)) {
            return;
        }
        
        int dataRead = 0;
        while (dataRead + startLocation < 0xFFFF && !executionLocations.contains(dataRead)) {
            if (dataRead % 32 == 0) {
                System.out.print("\n" + Integer.toHexString(dataRead + startLocation) + "\t");
            } else if (dataRead % 2 == 0) {
                System.out.print(" ");
            }
            
            if (systemMemory[dataRead + startLocation] < 0x10) {
                System.out.print("0");
            }
            
            System.out.print(Integer.toHexString(systemMemory[dataRead + startLocation]));
            dataRead++;
        }
        System.out.println("\n");
    }
    
    private void gatherExecutionLocations(int readFrom, List<Integer> executionLocations) throws Exception {
        if (executionLocations.contains(readFrom)) {
            return;
        }
        
        executionLocations.add(readFrom);
        
        var location = readFrom;
        
        while (location < 0xFFFF) {
            var nextOp = Parser.getCommand(systemMemory, location);
            
            if (nextOp.mode == Mode.Missing_Error) {
                return;
            }
            
            if (nextOp.getTarget() != 0 
                    && nextOp.getTarget() != nextOp.getOpLocation() // skip infinite loops
                    && (Parser.isCommandBranch(nextOp.opValue) 
                        || Parser.isJump(nextOp.opValue) 
                        || Parser.isJumpSubroutine(nextOp.opValue))) {
                
                if (nextOp.getTarget() < resetLocation) {
                    System.out.println(Integer.toHexString(nextOp.getOpLocation()) + "\t" + nextOp.syntax + "\t" + Integer.toHexString(nextOp.getTarget()));
                }
                
                gatherExecutionLocations(nextOp.getTarget(), executionLocations);
            }
            
            if (Parser.isJump(nextOp.opValue) || Parser.isCommandReturn(nextOp.opValue)) {
                break;
            }
            
            location += nextOp.length;
        }
    }
    
    private ArrayList<Integer> getRoutineLocations(List<Operation> operations) {
        var routineLocations = new ArrayList<Integer>();
        
        routineLocations.add(resetLocation);
        routineLocations.add(nmiAddress);
        
        for (var op : operations) {
            if (Parser.isJump(op.opValue) || Parser.isCommandBranch(op.opValue) || Parser.isJumpSubroutine(op.opValue)) {
                routineLocations.add(op.getTarget());
            }
        }
        
        return routineLocations;
    }
}
