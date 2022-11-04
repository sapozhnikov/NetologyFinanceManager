public class Report {

    private CategoryReport maxCategory;
    private CategoryReport maxYearCategory;
    private CategoryReport maxMonthCategory;
    private CategoryReport maxDayCategory;

    public void setMaxCategory(CategoryReport maxCategory) {
        this.maxCategory = maxCategory;
    }

    public void setMaxYearCategory(CategoryReport maxYearCategory) {
        this.maxYearCategory = maxYearCategory;
    }

    public void setMaxMonthCategory(CategoryReport maxMonthCategory) {
        this.maxMonthCategory = maxMonthCategory;
    }

    public void setMaxDayCategory(CategoryReport maxDayCategory) {
        this.maxDayCategory = maxDayCategory;
    }

    public CategoryReport getMaxCategory() {
        return maxCategory;
    }

    public CategoryReport getMaxYearCategory() {
        return maxYearCategory;
    }

    public CategoryReport getMaxMonthCategory() {
        return maxMonthCategory;
    }

    public CategoryReport getMaxDayCategory() {
        return maxDayCategory;
    }

    public Report(CategoryReport maxCategory, CategoryReport maxYearCategory, CategoryReport maxMonthCategory, CategoryReport maxDayCategory) {
        this.maxCategory = maxCategory;
        this.maxYearCategory = maxYearCategory;
        this.maxMonthCategory = maxMonthCategory;
        this.maxDayCategory = maxDayCategory;
    }

    @Override
    public String toString() {
        return "maxCategory\n" + maxCategory.toString() +
                "maxYearCategory\n" + maxYearCategory.toString() +
                "maxMonthCategory\n" + maxMonthCategory.toString() +
                "maxDayCategory\n" + maxDayCategory;
    }
}
