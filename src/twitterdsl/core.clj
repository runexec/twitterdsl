(ns twitterdsl.core
  (:use clojure.template)
  (:require [twitterdsl.config :as config])
  (:import [twitter4j TwitterFactory]))

(def ^:dynamic *instance*)

(defmacro twitter-instance 
  [var-name & config-path]
  (if config-path
    (config/load-config (apply str 
                               config-path))
    (config/load-config))
  `(let [built-cfg# (.build 
                     (deref config/settings))]
     (def ~var-name 
       (.getInstance
        (TwitterFactory. built-cfg#)))))

(defmacro with-instance
  [twitter-instance & body]
  `(binding [*instance* ~twitter-instance]
     ~@body))
