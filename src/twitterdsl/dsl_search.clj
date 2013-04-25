(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-search)

(def ^:dynamic *query-results*)

(defn query [search]
  (let [search-instance (twitterdsl.dsl/search search)
        _ search-instance]
    {:search-instance _
     :next-page-instance (.nextQuery _)
     :access-level (.getAccessLevel _)
     :rate-limit-status (.getRateLimitStatus _)
     :complete-time (.getCompletedIn _)
     :max-id (.getMaxId _)
     :since-id (.getSinceId _)
     :tweets (.getTweets _)
     :has-next? (.hasNext _)
     :how-many? (.getCount _)
     :refresh-url (.getRefreshURL _)}))

(defmacro with [query-results & body]
  `(binding [*query-results* ~query-results]
     ~@body))

(defn get-instance []
  (:search-instance *query-results*))

(defn get-next-page-instance []
  (:next-page-instance *query-results*))

(defn get-access-level []
  (:access-level *query-results*))

(defn get-rate-limit-status []
  (:rate-limit-status *query-results*))

(defn get-complete-time []
  (:complete-time *query-results*))

(defn get-max-id []
  (:max-id *query-results*))

(defn get-since-id []
  (:search-instance *query-results*))

(defn has-next? []
  (:how-many? *query-results*))

(defn has-many? []
  (:how-many? *query-results*))

(defn get-refresh-url []
  (:refresh-url *query-results*))

(defn get-since-id []
  (:since-id *query-results*))

(defn get-tweets []
  (:tweets *query-results*))
