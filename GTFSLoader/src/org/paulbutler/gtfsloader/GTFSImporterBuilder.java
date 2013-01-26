
package org.paulbutler.gtfsloader;

import org.gephi.io.importer.spi.SpigotImporter;
import org.gephi.io.importer.spi.SpigotImporterBuilder;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = SpigotImporterBuilder.class)
public class GTFSImporterBuilder implements SpigotImporterBuilder {

    @Override
    public SpigotImporter buildImporter() {
        return new GTFSImporter();
    }

    @Override
    public String getName() {
        return "GTFS Importer";
    }
    
}
