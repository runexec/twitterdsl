(ns twitterdsl.user-resource
  (:use [twitterdsl.core
         :only [is-instance?
                user-validator]]
        [clojure.java.io
         :only [file]]))

(defn instance-user?
  [twitter user]
  (and (is-instance? twitter)
       (user-validator user)))

(defn instance-cursor?
  [twitter & [cursor]]
  (if cursor
    (and (is-instance? twitter)
         (pos? cursor))
    (is-instance? twitter)))

(defn intsance-image?
  [twitter image-path]
  (and (is-instance? twitter)
       (.exists (file image-path))))

(defn create-block 
  [twitter userid-or-name]
  {:pre [(instance-user? twitter userid-or-name)]}
  (.. twitter
      (createBlock userid-or-name)))

(defn destroy-block
  [twitter userid-or-name]
  {:pre [(instance-user? twitter userid-or-name)]}
  (.. twitter
      (destroyBlock userid-or-name)))

(defn get-account-settings [twitter]
  (.getAccountSettings twitter))

(defn get-blocks-ids 
  [twitter & [cursor]]
  {:pre [(instance-cursor? twitter cursor)]}
  (if-not cursor
    (.getBlocksIDs twitter)
    (.. twitter
        (getBlocksIDs cursor))))

(defn get-blocks-list
  [twitter & [cursor]]
  {:pre [(instance-cursor? twitter cursor)]}
  (if-not cursor
    (.getBlocksList twitter)
    (.. twitter
        (getBlocksList cursor))))

(defn get-contributees
  [twitter userid-or-name]
  {:pre [(instance-user? twitter userid-or-name)]}
  (.. twitter
      (getContributees userid-or-name)))

(defn get-contributors
  [twitter userid-or-name]
  {:pre [(instance-user? twitter userid-or-name)]}
  (.. twitter
      (getContributors userid-or-name)))

(defn lookup-users
  [twitter coll-userid-or-name]
  {:pre [(is-instance? twitter)
         (coll? coll-userid-or-name)
         (true?
          (or (every? string? coll-userid-or-name)
              (every? pos? coll-userid-or-name)))]}
  (.. twitter
      (lookupUsers coll-userid-or-name)))

(defn remove-profile-banner [twitter]
  {:pre [(is-instance? twitter)]}
  (.removeProfileBanner twitter))

(defn search-users 
  [twitter string-query int-page]
  {:pre [(is-instance? twitter)]}
  (.. twitter
      (searchUsers string-query int-page)))

(defn show-user
  [twitter userid-or-name]
  {:pre [(instance-user? twitter userid-or-name)]}
  (.. twitter
      (showUser userid-or-name)))

(defn update-account-settings
  [twitter
   trend-location-woe-id
   sleep-time-enabled?
   start-sleep-time
   end-sleep-time
   timezone
   language]
  {:pre [(is-instance? twitter)
         (pos? trend-location-woe-id)
         (every? string? 
                 [start-sleep-time
                  end-sleep-time
                  timezone
                  language])]}
  (.. twitter
      (updateAccountSettings
       trend-location-woe-id
       (boolean sleep-time-enabled?)
       start-sleep-time
       end-sleep-time
       timezone
       language)))

(defn update-profile
  [twitter name url location description]
  {:pre [(is-instance? twitter)
         (every? string?
                 [name url location description])]}
  (.. twitter
      (updateProfile name url location description)))

(defn update-profile-background-image
  [twitter image-path tile?]
  {:pre [(intsance-image? twitter 
                          image-path)]}
  (.. twitter
      (updateProfileBackgroundimage 
       (file image-path)
       (boolean tile?))))

(defn update-profile-banner
  [twitter image-path tile?]
  {:pre [(intsance-image? twitter 
                          image-path)]}
  (.. twitter
      (updateProfileBanner
       (file image-path)
       (boolean tile?))))

(defn update-profile-colors 
  [twitter
   bg-color
   txt-color 
   link-color
   sidebar-fill-color
   sidebar-border-color]
  {:pre [(is-instance? twitter)
         (every? string? 
                 [bg-color txt-color link-color 
                  sidebar-fill-color sidebar-border-color])]}
  (.. twitter
      (updateProfileColors 
       bg-color 
       txt-color 
       link-color 
       sidebar-fill-color 
       sidebar-border-color)))

(defn update-profile-image 
  [twitter image-path]
  {:pre [(is-instance? twitter)
         (.exists (file image-path))]})

(defn verify-credentials [twitter]
  {:pre [(is-instance? twitter)]}
  (.verifyCredentials twitter))
