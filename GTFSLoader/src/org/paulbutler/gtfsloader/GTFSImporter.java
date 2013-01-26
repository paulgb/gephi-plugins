/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paulbutler.gtfsloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.Report;
import org.gephi.io.importer.spi.SpigotImporter;
import org.openide.util.Exceptions;

import com.sunsetbrew.csv4180.CSVReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 *
 * @author paulbutler
 */
class GTFSImporter implements SpigotImporter {
    private ContainerLoader loader;
    private File file;


    @Override
    public boolean execute(ContainerLoader loader) {
        FileInputStream fis = null;
        ZipFile zf = null;
        CSVReader c;
        
        this.loader = loader;
        System.out.println(file);
        
        try {
            fis = new FileInputStream(file);
            zf = new ZipFile(file);
        } catch (ZipException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        ZipEntry routes = zf.getEntry("routes.txt");
        
        try {
            c = new CSVReader(new InputStreamReader(zf.getInputStream(routes)));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return false;
        }
        
        ArrayList<String> st = new ArrayList<String>();
        try {
            c.readFields(st);
            System.out.println(st);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        
        return true;

    }

    @Override
    public ContainerLoader getContainer() {
        return loader;
    }

    @Override
    public Report getReport() {
        return new Report();
    }

    void setFile(File file) {
        this.file = file;
    }
    
}
