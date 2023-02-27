package twitter4j;

import twitter4j.conf.Configuration;

import java.util.Arrays;
import java.util.Date;

/*package*/ final class EditHistoryTweetJSONImpl extends TwitterResponseImpl implements EditHistoryTweet, java.io.Serializable {
    private static final long serialVersionUID = -332859162850649553L;
    private long id;
    private String text;

    /*package*/EditHistoryTweetJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    /*package*/EditHistoryTweetJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    private void init(JSONObject json) throws TwitterException {
        id    = ParseUtil.getLong("id", json);
        text  = ParseUtil.getUnescapedString("text", json);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public java.util.Date getCreatedAt() {
        return null;
    }

    /*package*/
    static ResponseList<EditHistoryTweet> createEditHistoryTweetList(HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray list = res.asJSONArray();
            int size = list.length();
            ResponseList<EditHistoryTweet> editHistoryTweets = new ResponseListImpl<EditHistoryTweet>(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                EditHistoryTweet editHistoryTweet = new EditHistoryTweetJSONImpl(json);
                editHistoryTweets.add(editHistoryTweet);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(editHistoryTweet, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(editHistoryTweets, list);
            }
            return editHistoryTweets;
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj instanceof EditHistoryTweet && ((EditHistoryTweet) obj).getId() == this.id;
    }

    @Override
    public String toString() {
        return "EditHistoryTweetJSONImpl{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public twitter4j.UserMentionEntity[] getUserMentionEntities() {
        return new twitter4j.UserMentionEntity[0];
    }

    @Override
    public twitter4j.URLEntity[] getURLEntities() {
        return new twitter4j.URLEntity[0];
    }

    @Override
    public twitter4j.HashtagEntity[] getHashtagEntities() {
        return new twitter4j.HashtagEntity[0];
    }

    @Override
    public twitter4j.MediaEntity[] getMediaEntities() {
        return new twitter4j.MediaEntity[0];
    }

    @Override
    public twitter4j.SymbolEntity[] getSymbolEntities() {
        return new twitter4j.SymbolEntity[0];
    }
}
