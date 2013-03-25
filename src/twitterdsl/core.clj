(ns twitterdsl.core
  (:use [clojure.string :only [split 
                               split-lines]])
  (:import [twitter4j.conf ConfigurationBuilder]
           [twitter4j TwitterFactory TwitterImpl]))

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

(defn- auth-instance [& config-path]
  (let [config (if-not config-path
                 (load-config)
                 (-> config-path
                     first
                     load-config))
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

(defmacro def-twitter [_symbol & config-path]
  (let [instance (if-not config-path
                   (build-instance)
                   (build-instance 
                    (first config-path)))]
    `(def ~_symbol ~instance)))

(defn is-instance? [twitter]
  (= (class twitter)
     TwitterImpl))

(defn add-rate-limit-status-listener 
  [twitter listener]
  {:pre [(is-instance? twitter)]}
  (.. twitter 
      (addRateLimitStatusListener listener)))

(defn get-authorization [twitter]
  {:pre [(is-instance? twitter)]}
  (.getAuthorization twitter))

(defn get-configuration [twitter]
  {:pre [(is-instance? twitter)]}
  (.getConfiguration twitter))

(defn get-user-id [twitter]
  {:pre [(is-instance? twitter)]}
  (.getId twitter))

(defn get-screen-name [twitter]
  {:pre [(is-instance? twitter)]}
  (.getScreenName twitter))

(defn shutdown [twitter] 
  {:pre [(is-instance? twitter)]}
  (.shutdown twitter))



