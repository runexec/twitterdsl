(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-friendship
  (:refer-clojure :exclude [name]))

(def ^:dynamic *friendship*)

(defmacro with [parse-result & body]
  `(binding [*friendship* ~parse-result]
     ~@body))

(defn parse [friendship]
  (let [_ friendship]
    {:id (.getId _)
     :name (.getName _)
     :screen-name (.getScreenName _)
     :followed-by? (.isFollowedBy _)
     :following? (.isFollowing _)}))

(defn id []
  (:id *friendship*))

(defn name []
  (:name *friendship*))

(defn screen-name []
  (:screen-name *friendship*))

(defn followed-by? []
  (:followed-by? *friendship*))

(defn following? []
  (:following? *friendship*))
