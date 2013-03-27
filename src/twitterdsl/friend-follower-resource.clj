(ns twitterdsl.friend-follow-resource
  (:use [twitterdsl.core 
         :only [is-instance?]]))

(defn user-validator [userid-or-name]
  (true?
   (or (string? userid-or-name)
       (pos? userid-or-name))))

(defn cursor-validator 
  [twitter userid-or-name cursor]
  (and (is-instance? twitter)
       (user-validator userid-or-name)
       (pos? cursor)))

(defn create-friendship
  [twitter userid-or-name & [follow?]]
  {:pre [(is-instance? twitter)
         (user-validator userid-or-name)]}
  (if-not (boolean follow?)
    (.. twitter
        (create-friendship userid-or-name))
    (.. twitter
        (create-friendship userid-or-name
                           true))))

(defn destroy-friendship
  [twitter userid-or-name]
  {:pre [(is-instance? twitter)
         (user-validator userid-or-name)]}
  (.. twitter
      (destroyFriendship userid-or-name)))

(defn get-followers-ids
  ([twitter cursor]
     {:pre [(is-instance? twitter)
            (pos? cursor)]}
     (.. twitter
         (getFollowersIDs cursor)))
  ([twitter userid-or-name cursor]
     {:pre [(cursor-validator twitter
                              userid-or-name
                              cursor)]}
      (.. twitter
          (getFollowersIDs userid-or-name
                           cursor))))

(defn get-followers-list
  [twitter userid-or-name cursor]
  {:pre [(cursor-validator twitter
                           userid-or-name
                           cursor)]}
  (.. twitter
      (getFollowersList userid-or-name
                        cursor)))

(defn get-friends-ids
  ([twitter cursor]
     {:pre [(is-instance? twitter)
            (pos? cursor)]}
     (.. twitter
         (getFriendsIDs cursor)))
  ([twitter userid-or-name cursor]
     {:pre [(cursor-validator twitter
                              userid-or-name
                              cursor)]}
     (.. twitter
         (getFriendsIDs userid-or-name
                        cursor))))

(defn get-friends-list
  [twitter userid-or-name cursor]
  {:pre [(cursor-validator twitter
                           userid-or-name
                           cursor)]}
  (.. twitter
      (getFriendsList userid-or-name
                      cursor)))

(defn get-incoming-friendships
  [twitter cursor]
  {:pre [(is-instance? twitter)
         (pos? cursor)]}
  (.. twitter
      (getIncomingFriendships cursor)))

(defn lookup-friendships
  [twitter coll-ids-or-names]
  {:pre [(is-instance? twitter)
         (if-not (coll? coll-ids-or-names)
           false
           (or (every? string? coll-ids-or-names)
               (every? pos? coll-ids-or-names)))]}
  (.. twitter
      (lookupFriendships coll-ids-or-names)))

(defn show-friendship
  [twitter sourceid-or-name targetid-or-name]
  {:pre [(is-instance? twitter)
         (true?
          (or (every? string? [sourceid-or-name
                               targetid-or-name])
              (every? pos? [sourceid-or-name
                            targetid-or-name])))]}
  (.. twitter
      (showFriendship sourceid-or-name
                      targetid-or-name)))

(defn update-friendship
  [twitter 
   userid-or-name
   enable-device-notify?
   retweets?]
  {:pre [(is-instance? twitter)
         (user-validator userid-or-name)]}
  (.. twitter
      (updateFriendship userid-or-name
                        (boolean enable-device-notify?)
                        (boolean retweets?))))
  
  
         
