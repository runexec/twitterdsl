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

(defn instance []
  (:search-instance *query-results*))

(defn next-page-instance []
  (:next-page-instance *query-results*))

(defn access-level []
  (:access-level *query-results*))

(defn rate-limit-status []
  (:rate-limit-status *query-results*))

(defn complete-time []
  (:complete-time *query-results*))

(defn max-id []
  (:max-id *query-results*))

(defn since-id []
  (:search-instance *query-results*))

(defn has-next? []
  (:how-many? *query-results*))

(defn has-many? []
  (:how-many? *query-results*))

(defn refresh-url []
  (:refresh-url *query-results*))

(defn since-id []
  (:since-id *query-results*))

(defn tweets []
  (:tweets *query-results*))
