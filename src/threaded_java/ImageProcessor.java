package threaded_java;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageProcessor {
    final static String IMAGES_DIRECTORY = "images/";
    final static String OUTPUT_DIRECTORY = "output/images/";
    public static void main(String[] args) throws IOException {
        boolean verbose = true;
        var start = System.currentTimeMillis();

        File directory = new File(IMAGES_DIRECTORY);
        File output = new File(OUTPUT_DIRECTORY);
        if(!output.exists())
            output.mkdirs();

        var files = directory.listFiles();
        var printer = new BufferedImagePrinter();
        var threads = new ArrayList<Thread>();
        for (final var file : files){
            var imageProcessor = new GreyScaleImageProcessor(file, OUTPUT_DIRECTORY);
            imageProcessor.start();
            threads.add(imageProcessor);
        }
        threads.forEach( e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        if(verbose){
            var end = System.currentTimeMillis();
            var milliseconds = end - start;
            System.out.println("Overall process run in " + milliseconds + " ms.");

        }
    }

}




abstract class ThreadedImageProcessor extends Thread {

    final File file;

    public ThreadedImageProcessor(File file){ this.file = file;}

    public void process() throws IOException {}

    @Override
    public void run(){
        try {
            process();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

class GreyScaleImageProcessor extends ThreadedImageProcessor {
    final String outputDirectory;
    public GreyScaleImageProcessor(File file, String outputDirectory) {
        super(file);
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void process() throws IOException {
        var start = System.currentTimeMillis();
        var basename = file.getName();
        basename = basename.substring(0,basename.lastIndexOf('.'));
        boolean verbose = true;
        if(verbose){
            System.out.println("Processing " + basename + " in thread " + Thread.currentThread().getName());
        }

        BufferedImage image = ImageIO.read(file);
        for (var i = 0; i < image.getWidth() ;i++){
            for (var j = 0 ; j < image.getHeight(); j++){
                int rgb = image.getRGB(i,j);
               int a = (rgb >> 24) & 0xff; // Transparency
                int r = (rgb >> 16) & 0xff; // Red channel
                int g = (rgb >> 8) & 0xff;  // Green channel
                int b = rgb & 0xff; // Blue Channel

                int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);


                image.setRGB(i,j,( a << 24 | gray << 16) | (gray << 8) | gray);
            }
        }


        if(verbose){
            System.out.println("Saving " + basename + " in thread " + Thread.currentThread().getName());

        }
        ImageIO.write(image, "png", new File(outputDirectory + basename + ".png"));

        if(verbose){
            var end = System.currentTimeMillis();
            var milliseconds = end - start;
            System.out.println(Thread.currentThread().getName() + " run in " + milliseconds + " ms.");

        }


    }
}

class BufferedImagePrinter {
    final private String PIXELS = "px";
    public void printWidthAndHeight(BufferedImage image){

        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("Width  " + width + PIXELS);
        System.out.println("Height " + height + PIXELS);
        System.out.println("" + width + "x" + height);

    }


}