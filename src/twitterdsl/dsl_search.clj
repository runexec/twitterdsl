(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-search)

(defmacro search-for
  [search]
  (let [search-instance (twitterdsl.dsl/search search)
        _ search-instance]
    {:search-instance _
     :tweets (.getTweets _)
     :has-next? (.hasNext _)
     :how-many? (.getCount _)
     :refresh-url (.getRefreshURL _)}))
     
