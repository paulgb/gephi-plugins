
package org.paulbutler.gtfsloader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipException;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.io.importer.api.Report;
import org.gephi.io.importer.spi.SpigotImporter;
import org.openide.util.Exceptions;

class GTFSImporter implements SpigotImporter {
    private ContainerLoader loader;
    private File file;
    private GTFSImportOptions options;

    @Override
    public boolean execute(ContainerLoader loader) {
        GTFSFile g;
        
        Map<Coordinate, String> coordToStop = new HashMap<Coordinate, String>();
        Map<String, String> stopIdToStationId = new HashMap<String, String>();
        
        try {
            g = new GTFSFile(file);
            
            Iterable<Map<String, String>> stops = g.getFile("stops.txt");
            for (Map<String, String> stop : stops) {
                if (!stop.get("parent_station").equals("")) {
                    stopIdToStationId.put(stop.get("stop_id"), stop.get("parent_station"));
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
            
            switch (options.getEdgeAlgorithm()) {
                case SHAPES:

                    Iterable<Map<String, String>> shapes = g.getFile("shapes.txt");
                    String lastStop = "";
                    String shapeId = "";
                    HashSet<String> edges = new HashSet<String>();
                    for (Map<String, String> shape : shapes) {
                        Coordinate c = new Coordinate(new Float(shape.get("shape_pt_lat")), new Float(shape.get("shape_pt_lon")));
                        if (!coordToStop.containsKey(c)) {
                            continue;
                        }
                        String stop = coordToStop.get(c);

                        if (shape.get("shape_id").equals(shapeId)) {
                            NodeDraft source = loader.getNode(lastStop);
                            NodeDraft target = loader.getNode(stop);
                            if (!loader.edgeExists(source, target)) {
                                EdgeDraft ed = loader.factory().newEdgeDraft();
                                ed.setSource(loader.getNode(lastStop));
                                ed.setTarget(loader.getNode(stop));
                                ed.setType(EdgeDraft.EdgeType.UNDIRECTED);
                                loader.addEdge(ed);
                            } else {
                            }
                        } else {
                            shapeId = shape.get("shape_id");
                        }

                        lastStop = stop;
                    }
                    
                    break;
                case STOP_ORDER:

                    Iterable<Map<String, String>> stop_times = g.getFile("stop_times.txt");
                    lastStop = "";
                    String tripId = "";
                    for (Map<String, String> stop_time : stop_times) {
                        String stop = stop_time.get("stop_id");
                        if (stopIdToStationId.containsKey(stop)) {
                            stop = stopIdToStationId.get(stop);
                        }

                        if (stop_time.get("trip_id").equals(tripId)) {
                            EdgeDraft ed = loader.factory().newEdgeDraft();
                            NodeDraft source = loader.getNode(lastStop);
                            NodeDraft target = loader.getNode(stop);
                            if (!loader.edgeExists(source, target)) {
                                ed.setSource(source);
                                ed.setTarget(target);
                                ed.setType(EdgeDraft.EdgeType.UNDIRECTED);
                                loader.addEdge(ed);
                            }
                        } else {
                            tripId = stop_time.get("trip_id");
                        }
                        lastStop = stop;
                    }

                    break;

                case DISTANCE:
                    
                    AttributeModel am = loader.getAttributeModel();
                    AttributeColumn shapeColumn = am.getEdgeTable().addColumn("shape", AttributeType.STRING);
                                    
                    
                    HashMap<String, String> tripToShape = new HashMap<String, String>();
                    
                    Iterable<Map<String, String>> trips = g.getFile("trips.txt");
                    for (Map<String, String> trip : trips) {
                        tripToShape.put(trip.get("trip_id"), trip.get("shape_id"));
                    }
                    
                    HashMap<String, TreeMap<Float, String>> shapeToDistanceToStop = new HashMap<String, TreeMap<Float, String>>();
                    stop_times = g.getFile("stop_times.txt");
                    for (Map<String, String> stop_time : stop_times) {
                        String shape_id = tripToShape.get(stop_time.get("trip_id"));
                        if (!shapeToDistanceToStop.containsKey(shape_id)) {
                            shapeToDistanceToStop.put(shape_id, new TreeMap<Float, String>());
                        }
                        TreeMap<Float, String> map = shapeToDistanceToStop.get(shape_id);
                        Float distTraveled = 0.0f;
                        if (!stop_time.get("shape_dist_traveled").equals("")) {
                            distTraveled = new Float(stop_time.get("shape_dist_traveled"));
                        }
                        map.put(distTraveled, stop_time.get("stop_id"));
                    }
                    
                    for (String shpId : shapeToDistanceToStop.keySet()) {
                        TreeMap<Float, String> shapeStops = shapeToDistanceToStop.get(shpId);
                        lastStop = null;
                        for (String stop : shapeStops.values()) {
                            if (lastStop != null) {
                                EdgeDraft ed = loader.factory().newEdgeDraft();
                                NodeDraft source = loader.getNode(lastStop);
                                NodeDraft target = loader.getNode(stop);
                                if (!loader.edgeExists(source, target)) {
                                    ed.setSource(source);
                                    ed.setTarget(target);
                                    ed.setType(EdgeDraft.EdgeType.UNDIRECTED);
                                    ed.addAttributeValue(shapeColumn, shpId);
                                    loader.addEdge(ed);
                                }
                            }
                            lastStop = stop;
                        }
                    }
                    
                    break;
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

    void setOptions(GTFSImportOptions options) {
        this.options = options;
    }
    
}
