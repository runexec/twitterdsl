(ns twitterdsl.dsl)

(defmacro new-instance
  [symbol & [config-path]]
  (if-not config-path
    `(twitterdsl.core/def-twitter ~symbol)
    `(twitterdsl.core/def-twitter ~symbol ~config-path)))
  
(def ^:dynamic *instance*)

(def ^:private twitter-api-resource
  {:core 'twitterdsl.core
   :direct-message 'twitterdsl.direct-message-resource
   :favorite 'twitterdsl.favorite-resource
   :friend 'twitterdsl.friend-follower-resource
   :lists 'twitterdsl.list-resource
   :geo-tag 'twitterdsl.places-geo-resource
   :saved-search 'twitterdsl.saved-search-resource
   :search 'twitterdsl.search-resource
   :suggestions 'twitterdsl.suggested-user-resource
   :timeline 'twitterdsl.timeline
   :tweet 'twitterdsl.tweet-resource
   :user 'twitterdsl.user-resource
   })

(defn- load-api-resource
  "Returns public symbols from a twitter api ns.
   {:symbols :full-name}"
  [resource-type]
  (let [api-ns (resource-type twitter-api-resource)]
    (require api-ns)
    (let [publics (ns-publics api-ns)]
      {:symbols (keys publics)
       :full-name (vals publics)})))

(def ^:private twitter-api
  "Returns all the fully qualified names of a
   the twitter api."
  (->> (keys twitter-api-resource)
       (map load-api-resource)
       (map :full-name)
       flatten
       (map #(drop 2 (str %)))
       (map #(symbol
              (apply str %)))))

(def ^:private with-instance-symbol-map
  "Returns kv map with the fully qualifed name
   as the key and the symbol as the value"
  (zipmap (map keyword twitter-api)
          (map #(symbol
                 (last
                  (clojure.string/split (str %) #"/")))
               twitter-api)))

(defn reload-defns
  "Find all the twitter api calls that
   require the twitter variable and 
   define the same symbols in this 
   namespace with *instance* as the
   first argument in a (partial) def"
  []
  (letfn [(symbol-partial [[fqn -symbol]]
            (let [-symbol (symbol -symbol)
                  fqn (->> fqn
                           str
                           rest
                           (apply str)
                           symbol)]
              (when (->> (eval `(meta #'~fqn))
                         :arglists
                         first
                         first
                         (= 'twitter))
                (eval 
                 `(def ~-symbol
                    (partial ~fqn ~*instance*))))))]
    (doseq [_ with-instance-symbol-map]
      (symbol-partial _))))

(defmacro with-instance
  [instance & body]
  `(binding [*instance* ~instance]
    (reload-defns)
    ~@body))

(defmacro twitter
  "Alias for with-instance"
  [instance & body]
  `(with-instance ~instance ~@body))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Trigger Handlers


(def a-triggers (atom '()))

(defn add-trigger 
  [trigger-name fn-trigger]
  (swap! a-triggers
         conj
         {:name trigger-name
          :trigger fn-trigger}))

(defn remove-trigger [trigger-name]
  (swap! a-triggers
         (fn [x]
           (filter
            #(not= trigger-name
                   (:name %))
            x))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Init and Require
              
;; Init so other namespaces can refer to
;; the api wraps
(reload-defns)

(require '[twitterdsl.dsl-search :as search]
         '[twitterdsl.dsl-tweet :as tweet]
         '[twitterdsl.dsl-user :as user]
         '[twitterdsl.dsl-status :as status]
         '[twitterdsl.dsl-direct-message :as message]
         '[twitterdsl.dsl-favorite :as favorite]
         '[twitterdsl.dsl-timeline :as timeline]
         '[twitterdsl.dsl-friend :as following]
         '[twitterdsl.dsl-friendship :as friendship]
         '[twitterdsl.dsl-relationship :as relationship])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Triggers

(defn enable-logging [& [log-file-path]]
  (let [fp (or log-file-path
               (-> (java.util.Date.)
                   str
                   (clojure.string/replace #"\s" "-")
                   (clojure.string/replace #"\:" "-")))]
    (add-trigger
     "logging-enabled"
     #(let [msg (tweet/text)
            date (str (tweet/created-date))
            [user-name
             user-screen-name] (user/with
                                (user/parse (tweet/user))
                                [(user/name)
                                 (user/screen-name)])
            log (format "%s (%s - %s) %s\n\n"
                        date
                        user-screen-name
                        user-name
                        msg)]
        (spit fp
              log
              :append true)))))

(defn disable-logging []
  (remove-trigger "logging-enabled"))

