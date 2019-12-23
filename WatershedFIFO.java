import java.util.*;
import ij.*;

/** Kelas ini mengimplementasikan antrian FIFO
 * yang menggunakan formula yang sama dengan
 * algoritma Vincent dan Soille (1991)
 **/

/**  LinkedList merupakan sebuah collection yang digunakan sebagai tempat penyimpanan data
  *  yang terdiri dari node-node (simpul-simpul) yang saling terhubung. Setiap element
  *  dihubungkan dengan element lain menggunakan sebuah Pointer, Pointer (penunjuk)
 * adalah sebuah variabel yang digunakan sebagai penunjuk alamat dari variabel lain.
 **/
public class WatershedFIFO {
    private LinkedList watershedFIFO;

    public WatershedFIFO() {
	watershedFIFO = new LinkedList();
    }

    public void fifo_add(WatershedPixel p) {
	watershedFIFO.addFirst(p);
    }

    public WatershedPixel fifo_remove() {
	return (WatershedPixel) watershedFIFO.removeLast();
    }

    public boolean fifo_empty() {
	return watershedFIFO.isEmpty();
    }

    public void fifo_add_FICTITIOUS() {
	watershedFIFO.addFirst(new WatershedPixel());
    }

    public String toString() {
	    StringBuffer ret = new StringBuffer();
	    for(int i=0; i<watershedFIFO.size(); i++) {
	        ret.append( ((WatershedPixel)watershedFIFO.get(i)).toString() );
	        ret.append( "\n" );
	}
	
	return ret.toString();
    }
}
