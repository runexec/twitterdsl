(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-favorite)

(defn mark [tweet-id]
  (twitterdsl.dsl/create-favorite tweet-id))

(defn unmark [tweet-id]
  (twitterdsl.dsl/destroy-favorite tweet-id))

(defn favorites
  ([] (twitterdsl.dsl/get-favorites))
  ([userid-or-name-or-paging]
     (twitterdsl.dsl/get-favorites userid-or-name-or-paging))
  ([userid-or-name paging]
     (twitterdsl.dsl/get-favorites userid-or-name
                                   paging)))
