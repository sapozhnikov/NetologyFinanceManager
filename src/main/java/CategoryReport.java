public class CategoryReport {
    public CategoryReport(String category, Integer sum) {
        this.category = category;
        this.sum = sum;
    }

    private String category;
    private Integer sum;

    public String getCategory() {
        return category;
    }

    public Integer getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "\tcategory='" + category + '\n' +
                "\tsum=" + sum + '\n';
    }
}
