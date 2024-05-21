import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class ProductThread  extends Thread{
    private String fileName;
    private PriorityBlockingQueue<Product> products;

    ProductThread(String name, String fileName, PriorityBlockingQueue<Product> products){
        super(name);
        this.fileName = fileName;
        this.products = products;
    }
    public String getFileName(){
        return fileName;
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void run(){
        File file = new File(fileName);
        Scanner in = null;
        try{
            in = new Scanner(file);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        while(in.hasNext()){
            String name = in.next();
            String category = in.next();
            Double price = in.nextDouble();
            Product p = new Product(name, category, price);
            synchronized (products){
                products.add(p);
            }
        }

        in.close();
    }

}
