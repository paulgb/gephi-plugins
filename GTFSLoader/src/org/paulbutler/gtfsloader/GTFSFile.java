package org.paulbutler.gtfsloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


public class GTFSFile {
    private final ZipFile zip;
    
    public GTFSFile (File file) throws ZipException, IOException {
        this.zip = new ZipFile(file);
    }
    
    public Iterable<Map<String, String>> getFile(String filename) throws IOException {
        ZipEntry entry = new ZipEntry(filename);
        
        return new CSVFieldReader(new InputStreamReader(zip.getInputStream(entry)));
    }
    
    public Iterable<Map<String, String>> getStops () throws IOException {
        return getFile("stops.txt");
    }
}
