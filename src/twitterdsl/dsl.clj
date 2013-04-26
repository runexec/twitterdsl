(ns twitterdsl.dsl
  (:use [twitterdsl.core
         :only [def-twitter]]))

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
    (println "requiring" api-ns)
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
  (binding [*instance* instance]
    (reload-defns)
    `(do ~@body)))

(defmacro twitter
  "Alias for with-instance"
  [instance & body]
  `(with-instance ~instance ~@body))

;; Init so other namespaces can refer to
;; the api wraps
(reload-defns)

(require '[twitterdsl.dsl-search :as search]
         '[twitterdsl.dsl-tweet-handler :as tweet]
         '[twitterdsl.dsl-user-handler :as user]
         '[twitterdsl.dsl-status :as status])
