package com.pos.mahmoud.pos.Helpers;

import android.util.Log;

public class PINCALC {


    public PINCALC() {
    }
    private static final String PIN_PAD = "FFFFFFFFFFFFFF";

    private  String padPin(String pin)  {
        String pinBlockString = "0" + pin.length() + pin + PIN_PAD;
        pinBlockString = pinBlockString.substring(0, 16);
        return pinBlockString;
    }

    private static final String ZERO_PAD = "0000000000000000";


    private String padCard(String cardNumber){
        cardNumber = ZERO_PAD + cardNumber;
        int cardNumberLength = cardNumber.length();
        int beginIndex = cardNumberLength - 13;
        String acctNumber = "0000"
                + cardNumber.substring(beginIndex, cardNumberLength - 1);
        return acctNumber;
    }
    /**
     * getPinBlock
     * 标准ANSI X9.8 Format（带主帐号信息）的PIN BLOCK计算
     * PIN BLOCK 格式等于 PIN  按位异或  主帐号;
     * @param pin String
     * @param accno String
     * @return byte[]
     */
    public String process(String pin, String accno) {
        pin=padPin(pin);
        accno=padCard(accno);
        byte arrAccno[] = getHAccno(accno);
        byte arrPin[] = getHPin(pin);
        byte arrRet[] = new byte[8];
        //PIN BLOCK 格式等于 PIN  按位异或  主帐号;
        for (int i = 0; i < 8; i++) {
            arrRet[i] = (byte) (arrPin[i] ^ arrAccno[i]);
        }
        //Util.printHexString("PinBlock：", arrRet);
        return getHexString(arrRet);
    }
    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }
    public static byte[] hex2bin(String hex) throws NumberFormatException {
        if (hex.length() % 2 > 0) {
            throw new NumberFormatException("Hexadecimal input string must have an even length.");
        }
        byte[] r = new byte[hex.length() / 2];
        for (int i = hex.length(); i > 0;) {
            r[i / 2 - 1] = (byte) (digit(hex.charAt(--i)) | (digit(hex.charAt(--i)) << 4));
        }
        return r;
    }

    private static int digit(char ch) {
        int r = Character.digit(ch, 16);
        if (r < 0) {
            throw new NumberFormatException("Invalid hexadecimal string: " + ch);
        }
        return r;
    }
    /**
     * getHPin
     * 对密码进行转换
     * PIN格式
     * BYTE 1 PIN的长度
     * BYTE 2 – BYTE 3/4/5/6/7   4--12个PIN(每个PIN占4个BIT)
     * BYTE 4/5/6/7/8 – BYTE 8   FILLER “F” (每个“F“占4个BIT)
     * @param pin String
     * @return byte[]
     */
    public byte[] getHPin(String pin) {
        byte arrAccno[] = pin.getBytes();
        byte encode[] = new byte[8];
        encode[0] = (byte) uniteBytes(arrAccno[0], arrAccno[1]);
        encode[1] = (byte) uniteBytes(arrAccno[2], arrAccno[3]);
        encode[2] = (byte) uniteBytes(arrAccno[4], arrAccno[5]);
        encode[3] = (byte) uniteBytes(arrAccno[6], arrAccno[7]);
        encode[4] = (byte) uniteBytes(arrAccno[8], arrAccno[9]);
        encode[5] = (byte) uniteBytes(arrAccno[10], arrAccno[11]);
        encode[6] = (byte) uniteBytes(arrAccno[12], arrAccno[13]);
        encode[7] = (byte) uniteBytes(arrAccno[14], arrAccno[15]);
        //getHexString( encode);
        return encode;
    }

    /**
     * getHAccno
     * 对帐号进行转换
     * BYTE 1 — BYTE 2  0X0000
     * BYTE 3 — BYTE 8 12个主帐号
     * 取主帐号的右12位（不包括最右边的校验位），不足12位左补“0X00”。
     * @param accno String
     * @return byte[]
     */
    private byte[] getHAccno(String accno) {
        //取出主帐号；
        int len = accno.length();
        //byte arrTemp[] = accno.substring(len < 13 ? 0 : len - 13, len - 1).getBytes();
        byte arrAccno[] = accno.getBytes();

        byte encode[] = new byte[8];
        encode[0] = (byte) uniteBytes(arrAccno[0], arrAccno[1]);
        encode[1] = (byte) uniteBytes(arrAccno[2], arrAccno[3]);
        encode[2] = (byte) uniteBytes(arrAccno[4], arrAccno[5]);
        encode[3] = (byte) uniteBytes(arrAccno[6], arrAccno[7]);
        encode[4] = (byte) uniteBytes(arrAccno[8], arrAccno[9]);
        encode[5] = (byte) uniteBytes(arrAccno[10], arrAccno[11]);
        encode[6] = (byte) uniteBytes(arrAccno[12], arrAccno[13]);
        encode[7] = (byte) uniteBytes(arrAccno[14], arrAccno[15]);
        //getHexString(encode);
        return encode;
    }
     public String getHexString( byte[] b) {
         String out="";
         for (int i = 0; i < b.length; i++) {
           String hex = Integer.toHexString(b[i] & 0xFF);
           if (hex.length() == 1) {
             hex = '0' + hex;
           }
         out+=hex;
         }
         Log.d("HEXOUT",out);
         return out;
       }

}
