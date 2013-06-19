/*******************************************************************************
 * Salt Payment Client API
 * Version 1.0.0
 * http://salttechnology.github.io/core_api_doc.htm
 * 
 * Copyright (c) 2013 Salt Technology
 * Licensed under the MIT license
 * https://github.com/SaltTechnology/salt-payment-client-java/blob/master/LICENSE
 ******************************************************************************/
package com.salt.payment.client.creditcard.api;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Contains utility methods.
 * 
 * @since JSE5
 */
public abstract class Utils {
    /** The buffer size to use when processing streams. */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Copy the contents of the given Reader to the given Writer. Closes both
     * when done.
     * 
     * @param in
     *            the Reader to copy from
     * @param out
     *            the Writer to copy to
     * @return the number of characters copied
     * @throws IOException
     *             in case of I/O errors
     */
    public static int copy(Reader in, Writer out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("in must not be null");
        }
        if (out == null) {
            throw new IllegalArgumentException("out must not be null");
        }
        try {
            int byteCount = 0;
            final char[] buffer = new char[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (final IOException ex) {
                System.out.println("WARNING: error closing reader");
                ex.printStackTrace();
            }
            try {
                out.close();
            } catch (final IOException ex) {
                System.out.println("WARNING: error closing writer");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Writes the string <code>in</code> to the writer <code>out</code>. Closes
     * the writer when done.
     * 
     * @param in
     *            the string to write. Not null.
     * @param out
     *            the writer to write to. Not null.
     * @throws IOException
     *             if an i/o error occurs
     */
    public static void copy(String in, Writer out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("in must not be null");
        }
        if (out == null) {
            throw new IllegalArgumentException("out must not be null");
        }
        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (final IOException ex) {
                System.out.println("WARNING: error closing writer");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Copy the contents of the given Reader into a String. Closes the reader
     * when done.
     * 
     * @param in
     *            the reader to copy from
     * @return the String that has been copied to
     * @throws IOException
     *             in case of I/O errors
     */
    public static String copyToString(Reader in) throws IOException {
        final StringWriter out = new StringWriter();
        copy(in, out);
        return out.toString();
    }

    /**
     * Check if the string is empty.
     * 
     * @param str
     *            the string to check
     * @return true if str is null or it is an empty string
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * Appends a Property (of the form name=value[newline]) to a string builder
     * 
     * @param str
     *            the builder
     * @param name
     *            property name
     * @param value
     *            property value
     */
    public static void appendToStringBuilder(StringBuilder str, String name, Object value) {
        if (str == null) {
            throw new IllegalArgumentException("str is required");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is required");
        }
        if (value != null) {
            if (str.length() != 0) {
                str.append("\n");
            }
            str.append(name).append("=").append(value);
        }
    }
}
