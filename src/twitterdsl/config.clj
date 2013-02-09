(ns twitterdsl.config
  (:refer-clojure :exclude [set])
  (:use [clojure.string :only (split-lines split)])
  (:import [twitter4j.conf ConfigurationBuilder]))

(def settings (atom nil))

(def ^{:private true} consumer-key (atom nil))
(def ^{:private true} consumer-key-secret (atom nil))
(def ^{:private true} access-token (atom nil))
(def ^{:private true} access-token-secret (atom nil))    

(defn- set
  [consumer-key
   consumer-secret
   access-token
   access-token-secret]
  (reset! settings
          (doto (ConfigurationBuilder.)
            (.setOAuthConsumerKey consumer-key)
            (.setOAuthConsumerSecret consumer-secret)
            (.setOAuthAccessToken access-token)
            (.setOAuthAccessTokenSecret access-token-secret))))
            
(defn- read-config 
  [file-path]
  (split-lines
   (slurp file-path)))

(defn load-config
  ([] (load-config "api.config"))
  ([file-path]
     (doseq [line (read-config file-path)
             :let [[k v] (split line #"\s")]]
       (case k
         "consumer-key" (reset! consumer-key v)
         "consumer-key-secret" (reset! consumer-key-secret v)
         "access-token" (reset! access-token v)
         "access-token-secret" (reset! access-token-secret v)))
     (set @consumer-key
          @consumer-key-secret
          @access-token
          @access-token-secret)))
