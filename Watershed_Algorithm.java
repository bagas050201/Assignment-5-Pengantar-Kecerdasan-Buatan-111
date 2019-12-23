/** PlugInFilter berfungsi sebagai tools yang akan memproses gambar **/
public class Watershed_Algorithm implements PlugInFilter {
    private int threshold;
    final static int HMIN = 0; //grayscale minimal
    final static int HMAX = 255; // grayscale maksimal

    public int setup(String arg, ImagePlus imp) {
		if (arg.equals("about"))
	    	{showAbout(); return DONE;}
		return DOES_8G+DOES_STACKS+SUPPORTS_MASKING+NO_CHANGES;
    }
    
    public void run(ImageProcessor ip) {
	boolean debug = false;

	IJ.showStatus("Sorting pixels...");
	IJ.showProgress(0.1);

	/** Langkah pertama : Piksel di sortir bedasarkan peningkatan warna nilai abu-abu **/
	WatershedStructure watershedStructure = new WatershedStructure(ip);
	if(debug)
	    IJ.write(""+watershedStructure.toString());

	IJ.showProgress(0.8);
	IJ.showStatus("Start flooding...");

	if(debug)
	    IJ.write("Starting algorithm...\n");

	/** memulai flooding **/
	WatershedFIFO queue = new WatershedFIFO();
	int curlab = 0;

	int heightIndex1 = 0;
	int heightIndex2 = 0;
	
	for(int h=HMIN; h<HMAX; h++) /*Geodesic SKIZ of level h-1 inside level h */ {
	
	    for(int pixelIndex = heightIndex1 ; pixelIndex<watershedStructure.size() ; pixelIndex++) /*mask all pixels at level h*/ {
			WatershedPixel p = watershedStructure.get(pixelIndex);

		if(p.getIntHeight() != h) {
		    /** pixel ini berada pada level h+1 **/
		    heightIndex1 = pixelIndex;
		    break;
		}

		p.setLabelToMASK(); // Line 19 pada PDF

		Vector neighbours = p.getNeighbours();
		for(int i=0 ; i<neighbours.size() ; i++) {
		    WatershedPixel q = (WatershedPixel) neighbours.get(i);
		    
		    if(q.getLabel()>=0) {/*Menginisialisasi antrian dengan tetangga pada tingkat h dari cekungan daerah watershed*/
				p.setDistance(1);
				queue.fifo_add(p);
				break;
		    } // end if
		} // end for
	    } // end for , Line ke 24 jika pada pdf
 
	
	    int curdist = 1;
	    queue.fifo_add_FICTITIOUS();

	    while(true) /** memperpanjang cekungan **/{
			WatershedPixel p = queue.fifo_remove();

			if(p.isFICTITIOUS())
		    	if(queue.fifo_empty())
				break;
		    	else {
					queue.fifo_add_FICTITIOUS();
					curdist++;
					p = queue.fifo_remove();
		    }
			if(debug) {
		    	IJ.write("\nWorking on :");
		    	IJ.write(""+p);
		}
		
		Vector neighbours = p.getNeighbours();
		for(int i=0 ; i<neighbours.size() ; i++) /* Labelling p by inspecting neighbours */{
		    WatershedPixel q = (WatershedPixel) neighbours.get(i);
		    
		    if(debug)
				IJ.write("Neighbour : "+q);

		    if( (q.getDistance() <= curdist) && (q.getLabel()>=0) ) {
			/* q merujuk pada sebuah cekungan */
			
			if(q.getLabel() > 0) {
			    if( p.isLabelMASK() )
					p.setLabel(q.getLabel());
			    	else
					if(p.getLabel() != q.getLabel())
				    p.setLabelToWSHED();
			} // end if lab>0
			else
			    if(p.isLabelMASK())
					p.setLabelToWSHED();
		    }
		    else
			if( q.isLabelMASK() &&
			    (q.getDistance() == 0) ) {
			    
			    if(debug)
				IJ.write("Adding value");
			    q.setDistance( curdist+1 );
			    queue.fifo_add( q );
			}	    
		} // end for, end proses tetangga

		if(debug) {
		    IJ.write("End processing neighbours");
		    IJ.write("New val :\n"+p);
		    IJ.write("Queue :\n"+queue);
		}
	    } // end while (loop)

	    /* mendeteksi dan memproses nilai minimal baru di h */
	    for(int pixelIndex = heightIndex2 ; pixelIndex<watershedStructure.size() ; pixelIndex++) {
			WatershedPixel p = watershedStructure.get(pixelIndex);

		if(p.getIntHeight() != h) {
		    /** pixel ini berada di h+1 **/
		    heightIndex2 = pixelIndex;
		    break;
		}

		p.setDistance(0); /* mengatur ulang jarak ke 0 */
		
		if(p.isLabelMASK()) { /* pixel yang saat ini berada didalam nilai minimum terbaru */
		    curlab++;
		    p.setLabel(curlab);		    
		    queue.fifo_add(p);
		    
		    
		    while(!queue.fifo_empty()) {
			WatershedPixel q = queue.fifo_remove();

			Vector neighbours = q.getNeighbours();

			for(int i=0 ; i<neighbours.size() ; i++) /* periksa nilai tetangga, line 61 pada PDF */{
			    WatershedPixel r = (WatershedPixel) neighbours.get(i);
		    
			    if( r.isLabelMASK() ) {
				r.setLabel(curlab);
				queue.fifo_add(r);
			    }
			}
		    } // end while
		} // end if
	    } // end for
	} /** ending flooding **/

	IJ.showProgress(0.9);
	IJ.showStatus("Putting result in a new image...");

	/** memasukan hasil kedalam gambar terbaru **/
	int width = ip.getWidth();

	ImageProcessor outputImage = new ByteProcessor(width, ip.getHeight());
	byte[] newPixels = (byte[]) outputImage.getPixels();

	for(int pixelIndex = 0 ; pixelIndex<watershedStructure.size() ; pixelIndex++) {
	    WatershedPixel p = watershedStructure.get(pixelIndex);
	
	    if(p.isLabelWSHED() && !p.allNeighboursAreWSHED())
		newPixels[p.getX()+p.getY()*width] = (byte)255;
	}

	IJ.showProgress(1);
	IJ.showStatus("Displaying result...");

	new ImagePlus("Watershed", outputImage).show();
    }
    
    void showAbout() {
	IJ.showMessage("program ini dapat dijalankan dengan cara meng input gambar pada imageJ" +
					"kemudian mengubah format gambar kedalam type 8-bit " +
					"lalu dijalankan melalui plugins \n"
		       );
    }
}

