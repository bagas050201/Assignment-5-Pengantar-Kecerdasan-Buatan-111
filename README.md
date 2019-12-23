# Assignment-5-Pengantar-Kecerdasan-Buatan-111 Menggunakan Watershed : Vincent - Soille Algorithm
Tugas Akhir Pengantar Kecerdasan Buatan 111

Watershed transform adalah salah satu metode dalam bidang morfologi matematika yang mempelajari tentang segmentasi pemrosesan gambar. Transformasi watershed menggunakan grayscale sebagai penanda pada gambar, semakin gelap titik pewarnaan tersebut berarti titik itu diibaratkan berada pada cekungan, dan dari cekungan pastinya memiliki bagian yang semakin lama semakin tinggi, pada bagian ini biasanya berwarna putih. Dari setiap titik nantinya dihubungkan dengan suatu garis-garis yang melambangkan titik tertinggi dari setiap cekungan.  

Beberapa Istilah yang terdapat pada Watershed Transform yaitu :
  1. Graf
  2. grid digital
  3. gambar digital
  4. Geodesic
  
Pada pengimplementasian Watershed ini, Program ini menggunakan  Immersion Watershed versi algoritma Vincent-Soille pada bahasa pemrograman java (.java). Bahasa Pemrograman Java memungkinkan untuk memaksimalkan hasil program dalam bentuk GUI dengan menggunakan Library Java.awt dan ImageJ. Image-J sendiri adalah software gratis untuk pengolahan gambar digital berbasis Java yang dibuat oleh Peneliti di Research Services Branch, National Institute of Mental Health, Bethesda, Maryland, USA. ImageJ.exe dapat diunduh melalui Link : https://bit.ly/2EIzOof. 

Dalam Implementasi Program ini, terdiri dari empat Class yaitu Watershed_Algorithm, WatershedFIFO, WatershedPixel, dan WatershedStructure.
Pada Class  Watershed_Algorithm berisi sistem yang menjalankan Watershed Algoritm dan sistem yang akan memproses kedalam ImageJ.exe. Kemudian pada Class WatershedFIFO untuk melakukan suatu pemrosesan antrian, pada class ini menggunakan List yang bernama LinkedList. Pada Java  LinkedList merupakan sebuah collection yang digunakan sebagai tempat penyimpanan data yang terdiri dari node-node (simpul-simpul) yang saling terhubung. Setiap element dihubungkan dengan element lain menggunakan sebuah Pointer, Pointer (penunjuk) adalah sebuah variabel yang digunakan sebagai penunjuk alamat dari variabel lain. LinkedList digunakan untuk melakukan remove pada antrian pixel yang akan digunakan. Antrian Pixel kita pop() agar saat mencari nilai yang grayscale nya sesuai dengan treshold lebih cepat. Kemudian pada Class WatershedPixel bertujuan untuk mecari kemungkinan pengurutan pixel dari suatu gambar berdasarkan grayscale. Ini adalah langkah pertama dari algoritma watershed Vincent dan Soille pada tahun 1991. Disini mengimplementasikan bagian dari Library java.lang yaitu Comparable, Comparable  adalah salah satu interface di class library java (java.lang) yang mendefinisikan method .compareTo() secara default  yang fungsinya untuk membandingkan objek,  yang tipenya sama, untuk urutan penyortiran data tunggal. Jadi di Class bertujuan untuk memproses pixel gambar yang bertujuan untuk mencari piksel gambar yang sesuai dengan grayscale. Yang terakhir adalah Class WatershedStructure , pada class ini berisi sistem yang akan memproses piksel dari gambar yang sesuai dengan grayscale, Lalu class ini  memiliki atribut kumpulan pixel yang berbentuk list 1D. Selain itu juga, dalam Class WatershedStructure memiliki Sistem  yang bertugas memberikan label pada tiap pixel berdasarkan definisi Immersion Watershed.

Cara menjalankan program ini :
  1. Install ImageJ.exe
  2. Download Program ini
  3. Extract File lalu buatlah folder baru yang nantinya dimasukan kedalam Plugins pada ImageJ File 
  4. Open ImageJ.exe lalu ketuk File, kemudian Open (Ctrl + O)
  5. Pilih gambar yang ingin diproses
  6. Kemudian, Klik bagian Image lalu ubah Type kedalam 8-bit
  7. Terakhir, Klik Plugins lalu cari tab yang bernama folder yang anda buat atau tab yang bernama Watershed
