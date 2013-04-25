```clojure
(use 'twitterdsl.dsl)

(def-twitter instance)

(twitter instance
         (let [tweets (search/with 
                       (search/query "Clojure")
                       (search/get-tweets))]
           (tweet/with
            (tweet/parse (first tweets))
            (println "Message:"
                     (tweet/get-text)
                     "\n"
                     (tweet/get-created-date)))))

```

Work in Progress

### tweet/ API###

```clojure
(parse [twitter-status])

(with [parse-results & body])

(get-contributors)

(get-created-date)

(get-current-user-retweet-id)

(get-geo-location)

(get-tweet-id)

(get-reply-to-screen-name)

(get-reply-to-status-id)

(get-reply-to-user-id)

(get-place)

(get-retweet-count)

(get-retweet-status)

(get-source)

(get-text)

(get-user)

(favorited?)

(sensitive?)

(retweet?)

(retweeted-by-me?)

(truncated?)
```

### search/ API###
```clojure
(get-instance)

(get-next-page-instance)

(get-access-level)

(get-rate-limit-status)

(get-complete-time)

(get-max-id)

(get-since-id)

(has-next?)

(has-many?)

(get-refresh-url)

(get-since-id)

(get-tweets)

```


## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
