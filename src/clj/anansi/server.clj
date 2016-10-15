(ns anansi.server
  (:require [compojure.core :refer :all]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [compojure.route :as route]))

(defroutes app
  ;; (route/resources "/")
  (GET "/data" []
    (do (println "data")
         "<h1>Data route</h1>"))
  (route/not-found "<h1>Page not found</h1>"))




(def server
  (-> app
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))
