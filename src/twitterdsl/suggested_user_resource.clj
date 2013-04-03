(ns twitterdsl.suggested-user-resource
  (:use [twitterdsl.core
         :only [is-instance?]]))

(defn get-member-suggestions
  [twitter category-slug]
  {:pre [(is-instance? twitter)
         (string? category-slug)]}
  (.. twitter
      (getMemeberSuggestions category-slug)))

(defn get-suggested-user-categories [twitter]
  {:pre [(is-instance? twitter)]}
  (.getSuggestedUserCategories twitter))

(defn get-user-suggestions
  [twitter category-slug]
  {:pre [(is-instance? twitter)
         (string? category-slug)]}
  (.. twitter
      (getUserSuggestions category-slug)))
