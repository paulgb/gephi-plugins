
package org.paulbutler.gtfsloader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.io.importer.api.Report;
import org.gephi.io.importer.spi.SpigotImporter;
import org.openide.util.Exceptions;

class GTFSImporter implements SpigotImporter {
    private ContainerLoader loader;
    private File file;

    @Override
    public boolean execute(ContainerLoader loader) {
        GTFSFile g;
        
        Map<Coordinate, String> coordToStop = new HashMap<Coordinate, String>();
        
        try {
            g = new GTFSFile(file);
            
            Iterable<Map<String, String>> stops = g.getFile("stops.txt");
            for (Map<String, String> stop : stops) {
                if (!stop.get("parent_station").equals("")) {
                    continue;
                }
                
                Coordinate c = new Coordinate(new Float(stop.get("stop_lat")), new Float(stop.get("stop_lon")));
                coordToStop.put(c, stop.get("stop_id"));
                
                NodeDraft nd = loader.factory().newNodeDraft();
                nd.setId(stop.get("stop_id"));
                nd.setX(c.getLon());
                nd.setY(c.getLat());
                nd.setLabel(stop.get("stop_name"));
                loader.addNode(nd);
            }
            
            Iterable<Map<String, String>> shapes = g.getFile("shapes.txt");
            String lastStop = "";
            String shapeId = "";
            for (Map<String, String> shape : shapes) {
                Coordinate c = new Coordinate(new Float(shape.get("shape_pt_lat")), new Float(shape.get("shape_pt_lon")));
                if (!coordToStop.containsKey(c)) {
                    continue;
                }
                String stop = coordToStop.get(c);

                if (shape.get("shape_id").equals(shapeId)) {
                    EdgeDraft ed = loader.factory().newEdgeDraft();
                    ed.setSource(loader.getNode(lastStop));
                    ed.setTarget(loader.getNode(stop));
                    ed.setType(EdgeDraft.EdgeType.UNDIRECTED);
                    loader.addEdge(ed);
                } else {
                    shapeId = shape.get("shape_id");
                }
                
                lastStop = stop;
            }
            
        } catch (ZipException ex) {
            Exceptions.printStackTrace(ex);
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
