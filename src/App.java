import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import feed.FeedParser;
import feed.FeedSelect;
import namedEntities.NamedEntiy;
import feed.Article;
import utils.Config;
import utils.DictionaryData;
import utils.FeedsData;
import utils.JSONParser;
import utils.UserInterface;
import java.net.MalformedURLException;
import utils.Stats;
import utils.HandleHeuristic;
import java.lang.String;
public class App {

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try { 
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);

        run(config, feedsDataArray);
    }

    // TODO: Change the signature of this function if needed
    private static void run(Config config, List<FeedsData> feedsDataArray) {

        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            return;
        }
        if (config.getPrinthelp()) {
            printHelp(feedsDataArray);
            return; 
        }


        //-------------------------Dictionary Data-----------------------------------------
        List<DictionaryData> dataDict = new ArrayList<>();
        List<NamedEntiy> namedEnt = new ArrayList<>(); 
        try {
            dataDict = JSONParser.parseJsonDictionaryData("src/data/dictionary.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error parsing dictionary data");
        }

        //----------------------------------------------------------------------------------
        try {
            // ---------------------------feedSelect----------------------------------------
            FeedSelect feedSelect = new FeedSelect(); 
            List<Article> allArticles = new ArrayList<>(); 
            String fetchedUrl = "";
            feedSelect.pString(config.getFeedKey());
            if (!(null == (feedSelect.gString()))) { 
                allArticles = feedSelect.selectOne(config.getFeedKey(), feedsDataArray);       
                fetchedUrl = feedSelect.getfetchedUrl();
            }
            else {
                allArticles = FeedParser.parseXML(FeedParser.fetchFeed(feedsDataArray.get(0).getUrl()));
                fetchedUrl =FeedParser.fetchFeed(feedsDataArray.get(0).getUrl());
            }
                // TODO: Populate allArticles with articles from corresponding feed
            if (config.getPrintFeed()) {
                feedSelect.printFeed(allArticles, feedsDataArray);
            }
            // -----------------------------------------------------------------------------
            Article article = new Article();
            String articlesToString= article.toText(allArticles); 

            if (config.getComputeNamedEntities()) {
                // TODO: complete the message with the selected heuristic name
                String heuristicName = config.getHeuristic();
                System.out.println("Computing named entities using " + heuristicName + " heuristic");

                // TODO: compute named entities using the selected heuristic
                HandleHeuristic handleHeuristic = new HandleHeuristic();
                namedEnt = handleHeuristic.handleString(heuristicName, fetchedUrl, dataDict, articlesToString);
             
                System.out.println("Named Entities: ");
                for(NamedEntiy entity : namedEnt){
                    System.out.println(entity.getCategory() + entity.getTopics()+ entity.getName());
                }
            }
        
        if(config.getStats().getPrintStats()){
         // TODO: Print stats
            System.out.println("\nStats: ");
            config.getStats().count(namedEnt);
            config.getStats().printStats(config.getStats().getCount());
        }
    
    } catch (MalformedURLException e) {
        e.printStackTrace();
        System.out.println("Malformed URL");
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("IO Exception");
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Exception");
        } 
    }
    

    private static void printHelp(List<FeedsData> feedsDataArray) {
        System.out.println("Usage: make run ARGS=\"[OPTION]\"");
        System.out.println("Options:");
        System.out.println("  -h, --help: Show this help message and exit");
        System.out.println("  -f, --feed <feedKey>:                Fetch and process the feed with");
        System.out.println("                                       the specified key");
        System.out.println("                                       Available feed keys are: ");
        for (FeedsData feedData : feedsDataArray) {
            System.out.println("                                       " + feedData.getLabel());
        }
        System.out.println("  -ne, --named-entity <heuristicName>: Use the specified heuristic to extract");
        System.out.println("                                       named entities");
        System.out.println("                                       Available heuristic names are: ");
        // TODO: Print the available heuristics with the following format
        System.out.println("                                       CapitalizedWordHeuristic: captures capitalized words ");
        System.out.println("                                       FiltredCwh: captures capitalized words that are not in the stop words list");
        System.out.println("  -pf, --print-feed:                   Print the fetched feed");
        System.out.println("  -sf, --stats-format <format>:        Print the stats in the specified format");
        System.out.println("                                       Available formats are: ");
        System.out.println("                                       cat: Category-wise stats");
        System.out.println("                                       topic: Topic-wise stats");
        System.out.println("Example: Make run ARGS=\"-f <feedKey> -ne <heuristicName> -pf -sf <format>\"");
    }

}
