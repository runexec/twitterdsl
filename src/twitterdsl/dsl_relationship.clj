(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-relationship)

(def ^:dynamic *relationship*)

(defmacro with [parse-result & body]
  `(binding [*relationship* ~parse-result]
     ~@body))

(defn parse [relationship]
  (let [_ relationship]
    {:source-id (.getSourceUserId _)
     :source-screen-name (.getSourceUserScreenName _)
     :target-userid (.getTargetUserId _)
     :target-screen-name (.getTargetUserScreenName _)
     :source-blocking-target? (.isSourceBlockingTarget _)
     :source-followed-by-target? (.isSourceFollowedByTarget _)
     :source-following-target? (.isSourceFollowingTarget _)
     :source-notification-enabled? (.isSourceNotificationsEnabled _)
     :source-wants-retweets? (.isSourceWantRetweets _)
     :target-followed-by-source? (.isTargetFollowedBySource _)
     :target-following-source? (.isTargetFollowingSource _)}))

(defn source-id []
  (:source-id  *relationship*))

(defn source-user-screen-name []
  (:source-screen-name *relationship*))

(defn target-userid []
  (:target-userid *relationship*))

(defn target-screen-name []
  (:target-screen-name *relationship*))

(defn source-blocking-target? []
  (:source-blocking-target? *relationship*))

(defn source-followed-by-target? []
  (:source-followed-by-target? *relationship*))

(defn source-following-target? []
  (:source-following-target? *relationship*))

(defn source-notification-enabled? []
  (:source-notification-enabled? *relationship*))

(defn source-wants-retweets? []
  (:source-wants-retweets? *relationship*))

(defn target-followed-by-source? []
  (:target-followed-by-source? *relationship*))

(defn target-following-source? []
  (:target-following-source? *relationship*))
