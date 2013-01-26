/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paulbutler.gtfsloader;

import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.Report;
import org.gephi.io.importer.spi.SpigotImporter;

/**
 *
 * @author paulbutler
 */
class GTFSImporter implements SpigotImporter {
    private ContainerLoader loader;


    @Override
    public boolean execute(ContainerLoader loader) {
        this.loader = loader;
        
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
    
}
