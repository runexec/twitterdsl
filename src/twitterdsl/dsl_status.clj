(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-status)

(defn destroy [status-id]
  (twitterdsl.dsl/destroy-status status-id))

(defn get-oembed [oembed-req]
  (twitterdsl.dsl/get-oembed oembed-req))

(defn get-retweets [status-id]
  (twitterdsl.dsl/get-retweets status-id))

(defn retweet [status-id]
  (twitterdsl.dsl/retweet-status status-id))


(defn show [status-id]
  (twitterdsl.dsl/show-status status-id))

(defn tweet [msg]
  (twitterdsl.dsl/update-status msg))
