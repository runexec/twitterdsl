(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-direct-message
  (:refer-clojure :exclude [send]))

(defn destroy [id]
  (twitterdsl.dsl/destroy-direct-message id))

(defn get-messages [& paging]
  (let [_ twitterdsl.dsl/get-direct-messages]
    (if paging (_ paging) (_))))

(defn get-sent-messages [& paing]
  (let [_ twitterdsl.dsl/get-sent-direct-messages]
    (if paing (_ paing) (_))))

(defn send [username-or-id msg]
  (twitterdsl.dsl/send-direct-message
   username-or-id
   msg))

(defn show [id]
  (twitterdsl.dsl/show-direct-message id))
