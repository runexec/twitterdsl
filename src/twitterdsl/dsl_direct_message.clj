(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-direct-message
  (:refer-clojure :exclude [send]))

(defn destroy [id]
  (twitterdsl.dsl/destroy-direct-message id))

(defn messages [& paging]
  (let [_ twitterdsl.dsl/get-direct-messages]
    (if paging (_ paging) (_))))

(defn sent-messages [& paing]
  (let [_ twitterdsl.dsl/get-sent-direct-messages]
    (if paing (_ paing) (_))))

(defn send [username-or-id msg]
  (twitterdsl.dsl/send-direct-message
   username-or-id
   msg))

(defn show [id]
  (twitterdsl.dsl/show-direct-message id))

(defn parse [dm]
  (let [_ dm]
    {:created-date (.getCreatedAt _)
     :id (.getId _)
     :recipient (.getRecipient _)
     :recipient-id (.getRecipientId _)
     :recipient-screen-name (.getRecipientScreenName _)
     :sender (.getSender _)
     :sender-id (.getSenderId _)
     :sender-screen-name (.getSenderScreenName _)
     :message (.getText _)}))
     
(def ^:dynamic *dm*)

(defmacro with [parse-result & body]
  `(binding [*dm* parse-result]
     ~@body))

(defn created-date []
  (:created-date *dm*))

(defn id []
  (:id *dm*))

(defn recipient []
  (:recipient *dm*))

(defn recipient-id []
  (:recipient-id *dm*))

(defn recipient-screen-name []
  (:recipient-screen-name *dm*))

(defn sender []
  (:sender *dm*))

(defn sender-id []
  (:sender-id *dm*))

(defn sender-screen-name []
  (:sender-screen-name *dm*))

(defn message []
  (:message *dm*))
