(ns ^{:doc "Can only be loaded after twitterdsl.dsl"}
  twitterdsl.dsl-user-handler)

(def ^:dynamic *user*)

(defmacro with [parsed-result & body]
  `(binding [*user* ~parsed-result]
     ~@body))

(defn parse [user]
  (let [_ user]
    {:bigger-profile-image-url (.getBiggerProfileImageURL _)
     :bigger-profile-image-url-https (.getBiggerProfileImageURLHttps _)
     :created-date (.getCreatedAt _)
     :description (.getDescription _)
     :description-url-entities (.getDescriptionURLEntities _)
     :favourites-count (.getFavouritesCount _)
     :followers-count (.getFollowersCount _)
     :friends-count (.getFriendsCount _)
     :user-id (.getId _)
     :language (.getLang _)
     :listed-count (.getListedCount _)
     :location (.getLocation _)
     :mini-profile-image-url (.getMiniProfileImageURL _)
     :mini-profile-image-url-https (.getMiniProfileImageURLHttps _)
     :name (.getName _)
     :original-profile-image-url (.getOriginalProfileImageURL _)
     :original-profile-image-url-https (.getOriginalProfileImageURLHttps _)
     :profile-background-color (.getProfileBackgroundColor _)
     :profile-background-image-url (.getProfileBackgroundImageURL _)
     :profile-background-image-url-https (.getProfileBackgroundImageUrlHttps _)
;;     :profile-banner-ipad-retina-url (.getProfileBannerIPadRetinaURL _)
;;     :profile-banner-ipad-url (.getProfilerBannerIPadURL _)
     :profile-banner-mobile-retina-url (.getProfileBannerMobileRetinaURL _)
     :profile-banner-mobile-url (.getProfileBannerMobileURL _)
     :profile-banner-url (.getProfileBannerURL _)
     :profile-image-url (.getProfileImageURL _)
     :profile-image-url-https (.getProfileImageURLHttps _)
     :profile-link-color (.getProfileLinkColor _)
     :profile-sidebar-border-color (.getProfileSidebarBorderColor _)
     :profile-sidebar-fill-color (.getProfileSidebarFillColor _)
     :profile-text-color (.getProfileTextColor _)
     :screen-name (.getScreenName _)
     :status (.getStatus _)
     :statuses-count (.getStatusesCount _)
     :timezone (.getTimeZone _)
     :user-url (.getURL _)
     :url-entity (.getURLEntity _)
     :utc-offset (.getUtcOffset _)
     :contributors-enabled? (.isContributorsEnabled _)
     :follow-request-sent? (.isFollowRequestSent _)
     :geo-enabled? (.isGeoEnabled _)
     :profile-background-tiled? (.isProfileBackgroundTiled _)
     :profile-use-background-image? (.isProfileUseBackgroundImage _)
     :protected? (.isProtected _)
     :show-all-inline-media? (.isShowAllInlineMedia _)
     :translator? (.isTranslator _)
     :verified? (.isVerified _)}))

(defn get-bigger-profile-image-url []
  (:bigger-profile-image-url *user*))

(defn get-bigger-profile-image-url-https []
  (:bigger-profile-image-url-https *user*))

(defn get-created-date []
  (:created-date *user*))

(defn get-description []
  (:description *user*))

(defn get-description-url-entities []
  (:description-url-entities *user*))

(defn get-favourites-count []
  (:favourites-count *user*))

(defn get-followers-count []
  (:followers-count *user*))

(defn get-friends-count []
  (:friends-count *user*))

(defn get-user-id []
  (:user-id *user*))

(defn get-language []
  (:language *user*))

(defn get-listed-count []
  (:listed-count *user*))

(defn get-location []
  (:location *user*))

(defn get-mini-profile-image-url []
  (:mini-profile-image-url *user*))

(defn get-mini-profile-image-url-https []
  (:mini-profile-image-url-https *user*))

(defn get-name []
  (:name *user*))

(defn get-original-profile-image-url []
  (:original-profile-image-url *user*))

(defn get-original-profile-image-url-https []
  (:original-profile-image-url-https *user*))

(defn get-profile-background-color []
  (:profile-background-color *user*))

(defn get-profile-background-image-url []
  (:profile-background-image-url *user*))

(defn get-profile-background-image-url-https []
  (:profile-background-image-url-https *user*))

(comment 
  (defn get-profile-banner-ipad-retina-url []
    (:profile-banner-ipad-retina-url *user*))

  (defn get-profile-banner-ipad-url []
    (:profile-banner-ipad-url *user*)))

(defn get-profile-banner-mobile-retina-url []
  (:profile-banner-mobile-retina-url *user*))

(defn get-profile-banner-mobile-url []
  (:profile-banner-mobile-url *user*))

(defn get-profile-banner-url []
  (:profile-banner-url *user*))

(defn get-profile-image-url []
  (:profile-image-url *user*))

(defn get-profile-image-url-https []
  (:profile-image-url-https *user*))

(defn get-profile-link-color []
  (:profile-link-color *user*))

(defn get-profile-sidebar-border-color []
  (:profile-sidebar-border-color *user*))

(defn get-profile-sidebar-fill-color []
  (:profile-sidebar-fill-color *user*))

(defn get-profile-text-color []
  (:profile-text-color *user*))

(defn get-screen-name []
  (:screen-name *user*))

(defn get-status []
  (:status *user*))

(defn get-statuses-count []
  (:statuses-count *user*))

(defn get-timezone []
  (:timezone *user*))

(defn get-user-url []
  (:user-url *user*))

(defn get-url-entity []
  (:url-entity *user*))

(defn get-utc-offset []
  (:utc-offset *user*))

(defn contributors-enabled? []
  (:contributors-enabled? *user*))

(defn follow-request-sent? []
  (:follow-request-sent? *user*))

(defn geo-enabled? []
  (:geo-enabled? *user*))

(defn profile-background-tiled? []
  (:profile-background-tiled? *user*))

(defn profile-use-background-image? []
  (:profile-use-background-image? *user*))

(defn protected? []
  (:protected? *user*))

(defn show-all-inline-media? []
  (:show-all-inline-media? *user*))

(defn translator? []
  (:translator? *user*))

(defn verified? []
  (:verified? *user*))
