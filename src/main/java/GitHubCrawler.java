import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class GitHubCrawler {

    private static final String CLASS_OF_REPOSITORY_BOXES = "Box-row";
    private static final String CLASS_OF_STAR_COUNT = "js-social-count";
    private static final String DIV_TAG = "div";
    private static final String SPAN_TAG = "span";
    private static final String H1_TAG = "h1";
    private static final String A_TAG = "a";
    private static final String HREF_ATTRIBUTE = "href";
    private WebDriver primaryDriver;
    private WebDriver secondaryDriver;


    GitHubCrawler() {
        primaryDriver = new ChromeDriver();
        secondaryDriver = new ChromeDriver();

        // Maximize browser window
        primaryDriver.manage().window().maximize();
        secondaryDriver.manage().window().maximize();
    }

    public void goToUrl(String url) {
        primaryDriver.get(url);
    }

    public void closeCrawler() {
        primaryDriver.quit();
        secondaryDriver.quit();
    }

    public List<WebElement> getRepositoryOverviews() {
        return primaryDriver.findElements(By.className(CLASS_OF_REPOSITORY_BOXES));
    }

    public GithubRepoRecord getGithubRepoRecord(WebElement repositoryOverview) {

        // Get repository name
        WebElement repoHeader = repositoryOverview.findElement(By.tagName(H1_TAG));
        String repoName = repoHeader.getText().split("/")[1].strip();

        // Get num of stars of repository
        String linkOfRepo = repoHeader.findElement(By.tagName(A_TAG)).getAttribute(HREF_ATTRIBUTE);
        secondaryDriver.get(linkOfRepo);

        String numOfStars = secondaryDriver.findElements(By.className(CLASS_OF_STAR_COUNT)).get(0).getText();

        // Get language used by repository
        WebElement repoLanguageBox = repositoryOverview.findElements(By.tagName(DIV_TAG)).get(1);
        WebElement repoLanguageText = repoLanguageBox.findElements(By.tagName(SPAN_TAG)).get(0);
        String repoLanguage = repoLanguageText.getText();

        return new GithubRepoRecord(repoName, repoLanguage, numOfStars);

    }
}
