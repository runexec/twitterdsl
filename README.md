#### Basics ####

```clojure
(use 'twitterdsl.dsl)

(def-twitter instance)

(twitter
 instance
 (let [tweets (search/with 
               (search/query "Clojure")
               (search/tweets))]
   (doseq [t tweets
           :let [status (tweet/parse t)]]
     (tweet/with 
      status
      (user/with
       (user/parse (tweet/user))
       (println
        (user/screen-name) " - " (tweet/text) "\n"))))))

```

#### Triggers ####

```clojure

(add-trigger "clojure"
             #(println
               (tweet/created-date) " - " (tweet/text)))

(twitter
 instance
 (let [tweets (search/with 
               (search/query "Clojure")
               (search/tweets))]
   (doseq [t tweets
           :let [status (tweet/parse t)]]
     ;; Calls All Triggers 
     (tweet/with status))))

(remove-trigger "clojure")

```



Work in Progress

### status/ API ###
```clojure
(destroy [status-id])

(oembed [oembed-req])

(retweets [status-id])

(retweet [status-id])

(show [status-id])

(tweet [msg])
```

### message/ API ###

```clojure
(destroy [id])

(messages [& [paging]])

(sent-messages [& [paging]])

(send [username-or-id msg])

(show [id])

(parse [dm])

(with parse-result & body)

;; message/with fns

(created-date)

(id)

(recipient)

(recipient-id)

(recipient-screen-name)

(sender)

(sender-id)

(sender-screen-name)

(message)

```

### favorite/ API ###

```clojure
(mark [tweet-id])

(unmark [tweet-id])

(favorites [])

(favorites [userid-or-name-or-paging])

(favorites [userid-or-name paging])
```

### tweet/ API###

```clojure
(parse [twitter-status])

(with [parse-results & body])

;; tweet/with fns

(contributors)

(created-date)

(current-user-retweet-id)

(geo-location)

(tweet-id)

(reply-to-screen-name)

(reply-to-status-id)

(reply-to-user-id)

(place)

(retweet-count)

(retweet-status)

(source)

(text)

(user)

(favorited?)

(sensitive?)

(retweet?)

(retweeted-by-me?)

(truncated?)
```

### search/ API###
```clojure

(query [search])

(with [query-results & body])

;; search/with fns

(instance)

(next-page-instance)

(access-level)

(rate-limit-status)

(complete-time)

(max-id)

(since-id)

(has-next?)

(has-many?)

(refresh-url)

(since-id)

(tweets)

```

### user/ API ###

```clojure

(parse [user])

(with [parsed-result & body])

;; user/with fns

(bigger-profile-image-url)

(bigger-profile-image-url-https)

(created-date)

(description)

(description-url-entities)

(favourites-count)

(followers-count)

(friends-count)

(user-id)

(language)

(listed-count)

(location)

(mini-profile-image-url)

(mini-profile-image-url-https)

(name)

(original-profile-image-url)

(original-profile-image-url-https)

(profile-background-color)

(profile-background-image-url)

(profile-background-image-url-https)

(profile-banner-mobile-retina-url)

(profile-banner-mobile-url)

(profile-banner-url)

(profile-image-url)

(profile-image-url-https)

(profile-link-color)

(profile-sidebar-border-color)

(profile-sidebar-fill-color)

(profile-text-color)

(screen-name)

(status)

(statuses-count)

(timezone)

(user-url)

(url-entity)

(utc-offset)

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
