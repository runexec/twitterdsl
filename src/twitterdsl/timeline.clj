(ns twitterdsl.timeline
  (:use [twitterdsl.core 
         :only [is-instance?
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
     (.getRetweetsOfMe twitter))
  ([twitter paging]
     {:pre [(valid-paging? twitter
                           paging)]}
     (.. twitter 
         (getRetweetsOfMe paging))))

(defn get-user-timeline
  ([twitter & [name-or-id paging]] 
     {:pre [(is-instance? twitter)
            (if-not name-or-id
              true
              (true?
               (or (pos? name-or-id)
                   (string? name-or-id))))
            (if-not paging
              true
              (is-paging? paging))]}
     (case (->> [name-or-id paging]
                (filter true?)
                count)
       0 (.getUserTimeline twitter)
       1 (.. twitter 
             (getUserTimeline name-or-id))
       2 (.. twitter 
             (getUserTimeline name-or-id
                              paging)))))
