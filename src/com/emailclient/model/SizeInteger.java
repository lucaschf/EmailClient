package com.emailclient.model;

public class SizeInteger implements Comparable<SizeInteger> {
    private final int size;

    public SizeInteger(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        if (size <= 0)
            return "0";

        if (size < 1024)
            return size + "B";

        if (size < 1048576)
            return size / 1024 + "KB";

        return size / 1048576 + "MB";
    }

    @Override
    public int compareTo(SizeInteger o) {
        return Integer.compare(size, o.size);
    }
}
