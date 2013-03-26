(ns twitterdsl.direct-message-resource
  (:use [twitterdsl.core 
         :only [is-instance?
                is-paging?]]))

(defn paging-validator
  [twitter paging]
  (and (is-instance? twitter)
       (if-not paging
         true
         (is-paging? paging))))

(defn destroy-direct-message
  [twitter id]
  {:pre [(is-instance? twitter)
         (pos? id)]}
  (.. twitter
      (destroyDirectMessage id)))

(defn get-direct-messages 
  [twitter & [paging]]
  {:pre [(paging-validator twitter
                           paging)]}
  (if-not paging
    (.getDirectMessages twitter)
    (.. twitter 
        (getDirectMessages paging))))

(defn get-sent-direct-messages
  [twitter & [paging]]
  {:pre [(paging-validator twitter
                           paging)]}
  (if-not paging
    (.getSentDirectMessages twitter)
    (.. twitter
        (getSentDirectMessages paging))))

(defn send-direct-message
  [twitter userid-or-name txt]
  {:pre [(is-instance? twitter)
         (<= (count txt) 120)
         (or (string? userid-or-name)
             (pos? userid-or-name))]}
  (.. twitter
      (sendDirectMessage userid-or-name
                         txt)))
(defn show-direct-message
  [twitter id]
  {:pre [(is-instance? twitter)
         (pos? id)]}
  (.. twitter
      (showDirectMessage id)))
