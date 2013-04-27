(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-timeline)

(defn tweets [& [paging]]
  (let [_ twitterdsl.dsl/get-home-timeline]
    (if paging (_ paging) (_))))

(defn mentions [& [paging]]
  (let [_ twitterdsl.dsl/get-mentions-timeline]
    (if paging (_ paging) (_))))

(defn retweets [& [paging]]
  (let [_ twitterdsl.dsl/get-retweets-of-me]
    (if paging (_ paging) (_))))

(let [_ twitterdsl.dsl/get-user-timeline]
  (defn user-timeline
    ([] (_))
    ([username-or-id] (_ username-or-id))
    ([username-or-id paging] (_ username-or-id paging))))
