(ns twitterdsl.core
  (:use [clojure.string :only [split 
                               split-lines]])
  (:import [twitter4j.conf ConfigurationBuilder]
           [twitter4j TwitterFactory]))

(defn- load-config
  ([] (load-config "api.config"))
  ([file-path]
     {:pre [(->> file-path
                (apply str)
                java.io.File.
                .exists)]}
     (loop [settings {}
            config (->> file-path
                       (apply str)
                       slurp
                       split-lines)]
       (if-not (last config)
         settings
         (let [[k v] (-> config
                         last
                         (split #"\s"))]
           (recur 
            (merge settings {(keyword k) v})
            (drop-last config))))))) 

(defn- auth-instance [& config-path]
  (let [config (if-not config-path
                 (load-config)
                 (load-config 
                  (apply str config-path)))
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
                   (build-instance config-path))]
    `(def ~_symbol ~instance)))

