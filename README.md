```clojure
(use 'twitterdsl.dsl)

(def-twitter instance)

(twitter
 instance
 (let [tweets (search/with 
               (search/query "Clojure")
               (search/get-tweets))]
   (doseq [t tweets
           :let [status (tweet/parse t)]]
     (tweet/with 
      status
      (user/with
       (user/parse (tweet/get-user))
       (println
        (user/get-screen-name) " - " (tweet/get-text) "\n"))))))

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

###user/ API###

```clojure
(get-bigger-profile-image-url)

(get-bigger-profile-image-url-https)

(get-created-date)

(get-description)

(get-description-url-entities)

(get-favourites-count)

(get-followers-count)

(get-friends-count)

(get-user-id)

(get-language)

(get-listed-count)

(get-location)

(get-mini-profile-image-url)

(get-mini-profile-image-url-https)

(get-name)

(get-original-profile-image-url)

(get-original-profile-image-url-https)

(get-profile-background-color)

(get-profile-background-image-url)

(get-profile-background-image-url-https)

(get-profile-banner-mobile-retina-url)

(get-profile-banner-mobile-url)

(get-profile-banner-url)

(get-profile-image-url)

(get-profile-image-url-https)

(get-profile-link-color)

(get-profile-sidebar-border-color)

(get-profile-sidebar-fill-color)

(get-profile-text-color)

(get-screen-name)

(get-status)

(get-statuses-count)

(get-timezone)

(get-user-url)

(get-url-entity)

(get-utc-offset)

(contributors-enabled?)

(follow-request-sent?)

(geo-enabled?)

(profile-background-tiled?)

(profile-use-background-image?)

(protected?)

(show-all-inline-media?)

(translator?)

(verified?)

```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
