package com.techelevator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class App {

    // The regex string to split the Strings in the dataset.
    private static final String FIELD_DELIMITER = "\\|";

    private static final int TITLE_FIELD = 0;
    private static final int AUTHOR_FIELD = 1;
    private static final int PUBLISHED_YEAR_FIELD = 2;
    private static final int PRICE_FIELD = 3;

    private final Scanner keyboard = new Scanner(System.in);

    private List<String> titles = new ArrayList<>();
    private List<String> authors = new ArrayList<>();
    private List<Integer> publishedYears = new ArrayList<>();
    private List<BigDecimal> prices = new ArrayList<>();

    public static void main(String[] args) {

        App app = new App();
        app.loadData();
        app.run();

    }

    private void loadData() {

        String[] dataset = Dataset.load();
        /*
         Requirement: 1
         Populate the instance variables `titles`, `authors`, `publishedYears`,
         and `prices` by splitting each string in the `dataset` array and adding
         the individual fields to the appropriate list.
         See README for additional details.
         */

        for (int i = 0; i < dataset.length; i++) { // Loop through dataset and split elements at field delimiter "|"
            String[] splitData = dataset[i].split(FIELD_DELIMITER);
                // add fields to appropriate list by referencing their index
                titles.add(splitData[TITLE_FIELD]);
                authors.add(splitData[AUTHOR_FIELD]);
                publishedYears.add(Integer.valueOf(splitData[PUBLISHED_YEAR_FIELD]));
                BigDecimal price = new BigDecimal(splitData[PRICE_FIELD]);
                prices.add(price);
        }

    }

    private void run() {

        while (true) {
            // Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please choose an option: ");
            if (mainMenuSelection == 1) {
                // Display data and subsets loop
                while (true) {
                    printDataAndSubsetsMenu();
                    int dataAndSubsetsMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (dataAndSubsetsMenuSelection == 1) {
                        displayDataset(Dataset.load());
                    } else if (dataAndSubsetsMenuSelection == 2) {
                        displayTitlesList(titles);
                    } else if (dataAndSubsetsMenuSelection == 3) {
                        displayAuthorsList(authors);
                    } else if (dataAndSubsetsMenuSelection == 4) {
                        displayPublishedYearsList(publishedYears);
                    } else if (dataAndSubsetsMenuSelection == 5) {
                        displayPricesList(prices);
                    } else if (dataAndSubsetsMenuSelection == 0) {
                        break;
                    }
                }
            }
            else if (mainMenuSelection == 2) {
                while (true) {
                    printSearchBooksMenu();
                    int searchBooksMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (searchBooksMenuSelection == 1) {
                        // Search by title
                        String filterTitle = promptForString("Enter title: ");
                        /*
                         Requirement: 3b
                         Replace `displayTitlesList(titles)` with calls to the
                         `filterByTitle()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(filterByTitle(filterTitle));
                    } else if (searchBooksMenuSelection == 2) {
                        // Search by author
                        String filterAuthor = promptForString("Enter author: ");
                        /*
                         Requirement: 4b
                         Replace `displayAuthorsList(authors)` with calls to the
                         `filterByAuthor()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(filterByAuthor(filterAuthor));
                    } else if (searchBooksMenuSelection == 3) {
                        // Search by published year
                        int filterYear = promptForPublishedYear("Enter date (YYYY): ");
                        /*
                         Requirement: 5b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `filterByPublishedYear()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(filterByPublishedYear(filterYear));
                    } else if (searchBooksMenuSelection == 4) {
                        // Search by published year range
                        int filterFromYear = promptForPublishedYear("Enter \"from\" date (YYYY): ");
                        int filterToYear = promptForPublishedYear("Enter \"to\" date (YYYY): ");
                        /*
                         Requirement: 6b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `filterByPublishedYearRange()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(filterByPublishedYearRange(filterFromYear, filterToYear));
                    } else if (searchBooksMenuSelection == 5) {
                        // Find the most recent books
                        /*
                         Requirement: 7b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `findMostRecentBooks()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(findMostRecentBooks());
                    } else if (searchBooksMenuSelection == 6) {
                        // Search by price
                        double filterPrice = promptForPrice("Enter price: ");
                        /*
                         Requirement: 8b
                         Replace `displayPricesList(prices)` with calls to the
                         `filterByPrice()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(filterByPrice(filterPrice));
                    } else if (searchBooksMenuSelection == 7) {
                        // Search by price range
                        double filterFromPrice= promptForPrice("Enter \"from\" price: ");
                        double filterToPrice = promptForPrice("Enter \"to\" price: ");
                        /*
                         Requirement: 9b
                         Replace `displayPricesList(prices)` with calls to the
                         `filterByPriceRange()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(filterByPriceRange(filterFromPrice, filterToPrice));
                    } else if (searchBooksMenuSelection == 8) {
                        // Find the least expensive books
                        /*
                         Requirement: 10b
                         Replace `displayPricesList(prices)` with calls to the
                         `findLeastExpensiveBooks()` and `displaySearchResults()` methods.
                         */
                        displaySearchResults(findLeastExpensiveBooks());
                    } else if (searchBooksMenuSelection == 0) {
                        break;
                    }
                }
            } else if (mainMenuSelection == 0) {
                break;
            }
        }

    }

    /*
     Requirement: 2
     Write the displaySearchResults(List<Integer> indexes) method.
     See README for additional details.
     */
    private void displaySearchResults(List<Integer> indexes) {
        // Loop through each element in indexes list to return all matching search results
            for (Integer index : indexes) {
                System.out.println(titles.get(index) + ": " + authors.get(index) + ": " + publishedYears.get(index) + ": $" + prices.get(index));
            }
            System.out.println();

    }

    /*
     Requirement: 3a
     Complete the `filterByTitle()` method.
     See README for additional details.
     */
    private List<Integer> filterByTitle(String filterTitle) {
        List<Integer> titleIndexes = new ArrayList<>();
        Integer titleIndex = titles.indexOf(filterTitle);
        if (titles.contains(filterTitle)) {
            titleIndexes.add(titleIndex);
        }
        else {
            // Search for all occurances of title input ignoring case
            for (int i = 0; i < titles.size(); i++) {
                String title = titles.get(i);
                String upperTitle = title.toUpperCase();
                String lowerTitle = title.toLowerCase();
                if (title.contains(filterTitle) || upperTitle.contains(filterTitle) || lowerTitle.contains(filterTitle)) {
                    titleIndexes.add(titles.indexOf(title));
                }
                else {
                    // split the title to search through each individual word in the title
                    String[] splitTitle = title.split(" ");
                    for (int j = 0; j < splitTitle.length; j++) {
                        String lowerStr = splitTitle[j].toLowerCase();
                        String upperStr = splitTitle[j].toUpperCase();
                        if (splitTitle[j].contains(filterTitle) || lowerStr.contains(filterTitle) || upperStr.contains(filterTitle)) {
                            titleIndexes.add(titles.indexOf(title));
                        }
                    }
                }
            }
        }
        return titleIndexes;
    }

    /*
     Requirement: 4a
     Complete the `filterByAuthor()` method.
     See README for additional details.
     */
    private List<Integer> filterByAuthor(String filterAuthor) {
        List<Integer> authorIndexes = new ArrayList<>();
        Integer authorIndex = authors.indexOf(filterAuthor);
        if (authors.contains(filterAuthor)) {
            authorIndexes.add(authorIndex);
        }
        else {
            for (int i = 0; i < authors.size(); i++) {
                String author = authors.get(i);
                String upperAuthor = author.toUpperCase();
                String lowerAuthor = author.toLowerCase();
                if (author.contains(filterAuthor) || upperAuthor.contains(filterAuthor) || lowerAuthor.contains(filterAuthor)) {
                    authorIndexes.add(authors.indexOf(author));
                }
                else {
                    String[] splitAuthor = author.split(" ");
                    for (int j = 0; j < splitAuthor.length; j++) {
                        String lowerStr = splitAuthor[j].toLowerCase();
                        String upperStr = splitAuthor[j].toUpperCase();
                        if (splitAuthor[j].contains(filterAuthor) || lowerStr.contains(filterAuthor) || upperStr.contains(filterAuthor)) {
                            authorIndexes.add(authors.indexOf(author));
                        }
                    }
                }
            }
        }
        return authorIndexes;
    }

    /*
     Requirement: 5a
     Complete the `filterByPublishedYear()` method.
     See README for additional details.
     */
    private List<Integer> filterByPublishedYear(int filterYear) {
        // use IntStream to filter out objects within published years that do not equal filterYear input
        List<Integer> publishYears = IntStream.range(0, publishedYears.size())
                .filter(i -> Objects.equals(publishedYears.get(i), filterYear))
                .boxed().collect(Collectors.toList());


        return publishYears;
    }

    /*
     Requirement: 6a
     Complete the `filterByPublishedYearRange()` method.
     See README for additional details.
     */
    private List<Integer> filterByPublishedYearRange(int filterFromYear, int filterToYear) {
        List<Integer> years = IntStream.range(0, publishedYears.size())
                .filter(i -> publishedYears.get(i) >= filterFromYear && publishedYears.get(i) <= filterToYear)
                .boxed().collect(Collectors.toList());

        return years;
    }

    /*
     Requirement: 7a
     Add the `private List<Integer> findMostRecentBooks()` method.
     See README for additional details.
     */
    private List<Integer> findMostRecentBooks() {
        // loop through publishedYears list to determine most recent year.
        Integer mostRecentYear = 0;
        for (int i = 0; i < publishedYears.size(); i++) {
            if (publishedYears.get(i) >= mostRecentYear) {
                mostRecentYear = publishedYears.get(i);
            }
        }
        // Use IntStream to filter out objects in published years that do not equal the most year.
        Integer finalMostRecentYear = mostRecentYear;
        List<Integer> mostRecentYears = IntStream.range(0, publishedYears.size())
                .filter(i -> Objects.equals(publishedYears.get(i), finalMostRecentYear))
                .boxed().collect(Collectors.toList());
        return mostRecentYears;
    }

    /*
     Requirement: 8a
     Complete the `filterByPrice()` method.
     See README for additional details.
     */
    private List<Integer> filterByPrice(double filterPrice) {
        BigDecimal filtPrice = BigDecimal.valueOf(filterPrice);
        List<Integer> priceIndexes = IntStream.range(0, prices.size())
                .filter(i -> Objects.equals(prices.get(i), filtPrice))
                .boxed().collect(Collectors.toList());
        return priceIndexes;
    }

    /*
     Requirement: 9a
     Complete the `filterByPriceRange()` method.
     See README for additional details.
     */
    private List<Integer> filterByPriceRange(double filterFromPrice, double filterToPrice) {
        List<Integer> priceRanges = IntStream.range(0, prices.size())
                .filter(i -> prices.get(i).doubleValue() >= filterFromPrice && prices.get(i).doubleValue() <= filterToPrice)
                .boxed().collect(Collectors.toList());
        return priceRanges;
    }

    /*
     Requirement: 10a
     Add the `private List<Integer> findLeastExpensiveBooks()` method.
     See README for additional details.
     */
    private List<Integer> findLeastExpensiveBooks() {
        double leastExpensiveBook = prices.get(0).doubleValue();
        for (int i = 0; i < prices.size(); i++) {
                if (prices.get(i).doubleValue() <= leastExpensiveBook) {
                    leastExpensiveBook = prices.get(i).doubleValue();
            }
        }
        double finalLeastExpensiveBook = leastExpensiveBook;
        List<Integer> leastExpensiveBooks = IntStream.range(0, prices.size())
                .filter(i -> Objects.equals(prices.get(i).doubleValue(), finalLeastExpensiveBook))
                .boxed().collect(Collectors.toList());
        return leastExpensiveBooks;
    }


    // UI methods

    private void printMainMenu() {
        System.out.println("1: Display data and subsets");
        System.out.println("2: Search books");
        System.out.println("0: Exit");
        System.out.println();
    }

    private void printDataAndSubsetsMenu() {
        System.out.println("1: Display dataset");
        System.out.println("2: Display titles");
        System.out.println("3: Display authors");
        System.out.println("4: Display published years");
        System.out.println("5: Display prices");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void printSearchBooksMenu() {
        System.out.println("1: Search by title");
        System.out.println("2: Search by author");
        System.out.println("3: Search by published year");
        System.out.println("4: Search by published year range");
        System.out.println("5: Find most recent books");
        System.out.println("6: Search by price");
        System.out.println("7: Search by price range");
        System.out.println("8: Find least expensive books");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void displayDataset(String[] dataset) {
        System.out.println("Dataset");
        System.out.println("-------");
        for (String data : dataset) {
            System.out.println(data);
        }
        System.out.println();
        promptForReturn();
    }

    private void displayTitlesList(List<String> titles) {
        System.out.println("Titles");
        System.out.println("-------");
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + ": " + titles.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayAuthorsList(List<String> authors) {
        System.out.println("Authors");
        System.out.println("-------");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println(i + ": " + authors.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayPublishedYearsList(List<Integer> publishedYears) {
        System.out.println("Published Years");
        System.out.println("---------------");
        for (int i = 0; i < publishedYears.size(); i++) {
            System.out.println(i + ": " + publishedYears.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayPricesList(List<BigDecimal> prices) {
        System.out.println("Prices");
        System.out.println("------");
        for (int i = 0; i < prices.size(); i++) {
            System.out.println(i + ": " + prices.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private int promptForMenuSelection(String prompt) {
        System.out.print(prompt);
        int menuSelection;
        try {
            menuSelection = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private int promptForPublishedYear(String prompt) {
        int year;
        while (true) {
            System.out.println(prompt);
            try {
                year = Integer.parseInt(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The year provided is not well-formed. It must be YYYY.");
            }
        }
        return year;
    }

    private double promptForPrice(String prompt) {
        double price;
        while (true) {
            System.out.println(prompt);
            try {
                price = Double.parseDouble(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The price provided is not a valid monetary value.");
            }
        }
        return price;
    }

    private void promptForReturn() {
        System.out.println("Press RETURN to continue.");
        keyboard.nextLine();
    }
}
