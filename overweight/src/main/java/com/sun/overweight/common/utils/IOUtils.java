package com.sun.overweight.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class IOUtils
{
    public static String toString(InputStream input)
            throws IOException
    {
        return toString(input, 4096, "utf-8");
    }

    public static String toString(InputStream input, String charset)
            throws IOException
    {
        return toString(input, 4096, charset);
    }

    public static String toString(InputStream input, int bufferSize)
            throws IOException
    {
        return toString(input, bufferSize, null);
    }

    public static String toString(InputStream input, int bufferSize, String charset)
            throws IOException
    {
        int avail = input.available();
        if (avail > bufferSize) {
            bufferSize = avail;
        }
        Reader reader = new InputStreamReader(input, charset);

        return toString(reader, bufferSize);
    }

    public static String toString(Reader input, int bufSize)
            throws IOException
    {
        StringBuilder buf = new StringBuilder();
        char[] buffer = new char[bufSize];
        int n = 0;
        n = input.read(buffer);
        while (-1 != n)
        {
            if (n == 0) {
                throw new IOException("0 bytes read in violation of InputStream.read(byte[])");
            }
            buf.append(new String(buffer, 0, n));
            n = input.read(buffer);
        }
        input.close();
        return buf.toString();
    }
}
