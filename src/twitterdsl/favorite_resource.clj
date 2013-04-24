(ns twitterdsl.favorite-resource
  (:use [twitterdsl.core
         :only [is-instance?
                is-paging?
                user-validator]])
  (:import [twitter4j Paging]))


(defn instance-id? 
  [twitter id]
  (and
   (is-instance? twitter)
   (pos? id)))

(defn create-favorite 
  [twitter id]
  {:pre [(instance-id? twitter id)]}
  (.. twitter
      (createFavorite id)))

(defn destroy-favorite
  [twitter id]
  {:pre [(instance-id? twitter id)]}
  (.. twitter
      (destroyFavorite id)))

(defn get-favorites
  ([twitter]
     {:pre [(is-instance? twitter)]}
     (.getFavorites twitter))
  ([twitter userid-name-or-paging]
     {:pre [(is-instance? twitter)
            (true?
             (or (is-paging? userid-name-or-paging)
                 (user-validator userid-name-or-paging)))]}
     (.. twitter
         (getFavorites userid-name-or-paging)))
  ([twitter userid-or-name paging]
     {:pre [(is-instance? twitter)
            (is-paging? paging)
            (user-validator userid-or-name)]}
     (.. twitter
         (getFavorites userid-or-name paging))))
