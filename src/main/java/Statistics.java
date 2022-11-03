import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Statistics {
    final private String categoriesFileName = "categories.tsv";
    final private String saveFileName = "data.bin";
    private Map<String, String> itemsForPurchase; // item name, category
    private Collection<String> allCategories;
    private ArrayList<PurchaseStat> purchases;
    protected void ParseCategories() throws IOException {
        itemsForPurchase = new HashMap<>();

        try(BufferedReader bufReader = new BufferedReader(new FileReader(categoriesFileName))){
            String line;
            while ((line = bufReader.readLine()) != null)
            {
                String[] words = line.split("\t");
                itemsForPurchase.put(words[0], words[1]);
            }
        }
        itemsForPurchase.put("другое", "другое");
        allCategories = itemsForPurchase.values().stream().distinct().collect(Collectors.toList());
    }

    private void SaveStatistics(){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(saveFileName));){
            if (purchases != null) {
                objectOutputStream.writeObject(purchases);
            }
        } catch (IOException e) {
            System.out.println("Can't save statistics");
        }
    }

    private void LoadStatistics(){
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(saveFileName));){
            purchases = (ArrayList<PurchaseStat>)objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous saved statistics found");
        } catch (IOException e) {
            System.out.println("Can't load statistics");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (purchases == null){
            purchases = new ArrayList<>();
        }
    }

    public Statistics() throws IOException {
        ParseCategories();
        LoadStatistics();
    }

    CategoryReport getMaxCategoryReportForPeriod(String date) {
        Map<String, Integer> spendByCategory = purchases.stream()
                .filter(p -> date == null || p.getDate().startsWith(date))
                .collect(Collectors.groupingBy(PurchaseStat::getCategory, Collectors.summingInt(PurchaseStat::getSum)));
        //find key with max value
        Map.Entry<String, Integer> spendMax = null;
        for (Map.Entry<String, Integer> spend : spendByCategory.entrySet()) {
            if (spendMax == null) {
                spendMax = spend;
            } else {
                if (spendMax.getValue() < spend.getValue()) {
                    spendMax = spend;
                }
            }
        }

        return spendMax == null ? null : new CategoryReport(spendMax.getKey(), spendMax.getValue());
    }

    private PurchaseStat purchaseToStat(Purchase p){
        String category = itemsForPurchase.get(p.getTitle());
        if (category == null) {
            category = "другое";
        }

        return new PurchaseStat(category, p.getDate(), p.getSum());
    }

    public Report CommitPurchase(Purchase purchase){
        if (purchase == null){
            return null;
        }

        if (!Pattern.matches("^\\d{4}[.]\\d{2}[.]\\d{2}$", purchase.getDate())){ //YYYY.MM.DD
            return null;
        }

        purchases.add(purchaseToStat(purchase)); //TODO optimize

        CategoryReport maxCategory = getMaxCategoryReportForPeriod(null);
        CategoryReport maxYearCategory = getMaxCategoryReportForPeriod(purchase.getDate().substring(0, 4));
        CategoryReport maxMonthCategory = getMaxCategoryReportForPeriod(purchase.getDate().substring(0, 7));
        CategoryReport maxDayCategory = getMaxCategoryReportForPeriod(purchase.getDate());

        Report report = new Report(maxCategory, maxYearCategory, maxMonthCategory, maxDayCategory);

        SaveStatistics();
        return report;
    }
}
