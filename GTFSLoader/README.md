
## Edges

GTFS does not contain an explicit edge set. It is possible to infer the edge set in a number of different ways, each of which have subtle differences in the results:

### Shape Files

GTFS files include shape data so that routes are drawn accurrately. Depending on how these shapes are created, they may contain points with identical coordinates as the stops themselves. In this case, we can infer that stops are connected in the order that they occur in a shape. Any coordinates in the shape which do not correspond to a stop's coordinates are ignored.

### Stop Order

The Stop Order method creates edges between every pair of stops between which direct transit is available. Direct transit means any mode of transit which goes from one stop to the other with no stops in between. It is more reliable than Shape Files when the coordinates used by shapes do not exactly match the coordinates of the stops, but it may not produce the results you want when express routes exist as they will appear as edges between distant stations.

### Distance Travelled

If the GTFS file includes the distance travelled along a shape, this can combine multiple routes on the same shape to give a more accurate graph of how stops are connected.

