(ns twitterdsl.list-resource
  (:use [twitterdsl.core
         :only [is-instance?
                is-paging?
                user-validator]]))

(defn valid? [twitter user-id]
  (and (is-instance? twitter)
       (user-validator user-id)))

(defn valid-coll? [twitter user-id-coll]
  (and (is-instance? twitter)
       (every? true?
               (map user-validator
                    user-id-coll))))

(defn create-user-list
  [twitter list-name public? description]
  {:pre (is-instance? twitter)}
  (.. twitter
      (createUserList list-name
                      public?
                      description)))

(defn create-user-list-member
  ([twitter list-id user-id]
     {:pre (valid? twitter user-id)}
     (.. twitter
         (createUserListMember list-id
                               user-id)))
  ([twitter owner-id-or-username slug user-id]
     {:pre (valid? twitter user-id)}
     (.. twitter
         (createUserListMember owner-id-or-username
                               slug
                               user-id))))

(defn create-user-list-members
  ([twitter list-id user-id-or-name-coll]
     {:pre (valid-coll? twitter user-id-or-name-coll)}
     (.. twitter
         (createUserListMembers list-id
                                user-id-or-name-coll)))
  ([twitter owner-id-or-username slug user-id-or-name-coll]
     {:pre (valid-coll? twitter user-id-or-name-coll)}
     (.. twitter
         (createUserListMembers owner-id-or-username
                                slug
                                user-id-or-name-coll))))

(defn create-user-list-subscription
  ([twitter list-id]
     {:pre (is-instance? twitter)}
     (.. twitter
         (createUserListSubscription list-id)))
  ([twitter owner-id-or-username slug]
     {:pre (is-instance? twitter)}
     (.. twitter
         (createUserListSubscription owner-id-or-username
                                     slug))))

(defn desstroy-user-list
  ([twitter list-id]
     {:pre (is-instance? twitter)}
     (.. twitter
         (destroyUserList list-id)))
  ([twitter owners-id-or-name slug]
     {:pre (valid? twitter owners-id-or-name)}
     (.. twitter
         (destroyUserList owners-id-or-name
                          slug))))

(defn destroy-user-list-member
  ([twitter list-id user-id]
     {:pre (valid? twitter user-id)}
     (.. twitter
         (destroyUserListMember list-id user-id)))
    ([twitter owners-id-or-name slug user-id]
     {:pre (valid? twitter owners-id-or-name)}
     (.. twitter
         (destroyUserListMember owners-id-or-name
                                slug
                                user-id))))

(defn destroy-user-list-subscription
  ([twitter list-id]
     {:pre (is-instance? twitter)}
     (.. twitter
         (destroyUserListSubscription list-id)))
  ([twitter owners-id-or-name slug]
     {:pre (valid? twitter owners-id-or-name)}
     (.. twitter
         (destroyUserListSubscription owners-id-or-name
                                      slug))))

(defn get-user-list-memebers
  ([twitter list-id cursor]
     {:pre (is-instance? twitter)}
     (.. twitter
         (getUserListMemebers list-id cursor)))
  ([twitter owners-id-or-name slug cursor]
     {:pre (valid? twitter owners-id-or-name)}
     (.. twitter
         (getUserListMemebers owners-id-or-name
                              slug
                              cursor))))

(defn get-user-list-memberships
  ([twitter cursor]
     {:pre (is-instance? twitter)}
     (.. twitter
         (getUserListMemberships cursor)))
  ([twitter list-member-id cursor]
     {:pre (valid? twitter list-member-id)}
     (.. twitter
         (getUserListMemberships list-member-id
                                 cursor)))
  ([twitter list-member-id cursor filter-owned-lists?]
     {:pre (is-instance? twitter)}
     (.. twitter
         (getUserListMemberships list-member-id
                                 cursor
                                 filter-owned-lists?))))

(defn get-user-lists
  [twitter user-id-or-name]
  {:pre (valid? twitter user-id-or-name)}
  (.. twitter
      (getUserLists user-id-or-name)))

(defn get-user-list-statuses
  ([twitter list-id paging]
     {:pre [(is-instance? twitter)
            (is-paging? paging)]}
     (.. twitter
         (getUserListStatuses list-id paging)))
  ([twitter owners-id-or-name slug paging]
     {:pre [(valid? twitter owners-id-or-name)
            (is-paging? paging)]}
     (.. twitter
         (getUserListStatuses owners-id-or-name
                          slug
                          paging))))

(defn get-user-list-subscribers
  ([twitter list-id cursor]
     {:pre (is-instance? twitter)}
     (.. twitter
         (getUserListSubscribers list-id cursor)))
  ([twitter owners-id-or-name slug paging]
     {:pre (valid? twitter owners-id-or-name)}
     (.. twitter
         (getUserListSubscribers owners-id-or-name
                                 slug
                                 paging))))

(defn get-user-list-subscriptions
  [twitter list-owner-screen-name cursor]
  {:pre (is-instance? twitter)}
  (.. twitter
      (getUserListSubscriptions list-owner-screen-name
                                cursor)))

(defn show-user-list
  ([twitter list-id]
     {:pre (is-instance? twitter)}
     (.. twitter
         (showUserList list-id)))
  ([twitter owner-id-or-username slug]
     {:pre (is-instance? twitter)}
     (.. twitter
         (showUserList owner-id-or-username
                       slug))))

(defn show-user-list-membership
  ([twitter list-id user-id]
     {:pre [(is-instance? twitter)
            (pos? user-id)]}
     (.. twitter
         (showUserListMembership list-id user-id)))
  ([twitter owner-id-or-username slug user-id]
     {:pre [(valid? twitter owner-id-or-username)
            (pos? user-id)]}
     (.. twitter
         (showUserListMembership owner-id-or-username
                                 slug
                                 user-id))))

(defn show-user-list-subscription
  ([twitter list-id user-id]
     {:pre [(is-instance? twitter)
            (pos? user-id)]}
     (.. twitter
         (showUserListSubscription list-id user-id)))
  ([twitter owner-id-or-username slug user-id]
     {:pre [(valid? twitter owner-id-or-username)
            (pos? user-id)]}
     (.. twitter
         (showUserListSubscription owner-id-or-username
                                 slug
                                 user-id))))

(defn update-user-list
  ([twitter list-id new-list-name public? new-description]
     {:pre (is-instance? twitter)}
     (.. twitter
         (updateUserList list-id
                         new-list-name
                         public?
                         new-description)))
  ([twitter owner-id-or-username slug
    new-list-name public? new-description]
     {:pre (is-instance? twitter)}
     (.. twitter
         (updateUserList owner-id-or-username
                         slug
                         new-list-name
                         public?
                         new-description))))
