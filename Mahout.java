import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import java.io.File;
import java.util.*;
public class Mahout {
    public static void main(String[] args) {
        try {
            DataModel model = new FileDataModel(new File("data.csv"));
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            Map<Long, String> productMap = new HashMap<>();
            productMap.put(101L, "Laptop");
            productMap.put(102L, "Mobile");
            productMap.put(103L, "Headphones");
            productMap.put(104L, "Tablet");
            productMap.put(105L, "Smartwatch");
            productMap.put(106L, "Camera");
            productMap.put(107L, "Speaker");
            productMap.put(108L, "Printer");
            for (long userId = 1; userId <= 5; userId++) {
                List<RecommendedItem> recommendations = recommender.recommend(userId, 3);
                System.out.println("\nAI-Based Product Recommendations for User " + userId + ":\n");
                if (recommendations.isEmpty()) {
                    System.out.println("No recommendations available.");
                } else {
                    for (RecommendedItem item : recommendations) {
                        long itemId = item.getItemID();
                        String productName = productMap.getOrDefault(itemId, "Unknown Product");
                        System.out.println("Recommended Product: " + productName +
                                " (Score: " + item.getValue() + ")");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
