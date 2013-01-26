
package org.paulbutler.gtfsloader;

import com.sunsetbrew.csv4180.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.openide.util.Exceptions;


public class CSVFieldReader extends CSVReader implements Iterable<Map<String, String>>, Iterator<Map<String, String>> {
    private final ArrayList<String> fieldNames;
    private ArrayList<String> rowBuffer;
    
    public CSVFieldReader(Reader r) {
        super(r);

        fieldNames = new ArrayList<String>();
        System.out.println(fieldNames);
        
        try {
            this.readFields(fieldNames);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    private boolean bufferRow() throws IOException {
        if (this.rowBuffer != null) {
            return true;
        }
        if (this.isEOF()) {
            return false;
        }
        
        HashMap<String, String> map = new HashMap<String, String>();
        
        rowBuffer = new ArrayList<String>();
        
        readFields(rowBuffer);
        
        if (rowBuffer.size() == 1 && rowBuffer.get(0).equals("")) {
            return false;
        } else {
            return true;
        }
    }
    
    public Map<String, String> readRow() throws IOException {
        if (rowBuffer == null) {
            bufferRow();
        }
        
        HashMap<String, String> row = new HashMap<String, String>();
        
        for (int i = 0; i < rowBuffer.size(); i++) {
            row.put(fieldNames.get(i), rowBuffer.get(i));
        }
        
        rowBuffer = null;
        return row;
    }

    @Override
    public boolean hasNext() {
        try {
            return bufferRow();
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public Map<String, String> next() {
        try {
            return readRow();
        } catch (IOException ex) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Map<String, String>> iterator() {
        return this;
    }
    
}
