A lot better documentation is coming shortly. Hang tight.

#### TwitterDSL API Documentation ####

<a href="#basics">Basics</a><br />
<a href="#triggers">Triggers</a><br />
<a href="#twitter-instance-api">Twitter Instance</a><br />
<a href="#status-api">Status</a><br />
<a href="#message-api">Message</a><br />
<a href="#favorite-api">Favorite</a><br />
<a href="#tweet-api">Tweet</a><br />
<a href="#search-api">Search</a><br />
<a href="#user-api">User</a><br />
<a href="#timeline-api">Timeline</a><br />
<a href="#following-api">Follow</a><br />
<a href="#friendship-api">Friendship</a><br />
<a href="#relationship-api">Relationship</a><br />

#### Installation & Setup ####

Command Line

```bash
# Get files 
cd ~/; git clone https://github.com/runexec/twitterdsl

# Install
cd twitterdsl; lein install

# Create test project
cd /tmp/; lein new test-project; cd test-project
```

Add the project dependency

```clojure
[twitterdsl/twitterdsl "0.1.0-SNAPSHOT"]
```

Command Line

```bash
# Copy and configure API
cp ~/twitterdsl/api.config.example api.config
# Test install 
echo "(use 'twitterdsl.dsl) (new-instance instance) instance" | lein repl
```

#### Dependencies ####

Filenames that aren't prefixed with dsl depend on the twitter4j library. The file dsl.clj and all files with <br />
the prefix of dsl_ are built on top of the Clojure implementatio of the twitter4j library.

#### Work in Progress ####

A good chunk of the twitter4j library has a Clojure abstraction but there's still some work to do to completely <br />
escape the Java interop calls that have to be made on objects.


#### Basics ####

```clojure
(use 'twitterdsl.dsl)

(new-instance instance)

(twitte  instance
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

(enable-logging "/tmp/outfile")

(twitter instance 
         (doseq [_ (timeline/tweets)
                 :let [t (tweet/parse _)]]
           (tweet/with t))
         (println "Done logging"))

(disable-logging)

```

### Twitter Instance API ###
```clojure

;; Create a new instance using the default config
;; default config = api.config in project classpath
(new-instance -symbol)

;; Create a new instance instance based on the 
;; non-default configuratin path
(new-instance -symbol "/tmp/api.config")

;; Enable logging to default file of {date}.log
(enable-logging)

;; Enable logging to the non-default config path
(enable-logging "/tmp/output.log")

;; Disable the logging trigger
(disable-logging)

;; twitter/with fns

;; Twitter4j Instance Listener
(twitter instance
         (add-rate-limit-status-listener listener))

;; OAuthAuthorization / consumerkey, consumersecret, accesstoken
(twitter instance
         (authorization))

;; Twitter instance configuration
(twitter instance
         (configuration))

;; User id of the authorized user
(twitter instance
         (user-id))

;; User @screen-name
(twitter instance
         (screen-name))

;; Free resources 
(twitter instance
         (shutdown))
```

### status/ API ###
```clojure
;; Delete a tweet
(twitter instance
         (status/destroy status-id))

;; Get embeded object form a tweet
(twitter instance
         (status/oembed oembed-req))

;; Returns up to 100 of the first retweets of a given tweet
(twitter instance
         (status/retweets status-id))

;; Retweet a message
(twitter instance
         (status/retweet status-id))

;; Returns a single status, specified by the id parameter below.
;; The status's author will be returned inline. 
(twitter instance
         (status/show status-id))

;; Write a new tweet
(twitter instance
         (status/tweet msg))
```

### message/ API ###

```clojure
;; Delete a direct message sent or received by you
(twitter instance
         (message/destroy id))

;; Get received messages
(twitter instance
         (message/messages [& paging]))

;; Returns list of direct messages sent by you
(twitter instance
         (message/sent-messages [& [paging]]))

;; Sends direct message to the specified user 
(twitter instance
         (message/send username-or-id msg))

;; Returns direct message with the dm id
(twitter instance
         (message/show id))

;; Returns a hash-set with all the characteristics
;; of a direct message
(twitter instance
         (message/parse dm))

;; Binds parsed direct message so that all calls 
;; from the message/ ns is referring to the parsed
;; direct message
(twitter instance
         (message/with
          (message/parse dm) & body))

;; message/with fns

;; Returns java.util.Date creation time of a 
;; direct message
(twitter instance
         (message/with
          (message/parse dm)
          (message/created-date)))

;; Returns the id of a direct message
(twitter instance
         (message/with
          (message/parse dm)
          (message/id)))

;; Returns who the message was being sent to and
;; can be manipulated with the user/ ns
(twitter instance
         (message/with
          (message/parse dm)
          (message/recipient)))

;; The user id of the person who recieved the
;; direct message
(twitter instance
         (message/with
          (message/parse dm)
          (message/recipient-id)))


;; The @screen-name of the person who recieved the
;; direct message
(twitter instance
         (message/with
          (message/parse dm)
          (message/recipient-screen-name)))

;; Returns who the message was being sent from and
;; can be manipulated with the user/ ns
(twitter instance
         (message/with
          (message/parse dm)
          (message/sender)))

;; The user id of the person who sent the direct message
(twitter instance
         (message/with
          (message/parse dm)
          (message/sender-id)))

;; The @screen-name of the person who sent
;; the direct message
(twitter instance
         (message/with
          (message/parse dm)
          (message/sender-screen-name)))

;; Returns the text from within the direct message
(twitter instance
         (message/with
          (message/parse dm)
          (message/message)))

```

### favorite/ API ###

```clojure
;; Mark as a favorite tweet
(twitter instance
         (favorite/mark tweet-id))

;; Unfavorite a favorited tweet
(twitter instance
         (favorite/unmark tweet-id))

;; Returns the 20 most recent favorite statuses
(twitter instance
         (favorite/favorites))

;; Returns the 20 most recent favorite statuses for
;; the authenticating user or user specified by the ID 
(twitter instance
         (favorite/favorites userid-or-name-or-paging))


;; Returns the 20 most recent favorite statuses for
;; the authenticating user or user specified by the ID 
(twitter instance
         (favorite/favorites userid-or-name
                             paging))
```

### tweet/ API###

```clojure
;; Returns a hash-set with all the characteristics
;; of a tweet status
(twitter instance
         (tweet/parse twitter-status))

;; Binds parsed tweet status so that all calls 
;; from the tweet/ ns is referring to the parsed
;; status message
(twitter instance
         (tweet/with [parse-results & body]))

;; tweet/with fns

;; Returns an array of contributors, or null if no contributor
;; is associated with this status.
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/contributors)))

;; Returns java.util.Date creation time of a tweet status message
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/created-date)))

;; Get your retweet id
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/current-user-retweet-id)))

;; Returns The location that this tweet refers to if available.
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/geo-location)))

;; Returns id of the tweet status message
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/tweet-id)))

;; Returns the @screen-name of the reply-to user of the
;; tweet status message
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/reply-to-screen-name)))

;; Returns the tweet status id of the reply-to tweet 
;; status message
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/reply-to-status-id)))

;; Returns the user id of the reply-to user of the
;; tweet status message
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/reply-to-user-id)))

;; Returns the place attached to the tweet status
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/place)))

;; Returns the number of times the tweet stats has been retweeted. 
;; -1 if the tweet was created before this feature was enabled
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/retweet-count)))

;; Returns original tweet status of a retweeted status 
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/retweet-status)))

;; Returns the source of a tweet status
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/source)))

;; Returns the text from within a tweet status
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/text)))

;; Returns the user associated with the tweet status.
;; The user can be manipulated with the user/ ns.
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/user)))

;; Test if you've favorited a tweet status
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/favorited?)))

;; Returns true if the tweet status contains a link that
;; is identified as sensitive.
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/sensitive?)))

;; Test if the tweet status is original or a retweet
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/retweet?)))

;; Test if you've retweeted the tweet status
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/retweeted-by-me?)))

;; Test if the status is truncated
(twitter instance
         (tweet/with
          (tweet/parse status)
          (tweet/truncated?)))
```

### search/ API###
```clojure

;; Returns hash-set of search characteristics
(twitter instance
         (search/query "search text"))

;; Binds search/query so that all calls from the 
;; search/ ns is referring to the query
(twitter instance
         (search/with
          (search/query "search text") & body))

;; search/with fns

;; Return search page instance object.
(twitter instance
         (search/with
          (search/query "search text")
          (search/instance)))

;; Returns a search instance to fetch next page or null if there 
;; is no next page.
(twitter instance
         (search/with
          (search/query "search text")
          (search/next-page-instance)))

;; Returns application permission model
(twitter instance
         (search/with
          (search/query "search text")
          (search/access-level)))

;; Returns current rate limit status
(twitter instance
         (search/with
          (search/query "search text")
          (search/rate-limit-status)))

;; Return the duration it took to fetch the search results
(twitter instance
         (search/with
          (search/query "search text")
          (search/complete-time)))

;; Return the max id of a search
(twitter instance
         (search/with
          (search/query "search text")
          (search/max-id)))

;; Return the since id of a search
(twitter instance
         (search/with
          (search/query "search text")
          (search/since-id)))

;; Test if there results have another page. If so, you can
;; get the next page with (search/next-page-instance)
(twitter instance
         (search/with
          (search/query "search text")
          (search/has-next?)))

;; Returns the number of tweets found
(twitter instance
         (search/with
          (search/query "search text")
          (search/how-many)))

;; Return the since id of the search results
(twitter instance
         (search/with
          (search/query "search text")
          (search/since-id)))

;; Return tweets found in the search results
(twitter instance
         (search/with
          (search/query "search text")
          (search/tweets)))
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

### timeline/ API ###

```clojure
(tweets [& [paging]])

(mentions [& [paging]])

(retweets [& [paging]])

(user-timeline
 ([])
 ([username-or-id])
 ([username-or-id paging]))
```

### following/ API ###

```clojure
(add [userid-or-name])

(delete [userid-or-name])

(follower-ids
  ([cursor])
  ([userid-or-name cursor]))

(follower-list [userid-or-name cursor])

(my-follower-ids [cursor]

(follower-ids-of [userid-or-name cursor])

(follower-list-of [userid-or-name cursor])

(incoming [cursor])

(lookup [coll-userids-or-names])

(show-friendship
  [sourceid-or-name
   targetid-or-name])

(update-friendship
  [userid-or-name
   enable-device-notify?
   retweets?])

```

### friendship/ API ###

```clojure
(parse [friendship])

(with [parse-result & body])

;; friendship/with fns

(id)

(name)

(screen-name)

(followed-by?)

(following?)

```

### relationship/ API###

```clojure
(parse [relationship])

(with [parse-result & body])

;; relationship/with fns

(source-id)

(source-user-screen-name)

(target-userid)

(target-screen-name)

(source-blocking-target?)

(source-followed-by-target?)

(source-following-target?)

(source-notification-enabled?)

(source-wants-retweets?)

(target-followed-by-source?)

(target-following-source?)
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
