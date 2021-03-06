(ns twitterdsl.core
  (:use [clojure.string 
         :only [split 
                split-lines]])
  (:import [twitter4j.conf 
            ConfigurationBuilder]
           [twitter4j 
            Paging
            GeoQuery
            GeoLocation
            TwitterFactory 
            TwitterImpl]))

(defn- load-config
  ([] (load-config "api.config"))
  ([file-path]
     {:pre [(.exists 
             (java.io.File. file-path))]}
     (loop [settings {}
            config (-> file-path
                       slurp
                       split-lines)]
       (if-not (last config)
         settings
         (let [[k v] (-> config
                         last
                         (split #"\s"))]
           (recur 
            (assoc settings
              (keyword k) v)
            (drop-last config))))))) 

(defn- auth-instance [& [config-path]]
  (let [config (if-not config-path
                 (load-config)
                 (load-config config-path))
        {:keys [consumer-key 
                consumer-key-secret
                access-token
                access-token-secret]} config]
    (.. (ConfigurationBuilder.)
        (setPrettyDebugEnabled true)
        (setUseSSL true)
        (setOAuthConsumerKey consumer-key)
        (setOAuthConsumerSecret consumer-key-secret)
        (setOAuthAccessToken access-token)
        (setOAuthAccessTokenSecret access-token-secret)
        build)))

(defn- build-instance
  ([]
     (-> (auth-instance)
         TwitterFactory.
         .getInstance))
  ([config-path]
     (-> config-path
         auth-instance
         TwitterFactory.
         .getInstance)))

(defmacro def-twitter [-symbol & [config-path]]
  (let [instance (if-not config-path
                   (build-instance)
                   (build-instance config-path))]
    `(def ~-symbol ~instance)))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Class Test

(defn is-instance? [twitter]
  (= (class twitter)
     TwitterImpl))

(defn is-paging? [p]
 (= (class p)
     Paging))

(defn is-geolocation? [g]
  (= (class g)
     GeoLocation))

(defn is-geo-query? [gq]
  (= (class gq)
     GeoQuery))


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Common Validators

(defn user-validator [userid-or-name]
  (true?
   (or (string? userid-or-name)
       (pos? userid-or-name))))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Core Fns 

(defn add-rate-limit-status-listener 
  [twitter listener]
  {:pre [(is-instance? twitter)]}
  (.. twitter 
      (addRateLimitStatusListener listener)))

(defn authorization [twitter]
  {:pre [(is-instance? twitter)]}
  (.getAuthorization twitter))

(defn configuration [twitter]
  {:pre [(is-instance? twitter)]}
  (.getConfiguration twitter))

(defn user-id [twitter]
  {:pre [(is-instance? twitter)]}
  (.getId twitter))

(defn screen-name [twitter]
  {:pre [(is-instance? twitter)]}
  (.getScreenName twitter))

(defn shutdown [twitter] 
  {:pre [(is-instance? twitter)]}
  (.shutdown twitter))
