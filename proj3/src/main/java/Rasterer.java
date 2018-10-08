import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method will return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */

/**
 * @author: Vinay Maruri, vmaruri1@berkeley.edu
 * @version: 2.3
 */
public class Rasterer {
    private Double ullon;
    private Double ullat;
    private Double lrlon;
    private Double lrlat;
    private int depth;
    private boolean querysuccess;
    private Double width;
    private Double lonDPP;
    private Double latDPP;
    private String[][] rendergrid;

    public Rasterer() {
        rendergrid = null;
        ullon = 0.0;
        ullat = 0.0;
        lrlon = 0.0;
        lrlat = 0.0;
        depth = 0;
        width = 0.0;
        lonDPP = 0.0;
        latDPP = 0.0;
        querysuccess = false;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     * <li>The tiles collected must cover the most longitudinal distance per pixel
     * (LonDPP) possible, while still covering less than or equal to the amount of
     * longitudinal distance per pixel in the query box for the user viewport size. </li>
     * <li>Contains all tiles that intersect the query bounding box that fulfill the
     * above condition.</li>
     * <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
<<<<<<< HEAD
     * "depth"         : Number, the depth of the nodes of the rastered image;
     * can also be interpreted as the length of the numbers in the image
     * string. <br>
=======
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
>>>>>>> 0ff9935f3e4eef382e47c567733a55bb2bd2bcab
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        Rasterer rast = new Rasterer();
        rast.ullon = params.get("ullon");
        rast.ullat = params.get("ullat");
        rast.lrlat = params.get("lrlat");
        rast.lrlon = params.get("lrlon");
        rast.width = params.get("w");
        rast.lonDPP = (rast.lrlon - rast.ullon) / rast.width;
        double referencelonDPP = 0.000343322754;
        while (referencelonDPP >= rast.lonDPP) {
            referencelonDPP = referencelonDPP / 2.0;
            rast.depth += 1;
        }
        if (rast.depth > 7) {
            rast.depth = 7;
        }
        if (rast.ullon > rast.lrlon || rast.ullat < rast.lrlat) {
            rast.querysuccess = false;
        } else {
            rast.querysuccess = true;
        }
        int rows = ((int) Math.pow(2, rast.depth));
        int cols = ((int) Math.pow(2, rast.depth));

        double binwidth = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / cols;
        double binheight = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / rows;

        rast.ullon = rast.trueullon(rast.ullon, binheight, binwidth, rast.ullat);
        rast.ullat = rast.trueullat(rast.ullat, binheight, binwidth, rast.ullon);
        rast.lrlat = rast.truelrlat(rast.lrlat, binheight, binwidth, rast.lrlon);
        rast.lrlon = rast.truelrlon(rast.lrlon, binheight, binwidth, rast.lrlat);

        double rowholder = rast.ullon - MapServer.ROOT_ULLON;
        rowholder = rowholder / binheight;
        double rowstart = rowholder;
        double rowholder2 = rast.lrlon - MapServer.ROOT_ULLON;
        rowholder2 = rowholder2 / binheight;
        double rowend = rowholder2;
        System.out.println(rowend);
        System.out.println(rowstart);

        double colholder = MapServer.ROOT_ULLAT - rast.ullat;
        colholder = colholder / binwidth;
        double colstart = (double) (Math.round(colholder));
        double colholder2 = MapServer.ROOT_ULLAT - rast.lrlat;
        colholder2 = colholder2 / binwidth;
        double colend = (double) (Math.round(colholder2));
        System.out.println(colend);
        System.out.println(colstart);

        int numrows = (int) (rowend - rowstart);
        int numcols = (int) (colend - colstart);
        rast.rendergrid = new String[numcols][numrows];
        int z = (int) rowstart;
        int q = (int) colstart;
        for (int i = 0; i < numcols; i++) {
            for (int j = 0; j < numrows; j++) {
                rast.rendergrid[i][j] = "d" + rast.depth + "_" + "x" + z + "_" + "y" + q + ".png";
                z += 1;
            }
            z = (int) rowstart;
            q += 1;
        }
        results.put("raster_ul_lon", rast.ullon);
        results.put("raster_ul_lat", rast.ullat);
        results.put("raster_lr_lon", rast.lrlon);
        results.put("raster_lr_lat", rast.lrlat);
        results.put("depth", rast.depth);
        results.put("render_grid", rast.rendergrid);
        results.put("query_success", rast.querysuccess);

        return results;
    }

    private double trueullon(double o, double h, double w, double z) {
        double holder = o - MapServer.ROOT_ULLON;
        holder = holder / h;
        int numh = (int) holder;
        double result = MapServer.ROOT_ULLON + (numh * h);
        return result;
    }

    private double trueullat(double o, double h, double w, double z) {
        double holder = MapServer.ROOT_ULLAT - o;
        holder = holder / w;
        int numw = (int) holder;
        double result = MapServer.ROOT_ULLAT - (numw * w);
        return result;
    }

    private double truelrlon(double o, double h, double w, double z) {
        double holder = o - MapServer.ROOT_LRLON;
        holder = holder / h;
        int numh = (int) holder;
        double result = MapServer.ROOT_LRLON + (numh * h);
        return result;
    }

    private double truelrlat(double o, double h, double w, double z) {
        double holder = o - MapServer.ROOT_LRLAT;
        holder = holder / w;
        int numw = (int) holder;
        double result = MapServer.ROOT_LRLAT + (numw * w);
        return result;
    }
}
