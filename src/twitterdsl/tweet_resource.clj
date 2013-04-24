(ns twitterdsl.tweet-resource
  (:use [twitterdsl.core
         :only [is-instance?]])
  (:import [twitter4j
            OEmbedRequest
            StatusUpdate]
           [twitter4j.api 
            TweetsResources]))

(defn destroy-status
  [twitter status-id]
  {:pre [(and (is-instance? twitter)
              (pos? status-id))]}
  (.. twitter
      (destroyStatus status-id)))

(defn get-oembed
  [twitter oembed-req]
  {:pre [(and (is-instance? twitter)
              (= (type oembed-req)
                 OEmbedRequest))]}
  (.. twitter
      (getOEmbed oembed-req)))

(defn get-retweets
  [twitter status-id]
  {:pre [(and (is-instance? twitter)
              (pos? status-id))]}
  (.. twitter
      (getRetweets status-id)))

(defn retweet-status
  [twitter status-id]
  {:pre [(and (is-instance? twitter)
              (pos? status-id))]}
  (.. twitter
      (retweetStatus status-id)))

(defn show-status
  [twitter id]
  {:pre [(and (is-instance? twitter)
              (pos? id))]}
  (.. twitter
      (showStatus id)))

(defn update-status
  [twitter status]
  {:pre [(and (is-instance? twitter)
              (or (string? status)
                  (= (type status)
                     StatusUpdate)))]}
  (.. twitter
      (updateStatus status)))
