(ns twitterdsl.search-resource
  (:use [twitterdsl.core
         :only [is-instance?]])
  (:import [twitter4j 
            Query]))

(defn search
  [twitter txt]
  {:pre [(and (is-instance? twitter)
              (string? txt))]}
  (.. twitter
      (search
       (Query. txt))))
