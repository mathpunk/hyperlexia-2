(ns anansi.data
  (:require [cheshire.core :refer [parse-string]]
    [cognitect.transit :as transit]
    [clojure.tools.reader.edn :as edn]
    [clj-http.client :as client]
    [clojure.walk :refer [keywordize-keys]]))

(defn recent-pins []
  (let [url "https://mathpunk:u890ppnb@api.pinboard.in/v1/posts/recent?format=json"]
    (client/get url)))
