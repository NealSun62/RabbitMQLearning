package com.sun.overweight.common.utils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;

public class ByteUtil
{
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final char[] INT_TO_CHAR_TABLE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String byteArrayToHexString(byte[] byteArray)
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++)
        {
            byte b = byteArray[i];
            buf.append(toHexChar(b >> 4 & 0xF));
            buf.append(toHexChar(b & 0xF));
            buf.append(" ");
        }
        return buf.toString();
    }

    public static char toHexChar(int i)
    {
        if ((i < 0) || (i > 15)) {
            throw new IllegalArgumentException("toHex parameter should be 0-15");
        }
        return INT_TO_CHAR_TABLE[i];
    }

    public static int byte2Int(byte b)
    {
        return b & 0xFF;
    }

    public static byte[] int2ByteArray(int intValue)
    {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = ((byte)(intValue >> 8 * (3 - i) & 0xFF));
        }
        return b;
    }

    public static byte[] int2ByteArray(int intValue, int arrayLength)
    {
        byte[] b = new byte[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            b[i] = ((byte)(intValue >> 8 * (arrayLength - 1 - i) & 0xFF));
        }
        return b;
    }

    public static int byteArray2Int(byte[] b)
    {
        int intValue = 0;
        int length = b.length - 1;
        for (int i = 0; i < b.length; i++) {
            intValue += ((b[i] & 0xFF) << 8 * (length - i));
        }
        return intValue;
    }

    public static long byteArray2Long(byte[] b)
    {
        long longValue = 0L;
        int length = b.length - 1;
        for (int i = 0; i < b.length; i++) {
            longValue += ((b[i] & 0xFF) << 8 * (length - i));
        }
        return longValue;
    }

    public static byte[] long2ByteArray(long longValue)
    {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = ((byte)(int)(longValue >> 8 * (7 - i) & 0xFF));
        }
        return b;
    }

    public static byte[] float2ByteArray(float f)
    {
        int fInt = Float.floatToIntBits(f);
        return int2ByteArray(fInt);
    }

    public static float byteArray2Float(byte[] bytes)
    {
        int intf = byteArray2Int(bytes);
        return Float.intBitsToFloat(intf);
    }

    public static byte[] date2Byte(Date date)
    {
        byte[] byteArray = new byte[0];
        try
        {
            byteArray = df.format(date).getBytes();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static Timestamp byte2Date(byte[] bytes)
    {
        Date date = null;
        try
        {
            date = df.parse(new String(bytes));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Timestamp(date.getTime());
    }

    public static int indexOf(byte[] array, int value)
    {
        if (array != null) {
            for (int i = 0; i < array.length; i++)
            {
                int temp = array[i];
                if (temp == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean byteArrayEqual(byte[] firstArray, byte[] secondArray)
    {
        if (firstArray.length != secondArray.length) {
            return false;
        }
        for (int i = 0; i < firstArray.length; i++)
        {
            byte firstByte = firstArray[i];
            byte secondByte = secondArray[i];
            if (firstByte != secondByte) {
                return false;
            }
        }
        return true;
    }

    public static long CRC32(byte[] byteArray)
    {
        CRC32 crc32 = new CRC32();
        crc32.update(byteArray, 0, byteArray.length);
        long crcValue = crc32.getValue();
        return crcValue;
    }

    public static long byteArray2UnsignedInt(byte[] b)
    {
        long longValue = 0L;
        int length = b.length - 1;
        for (int i = 0; i < b.length; i++) {
            longValue += ((b[i] & 0xFF) << 8 * (length - i));
        }
        return longValue;
    }

    public static byte[] unsignedInt2ByteArray(long longValue)
    {
        BigInteger bint = new BigInteger(new Long(longValue).toString());
        BigInteger maxValue = new BigInteger("4294967296");
        if (bint.compareTo(new BigInteger(
                Integer.toString(Integer.MAX_VALUE))) > 0)
        {
            maxValue = maxValue.divide(new BigInteger("-1"));
            bint = bint.add(maxValue);
        }
        int value = Integer.parseInt(bint.toString());
        return int2ByteArray(value);
    }

}
