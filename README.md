```clojure
(use 'twitterdsl.dsl)

(def-twitter instance)

(twitter instance
         (let [tweets (search/with 
                       (search/query "Clojure")
                       (search/get-tweets))]
           (tweet/with 
            (tweet/parse (first tweets))
            (tweet/get-created-date))))
```
## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
