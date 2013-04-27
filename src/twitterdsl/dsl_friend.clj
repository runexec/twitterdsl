(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-friend)

(defn add [userid-or-name]
  (twitterdsl.dsl/create-friendship
   userid-or-name
   true))

(defn delete [userid-or-name]
  (twitterdsl.dsl/destroy-friendship
   userid-or-name))

(let [_ twitterdsl.dsl/get-followers-ids]
  (defn follower-ids
    ([cursor] (_ cursor))
    ([userid-or-name cursor] (_ userid-or-name cursor))))

(defn follower-list
  [userid-or-name cursor]
  (twitterdsl.dsl/get-followers-list
   userid-or-name
   cursor))


(defn my-follower-ids [cursor]
  (twitterdsl.dsl/get-friends-ids cursor))

(defn follower-ids-of
  [userid-or-name cursor]
  (twitterdsl.dsl/get-friends-ids
   userid-or-name
   cursor))

(defn follower-list-of
  [userid-or-name cursor]
  (twitterdsl.dsl/get-friends-list
   userid-or-name
   cursor))

(defn incoming [cursor]
  (twitterdsl.dsl/get-incoming-friendships
   cursor))

(defn lookup [coll-userids-or-names]
  (twitterdsl.dsl/lookup-friendships
   coll-userids-or-names))

(defn show-friendship
  [sourceid-or-name targetid-or-name]
  (twitterdsl.dsl/show-friendship
   sourceid-or-name
   targetid-or-name))

(defn update-friendship
  [userid-or-name
   enable-device-notify?
   retweets?]
  (twitterdsl.dsl/update-friendship
   userid-or-name
   enable-device-notify?
   retweets?))


