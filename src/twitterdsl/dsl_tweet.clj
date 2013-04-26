(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-tweet)

(def ^:dynamic *status*)

(defn parse [twitter-status]
  (let [_ twitter-status]
    {:contributors (.getContributors _)
     :created-date (.getCreatedAt _)
     :current-user-retweet-id (.getCurrentUserRetweetId _)
     :geo-location (.getGeoLocation _)
     :tweet-id (.getId _)
     :in-reply-to-screen-name (.getInReplyToScreenName _)
     :in-reply-to-status-id (.getInReplyToStatusId _)
     :in-reply-to-user-id (.getInReplyToUserId _)
     :place (.getPlace _)
     :retweet-count (.getRetweetCount _)
     :retweet-status (.getRetweetedStatus _)
     :source (.getSource _)
     :text (.getText _)
     :user (.getUser _)
     :favorited? (.isFavorited _)
     :sensitive? (.isPossiblySensitive _)
     :retweet? (.isRetweet _)
     :retweeted-by-me? (.isRetweetedByMe _)
     :truncated? (.isTruncated _)}))

(defmacro with [parse-results & body]
  `(binding [*status* ~parse-results]
     ~@body))

(defn contributors []
  (:contributors *status*))

(defn created-date []
  (:created-date *status*))

(defn current-user-retweet-id []
  (:current-user-retweet-id *status*))

(defn geo-location []
  (:geo-location *status*))

(defn tweet-id []
  (:tweet-id *status*))

(defn reply-to-screen-name []
  (:in-reply-to-screen-name *status*))

(defn reply-to-status-id []
  (:in-reply-to-status-id *status*))

(defn reply-to-user-id []
  (:in-reply-to-user-id *status*))

(defn place []
  (:place *status*))

(defn retweet-count []
  (:retweet-count *status*))

(defn retweet-status []
  (:retweet-status *status*))

(defn source []
  (:source *status*))

(defn text []
  (:text *status*))

(defn user []
  (:user *status*))

(defn favorited? []
  (:favorited? *status*))

(defn sensitive? []
  (:sensitive? *status*))

(defn retweet? []
  (:retweet? *status*))

(defn retweeted-by-me? []
  (:retweeted-by-me? *status*))

(defn truncated? []
  (:truncated? *status*))
