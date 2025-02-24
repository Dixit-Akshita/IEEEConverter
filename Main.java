import java.util.Scanner;
public class Main {
        private static String decimalToBinary(int num) {
            return Integer.toBinaryString(num);
        }
        private static String fractionToBinary(double fraction, int precision) {
            StringBuilder binary = new StringBuilder();
            while (precision > 0 && fraction != 0) {
                fraction *= 2;
                if (fraction >= 1) {
                    binary.append("1");
                    fraction -= 1;
                } else {
                    binary.append("0");
                }
                precision--;
            }
            return binary.toString();
        }
        private static String binaryToHex(String binary) {
            int decimal = Integer.parseInt(binary, 2);
            return String.format("%8s", Integer.toHexString(decimal)).replace(' ', '0').toUpperCase();
        }
        public static String toIEEE754(float num) {
            if (num == 0) return "0 00000000 00000000000000000000000";
            int signBit = (num < 0) ? 1 : 0; // Determine sign bit
            num = Math.abs(num); // Work with absolute value
            int intPart = (int) num;
            double fracPart = num - intPart;
            String intBinary = decimalToBinary(intPart);
            String fracBinary = fractionToBinary(fracPart, 23);
            String normalizedBinary = intBinary + "." + fracBinary;
            int exponent = intBinary.length() - 1;
            int biasedExponent = exponent + 127;
            String exponentBinary = String.format("%8s", Integer.toBinaryString(biasedExponent)).replace(' ', '0');
            String mantissa = (intBinary + fracBinary).substring(1, Math.min(24, intBinary.length() + fracBinary.length()));
            while (mantissa.length() < 23) {
                mantissa += "0";
            }
           String ieeeBinary = signBit + exponentBinary + mantissa;
            String ieeeHex = binaryToHex(ieeeBinary);
            return signBit + " " + exponentBinary + " " + mantissa + " (Hex: 0x" + ieeeHex + ")";
        }
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a decimal number: ");
            float num = sc.nextFloat();
            System.out.println("\nStep-by-step Conversion:");
            System.out.println("------------------------");
            System.out.println("1. Given Decimal Number: " + num);
            int signBit = (num < 0) ? 1 : 0;
            System.out.println("2. Sign Bit: " + signBit);
            num = Math.abs(num);
            int intPart = (int) num;
            double fracPart = num - intPart;
            String intBinary = decimalToBinary(intPart);
            System.out.println("3. Integer Part Binary: " + intBinary);
            String fracBinary = fractionToBinary(fracPart, 23);
            System.out.println("4. Fractional Part Binary: " + fracBinary);
            String normalizedBinary = intBinary + "." + fracBinary;
            int exponent = intBinary.length() - 1;
            System.out.println("5. Normalized Binary: " + normalizedBinary);
            int biasedExponent = exponent + 127;
            String exponentBinary = String.format("%8s", Integer.toBinaryString(biasedExponent)).replace(' ', '0');
            System.out.println("6. Exponent (Biased by 127): " + biasedExponent + " (" + exponentBinary + ")");
            String mantissa = (intBinary + fracBinary).substring(1, Math.min(24, intBinary.length() + fracBinary.length()));
            while (mantissa.length() < 23) {
                mantissa += "0";
            }
            System.out.println("7. Mantissa (23 bits): " + mantissa);
            String ieeeBinary = signBit + exponentBinary + mantissa;
            String ieeeHex = binaryToHex(ieeeBinary);
            System.out.println("8. Final IEEE 754 Representation:");
            System.out.println(" " + signBit + " " + exponentBinary + " " + mantissa);
            System.out.println("9. IEEE 754 Hexadecimal Representation: " + ieeeHex);
            sc.close();
        }
    }
