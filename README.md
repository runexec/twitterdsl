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
;; from the message/ ns is refering to the parsed
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
