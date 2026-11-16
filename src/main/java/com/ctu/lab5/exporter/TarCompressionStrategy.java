package com.ctu.lab5.exporter;

public class TarCompressionStrategy implements CompressionStrategy{
    @Override
    public byte[] compress(String rawData) {
        System.out.println("Nén dữ liệu theo chuẩn TAR...");
        return ("TAR[" + rawData + "]").getBytes();
    }
}
