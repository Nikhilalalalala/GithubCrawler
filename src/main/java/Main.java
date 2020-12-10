import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String URL = "https://github.com/trending?since=weekly%5D";
    // TODO: Replace with your path to chrome driver
    private static final String PATH_TO_CHROME_DRIVER = "C:\\Users\\ASUS\\chromedriver_win32\\chromedriver.exe\\";

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);

        GitHubCrawler crawler = new GitHubCrawler();

        crawler.goToUrl(URL);

        List<WebElement> repositories = crawler.getRepositoryOverviews();
        List<GithubRepoRecord> listOfRecords = new ArrayList<>();

        for (WebElement repoBox : repositories) {
            GithubRepoRecord record = crawler.getGithubRepoRecord(repoBox);
            listOfRecords.add(record);
        }

        saveGitHubRecords(listOfRecords);

        crawler.closeCrawler();

    }

    public static void saveGitHubRecords(List<GithubRepoRecord> records) {

        try {
            File file = new File("trending_repos.csv");
            PrintWriter writer = new PrintWriter(file);
            if (file.exists()) {
                writer.print("");
            }
            final String header = "Github repo name,Number of stars,Programming language";
            writer.print(header);
            writer.print("\n");
            for (int i = 0; i < records.size(); i++) {
                writer.print(records.get(i).toCsv());
                if (i < records.size() - 1) {
                    writer.print("\n");
                }
            }

            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }


    }
}
