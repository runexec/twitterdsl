(ns twitterdsl.timeline
  (:use [twitterdsl.core 
         :only [is-instance?]])
  (:import [twitter4j.api
            TimelinesResources]
           [twitter4j
            TwitterImpl
            Paging]))

(defn is-paging? [p]
 (= (class p)
     Paging))

(defn valid-paging?
  [twitter paging]
  (and (is-instance? twitter)
       (is-paging? paging)))

(defn get-home-timeline
  ([twitter]
     {:pre [(is-instance? twitter)]}
     (.. twitter getHomeTimeLine))
  ([twitter paging]
     {:pre [(valid-paging? twitter
                           paging)]}
     (.. twitter 
         (getHomeTimeLine paging))))

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
     (.. twitter getRetweetsOfMe))
  ([twitter paging]
     {:pre [(valid-paging? twitter
                           paging)]}
     (.. twitter 
         (getRetweetsOfMe paging))))


;; TODO get-user-timeline
