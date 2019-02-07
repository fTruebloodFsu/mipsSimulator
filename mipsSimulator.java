import java.util.function.Consumer;
import java.io.*;
import java.lang.*;
import java.util.*;

public class mipsSimulator {

    class Rinstructions{
        private String insR;
        private String opCode;
        private String funct;

        public void Rinstructions(String ins, String code, String function){
            insR = ins;
            opCode = code;
            funct = function;
        }
    }


    class Iinstructions{
        private String insI;
        private String opCode;

        public void Iinstructions(String ins, String code){
            insI = ins;
            opCode = code;
        }
    }

    class Jinstructions{
        private String insJ;
        private String opCode;

        public void Jinstructions(String ins, String code){
            insJ = ins;
            opCode = code;
        }
    }

    public boolean isR(char arrBit[]){
        boolean result = false;
        if(arrBit[0] == '0' && arrBit[1] == '0' && arrBit[2] == '0' && arrBit[3] == '0' && arrBit[4] == '0' && arrBit[5] == '0') {
            result = true;
        }
        return result;
    }

    public static String getR(char arrBit[], Rinstructions arrIns[]){
        boolean result = false;
        String tempFunct = "";

        if(arrBit[0] == '0' && arrBit[1] == '0' && arrBit[2] == '0' && arrBit[3] == '0' && arrBit[4] == '0' && arrBit[5] == '0') {
            result = true;
        }
        if(result) {
            for (int i = arrBit.length - 6; i < arrBit.length; i++) {
                tempFunct = tempFunct + arrBit[i];
            }

        }
        String tempInstruction = "";
        for (int i = 0; i < arrIns.length; i++) {
            if (tempFunct.equals(arrIns[i].funct)) {
                tempInstruction = arrIns[i].insR;
            }
        }

        if(tempInstruction.equals("add")){
            mipsAdd(arrBit);
        }
        if(tempInstruction.equals("sub")){
            mipsSub(arrBit);
        }
        if(tempInstruction.equals("and")){
            mipsAnd(arrBit);
        }
        if(tempInstruction.equals("or")){
            mipsOr(arrBit);
        }
        if(tempInstruction.equals("nor")){
            mipsNor(arrBit);
        }
        if(tempInstruction.equals("xor")){
            mipsXor(arrBit);
        }
        if(tempInstruction.equals("sll")){
            mipsSll(arrBit);
        }
        if(tempInstruction.equals("srl")){
            mipsSrl(arrBit);
        }
        if(tempInstruction.equals("sra")){
            mipsSra(arrBit);
        }

        if(tempInstruction.equals("sllv")){
            mipsSllv(arrBit);
        }
        if(tempInstruction.equals("srlv")){
            mipsSrlv(arrBit);
        }

        if(tempInstruction.equals("srav")){
            mipsSrav(arrBit);
        }

        if(tempInstruction.equals("addu")){
            mipsAddu(arrBit);
        }

        if(tempInstruction.equals("subu")){
            mipsSubu(arrBit);
        }

        if(tempInstruction.equals("mfhi")){
            mipsMfhi(arrBit);
        }

        if(tempInstruction.equals("mthi")){
            mipsMthi(arrBit);
        }

        if(tempInstruction.equals("mflo")){
            mipsMflo(arrBit);
        }

        if(tempInstruction.equals("mtlo")){
            mipsMtlo(arrBit);
        }

        if(tempInstruction.equals("jr")){
            mipsJr(arrBit);
        }

        if(tempInstruction.equals("jalr")){
            mipsJalr(arrBit);
        }

        if(tempInstruction.equals("mult")){
            mipsMult(arrBit);
        }

        if(tempInstruction.equals("multu")){
            mipsMultu(arrBit);
        }

        if(tempInstruction.equals("div")){
            mipsDiv(arrBit);
        }

        if(tempInstruction.equals("divu")){
            mipsDivu(arrBit);
        }

        if(tempInstruction.equals("slt")){
            mipsSlt(arrBit);
        }

        if(tempInstruction.equals("sltu")){
            mipsSltu(arrBit);
        }

        if(tempInstruction.equals("movn")){
            mipsMovn(arrBit);
        }

        if(tempInstruction.equals("movz")){
            mipsMovz(arrBit);
        }

        if(tempInstruction.equals("syscall")){
            mipsSyscall(arrBit);
        }

        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];
        String shamt = "" + arrBit[21] + arrBit[22] + arrBit[23] + arrBit[24] + arrBit[25];

        //System.out.print("\n"+ tempInstruction + " " + findReg(rdR) + ", " + findReg(rsR) + ", " + findReg(rtR) + "\n");
        return tempInstruction;
    }

    public static void mipsSyscall(char arrBit[]) {
        int x = charArrToInt(useThisReg("00010"));

        if(x == 8){
            int a0 = charArrToInt(useThisReg("00100"));
            //int a1 = charArrToInt(useThisReg("00101"));
            int sizeMem = globalStorage.size();
            //int placeToStore = charArrToInt(useThisReg("11100")) + (4 * sizeMem+1);
            System.out.print("please enter a string: ");
            Scanner ss = new Scanner(System.in);
            String input = ss.nextLine();
            char arr[] = input.toCharArray();

            String hexStr = "";

            for(int i = 0; i < arr.length; i++){
                hexStr = hexStr + Integer.toHexString((int) arr[i]);
            }

            int hexStrSize = hexStr.length();
            int wordOffSetAdjust = 8 - hexStrSize % 8;

            for(int i = 0; i < wordOffSetAdjust; i ++){
                hexStr = hexStr + '0';
            }

            int n = 0;
            int m = 8;
            long tempNum = 0;
            String temp = "";
            String tempBin = "";
            while(m < hexStr.length() + 1){
                temp = hexStr.substring(n,m);
                temp = reverseString(temp);
                tempNum = Long.parseLong(temp, 16);
                tempBin = Long.toBinaryString(tempNum);
                tempBin = extendTo32Bit(tempBin);
                String temp1 = Integer.toString(a0);
                globalStorage.put(temp1, tempBin);
                n = n + 8;
                m = m + 8;
                a0 = a0 + 4;
            }
        }

        if(x == 10){
            System.out.print("\nend of program\n");
            printAllReg();
            System.exit(0);
        }
        if(x == 1){
            int y = 0;
            if(useThisReg("00100")[0] == '0'){
                y = charArrToInt(useThisReg("00100"));
            }
            else{
                String temp1 = undoTwosComp(useThisReg("00100"));
                y = (-1)* stringToInt(temp1)-1;
            }
            System.out.print(y + "\n");
        }
        if(x == 5){
            System.out.print("please enter an integer: ");
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            if(i >= 0){
                String input = intToBinString(i);
                input = extendTo32Bit(input);
                char arrIntInput[] = input.toCharArray();
                for(int j = 0; j < arrIntInput.length; j++){
                    $v0[j] = arrIntInput[j];
                }
            }
            else{
                i = i * (-1);
                String input = intToBinString(i);
                input = extendTo32Bit(input);
                char arrInput[] = input.toCharArray();
                input = arrToTwosComp(arrInput);
                char arrInput2s[] = input.toCharArray();
                for(int j = 0; j < arrInput2s.length; j++){
                    $v0[j] = arrInput2s[j];
                }
            }
            //sc.close();
        }
        if(x == 4){
            String stringToDisplay = "";
            int z = charArrToInt($a0);
            if(z%4 != 0){
                int fix = z%4;
                z = z - fix;
                z = z + 4;
            }
            while(true) {
                String addressOfString = Integer.toString(z);

                String stringAtAddress = (String) globalStorage.get(addressOfString);

                char arrString[] = stringAtAddress.toCharArray();

                String a = "0" + arrString[24] + arrString[25] + arrString[26] + arrString[27] + arrString[28] + arrString[29] + arrString[30] + arrString[31];
                String b = "0" + arrString[16] + arrString[17] + arrString[18] + arrString[19] + arrString[20] + arrString[21] + arrString[22] + arrString[23];
                String c = "0" + arrString[8] + arrString[9] + arrString[10] + arrString[11] + arrString[12] + arrString[13] + arrString[14] + arrString[15];
                String d = "0" + arrString[0] + arrString[1] + arrString[2] + arrString[3] + arrString[4] + arrString[5] + arrString[6] + arrString[7];

                int e = stringToInt(a);
                int f = stringToInt(b);
                int g = stringToInt(c);
                int h = stringToInt(d);

                char a1 = (char) e;
                char b1 = (char) f;
                char c1 = (char) g;
                char d1 = (char) h;

                if (e != 0) {
                    stringToDisplay = stringToDisplay + a1;
                    z++;
                }else{
                    break;
                }
                if (f != 0) {
                    stringToDisplay = stringToDisplay + b1;
                    z++;
                }else{
                    break;
                }
                if (g != 0) {
                    stringToDisplay = stringToDisplay + c1;
                    z++;
                }else{
                    break;
                }
                if (h != 0) {
                    stringToDisplay = stringToDisplay + d1;
                    z++;
                }else{
                    break;
                }

                //z = z + 4;
            }

            System.out.print(stringToDisplay + "\n");
        }
        incPcFour();
    }

    public static void mipsMovn(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        int x = 0;
        if(useThisReg(rtR)[0] == '0') {x = charArrToInt(useThisReg(rtR));}
        else{
            String temp1 = undoTwosComp(useThisReg(rtR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(x != 0){
            for(int i = 0; i < useThisReg(rdR).length; i++){
                useThisReg(rdR)[i] = useThisReg(rsR)[i];
            }
        }
        incPcFour();
    }

    public static void mipsMovz(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        int x = 0;
        if(useThisReg(rtR)[0] == '0') {x = charArrToInt(useThisReg(rtR));}
        else{
            String temp1 = undoTwosComp(useThisReg(rtR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(x == 0){
            for(int i = 0; i < useThisReg(rdR).length; i++){
                useThisReg(rdR)[i] = useThisReg(rsR)[i];
            }
        }
        incPcFour();
    }

    public static void mipsSltu(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        String tempX = charArrToString(useThisReg(rsR));
        long x = Long.parseLong(tempX, 2);
        String tempY = charArrToString(useThisReg(rtR));
        long y = Long.parseLong(tempY, 2);

        if(x >= y) {
            for(int i = 0; i < useThisReg(rdR).length; i++){
                useThisReg(rdR)[i] = '0';
            }
        }
        else{
            for(int i = 0; i < useThisReg(rdR).length; i++) {
                useThisReg(rdR)[i] = '0';
            }
            useThisReg(rdR)[31] = '1';
        }
        incPcFour();
    }


    public static void mipsSlt(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        int x = 0;
        int y = 0;
        if(useThisReg(rsR)[0] == '0') {x = charArrToInt(useThisReg(rsR));}
        else{
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        if(x >= y) {
            for(int i = 0; i < useThisReg(rdR).length; i++){
                useThisReg(rdR)[i] = '0';
            }
        }
        else{
            for(int i = 0; i < useThisReg(rdR).length; i++) {
                useThisReg(rdR)[i] = '0';
            }
            useThisReg(rdR)[31] = '1';
        }
        incPcFour();

    }

    public static void mipsDivu(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];

        String tempX = charArrToString(useThisReg(rsR));
        long x = Long.parseLong(tempX, 2);
        String tempY = charArrToString(useThisReg(rtR));
        long y = Long.parseLong(tempY, 2);

        if(y == 0){
            System.out.print("\n divide by zero error\n");
            System.exit(0);
        }

        long zDiv = x/y;
        long zRem = x%y;

        System.out.print("\n" + x + " / "+ y + " = " + zDiv + " remainder " + zRem + "\n");

        String tempZdiv = Long.toBinaryString(zDiv);
        String tempZrem = Long.toBinaryString(zRem);

        tempZdiv = extendTo32Bit(tempZdiv);
        tempZrem = extendTo32Bit(tempZrem);

        char arrDiv[] = tempZdiv.toCharArray();
        char arrRem[] = tempZrem.toCharArray();

        for(int i = 0; i < arrDiv.length; i++){
            $lo[i] = arrDiv[i];
            $hi[i] = arrRem[i];
        }
        incPcFour();
    }

    public static void mipsDiv(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];

        int x = 0;
        int y = 0;
        if(useThisReg(rsR)[0] == '0') {x = charArrToInt(useThisReg(rsR));}
        else{
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        if((x >= 0) && (y > 0)){
            int zDiv = x/y;
            int zRem = x%y;
            System.out.print("\n" + x + " / "+ y + " = " + zDiv + " remainder " + zRem + "\n");
            String tempZdiv = intToBinString(zDiv);
            String tempZrem = intToBinString(zRem);
            tempZdiv = extendTo32Bit(tempZdiv);
            tempZrem = extendTo32Bit(tempZrem);
            char arrDiv[] = tempZdiv.toCharArray();
            char arrRem[] = tempZrem.toCharArray();
            for(int i = 0; i < arrDiv.length; i++){
                $lo[i] = arrDiv[i];
                $hi[i] = arrRem[i];
            }
        }
        else if((x >= 0) && (y < 0)){
            y = y * (-1);
            int zDiv = x/y;
            int zRem = x%y;
            System.out.print("\n" + x + " / "+ (-1)*y + " = " + zDiv + " remainder " + zRem + "\n");
            String tempZdiv = intToBinString(zDiv);
            String tempZrem = intToBinString(zRem);
            tempZdiv = extendTo32Bit(tempZdiv);
            tempZrem = extendTo32Bit(tempZrem);
            char arrDiv[] = tempZdiv.toCharArray();
            char arrRem[] = tempZrem.toCharArray();
            for(int i = 0; i < arrDiv.length; i++){
                $lo[i] = arrDiv[i];
                $hi[i] = arrRem[i];
            }
        }
        else if((x < 0) && (y > 0)){
            x = x * (-1);
            int zDiv = x/y;
            int zRem = (x%y)-1;
            System.out.print("\n" + (-1)*x + " / "+ y + " = " + zDiv + " remainder " + zRem + "\n");
            String tempZdiv = intToBinString(zDiv);
            String tempZrem = intToBinString(zRem);
            tempZdiv = extendTo32Bit(tempZdiv);
            tempZrem = extendTo32Bit(tempZrem);
            char arrDiv[] = tempZdiv.toCharArray();
            char arrRem[] = tempZrem.toCharArray();
            tempZrem = arrToTwosComp(arrRem);
            char arrRem1[] = tempZrem.toCharArray();
            for(int i = 0; i < arrDiv.length; i++){
                $lo[i] = arrDiv[i];
                $hi[i] = arrRem[i];
            }
        }
        else if((x < 0) && (y < 0)){
            x = x * (-1);
            y = y * (-1);
            int zDiv = x/y;
            int zRem = (x%y)-1;
            System.out.print("\n" + (-1)*x + " / "+ y + " = " + zDiv + " remainder " + zRem + "\n");
            String tempZdiv = intToBinString(zDiv);
            String tempZrem = intToBinString(zRem);
            tempZdiv = extendTo32Bit(tempZdiv);
            tempZrem = extendTo32Bit(tempZrem);
            char arrDiv[] = tempZdiv.toCharArray();
            char arrRem[] = tempZrem.toCharArray();
            tempZrem = arrToTwosComp(arrRem);
            char arrRem1[] = tempZrem.toCharArray();
            for(int i = 0; i < arrDiv.length; i++){
                $lo[i] = arrDiv[i];
                $hi[i] = arrRem[i];
            }
        }
        else if(y == 0){
            System.out.print("\n divide by zero error\n");
            System.exit(0);
        }
        incPcFour();
    }

    public static void mipsMultu(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];

        String tempX = charArrToString(useThisReg(rsR));
        long x = Long.parseLong(tempX, 2);
        String tempY = charArrToString(useThisReg(rtR));
        long y = Long.parseLong(tempY, 2);

        long z = x * y;
        System.out.print("\n" + x + " * "+ y + " = " + z + "\n");

        if(z < 0) {
            System.out.print("\noverflow\n");
            System.exit(0);
        }

        String tempZ = Long.toBinaryString(z);
        System.out.print("\n" + tempZ + "\n");
        int zLen = tempZ.length();
        for(int i = 0; i < 64 - zLen; i++){
            tempZ =  '0' + tempZ;
        }
        System.out.print("\n" + tempZ + "\n");
        char arrZ[] = tempZ.toCharArray();
        for(int i = 0; i < 32; i++){
            $hi[i] = arrZ[i];
        }
        for(int i = 32; i < 64; i++){
            $lo[i - 32] = arrZ[i];
        }
        incPcFour();

    }

    public static void mipsMult(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtR = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        int x = 0;
        int y = 0;
        if(useThisReg(rsR)[0] == '0') {x = charArrToInt(useThisReg(rsR));}
        else{
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        long z = x * y;

        if(z >= 0){
            String tempZ = Long.toBinaryString(z);
            int zLen = tempZ.length();
            for(int i = 0; i < 64 - zLen; i++){
                tempZ =  '0' + tempZ;
            }
            char arrZ[] = tempZ.toCharArray();
            for(int i = 0; i < 32; i++){
                $hi[i] = arrZ[i];
            }
            for(int i = 32; i < 64; i++){
                $lo[i - 32] = arrZ[i];
            }
        }
        else{
            z = z * (-1);
            String tempZ = Long.toBinaryString(z);
            int zLen = tempZ.length();
            for(int i = 0; i < 64 - zLen; i++){
                tempZ =  '0' + tempZ;
            }
            char arrZ[] = tempZ.toCharArray();
            String tempZnew = arrToTwosComp(arrZ);
            char arrZnew[] = tempZnew.toCharArray();
            for(int i = 0; i < 32; i++){
                $hi[i] = arrZnew[i];
            }
            for(int i = 32; i < 64; i++){
                $lo[i-32] = arrZnew[i];
            }
        }
        incPcFour();

    }

    public static void mipsJr(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];

        for(int i = 0; i < useThisReg(rsR).length; i++){
            $pc[i] = useThisReg(rsR)[i];
        }
    }

    public static void mipsJalr(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        for(int i = 0; i < useThisReg(rdR).length; i++){
            useThisReg(rdR)[i] = $pc[i];
        }
        for(int i = 0; i < useThisReg(rsR).length; i++){
            $pc[i] = useThisReg(rsR)[i] ;
        }
    }

    public static void mipsMfhi(char arrBit[]) {
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        for(int i = 0; i < useThisReg(rdR).length; i++){
            useThisReg(rdR)[i] = $hi[i];
        }
        incPcFour();
    }

    public static void mipsMthi(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];

        for(int i = 0; i < useThisReg(rsR).length; i++){
            $hi[i] = useThisReg(rsR)[i];
        }
        incPcFour();
    }

    public static void mipsMflo(char arrBit[]) {
        String rdR = "" + arrBit[16] + arrBit[17] + arrBit[18] + arrBit[19] + arrBit[20];

        for(int i = 0; i < useThisReg(rdR).length; i++){
            useThisReg(rdR)[i] = $lo[i];
        }
        incPcFour();
    }

    public static void mipsMtlo(char arrBit[]) {
        String rsR = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];

        for(int i = 0; i < useThisReg(rsR).length; i++){
            $lo[i] = useThisReg(rsR)[i];
        }
        incPcFour();
    }

    public static void mipsAddu(char arr[]) {
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        String tempX = charArrToString(useThisReg(rsR));
        long x = Long.parseLong(tempX, 2);
        String tempY = charArrToString(useThisReg(rtR));
        long y = Long.parseLong(tempY, 2);

        long z = x + y;
        String tempZ = Long.toBinaryString(z);
        if (tempZ.length() >= 33) {
            System.out.print("\noverflow\n");
            System.exit(0);
        }
        else {  String tempZnew = extendTo32Bit(tempZ);
                char arrTemp[] = tempZnew.toCharArray();
                for(int i = 0; i < arrTemp.length; i ++){
                    useThisReg(rdR)[i] = arrTemp[i];
                }
        }
        incPcFour();

    }

    public static void mipsAdd(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        int x = 0;
        int y = 0;
        if(useThisReg(rsR)[0] == '0') {x = charArrToInt(useThisReg(rsR));}
        else{
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }


        int z = x + y;
        System.out.print("\n"+z+" = "+ x + " + " + y +"\n");

        if(z>=0) {
            String str = intToBinString(z);
            String str1 = extendTo32Bit(str);
            char temp[] = str1.toCharArray();
            System.out.print("\n");
            for (int i = 0; i < temp.length; i++) {
                useThisReg(rdR)[i] = temp[i];
            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }
        incPcFour();
    }

    public static void mipsSubu(char arr[]) {
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        String tempX = charArrToString(useThisReg(rsR));
        long x = Long.parseLong(tempX, 2);
        String tempY = charArrToString(useThisReg(rtR));
        long y = Long.parseLong(tempY, 2);

        long z = x - y;

        if(z <= Integer.MIN_VALUE){
            System.out.print("\noverflow");
            System.exit(0);
        }
        else {
            if (z >= 0) {
                String tempZ = Long.toBinaryString(z);
                String tempZext = extendTo32Bit(tempZ);
                char arrTemp[] = tempZext.toCharArray();
                for (int i = 0; i < arrTemp.length; i++) {
                    useThisReg(rdR)[i] = arrTemp[i];
                }
            } else {
                z = z * (-1);
                String temp = Long.toBinaryString(z);
                String temp1 = extendTo32Bit(temp);
                char temparr[] = temp1.toCharArray();
                String temp2 = arrToTwosComp(temparr);
                char temparr1[] = temp2.toCharArray();
                for (int i = 0; i < temparr.length; i++) {
                    useThisReg(rdR)[i] = temparr1[i];
                }
            }
        }
        incPcFour();
    }
    public static void mipsSub(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        int x = 0;
        int y = 0;
        if(useThisReg(rsR)[0] == '0') {x = charArrToInt(useThisReg(rsR));}
        else{
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        int z = x - y;

        System.out.print("\n"+z+" = "+ x + " - " + y +"\n");

        if(z>=0) {
            String str = intToBinString(z);
            String str1 = extendTo32Bit(str);
            char temp[] = str1.toCharArray();
            System.out.print("\n");
            for (int i = 0; i < temp.length; i++) {
                useThisReg(rdR)[i] = temp[i];
            }
        }
        else {
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }
        incPcFour();
    }

    public static void mipsAnd(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        for(int i = 0; i < arr.length; i++){
            if(useThisReg(rsR)[i] == '1' && useThisReg(rtR)[i] == '1'){useThisReg(rdR)[i] = '1';}
            else{useThisReg(rdR)[i] = '0';}
            System.out.print(useThisReg(rdR)[i]);
        }
        incPcFour();
    }

    public static void mipsOr(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        System.out.print("\n");
        for(int i = 0; i < arr.length; i++){
            if(useThisReg(rsR)[i] == '1' && useThisReg(rtR)[i] == '1'){useThisReg(rdR)[i] = '1';}
            else if(useThisReg(rsR)[i] == '0' && useThisReg(rtR)[i] == '1'){useThisReg(rdR)[i] = '1';}
            else if(useThisReg(rsR)[i] == '1' && useThisReg(rtR)[i] == '0'){useThisReg(rdR)[i] = '1';}
            else{useThisReg(rdR)[i] = '0';}
            System.out.print(useThisReg(rdR)[i]);
        }
        incPcFour();
    }

    public static void mipsNor(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        System.out.print("\n");
        for(int i = 0; i < arr.length; i++){
            if(useThisReg(rsR)[i] == '0' && useThisReg(rtR)[i] == '0'){useThisReg(rdR)[i] = '1';}
            else{useThisReg(rdR)[i] = '0';}
            System.out.print(useThisReg(rdR)[i]);
        }
        incPcFour();
    }

    public static void mipsXor(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        System.out.print("\n");
        for(int i = 0; i < arr.length; i++){
            if(useThisReg(rsR)[i] == '0' && useThisReg(rtR)[i] == '1'){useThisReg(rdR)[i] = '1';}
            else if(useThisReg(rsR)[i] == '1' && useThisReg(rtR)[i] == '0'){useThisReg(rdR)[i] = '1';}
            else{useThisReg(rdR)[i] = '0';}
            System.out.print(useThisReg(rdR)[i]);
        }
        incPcFour();
    }

    public static void mipsSll(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];
        String shamt = "" + arr[21] + arr[22] + arr[23] + arr[24] + arr[25];

        int x = stringToInt(shamt);
        int y = 0;
        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        int z = (y<<x);

        if(z>=0) {
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char arrT[] = temp1.toCharArray();
            for (int i = 0; i < arrT.length; i++) {
                useThisReg(rdR)[i] = arrT[i];
            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }
        incPcFour();
    }

    public static void mipsSrl(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];
        String shamt = "" + arr[21] + arr[22] + arr[23] + arr[24] + arr[25];

        int x = stringToInt(shamt);
        int y = 0;
        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        int z = (y>>x);
        System.out.print("\n");
        if(z>=0) {
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char arrT[] = temp1.toCharArray();
            for (int i = 0; i < arrT.length; i++) {
                useThisReg(rdR)[i] = arrT[i];
                System.out.print(useThisReg(rdR)[i]);

            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }
        useThisReg(rdR)[0] = '0';
        incPcFour();
    }

    public static void mipsSra(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];
        String shamt = "" + arr[21] + arr[22] + arr[23] + arr[24] + arr[25];

        int x = stringToInt(shamt);
        int y = 0;
        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        int z = (y>>x);

        if(z>=0) {
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char arrT[] = temp1.toCharArray();
            for (int i = 0; i < arrT.length; i++) {
                useThisReg(rdR)[i] = arrT[i];
                System.out.print(useThisReg(rdR)[i]);

            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }

        if(useThisReg(rtR)[0] == '0'){ useThisReg(rdR)[0] = '0';}
        else{useThisReg(rdR)[0] = '1';}
        incPcFour();
    }

    public static void mipsSllv(char arr[]){
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        int x = 0;
        int y = 0;
        if(useThisReg(rsR)[0] == '0') {x = charArrToInt(useThisReg(rsR));}
        else{
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1)* stringToInt(temp1)-1;
        }

        if(useThisReg(rtR)[0] == '0') {y = charArrToInt(useThisReg(rtR));}
        else{
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1)* stringToInt(temp1)-1;
        }

        int z = (y<<x);

        if(z>=0) {
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char arrT[] = temp1.toCharArray();
            for (int i = 0; i < arrT.length; i++) {
                useThisReg(rdR)[i] = arrT[i];
                System.out.print(useThisReg(rdR)[i]);

            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }
        incPcFour();
    }

    public static void mipsSrlv(char arr[]) {
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        int x = 0;
        int y = 0;
        if (useThisReg(rsR)[0] == '0') {
            x = charArrToInt(useThisReg(rsR));
        } else {
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1) * stringToInt(temp1) - 1;
        }

        if (useThisReg(rtR)[0] == '0') {
            y = charArrToInt(useThisReg(rtR));
        } else {
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1) * stringToInt(temp1) - 1;
        }

        int z = (y>>x);

        if(z>=0) {
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char arrT[] = temp1.toCharArray();
            for (int i = 0; i < arrT.length; i++) {
                useThisReg(rdR)[i] = arrT[i];
                System.out.print(useThisReg(rdR)[i]);

            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }
        incPcFour();

    }

    public static void mipsSrav(char arr[]) {
        String rsR = "" + arr[6] + arr[7] + arr[8] + arr[9] + arr[10];
        String rtR = "" + arr[11] + arr[12] + arr[13] + arr[14] + arr[15];
        String rdR = "" + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

        int x = 0;
        int y = 0;
        if (useThisReg(rsR)[0] == '0') {
            x = charArrToInt(useThisReg(rsR));
        } else {
            String temp = charArrToString(useThisReg(rsR));
            String temp1 = undoTwosComp(useThisReg(rsR));
            x = (-1) * stringToInt(temp1) - 1;
        }

        if (useThisReg(rtR)[0] == '0') {
            y = charArrToInt(useThisReg(rtR));
        } else {
            String temp = charArrToString(useThisReg(rtR));
            String temp1 = undoTwosComp(useThisReg(rtR));
            y = (-1) * stringToInt(temp1) - 1;
        }

        int z = (y>>x);

        if(z>=0) {
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char arrT[] = temp1.toCharArray();
            for (int i = 0; i < arrT.length; i++) {
                useThisReg(rdR)[i] = arrT[i];
                System.out.print(useThisReg(rdR)[i]);

            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rdR)[i] = temparr1[i];
            }
        }
        if(useThisReg(rtR)[0] == '0'){ useThisReg(rdR)[0] = '0';}
        else{useThisReg(rdR)[0] = '1';}
        incPcFour();
    }



    public boolean isI(char arr[], Iinstructions arrIns[]){
        boolean result = false;
        String temp = "" + arr[0] + arr[1] + arr[2] + arr[3] + arr[4] + arr[5];
        for(int i = 0; i < arrIns.length; i++){
            if(temp.equals(arrIns[i].opCode)) { result = true; }
        }
        return result;
    }

    public static String getI(char arrBit[], Iinstructions arrIns[]){
       String tempOpCode = "";
       for(int i = 0; i < 6; i++){
           tempOpCode = tempOpCode + arrBit[i];
       }
        String tempInstruction = "";
        for(int i = 0; i < arrIns.length; i++){
            if (tempOpCode.equals(arrIns[i].opCode)){
                tempInstruction = arrIns[i].insI;
            }
        }

        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];;
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for(int i = 16; i < arrBit.length; i++){
            Immediate = Immediate + arrBit[i];
        }

        char imm[] = Immediate.toCharArray();

        //System.out.print("\n"+ tempInstruction + " " + findReg(rtI) + ", " + findReg(rsI) + ", " + charArrToInt(imm) + "\n");

        if(tempInstruction.equals("sw")){
            mipsSw(arrBit);
        }

        if(tempInstruction.equals("lw")){
            mipsLw(arrBit);
        }

        if(tempInstruction.equals("beq")){
            mipsBeq(arrBit);
        }

        if(tempInstruction.equals("bne")){
            mipsBne(arrBit);
        }

        if(tempInstruction.equals("addi")){
            mipsAddi(arrBit);
        }

        if(tempInstruction.equals("andi")){
            mipsAndi(arrBit);
        }

        if(tempInstruction.equals("ori")){
            mipsOri(arrBit);
        }

        if(tempInstruction.equals("xori")){
            mipsXori(arrBit);
        }

        if(tempInstruction.equals("slti")){
            mipsSlti(arrBit);
        }

        if(tempInstruction.equals("blez")){
            mipsBlez(arrBit);
        }

        if(tempInstruction.equals("bgtz")){
            mipsBgtz(arrBit);
        }

        if(tempInstruction.equals("addiu")){
            mipsAddiu(arrBit);
        }

        if(tempInstruction.equals("sltiu")){
            mipsSltiu(arrBit);
        }

        if(tempInstruction.equals("sb")){
            mipsSb(arrBit);
        }

        if(tempInstruction.equals("sh")){
            mipsSh(arrBit);
        }

        if(tempInstruction.equals("lb")){
            mipsLb(arrBit);
        }

        if(tempInstruction.equals("lbu")){
            mipsLbu(arrBit);
        }

        if(tempInstruction.equals("lh")){
            mipsLh(arrBit);
        }

        if(tempInstruction.equals("lhu")){
            mipsLhu(arrBit);
        }

        if(tempInstruction.equals("lui")){
            mipsLui(arrBit);
        }

        return tempInstruction;
    }

    public boolean isJ(char arr[]){
        boolean result = false;
        if(arr[0] == '0' && arr[1] == '0' && arr[2] == '0' && arr[3] == '0' && arr[4] == '1' && arr[5] == '0'){
            result = true;
        }

        if(arr[0] == '0' && arr[1] == '0' && arr[2] == '0' && arr[3] == '0' && arr[4] == '1' && arr[5] == '1'){
            result = true;
        }

        return result;
    }

    public static void mipsBgtz(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = 0;
        int y = 0;
        int offSet = 0;

        if (useThisReg(rsI)[0] == '0') {
            x = charArrToInt(useThisReg(rsI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rsI));
            x = (-1) * stringToInt(temp1) - 1;
        }

        if (useThisReg(rtI)[0] == '0') {
            y = charArrToInt(useThisReg(rtI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rtI));
            y = (-1) * stringToInt(temp1) - 1;
        }

        if(x >= y){
            Immediate = Immediate + '0' + '0';
            char arrOffSet[] = Immediate.toCharArray();
            if(arrOffSet[0] == '0'){
                Immediate = extendTo32Bit(Immediate);
                offSet = stringToInt(Immediate);
                String temp = charArrToString($pc);
                long pcCount = Long.parseLong(temp, 2);
                pcCount = pcCount + offSet;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
            else{
                String pcOffSet = undoTwosComp(arrOffSet);
                int pcOS = stringToInt(pcOffSet) + 1;
                long pcCount = Long.parseLong(charArrToString($pc), 2);
                pcCount = pcCount - pcOS;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
        }
        else{
            incPcFour();
        }

    }

    public static void mipsBlez(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = 0;
        int y = 0;
        int offSet = 0;

        if (useThisReg(rsI)[0] == '0') {
            x = charArrToInt(useThisReg(rsI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rsI));
            x = (-1) * stringToInt(temp1) - 1;
        }

        if (useThisReg(rtI)[0] == '0') {
            y = charArrToInt(useThisReg(rtI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rtI));
            y = (-1) * stringToInt(temp1) - 1;
        }

        if(x <= y){
            Immediate = Immediate + '0' + '0';
            char arrOffSet[] = Immediate.toCharArray();
            if(arrOffSet[0] == '0'){
                Immediate = extendTo32Bit(Immediate);
                offSet = stringToInt(Immediate);
                String temp = charArrToString($pc);
                long pcCount = Long.parseLong(temp, 2);
                pcCount = pcCount + offSet;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
            else{
                String pcOffSet = undoTwosComp(arrOffSet);
                int pcOS = stringToInt(pcOffSet) + 1;
                long pcCount = Long.parseLong(charArrToString($pc), 2);
                pcCount = pcCount - pcOS;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
        }
        else{
            incPcFour();
        }

    }

    public static void mipsSltiu(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }
        int x = Integer.parseInt(Immediate, 2);
        String yVal = charArrToString(useThisReg(rsI));
        long y = Long.parseLong(yVal, 2);

        if(y < x){
            for(int i = 0; i < useThisReg(rtI).length; i++){
                useThisReg(rtI)[i] = '0';
            }
            useThisReg(rtI)[31] = '1';
        }
        else{
            for(int i = 0; i < useThisReg(rtI).length; i++){
                useThisReg(rtI)[i] = '0';
            }
        }
        incPcFour();
    }

    public static void mipsSlti(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = 0;
        int y = 0;
        if (useThisReg(rsI)[0] == '0') {
            x = charArrToInt(useThisReg(rsI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rsI));
            x = (-1) * stringToInt(temp1) - 1;
        }
        char yVal[] = Immediate.toCharArray();
        if (yVal[0] == '0') {
            y = charArrToInt(yVal);
        } else {
            String temp1 = undoTwosComp(yVal);
            y = (-1) * stringToInt(temp1) - 1;
        }

        if(x >= y) {
            for(int i = 0; i < useThisReg(rtI).length; i++){
                useThisReg(rtI)[i] = '0';
            }
        }
        else{
            for(int i = 0; i < useThisReg(rtI).length; i++) {
                useThisReg(rtI)[i] = '0';
            }
            useThisReg(rtI)[31] = '1';
        }
        incPcFour();

    }

    public static void mipsXori(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        Immediate = extendTo32Bit(Immediate);
        char arrImm[] = Immediate.toCharArray();

        for(int i = 0; i < arrImm.length; i++){
            if(useThisReg(rsI)[i] == '0' && arrImm[i] == '1'){useThisReg(rtI)[i] = '1';}
            else if(useThisReg(rsI)[i] == '1' && arrImm[i] == '0'){useThisReg(rtI)[i] = '1';}
            else{useThisReg(rtI)[i] = '0';}
        }
        incPcFour();

    }


    public static void mipsOri(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        Immediate = extendTo32Bit(Immediate);
        char arrImm[] = Immediate.toCharArray();

        for(int i = 0; i < arrImm.length; i++){
            if(useThisReg(rsI)[i] == '1' && arrImm[i] == '1'){useThisReg(rtI)[i] = '1';}
            else if(useThisReg(rsI)[i] == '0' && arrImm[i] == '1'){useThisReg(rtI)[i] = '1';}
            else if(useThisReg(rsI)[i] == '1' && arrImm[i] == '0'){useThisReg(rtI)[i] = '1';}
            else{useThisReg(rtI)[i] = '0';}
        }
        incPcFour();

    }

    public static void mipsAndi(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        Immediate = extendTo32Bit(Immediate);
        char arrImm[] = Immediate.toCharArray();

        for(int i = 0; i < arrImm.length; i++){
            if(useThisReg(rsI)[i] == '1' && arrImm[i] == '1'){useThisReg(rtI)[i] = '1';}
            else{useThisReg(rtI)[i] = '0';}
        }
        incPcFour();

    }

    public static void mipsAddiu(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = Integer.parseInt(Immediate, 2);
        String yVal = charArrToString(useThisReg(rsI));
        long y = Long.parseLong(yVal, 2);

        long z = x + y;
        String zVal = Long.toBinaryString(z);
        if(zVal.length()>32){
            System.out.print("\noverflow");
            System.exit(0);
        }
        zVal = extendTo32Bit(zVal);
        char arr[] = zVal.toCharArray();

        for(int i = 0; i < arr.length; i ++){
            useThisReg(rtI)[i] = arr[i];
        }
        incPcFour();
    }


    public static void mipsAddi(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = 0;
        int y = 0;
        if (useThisReg(rsI)[0] == '0') {
            x = charArrToInt(useThisReg(rsI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rsI));
            x = (-1) * stringToInt(temp1) - 1;
        }
        char yVal[] = Immediate.toCharArray();
        if (yVal[0] == '0') {
            y = charArrToInt(yVal);
        } else {
            String temp1 = undoTwosComp(yVal);
            y = (-1) * stringToInt(temp1) - 1;
        }

        int z = x + y;

        if(z > 0){
            String temp = intToBinString(z);
            temp = extendTo32Bit(temp);
            char arrZval[] = temp.toCharArray();
            for(int i = 0; i < arrZval.length; i++){
                useThisReg(rtI)[i] = arrZval[i];
            }
        }
        else{
            z = z * (-1);
            String temp = intToBinString(z);
            String temp1 = extendTo32Bit(temp);
            char temparr[] = temp1.toCharArray();
            String temp2 = arrToTwosComp(temparr);
            char temparr1[] = temp2.toCharArray();
            for (int i = 0; i < temparr.length; i++) {
                useThisReg(rtI)[i] = temparr1[i];
            }
        }
        incPcFour();

    }

    public static void mipsBeq(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }
        int x = 0;
        int y = 0;
        int offSet = 0;

        if (useThisReg(rsI)[0] == '0') {
            x = charArrToInt(useThisReg(rsI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rsI));
            x = (-1) * stringToInt(temp1) - 1;
        }

        if (useThisReg(rtI)[0] == '0') {
            y = charArrToInt(useThisReg(rtI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rtI));
            y = (-1) * stringToInt(temp1) - 1;
        }

        if(x == y){
            Immediate = Immediate + '0' + '0';
            char arrOffSet[] = Immediate.toCharArray();
            if(arrOffSet[0] == '0'){
                Immediate = extendTo32Bit(Immediate);
                offSet = stringToInt(Immediate);
                String temp = charArrToString($pc);
                long pcCount = Long.parseLong(temp, 2);
                pcCount = pcCount + offSet;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
            else{
                String pcOffSet = undoTwosComp(arrOffSet);
                int pcOS = stringToInt(pcOffSet) + 1;
                long pcCount = Long.parseLong(charArrToString($pc), 2);
                pcCount = pcCount - pcOS;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
        }
        else{
            incPcFour();
        }

    }

    public static void mipsBne(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }
        int x = 0;
        int y = 0;
        int offSet = 0;

        if (useThisReg(rsI)[0] == '0') {
            x = charArrToInt(useThisReg(rsI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rsI));
            x = (-1) * stringToInt(temp1) - 1;
        }

        if (useThisReg(rtI)[0] == '0') {
            y = charArrToInt(useThisReg(rtI));
        } else {
            String temp1 = undoTwosComp(useThisReg(rtI));
            y = (-1) * stringToInt(temp1) - 1;
        }

        if(x != y){
            Immediate = Immediate + '0' + '0';
            char arrOffSet[] = Immediate.toCharArray();
            if(arrOffSet[0] == '0'){
                Immediate = extendTo32Bit(Immediate);
                offSet = stringToInt(Immediate);
                String temp = charArrToString($pc);
                long pcCount = Long.parseLong(temp, 2);
                pcCount = pcCount + offSet;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
            else{
                String pcOffSet = undoTwosComp(arrOffSet);
                int pcOS = stringToInt(pcOffSet) + 1;
                long pcCount = Long.parseLong(charArrToString($pc), 2);
                pcCount = pcCount - pcOS;
                String pcCounter = intToBinString((int)pcCount);
                pcCounter = extendTo32Bit(pcCounter);
                char pc[] = pcCounter.toCharArray();
                for(int i = 0; i < $pc.length; i++){
                    $pc[i] = pc[i];
                }
            }
        }
        else{
            incPcFour();
        }

    }

    public static void mipsSh(char arrBit[]){
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];;
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for(int i = 16; i < arrBit.length; i++){
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        String strToStore = "";
        for(int i = 16; i < useThisReg(rtI).length; i++){
            strToStore = strToStore + useThisReg(rtI)[i];
        }

        strToStore = extendTo32Bit(strToStore);
        int offSet = x + y;

        String tempOffSet = Integer.toString(offSet);
        memStorage.put(tempOffSet, strToStore);
        incPcFour();
    }

    public static void mipsSb(char arrBit[]){
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];;
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for(int i = 16; i < arrBit.length; i++){
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        String strToStore = "" +  useThisReg(rtI)[24] + useThisReg(rtI)[25] + useThisReg(rtI)[26] + useThisReg(rtI)[27] + useThisReg(rtI)[28] +
                useThisReg(rtI)[29] + useThisReg(rtI)[30] + useThisReg(rtI)[31];
        strToStore = extendTo32Bit(strToStore);
        int offSet = x + y;

        String tempOffSet = Integer.toString(offSet);
        memStorage.put(tempOffSet, strToStore);
        incPcFour();
    }


    public static void mipsSw(char arrBit[]){
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];;
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for(int i = 16; i < arrBit.length; i++){
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        String strToStore = charArrToString(useThisReg(rtI));
        int offSet = x + y;

        String tempOffSet = Integer.toString(offSet);
        memStorage.put(tempOffSet, strToStore);
        incPcFour();
    }

    public static void mipsLbu(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        int offSet = x + y;
        String tempOffSet = Integer.toString(offSet);

        String tempGetVal = (String) memStorage.get(tempOffSet);
        if (tempGetVal == null) {
            tempGetVal = (String) globalStorage.get(tempOffSet);
        }

        char arr[] = tempGetVal.toCharArray();
        String byteToStore = "";

        for(int i = 31; i > 23; i--){
            byteToStore = arr[i] + byteToStore;
        }

        byteToStore = extendTo32Bit(byteToStore);
        char arrByteToStore[] = byteToStore.toCharArray();

        for(int i = 0; i < useThisReg(rtI).length; i++){
            useThisReg(rtI)[i] = arrByteToStore[i];
        }
        incPcFour();
    }

    public static void mipsLb(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        int offSet = x + y;
        String tempOffSet = Integer.toString(offSet);

        String tempGetVal = (String) memStorage.get(tempOffSet);
        if(tempGetVal == null){
            tempGetVal = (String)globalStorage.get(tempOffSet);
        }

        char arr[] = tempGetVal.toCharArray();
        String byteToStore = "";

        for(int i = 31; i > 23; i--){
            byteToStore = arr[i] + byteToStore;
        }

        char arrByte[] = byteToStore.toCharArray();
        if(arrByte[0] == '0'){
            byteToStore = extendTo32Bit(byteToStore);
        }
        else{
            byteToStore = extendTo32BitNeg(byteToStore);
        }

        char arrByteToStore[] = byteToStore.toCharArray();

        for(int i = 0; i < useThisReg(rtI).length; i++){
            useThisReg(rtI)[i] = arrByteToStore[i];
        }
        incPcFour();
    }

    public static void mipsLh(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        int offSet = x + y;
        String tempOffSet = Integer.toString(offSet);

        String tempGetVal = (String) memStorage.get(tempOffSet);
        if(tempGetVal == null){
            tempGetVal = (String)globalStorage.get(tempOffSet);
        }

        char arr[] = tempGetVal.toCharArray();
        String byteToStore = "";

        for(int i = 31; i > 15; i--){
            byteToStore = arr[i] + byteToStore;
        }

        char arrByte[] = byteToStore.toCharArray();
        if(arrByte[0] == '0'){
            byteToStore = extendTo32Bit(byteToStore);
        }
        else{
            byteToStore = extendTo32BitNeg(byteToStore);
        }

        char arrByteToStore[] = byteToStore.toCharArray();

        for(int i = 0; i < useThisReg(rtI).length; i++){
            useThisReg(rtI)[i] = arrByteToStore[i];
        }
        incPcFour();
    }
    public static void mipsLhu(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        int offSet = x + y;
        String tempOffSet = Integer.toString(offSet);

        String tempGetVal = (String) memStorage.get(tempOffSet);
        if (tempGetVal == null) {
            tempGetVal = (String) globalStorage.get(tempOffSet);
        }

        char arr[] = tempGetVal.toCharArray();
        String byteToStore = "";

        for(int i = 31; i > 15; i--){
            byteToStore = arr[i] + byteToStore;
        }

        byteToStore = extendTo32Bit(byteToStore);
        char arrByteToStore[] = byteToStore.toCharArray();

        for(int i = 0; i < useThisReg(rtI).length; i++){
            useThisReg(rtI)[i] = arrByteToStore[i];
        }
        incPcFour();
    }

    public static void mipsLui(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        Immediate = Immediate + "0000000000000000";
        char arrImm[] = Immediate.toCharArray();

        for(int i = 0; i < useThisReg(rtI).length; i++){
            useThisReg(rtI)[i] = arrImm[i];
        }

        //System.out.print("\nlui " + findReg(rsI) + " " + findReg(rtI) + " " + Immediate + "\n");

        incPcFour();
    }


    public static void mipsLw(char arrBit[]) {
        String rsI = "" + arrBit[6] + arrBit[7] + arrBit[8] + arrBit[9] + arrBit[10];
        String rtI = "" + arrBit[11] + arrBit[12] + arrBit[13] + arrBit[14] + arrBit[15];
        String Immediate = "";
        for (int i = 16; i < arrBit.length; i++) {
            Immediate = Immediate + arrBit[i];
        }

        int x = stringToInt(Immediate);
        int y = charArrToInt(useThisReg(rsI));

        int offSet = x + y;
        String tempOffSet = Integer.toString(offSet);

        String tempGetVal = (String) memStorage.get(tempOffSet);
        if(tempGetVal == null){
            tempGetVal = (String)globalStorage.get(tempOffSet);
        }
        char arr[] = tempGetVal.toCharArray();
        for(int i = 0; i < arr.length; i++){
            useThisReg(rtI)[i] = arr[i];
        }
        incPcFour();
    }

    public static String getJ(char arr[]) {
        String temp = "";
        if(arr[0] == '0' && arr[1] == '0' && arr[2] == '0' && arr[3] == '0' && arr[4] == '1' && arr[5] == '0'){
            temp = "j";
        }

        if(arr[0] == '0' && arr[1] == '0' && arr[2] == '0' && arr[3] == '0' && arr[4] == '1' && arr[5] == '1'){
            temp = "jal";
        }

        if(temp.equals("j")){
            mipsJ(arr);
        }

        if(temp.equals("jal")){
            mipsJal(arr);
        }

        return temp;
    }

    public static void mipsJal(char arr[]) {
        String offSet = "";
        for (int i = 6; i < arr.length; i++) {
            offSet = offSet + arr[i];
        }

        int x = stringToInt(offSet);
        offSet = intToBinString(x);


        int pcPlus8 = charArrToInt($pc);
        pcPlus8 = pcPlus8 + 4;
        String newPc = intToBinString(pcPlus8);
        newPc = extendTo32Bit(newPc);
        char nextPc[] = newPc.toCharArray();

        for(int i = 0; i < $pc.length; i++){
            $ra[i] = nextPc[i];
        }

        int l = offSet.length();
        for (int i = 0; i < 26 - l; i++) {
            offSet = "0" + offSet;
        }

        String top4pc = "";
        for(int i = 0; i < 4; i++){
            top4pc = top4pc + $pc[i];
        }

        offSet = top4pc + offSet + "00";

        char pcJump[] = offSet.toCharArray();
        for(int i = 0; i < pcJump.length; i++){
            $pc[i] = pcJump[i];
        }
    }

    public static void mipsJ(char arr[]){
        String offSet = "";
        for(int i = 6; i < arr.length; i++){
            offSet = offSet + arr[i];
        }

        int x = stringToInt(offSet);
        offSet = intToBinString(x);

        int l = offSet.length();
        for(int i = 0; i < 26 - l; i++){
                offSet = "0" + offSet;
        }

        String top4pc = "";
        for(int i = 0; i < 4; i++){
            top4pc = top4pc + $pc[i];
        }

        offSet = top4pc + offSet + "00";

        char pcJump[] = offSet.toCharArray();
        for(int i = 0; i < pcJump.length; i++){
            $pc[i] = pcJump[i];
        }
    }

    public static String reverseString(String a){
        char arr[] = a.toCharArray();
        String temp = "";

        temp = "" + arr[6] + arr[7] +arr[4] + arr[5] + arr[2] + arr[3] + arr[0] + arr[1];

        return temp;
    }

    public String hexStringToBitArray(String str){
        char[] chars = str.toCharArray();
        String temp = "";
        for(int i = 2; i < chars.length; i++){
            if(chars[i] == '0'){temp = temp + "0000";}
            else if(chars[i] == '1'){temp = temp + "0001";}
            else if(chars[i] == '2'){temp = temp + "0010";}
            else if(chars[i] == '3'){temp = temp + "0011";}
            else if(chars[i] == '4'){temp = temp + "0100";}
            else if(chars[i] == '5'){temp = temp + "0101";}
            else if(chars[i] == '6'){temp = temp + "0110";}
            else if(chars[i] == '7'){temp = temp + "0111";}
            else if(chars[i] == '8'){temp = temp + "1000";}
            else if(chars[i] == '9'){temp = temp + "1001";}
            else if(chars[i] == 'a'){temp = temp + "1010";}
            else if(chars[i] == 'b'){temp = temp + "1011";}
            else if(chars[i] == 'c'){temp = temp + "1100";}
            else if(chars[i] == 'd'){temp = temp + "1101";}
            else if(chars[i] == 'e'){temp = temp + "1110";}
            else if(chars[i] == 'f'){temp = temp + "1111";}
            else if(chars[i] == 'A'){temp = temp + "1010";}
            else if(chars[i] == 'B'){temp = temp + "1011";}
            else if(chars[i] == 'C'){temp = temp + "1100";}
            else if(chars[i] == 'D'){temp = temp + "1101";}
            else if(chars[i] == 'E'){temp = temp + "1110";}
            else if(chars[i] == 'F'){temp = temp + "1111";}
            else if(chars[i] == ' '){temp = temp + "";}
            else {
                System.out.print("not a valid char");
                System.exit(0);
            }
        }
        //System.out.print(temp);
        return temp;
    }

    public static void readToGlobalStorage(String s){
       if(!(s.equals("DATA SEGMENT"))){
           char arr[] = s.toCharArray();
           String address = "" + arr[2] + arr[3] + arr[4] + arr[5] + arr[6] + arr[7] + arr[8] + arr[9];
           String value = "" + arr[13] + arr[14] + arr[15] + arr[16] + arr[17] + arr[18] + arr[19] + arr[20];

           int x = Integer.parseInt(address, 16);
           int y = Integer.parseInt(value, 16);

           address = Integer.toString(x);
           value = intToBinString(y);
           value = extendTo32Bit(value);

           System.out.print("\naddress: " + address + " value: " + value);

           globalStorage.put(address, value);
       }
    }

    static char $r0[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $at[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $v0[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $v1[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $a0[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $a1[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $a2[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $a3[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t0[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t1[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t2[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t3[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t4[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t5[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t6[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t7[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s0[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s1[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s2[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s3[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s4[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s5[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s6[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $s7[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t8[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $t9[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $k0[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $k1[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $gp[] = {'0','0','0','1','0','0','0','0','0','0','0','0','0','0','0','1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $sp[] = {'0','1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $fp[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $ra[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $lo[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $hi[] = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    static char $pc[] = {'0','0','0','0','0','0','0','0','0','1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};

    public static String findReg(String a){
        String temp = "";
        if(a.equals("00000")){temp = temp + "$r0";}
        if(a.equals("00001")){temp = temp + "$at";}
        if(a.equals("00010")){temp = temp + "$v0";}
        if(a.equals("00011")){temp = temp + "$v1";}
        if(a.equals("00100")){temp = temp + "$a0";}
        if(a.equals("00101")){temp = temp + "$a1";}
        if(a.equals("00110")){temp = temp +"$a2";}
        if(a.equals("00111")){temp = temp +"$a3";}
        if(a.equals("01000")){temp = temp +"$t0";}
        if(a.equals("01001")){temp = temp +"$t1";}
        if(a.equals("01010")){temp = temp +"$t2";}
        if(a.equals("01011")){temp = temp +"$t3";}
        if(a.equals("01100")){temp = temp +"$t4";}
        if(a.equals("01101")){temp = temp +"$t5";}
        if(a.equals("01110")){temp = temp +"$t6";}
        if(a.equals("01111")){temp = temp +"$t7";}
        if(a.equals("10000")){temp = temp +"$s0";}
        if(a.equals("10001")){temp = temp +"$s1";}
        if(a.equals("10010")){temp = temp +"$s2";}
        if(a.equals("10011")){temp = temp +"$s3";}
        if(a.equals("10100")){temp = temp +"$s4";}
        if(a.equals("10101")){temp = temp +"$s5";}
        if(a.equals("10110")){temp = temp +"$s6";}
        if(a.equals("10111")){temp = temp +"$s7";}
        if(a.equals("11000")){temp = temp +"$t8";}
        if(a.equals("11001")){temp = temp +"$t9";}
        if(a.equals("11010")){temp = temp +"$k0";}
        if(a.equals("11011")){temp = temp +"$k1";}
        if(a.equals("11100")){temp = temp +"$gp";}
        if(a.equals("11101")){temp = temp +"$sp";}
        if(a.equals("11110")){temp = temp +"$fp";}
        if(a.equals("11111")){temp = temp +"$ra";}

        return temp;
    }

    public static char[] useThisReg(String a){

        if(a.equals("00000")){return $r0;}
        if(a.equals("00001")){return $at;}
        if(a.equals("00010")){return $v0;}
        if(a.equals("00011")){return $v1;}
        if(a.equals("00100")){return $a0;}
        if(a.equals("00101")){return $a1;}
        if(a.equals("00110")){return $a2;}
        if(a.equals("00111")){return $a3;}
        if(a.equals("01000")){return $t0;}
        if(a.equals("01001")){return $t1;}
        if(a.equals("01010")){return $t2;}
        if(a.equals("01011")){return $t3;}
        if(a.equals("01100")){return $t4;}
        if(a.equals("01101")){return $t5;}
        if(a.equals("01110")){return $t6;}
        if(a.equals("01111")){return $t7;}
        if(a.equals("10000")){return $s0;}
        if(a.equals("10001")){return $s1;}
        if(a.equals("10010")){return $s2;}
        if(a.equals("10011")){return $s3;}
        if(a.equals("10100")){return $s4;}
        if(a.equals("10101")){return $s5;}
        if(a.equals("10110")){return $s6;}
        if(a.equals("10111")){return $s7;}
        if(a.equals("11000")){return $t8;}
        if(a.equals("11001")){return $t9;}
        if(a.equals("11010")){return $k0;}
        if(a.equals("11011")){return $k1;}
        if(a.equals("11100")){return $gp;}
        if(a.equals("11101")){return $sp;}
        if(a.equals("11110")){return $fp;}
        if(a.equals("11111")){return $ra;}

        return $r0;
    }

    public static void incPcFour(){
        String temp = charArrToString($pc);
        long pcCount = Long.parseLong(temp, 2);
        pcCount = pcCount + 4;
        String pcReg = intToBinString((int)pcCount);
        pcReg = extendTo32Bit(pcReg);
        char arrPc[] = pcReg.toCharArray();
        for(int i = 0; i < arrPc.length; i++){
            $pc[i] = arrPc[i];
        }
    }

    public static String pcToString(){
        String temp = charArrToString($pc);
        return temp;
    }

    public static int charArrToInt(char arr[]){
       String a = "";
       for(int i = 0; i < arr.length; i++){
           a = a + arr[i];
        }
        int x = Integer.parseInt(a,2);
        if(arr[0] == '1'){ x = x*(-1); }
        return x;
    }

    public static int stringToInt(String a){
        char arr[] = a.toCharArray();
        return charArrToInt(arr);
    }

    public static String extendTo32Bit(String a){
        int x = a.length();
        for(int i = 0; i < 32-x; i++){
            a = "0" + a;
        }
        return a;
    }

    public static String extendTo32BitNeg(String a){
        int x = a.length();
        for(int i = 0; i < 32-x; i++){
            a = "1" + a;
        }
        return a;
    }

    public static String intToBinString(int x){
        return java.lang.Integer.toString(x, 2);
    }

    public static String arrToTwosComp(char arr[]){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == '0') {arr[i] = '1';}
            else{arr[i] = '0';}
        }

        char arr2[] = arr.clone();
        for (int i = arr.length - 1; i >= 0; i--)
        {
            if (arr[i] == '1')
                arr2[i] = '0';
            else
            {
                arr2[i] = '1';
                break;
            }
        }

        String temp = charArrToString(arr2);
        return temp;
    }

    public static String undoTwosComp(char arr[]) {
        char arr2[] = arr.clone();
        for (int i = arr.length - 1; i >= 0; i--)
        {
            if (arr[i] == '0')
                arr2[i] = '1';
            else
            {
                arr2[i] = '0';
                break;
            }
        }

        for(int i = 0; i < arr.length; i++){
            if(arr[i] == '0') {arr2[i] = '1';}
            else{arr2[i] = '0';}
        }
        String temp1 = charArrToString(arr2);
        return temp1;
    }

    public static String charArrToString(char arr[]){
        String temp = "";
        for(int i = 0; i < arr.length; i++){
            temp = temp + arr[i];
        }
        return temp;
    }

    public static void printArr(char arr[]){
        System.out.print(arr);
        System.out.print("\n");
    }

    public static void printAllReg(){
        System.out.print("$r0 = "); printArr($r0);
        System.out.print("$at = "); printArr($at);
        System.out.print("$v0 = "); printArr($v0);
        System.out.print("$v1 = "); printArr($v1);
        System.out.print("$a0 = "); printArr($a0);
        System.out.print("$a1 = "); printArr($a1);
        System.out.print("$a2 = "); printArr($a2);
        System.out.print("$a3 = "); printArr($a3);
        System.out.print("$t0 = "); printArr($t0);
        System.out.print("$t1 = "); printArr($t1);
        System.out.print("$t2 = "); printArr($t2);
        System.out.print("$t3 = "); printArr($t3);
        System.out.print("$t4 = "); printArr($t4);
        System.out.print("$t5 = "); printArr($t5);
        System.out.print("$t6 = "); printArr($t6);
        System.out.print("$t7 = "); printArr($t7);
        System.out.print("$s0 = "); printArr($s0);
        System.out.print("$s1 = "); printArr($s1);
        System.out.print("$s2 = "); printArr($s2);
        System.out.print("$s3 = "); printArr($s3);
        System.out.print("$s4 = "); printArr($s4);
        System.out.print("$s5 = "); printArr($s5);
        System.out.print("$s6 = "); printArr($s6);
        System.out.print("$s7 = "); printArr($s7);
        System.out.print("$t8 = "); printArr($t8);
        System.out.print("$t9 = "); printArr($t9);
        System.out.print("$k0 = "); printArr($k0);
        System.out.print("$k1 = "); printArr($k1);
        System.out.print("$gp = "); printArr($gp);
        System.out.print("$sp = "); printArr($sp);
        System.out.print("$fp = "); printArr($fp);
        System.out.print("$ra = "); printArr($ra);
        System.out.print("$hi = "); printArr($hi);
        System.out.print("$lo = "); printArr($lo);
        System.out.print("$pc = "); printArr($pc);
    }

    static Dictionary memStorage = new Hashtable();
    static Dictionary commands = new Hashtable();
    static Dictionary globalStorage = new Hashtable();

    public static void main(String[] args) {
        try
        {
            mipsSimulator obj = new mipsSimulator ();
            obj.run (args);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }

    public void run(String[] args) throws Exception {
        Jinstructions jIns[] = new Jinstructions[2];
        for (int i = 0; i < 2; i++) {
            jIns[i] = new Jinstructions();
        }
        jIns[0].insJ = "j";     jIns[0].opCode = "000010";      //done
        jIns[1].insJ = "jal";   jIns[1].opCode = "000011";      //done
        Iinstructions iIns[] = new Iinstructions[20];
        for (int i = 0; i < 20; i++) {
            iIns[i] = new Iinstructions();
        }
        iIns[0].insI = "beq";       iIns[0].opCode = "000100";      //done
        iIns[1].insI = "bne";       iIns[1].opCode = "000101";      //done
        iIns[2].insI = "blez";      iIns[2].opCode = "000110";      //done
        iIns[3].insI = "bgtz";      iIns[3].opCode = "000111";      //done
        iIns[4].insI = "addi";      iIns[4].opCode = "001000";      //done
        iIns[5].insI = "addiu";     iIns[5].opCode = "001001";      //done
        iIns[6].insI = "slti";      iIns[6].opCode = "001010";      //done
        iIns[7].insI = "sltiu";     iIns[7].opCode = "001011";      //done
        iIns[8].insI = "andi";      iIns[8].opCode = "001100";      //done
        iIns[9].insI = "ori";       iIns[9].opCode = "001101";      //done
        iIns[10].insI = "xori";     iIns[10].opCode = "001110";     //done
        iIns[11].insI = "lui";      iIns[11].opCode = "001111";     //done
        iIns[12].insI = "lb";       iIns[12].opCode = "100000";     //done
        iIns[13].insI = "lh";       iIns[13].opCode = "100001";     //done
        iIns[14].insI = "lw";       iIns[14].opCode = "100011";     //done
        iIns[15].insI = "lbu";      iIns[15].opCode = "100100";     //done
        iIns[16].insI = "lhu";      iIns[16].opCode = "100101";     //done
        iIns[17].insI = "sb";       iIns[17].opCode = "101000";     //done
        iIns[18].insI = "sh";       iIns[18].opCode = "101001";     //done
        iIns[19].insI = "sw";       iIns[19].opCode = "101011";     //done

        Rinstructions rIns[] = new Rinstructions[29];
        for (int i = 0; i < 29; i++) {
            rIns[i] = new Rinstructions();
        }
        rIns[0].insR = "sll";       rIns[0].opCode = "000000";      rIns[0].funct = "000000";   //done
        rIns[1].insR = "srl";       rIns[1].opCode = "000000";      rIns[1].funct = "000010";   //done
        rIns[2].insR = "sra";       rIns[2].opCode = "000000";      rIns[2].funct = "000011";   //done
        rIns[3].insR = "sllv";      rIns[3].opCode = "000000";      rIns[3].funct = "000100";   //done
        rIns[4].insR = "srlv";      rIns[4].opCode = "000000";      rIns[4].funct = "000110";   //done
        rIns[5].insR = "srav";      rIns[5].opCode = "000000";      rIns[5].funct = "000111";   //done
        rIns[6].insR = "jr";        rIns[6].opCode = "000000";      rIns[6].funct = "001000";   //done
        rIns[7].insR = "jalr";      rIns[7].opCode = "000000";      rIns[7].funct = "001001";   //done
        rIns[8].insR = "syscall";   rIns[8].opCode = "000000";      rIns[8].funct = "001100";   //done
        rIns[9].insR = "mfhi";      rIns[9].opCode = "000000";      rIns[9].funct = "010000";   //done
        rIns[10].insR = "mthi";     rIns[10].opCode = "000000";     rIns[10].funct = "010001";  //done
        rIns[11].insR = "mflo";     rIns[11].opCode = "000000";     rIns[11].funct = "010010";  //done
        rIns[12].insR = "mtlo";     rIns[12].opCode = "000000";     rIns[12].funct = "010011";  //done
        rIns[13].insR = "mult";     rIns[13].opCode = "000000";     rIns[13].funct = "011000";  //done
        rIns[14].insR = "multu";    rIns[14].opCode = "000000";     rIns[14].funct = "011001";  //done
        rIns[15].insR = "div";      rIns[15].opCode = "000000";     rIns[15].funct = "011010";  //done
        rIns[16].insR = "divu";     rIns[16].opCode = "000000";     rIns[16].funct = "011011";  //done
        rIns[17].insR = "add";      rIns[17].opCode = "000000";     rIns[17].funct = "100000";  //done
        rIns[18].insR = "addu";     rIns[18].opCode = "000000";     rIns[18].funct = "100001";  //done
        rIns[19].insR = "sub";      rIns[19].opCode = "000000";     rIns[19].funct = "100010";  //done
        rIns[20].insR = "subu";     rIns[20].opCode = "000000";     rIns[20].funct = "100011";  //done
        rIns[21].insR = "and";      rIns[21].opCode = "000000";     rIns[21].funct = "100100";  //done
        rIns[22].insR = "or";       rIns[22].opCode = "000000";     rIns[22].funct = "100101";  //done
        rIns[23].insR = "xor";      rIns[23].opCode = "000000";     rIns[23].funct = "100110";  //done
        rIns[24].insR = "nor";      rIns[24].opCode = "000000";     rIns[24].funct = "100111";  //done
        rIns[25].insR = "slt";      rIns[25].opCode = "000000";     rIns[25].funct = "101010";  //done
        rIns[26].insR = "sltu";     rIns[26].opCode = "000000";     rIns[26].funct = "101011";  //done
        rIns[27].insR = "movn";     rIns[27].opCode = "000000";     rIns[27].funct = "001011";  //done
        rIns[28].insR = "movz";     rIns[28].opCode = "000000";     rIns[28].funct = "001010";  //done


        File file = new File("C:\\Users\\ftrue\\Desktop\\school work\\testsys.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        boolean dataSeg = false;
        String st = br.readLine();

        while (st != null){
            if(st.equals("DATA SEGMENT")){
                dataSeg = true;
                System.out.print("\ndata segment");
            }

            if(dataSeg == true) {
                readToGlobalStorage(st);
            }
            else{
                commands.put(pcToString(), hexStringToBitArray(st));
                System.out.print("\npc = " + pcToString() + " " + hexStringToBitArray(st));
                incPcFour();
            }
            st = br.readLine();
        }

        br.close();
        System.out.print("\n");
        String resetPc = "00000000010000000000000000000000";
        char arrPc[] = resetPc.toCharArray();
        for(int k = 0; k < arrPc.length; k++){
            $pc[k] = arrPc[k];
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("\npress 1 for continuous run through. press 2 for step by step run through. ");
        int i = sc.nextInt();

        if(i == 1) {
            while (true) {
                String temp = (String) commands.get(pcToString());

                char arrTemp[] = temp.toCharArray();

                if (isJ(arrTemp)) {
                    System.out.print(getJ(arrTemp));
                }
                if (isI(arrTemp, iIns)) {
                    System.out.print(getI(arrTemp, iIns));
                }
                if (isR(arrTemp)) {
                    System.out.print(getR(arrTemp, rIns));
                }
                System.out.print("\n");
                //printAllReg();

                if (temp.equals("00000000000000000000001010001100")) {
                    break;
                }
            }
        }
        else{
            while(true){
                System.out.print("\ntype n for next instruction.");
                System.out.print("\ntype P all to print all registers.");
                System.out.print("\ntype P to print a specific register. ");
                System.out.print("\ntype P addr(hex address) to to print the contents of an address in memory.");
                System.out.print("\ntype finish to run to completion. ");
                System.out.print("\n");

                String userInput = sc.nextLine();

                if(userInput.equals("P all")){
                    System.out.print("\n");
                    printAllReg();
                }
                if(userInput.equals("n")){
                    System.out.print("\nHow many instructions would you like to run?");
                    int numToRun = sc.nextInt();
                    int count = 0;
                    while(count < numToRun) {
                        String temp = (String) commands.get(pcToString());

                        char arrTemp[] = temp.toCharArray();

                        if (isJ(arrTemp)) {
                            System.out.print(getJ(arrTemp));
                        }
                        if (isI(arrTemp, iIns)) {
                            System.out.print(getI(arrTemp, iIns));
                        }
                        if (isR(arrTemp)) {
                            System.out.print(getR(arrTemp, rIns));
                        }
                        System.out.print("\n");
                        count++;
                    }
                }
                if(userInput.equals("P")){
                    System.out.print("\nwhich register would you like to print?");
                    int reg = sc.nextInt();
                    String register = intToBinString(reg);
                    int len = register.length();
                    for(int j = 0; j < 5-len; j++){
                        register = "0" + register;
                    }
                    printArr(useThisReg(register));
                }
                if(userInput.equals("P addr")){
                    System.out.print("\nWhat is the address?");
                    String addr = sc.nextLine();
                    long x = Long.parseLong(addr, 16);
                    String y = Long.toString(x);
                    String getMemLoc = (String)globalStorage.get(y);
                    System.out.print("\n" + getMemLoc);
                }
                if (userInput.equals("finish")) {
                    while (true) {
                        String temp = (String) commands.get(pcToString());

                        char arrTemp[] = temp.toCharArray();

                        if (isJ(arrTemp)) {
                            System.out.print(getJ(arrTemp));
                        }
                        if (isI(arrTemp, iIns)) {
                            System.out.print(getI(arrTemp, iIns));
                        }
                        if (isR(arrTemp)) {
                            System.out.print(getR(arrTemp, rIns));
                        }
                        System.out.print("\n");

                        //printAllReg();

                        if (temp.equals("00000000000000000000001010001100")) {
                            printAllReg();
                            break;
                        }
                    }
                }
            }
        }
    }
}
