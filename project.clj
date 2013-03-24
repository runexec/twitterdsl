(defproject twitterdsl "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [org.twitter4j/twitter4j-core "3.0.3"]
                 [ritz/ritz-nrepl-middleware "0.7.0"]
                 [ritz/ritz-repl-utils "0.7.0"]
                 [ritz/ritz-debugger "0.7.0"]]
  :plugins [[lein-ritz "0.7.0"]]
  :repl-options {:nrepl-middleware
                 [ritz.nrepl.middleware.javadoc/wrap-javadoc
                  ritz.nrepl.middleware.simple-complete/wrap-simple-complete]})

