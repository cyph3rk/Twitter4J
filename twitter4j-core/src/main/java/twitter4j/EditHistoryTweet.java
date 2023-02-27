package twitter4j;

public interface EditHistoryTweet extends TwitterResponse, EntitySupport, java.io.Serializable {

    long getId();

    String getText();

    java.util.Date getCreatedAt();

}
