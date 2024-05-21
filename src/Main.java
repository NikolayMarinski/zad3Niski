import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    public static void main(String[] args) {
        PriorityBlockingQueue<Product> products = new PriorityBlockingQueue<Product>(10, new ProductComparator());
        try {
            ProductThread t1 = new ProductThread("CKThread", "Files/ck.txt", products);
            ProductThread t2 = new ProductThread("GuessThread", "Files/guess.txt", products);
            ProductThread t3 = new ProductThread("TrussardiThreads", "Files/trussardi.txt", products);

            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();

        }
        catch (Exception exception){
            System.out.println("Error with the data!");
            System.exit(1);
        }

        ArrayList<Product> polledElements = new ArrayList<>();
        products.drainTo(polledElements);
        File file = new File("result_products.txt");
        if(file.exists()){
            System.out.println("File already exists");
            System.exit(1);
        }

        PrintWriter out = null;
        try{
            out = new PrintWriter(file);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        for(Product p : polledElements){
            out.println(p+ " ");
        }


        out.close();
    }
}