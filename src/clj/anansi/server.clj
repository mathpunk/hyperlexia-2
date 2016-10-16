(ns anansi.server
  (:require [compojure.core :refer :all]
            [anansi.data :refer :all]
            [cognitect.transit :as transit]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [compojure.route :as route]))

(defroutes server
  (GET "/" [] (ring.util.response/redirect "/index.html"))
  (GET "/recent" [] (recent-pins))
  (route/resources "/"))
