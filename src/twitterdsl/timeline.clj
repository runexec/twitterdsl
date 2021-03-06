(ns twitterdsl.timeline
  (:use [twitterdsl.core 
         :only [is-instance?
                user-validator
                is-paging?]])
  (:import [twitter4j.api
            TimelinesResources]))

(defn valid-paging?
  [twitter paging]
  (and (is-instance? twitter)
       (is-paging? paging)))

(defn get-home-timeline
  ([twitter]
     {:pre [(is-instance? twitter)]}
     (.. twitter getHomeTimeline))
  ([twitter paging]
     {:pre [(valid-paging? twitter
                           paging)]}
     (.. twitter 
         (getHomeTimeline paging))))

(defn get-mentions-timeline
  ([twitter]
     {:pre [(is-instance? twitter)]}
     (.. twitter getMentionsTimeline))
  ([twitter paging]
     {:pre [(valid-paging? twitter
                           paging)]}
     (.. twitter 
         (getMentionsTimeline paging))))

(defn get-retweets-of-me
  ([twitter]
     {:pre [(is-instance? twitter)]}
     (.getRetweetsOfMe twitter))
  ([twitter paging]
     {:pre [(valid-paging? twitter
                           paging)]}
     (.. twitter 
         (getRetweetsOfMe paging))))

(defn get-user-timeline
  ([twitter]
     {:pre [(is-instance? twitter)]}
     (.getUserTimeline twitter))
  ([twitter name-or-id]
     {:pre [(is-instance? twitter)
            (user-validator name-or-id)]}
     (.. twitter
         (getUserTimeline name-or-id)))
  ([twitter name-or-id paging]
     {:pre [(valid-paging? twitter paging)
            (user-validator name-or-id)]}
     (.. twitter
         (getUserTimeline name-or-id
                          paging))))
     
