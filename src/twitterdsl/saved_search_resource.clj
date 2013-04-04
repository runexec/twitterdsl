(ns twitterdsl.saved-search-resource
  (:use [twitterdsl.core
         :only [is-instance?]]))

(defn create-saved-search
  [twitter txt]
  {:pre [(is-instance? twitter)
         (string? txt)]}
  (.. twitter
      (createSavedSearch txt)))

(defn destroy-saved-search
  [twitter id]
  {:pre [(is-instance? twitter)
         (pos? id)]}
  (.. twitter
      (destroySavedSearch id)))

(defn get-saved-searches [twitter]
  (.getSavedSearches twitter))

(defn show-saved-search 
  [twitter id]
  {:pre [(is-instance? twitter)
         (pos? id)]}
  (.. twitter
      (showSavedSearch id)))
