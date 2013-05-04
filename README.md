TwitterDSL is a Clojure library for working easily with the Twitter service.


#### TwitterDSL API Documentation ####

<p><a href="#installation--setup">Installation & Setup</a></p>
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

(twitter instance
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

(enable-logging! "/tmp/outfile")

(twitter instance 
         (doseq [_ (timeline/tweets)
                 :let [t (tweet/parse _)]]
           (tweet/with t))
         (println "Done logging"))

(disable-logging!)

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
(enable-logging!)

;; Enable logging to the non-default config path
(enable-logging! "/tmp/output.log")

;; Disable the logging trigger
(disable-logging!)

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
;; from the message/ ns are referring to the parsed
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
;; from the tweet/ ns are referring to the parsed
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
;; search/ ns are referring to the query
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

;; Returns a hash-set with all the characteristics
;; of a twitter user
(twitter instance
	 (user/parse user))


;; Binds parsed twitter user so that all calls 
;; from the user/ ns are referring to the parsed
;; twitter user
(twitter instance
	 (user/with 
          (user/parse user) & body))

;; user/with fns

;; Returns big profile image url
(twitter instance
	 (user/with 
          (user/parse user)
          (user/bigger-profile-image-url)))

;; Returns big profile image url with https
(twitter instance
	 (user/with 
          (user/parse user)
          (user/bigger-profile-image-url-https)

;; Returns java.util.Date of when the twitter
;; account was created
(twitter instance
	 (user/with 
          (user/parse user)
          (user/created-date)))

;; Returns the description of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/description)))

;; Returns URL entities for user description.
(twitter instance
	 (user/with 
          (user/parse user)
          (user/description-url-entities)))

;; Returns the number of favorites a user has
(twitter instance
	 (user/with 
          (user/parse user)
          (user/favourites-count)))

;; Returns the number of followers an user has
(twitter instance
	 (user/with 
          (user/parse user)
          (user/followers-count)))

;; Returns the number of friends a user has
(twitter instance
	 (user/with 
          (user/parse user)
          (user/friends-count)))

;; Returns the user id of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/user-id)))

;; Returns the preferred language of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/language)))

;; Returns the number of public lists the user is
;; listed on, or -1 if the count is unavailable.
(twitter instance
	 (user/with 
          (user/parse user)
          (user/listed-count)))

;; Returns the location of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/location)))

;; Returns a small user profile image url
(twitter instance
	 (user/with 
          (user/parse user)
          (user/mini-profile-image-url)))

;; Returns a small user profile image url with https
(twitter instance
	 (user/with 
          (user/parse user)
          (user/mini-profile-image-url-https)))

;; Returns the name of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/name)))

;; Returns a users original profile image url
(twitter instance
	 (user/with 
          (user/parse user)
          (user/original-profile-image-url)))

;; Returns a users original profile image url with https
(twitter instance
	 (user/with 
          (user/parse user)
          (user/original-profile-image-url-https)))

;; Returns profile background color of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-background-color)))

;; Returns profile background image of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-background-image-url)))


;; Returns profile background image of the user with https
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-background-image-url-https)))

;; Returns profile banner mobile retina image url of
;; the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-banner-mobile-retina-url)))

;; Returns profile banner mobile image url of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-banner-mobile-url)))

;; Returns profile banner image url of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-banner-url)))

;; Returns profile image url of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-image-url)))

;; Returns profile image url of the user with https
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-image-url-https)))

;; Returns the profile link color of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-link-color)))

;; Returns the profile sidebar border color of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-sidebar-border-color)))

;; Returns the profile sidebar fill color of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-sidebar-fill-color)))

;; Returns the profile text color of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-text-color)))

;; Returns the @screen-name of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/screen-name)))

;; Returns the current status of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/status)))

;; Returns the count of total status messages
;; of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/statuses-count)))

;; Returns the timezone of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/timezone)))

;; Returns the user url -> twiter.com/user-screen-name
(twitter instance
	 (user/with 
          (user/parse user)
          (user/user-url)))

;; Returns URL entity for user's URL.
(twitter instance
	 (user/with 
          (user/parse user)
          (user/url-entity)))

;; Returns the universal time code offset of the user
(twitter instance
	 (user/with 
          (user/parse user)
          (user/utc-offset)))

;; Tests if the user is enabling contributors
(twitter instance
	 (user/with 
          (user/parse user)
          (user/contributors-enabled?)))

;; Returns true if the authenticating user has requested 
;; to follow this user, otherwise false.
(twitter instance
	 (user/with 
          (user/parse user)
          (user/follow-request-sent?)))

;; Test if the user has geo location enabled
(twitter instance
	 (user/with 
          (user/parse user)
          (user/geo-enabled?)))

;; Test if the user has a tiled profile background
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-background-tiled?)))

;; Test if the user has a profile background image
(twitter instance
	 (user/with 
          (user/parse user)
          (user/profile-use-background-image?)))

;; Test if the user has a protected account
(twitter instance
	 (user/with 
          (user/parse user)
          (user/protected?)))

;; Test if the user has media autmatically displayed
(twitter instance
	 (user/with 
          (user/parse user)
          (user/show-all-inline-media?)))

;; Test if the user is a twitter translator
(twitter instance
	 (user/with 
          (user/parse user)
          (user/translator?)))

;; Test if the user is verified
(twitter instance
	 (user/with 
          (user/parse user)
          (user/verified?)))
```

### timeline/ API ###

```clojure

;; Get your home timeline
(twitter instance
         (timeline/tweets & paging))

;; Get your mentions
(twitter instance
         (timeline/mentions & paging))

;; Get retweets of your tweets
(twitter instance
         (timeline/retweets & paging))

;; Get your tweet timeline
(twitter instance
         (timeline/user-timeline))

;; Get tweet timeline of another user
(twitter instance
         (timeline/user-timeline username-or-id & paging))
```

### following/ API ###

```clojure
;; Follow a user
(twitter instance
         (following/add userid-or-name))

;; Unfollow a user
((twitter instance
         (following/delete userid-or-name))

;; Returns an array of numeric IDs for every user
;; the specified user is followed by. If no user is
;; specified, the authenticating user is the default
(twitter instance
         (following/follower-ids
          ([cursor])
          ([userid-or-name cursor])))

;; Returns a cursored collection of user objects for
;; users following the specified user. At this time,
;; results are ordered with the most recent following first
;;  — however, this ordering is subject to unannounced 
;; change and eventual consistency issues.
(twitter instance
         (following/follower-list userid-or-name
                                  cursor))
;; Get the ids of your followers
(twitter instance
         (following/my-follower-ids cursor))

;; Get the ids of another user's followers 
(twitter instance
         (following/follower-ids-of userid-or-name
                                    cursor)

;; Returns a cursored collection of user objects for
;; every user the specified user is following (otherwise 
;; known as their "friends"). At this time, results are
;; ordered with the most recent following first — however,
;; this ordering is subject to unannounced change and 
;; eventual consistency issues.
(twitter instance
         (following/follower-list-of userid-or-name
                                     cursor))

;; Returns an array of numeric IDs for every user who 
;; has a pending request to follow the authenticating user.
(twitter instance
         (following/incoming cursor))

;; Returns the relationship of the authenticating user
;; to the specified users.
(twitter instance
         (following/lookup coll-userids-or-names))

;; Returns detailed information about the relationship
;; between two users
(twitter instance
         (following/show-friendship sourceid-or-name
                                    targetid-or-name))

;; Allows you to enable or disable retweets and device
;; notifications from the specified user
(twitter instance
         (following/update-friendship userid-or-name
                                      enable-device-notify?
                                      retweets?))
```

### friendship/ API ###

```clojure

;; Returns a hash-set with all the characteristics
;; of a twitter friendship
(twitter instance
     (friendship/parse friendship))

;; Binds parsed friendship so that all calls from the 
;; friendship/ ns are referring to the parsed friendship
(twitter instance
         (friendship/with 
          (friendship/parse friendship) & body))

;; friendship/with fns

;; Get friendship friend id
(twitter instance
         (friendship/with 
          (friendship/parse friendship)
          (friendship/id)))

;; Get friendship friend name
(twitter instance
         (friendship/with 
          (friendship/parse friendship)
          (friendship/name)))

;; Get friendship friend @screen-name
(twitter instance
         (friendship/with 
          (friendship/parse friendship)
          (friendship/screen-name)))

;; Test friendship friend follow-by status
(twitter instance
         (friendship/with 
          (friendship/parse friendship)
          (friendship/followed-by?)))

;; Test friendship friend following status
(twitter instance
         (friendship/with 
          (friendship/parse friendship)
          (friendship/following?)))

```

### relationship/ API###

```clojure
;; Returns a hash-set with all the characteristics
;; of a twitter relationship
(twitter instance
         (relationship/parse relationship))

;; Binds parsed relationship so that all calls from the 
;; relationship/ ns are referring to the parsed relationship
(twitter instance
         (relationship/with
          (relationship/parse relationship) & body))

;; relationship/with fns

;; Returns the source user id
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/source-id)))

;; Returns the source user screen name
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/source-user-screen-name)))

;; Returns the target user id
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/target-userid)))

;; Returns the target user screen name
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/target-screen-name)))

;; Returns if the source user is blocking the target user
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/source-blocking-target?)))

;; Checks if source user is being followed by target user
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/source-followed-by-target?)))

;; Checks if source user is following target user
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/source-following-target?)))

;; Checks if the source user has enabled notifications
;; for updates of the target user
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/source-notification-enabled?)))

;; Checks if the retweets from the target user enabled
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/source-wants-retweets?)))

;; Checks if target user is being followed by source user.
;; This method is equivalent to source-following-target?
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/target-followed-by-source?)))

;; Checks if target user is following source user.
;; This method is equivalent to source-followed-by-target?
(twitter instance
         (relationship/with
          (relationship/parse relationship)
          (relationship/target-following-source?)))
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
