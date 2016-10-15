(ns anansi.server
  (:require [compojure.core :refer :all]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [compojure.route :as route]))

(defroutes server
  (GET "/" [] (ring.util.response/redirect "/index.html"))
  (GET "/data" [] (do (println "data") "<h1>Data route</h1>"))
  (route/resources "/"))
