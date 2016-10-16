(ns anansi.data
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream])
  (:require
    [clj-http.client :as client]
    [cheshire.core :refer [parse-string]]))

(defn recent-pins []
  (let [url "https://mathpunk:u890ppnb@api.pinboard.in/v1/posts/recent?format=json"
        body (:body (client/get url))
        json-or-edn (parse-string body true) ]
    (println json-or-edn)
    (str json-or-edn)))
