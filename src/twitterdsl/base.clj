(ns twitterdsl.base
  (:use [twitterdsl.core :only [*instance*] :as *instance*])
  (:import [twitter4j TwitterBase]))

(defn 
  ^{:doc "Returns screen name of current instance"
    :added 1} 
  screen-name []
  (.getScreenName *instance*))

(defn ^{:doc "Returns auth user's user id"
        :added 1}
  get-id [] 
  (.getId *instance*))

(defn ^{:doc "Returns auth scheme of instance"
        :added 1}
  get-auth []
  (.getAuthorization *instance*))
  
(defn ^{:doc "Returns config of instance"
        :added 1}
  get-config []
  (.getConfiguration *instance*))
