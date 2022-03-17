/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines;

/**
 *
 * @author draque
 */
public class killme {
    public static void main(String[] Args) {
        System.out.println((int)(byte)0xfb);
    }
    
    public static void getOpCode() {
        var first = true;
        var parse = "Implied       BRK           $00  1   7";
        
        for (String line : parse.split("\n")) {
            var cleanedLine = line.replace("Zero Page", "ZeroPage").replaceAll("\\s+", " ");
            var values = cleanedLine.split(" ");
            
            String opCode = "";
            String modeString = "";
            String syntax = "";
            String value = "";
            String length = "";
            String time = "";
            
            try {
                if (values.length == 6) {
                    opCode = values[1];
                    modeString = getMode(values[0]);
                    syntax = values[1] + " " + values[2];
                    value = Integer.decode(values[3].replaceAll("\\$", "0x")).toString();
                    length = values[4];
                    time = values[5];
                } else if (values.length == 5){
                    opCode = values[1];
                    modeString = getMode(values[0]);
                    syntax = values[1];
                    value = Integer.decode(values[2].replaceAll("\\$", "0x")).toString();
                    length = values[3];
                    time = values[4];
                } else {
                    System.out.println("ERROR: " + cleanedLine);
                    return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            
            if (first) {
                System.out.println("\n\n            // " + opCode + "        MODE           SYNTAX       HEX LEN TIM");
                first = false;
            }
            System.out.println("            case " + value + ":" + " // " + line);
            System.out.println(
                    "                return new Operation(" +
                            "\"" + opCode + "\"" + ", " +
                            modeString + ", " +
                            "\"" + syntax +"\"" + ", " +
                            value + ", " +
                            length + ", " +
                            time.replaceAll("\\+", "") + ", " +
                            (time.contains("+") ? "true" : "false") + ");"
            );
        }
    }
    
    public static String getMode(String modeString) throws Exception {
        switch(modeString) {
            case "Implied":
                return "Mode.Implied";
            case "Accumulator":
                return "Mode.Accumulator";
            case "Immediate":
                return "Mode.Immediate";
            case "ZeroPage":
                return "Mode.ZeroPage";
            case "ZeroPage,X":
                return "Mode.ZeroPageX";
            case "ZeroPage,Y":
                return "Mode.ZeroPageY";
            case "Absolute":
                return "Mode.Absolute";
            case "Absolute,X":
                return "Mode.AbsoluteX";
            case "Absolute,Y":
                return "Mode.AbsoluteY";
            case "Indirect":
                return "Mode.Indirect";
            case "Indirect,X":
                return "Mode.IndirectX";
            case "Indirect,Y":
                return "Mode.IndirectY";
        }
        
        throw new Exception("FUCKMODE: " + modeString);
    }
}
