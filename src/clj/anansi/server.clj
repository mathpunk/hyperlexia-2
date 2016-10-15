(ns anansi.server
  (:require [compojure.core :refer :all]
            [ring.middleware.resource :refer [wrap-resource]]
            [compojure.route :as route]))

(defroutes server
  (route/resources "/")
  (GET "/hello" [] "<h1>Hello World</h1>")
  (route/not-found "<h1>Page not found</h1>"))
