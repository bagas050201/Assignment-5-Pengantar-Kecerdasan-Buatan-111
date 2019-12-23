import java.lang.*;
import java.util.*;
import ij.process.*;
import ij.*; // imageJ adalah software pengolahan dan analisis gambar digital pada java
import java.awt.*;  //pustaka pada java yang berfungsi menyediakan kelas GUI pada java

/**
 *  WatershedStructure berisi piksel dari gambar
 *  yang didapatkan sesuai dengan nilai grayscale
 *  yang memiliki akses langsung dari setiap teteangga
 *  
 **/

public class WatershedStructure {
    private Vector watershedStructure;

    public WatershedStructure(ImageProcessor ip) {
	byte[] pixels = (byte[])ip.getPixels();
	Rectangle r = ip.getRoi();
	int width = ip.getWidth();
	int offset, topOffset, bottomOffset, i;

	watershedStructure = new Vector(r.width*r.height);

	/** Struktur klemudian diisi dengan piksel gambar. **/
	for (int y=r.y; y<(r.y+r.height); y++) {
	    offset = y*width;
	    
	    IJ.showProgress(0.1+0.3*(y-r.y)/(r.height));

	    for (int x=r.x; x<(r.x+r.width); x++) {
			i = offset + x;

		int indiceY = y-r.y;
		int indiceX = x-r.x;
		
		watershedStructure.add(new WatershedPixel(indiceX, indiceY, pixels[i]));
	    }
	}

	/** Kemudian Watershed Pixel diisi dengan nilai referensi dari tetangga mereka. **/
	for (int y=0; y<r.height; y++) {

	    offset = y*width;
	    topOffset = offset+width;
	    bottomOffset = offset-width;
	    
	    IJ.showProgress(0.4+0.3*(y-r.y)/(r.height));

	    for (int x=0; x<r.width; x++) {		
			WatershedPixel currentPixel = (WatershedPixel)watershedStructure.get(x+offset);

		if(x+1<r.width) {
		    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+1+offset));

		    if(y-1>=0)
				currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+1+bottomOffset));
		
		    if(y+1<r.height)
				currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+1+topOffset));
		}

		if(x-1>=0) {
		    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x-1+offset));
		
		    if(y-1>=0)
				currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x-1+bottomOffset));
		
		    if(y+1<r.height)
				currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x-1+topOffset));
		}
		
		if(y-1>=0)
		    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+bottomOffset));
		
		if(y+1<r.height)
		    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+topOffset));
	    }
	} 

	Collections.sort(watershedStructure);
	IJ.showProgress(0.8);
    }

    public String toString() {
		StringBuffer ret = new StringBuffer();

	for(int i=0; i<watershedStructure.size() ; i++) {
	    ret.append( ((WatershedPixel) watershedStructure.get(i)).toString() );
	    ret.append( "\n" );
	    ret.append( "Neighbours :\n" );

	    Vector neighbours = ((WatershedPixel) watershedStructure.get(i)).getNeighbours();

	    for(int j=0 ; j<neighbours.size() ; j++) {
			ret.append( ((WatershedPixel) neighbours.get(j)).toString() );
			ret.append( "\n" );
	    }
	    ret.append( "\n" );
	}
	return ret.toString();
    }

    public int size() {
	return watershedStructure.size();
    }

    public WatershedPixel get(int i) {
	return (WatershedPixel) watershedStructure.get(i);
    }
}
