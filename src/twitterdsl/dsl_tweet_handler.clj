(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-tweet-handler)

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

(defn get-contributors []
  (:contributors *status*))

(defn get-created-date []
  (:created-date *status*))

(defn get-current-user-retweet-id []
  (:current-user-retweet-id *status*))

(defn get-geo-location []
  (:geo-location *status*))

(defn get-tweet-id []
  (:tweet-id *status*))

(defn get-reply-to-screen-name []
  (:in-reply-to-screen-name *status*))

(defn get-reply-to-status-id []
  (:in-reply-to-status-id *status*))

(defn get-reply-to-user-id []
  (:in-reply-to-user-id *status*))

(defn get-place []
  (:place *status*))

(defn get-retweet-count []
  (:retweet-count *status*))

(defn get-retweet-status []
  (:retweet-status *status*))

(defn get-source []
  (:source *status*))

(defn get-text []
  (:text *status*))

(defn get-user []
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
