(ns anansi.data
  (:require
    [cheshire.core :refer [parse-string]]
    [cognitect.transit :as transit]
    [clojure.tools.reader.edn :as edn]
    [clj-http.client :as client]
    [clojure.walk :refer [keywordize-keys]]))

(defn recent-pins []
  (let [url "https://mathpunk:u890ppnb@api.pinboard.in/v1/posts/recent?format=json"]
    (client/get url)))



;; unused but possibly useful somewhere?
(defn destructure-tweet [tw]
  (if-let [matches (re-matches #"https://twitter\.com/(\w+)/status/(]0-9]+).*" (:href tw))]
    (assoc tw :user (nth matches 1) :status-id (nth matches 2))
    tw
  ))
