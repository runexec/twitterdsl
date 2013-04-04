(ns twitterdsl.places-geo-resource
  (:use [twitterdsl.core
         :only [is-instance?
                is-geo-query?
                is-geolocation?]]))

(defn create-place
  [twitter name contained-within
   token location address]
  {:pre [(is-instance? twitter)
         (is-geolocation? location)
         (true?
          (every? string? 
                  [name 
                   token 
                   address
                   contained-within]))]}
  (.. twitter
      (createPlace 
       name
       contained-within
       token
       location
       address)))

(defn get-geo-details
  [twitter place-id]
  {:pre [(is-instance? twitter)]}
  (.. twitter
      (getGeoDetails place-id)))

(defn get-similar-places
  [twitter location name contained-within address]
  {:pre [(is-instance? twitter)
         (is-geolocation? location)
         (true?
          (every? string?
                  [name
                   address
                   contained-within]))]}
  (.. twitter
      (getSimilarPlaces
       name
       contained-within
       address)))

(defn reverse-geo-code 
  [twitter geo-query]
  {:pre [(is-instance? twitter)
         (is-geo-query? geo-query)]}
  (.. twitter
      (reverseGeoCode geo-query)))

(defn search-places
  [twitter geo-query]
  {:pre [(is-instance? twitter)
         (is-geo-query? geo-query)]}
  (.. twitter
      (searchPlaces geo-query)))
