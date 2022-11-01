import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Statistics {

    final private String categoriesFileName = "categories.tsv";
    final private String saveFileName = "data.bin";
    private Map<String, String> itemsForPurchase; // item name, category
    private ArrayList<Purchase> purchases;
    protected void ParseCategories() throws IOException {
        try(BufferedReader bufReader = new BufferedReader(new FileReader(categoriesFileName))){
            String line;
            while ((line = bufReader.readLine()) != null)
            {
                String[] words = line.split("\t");
                itemsForPurchase.put(words[0], words[1]);
            }
        }
    }

    private void SaveStatistics(){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(saveFileName));){
            objectOutputStream.writeObject(purchases);
        } catch (IOException e) {
            System.out.println("Can't save statistics");
        }
    }

    private void LoadStatistics(){
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(saveFileName));){
            purchases = (ArrayList<Purchase>)objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous saved statistics found");
        } catch (IOException e) {
            System.out.println("Can't load statistics");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Statistics() throws IOException {
        itemsForPurchase = new HashMap<>();
        ParseCategories();
        LoadStatistics();
    }

    public boolean CommitPurchase(Purchase purchase){
        if (purchase == null){
            return false;
        }

        if (!Pattern.matches("^\\d{4}[.]\\d{2}[.]\\d{2}$", purchase.getDate())){ //YYYY.MM.DD
            return false;
        }

        purchases.add(purchase); //TODO optimize
        SaveStatistics();
        return true;
    }

    public Report GetReport() {
        Report report = new Report();

        return report;
    }
}
