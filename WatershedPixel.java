import java.lang.*;
import java.util.*;
import ij.*;

/**
 *  Tujuan dari WatershedPixel yaitu untuk memungkinkan
 *  terjadinya pengrutan pixel dari suatu gambar berdasarkan
 *  grayscale. Ini adalah langkah pertama dari algoritma
 *  watershed Vincent dan Soille pada tahun 1991
 **/
/** Comparable adalah salah satu interface di class library java (java.lang)
* yang mendefinisikan method .compareTo() secara default
* yang fungsinya untuk membandingkan objek,  yang tipenya
* sama, untuk urutan penyortiran data tunggal.
**/
 public class WatershedPixel implements Comparable {
    /** Nilai yang digunakan untuk menginisialisasi gambar */
    final static int INIT = -1;
    /** Nilai yang digunakan untuk menunjuk setiap piksel baru
     * yang akan di proses (Nilai awal disetiap level)
     **/
    final static int MASK = -2;
    /** Nilai yang menunjuk bahwa piksel tersebut
     * adalah bagian dari watershed (Label)
     **/
    final static int WSHED = 0;
    /** Piksel fiktif **/
    final static int FICTITIOUS = -3;

    /** Koordinat piksel di X **/
    private int x;
    /** Koordinat piksel di Y **/
    private int y;
    /** grayscale nilai pada piksel **/
    private byte height; 
    /** Label yang digunakan pada Watershed **/
    private int label;
    /** Jarak yang digunakan piksel untuk bekerja */
    private int dist;

    /** Tetangga **/
    private Vector neighbours;

    public WatershedPixel(int x, int y, byte height) {
	    this.x = x;
	    this.y = y;
	    this.height = height;
	    label = INIT;
	    dist = 0;
	    neighbours = new Vector(8);
    }

    public WatershedPixel() {
	label = FICTITIOUS;
    }

    public void addNeighbour(WatershedPixel neighbour) {
        IJ.write("In Pixel, adding :");
	    IJ.write(""+neighbour);
	    neighbours.add(neighbour);
	    IJ.write("Add done");

	neighbours.add(neighbour);
    }

    public Vector getNeighbours() {
	return neighbours;
    }



    public String toString() {
	return new String("("+x+","+y+"), height : "+getIntHeight()+", label : "+label+", distance : "+dist); 
    }



    public final byte getHeight() {
	return height;
    } 

    public final int getIntHeight() {
	return (int) height&0xff;
    } 

    public final int getX() {
	return x;
    } 

    public final int getY() {
	return y;
    }


    /** metode yang dapat melakukan  collection data **/
    public int compareTo(Object o) {
        if(!(o instanceof WatershedPixel))
	        throw new ClassCastException();

	    WatershedPixel obj =  (WatershedPixel) o;

	    if( obj.getIntHeight() < getIntHeight() )
	        return 1;

	    if( obj.getIntHeight() > getIntHeight() )
	        return -1;

	    return 0;
    }

    public void setLabel(int label) {
	this.label = label;
    }

    public void setLabelToINIT() {
	label = INIT;
    }

    public void setLabelToMASK() {
	label = MASK;
    }
    
    public void setLabelToWSHED() {
	label = WSHED;
    }


    public boolean isLabelINIT() {
	return label == INIT;
    }
    public boolean isLabelMASK() {
	return label == MASK;
    }    
    public boolean isLabelWSHED() {
	return label == WSHED;
    }

    public int getLabel() {
	return label;
    }

    public void setDistance(int distance) {
	dist = distance;
    }

    public int getDistance() {
	return dist;
    }

    public boolean isFICTITIOUS() {
	return label == FICTITIOUS;
    }

    public boolean allNeighboursAreWSHED() {
	for(int i=0 ; i<neighbours.size() ; i++) {
	    WatershedPixel r = (WatershedPixel) neighbours.get(i);
		    
	    if( !r.isLabelWSHED() ) 
		return false;
	}
	return true;
    }
}
