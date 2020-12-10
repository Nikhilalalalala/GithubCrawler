public class GithubRepoRecord {

    private String repoName;
    private String programmingLanguage;
    private long numOfStars;

    GithubRepoRecord(String repoName, String programmingLanguage, String numOfStars) {
        this.repoName = repoName;
        this.programmingLanguage = programmingLanguage;
        this.numOfStars = parseNumOfStars(numOfStars);
    }

    public static long parseNumOfStars(String numStars) {
        try {
            numStars = numStars.strip();
            int length = numStars.length();
            if (numStars.charAt(length - 1) == 'k') {
                double numberShown = Double.parseDouble(numStars.substring(0, length - 1));
                return Math.round(numberShown * 1000);
            } else {
                return Integer.parseInt(numStars);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Github stars should be integer");
        }
    }

    @Override
    public String toString() {
        return "repoName='" + repoName + '\'' +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                ", numOfStars=" + numOfStars +
                '\n';
    }

    public String toCsv() {
        return repoName + "," + numOfStars + "," + programmingLanguage;
    }
}
