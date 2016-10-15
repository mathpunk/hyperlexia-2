(ns anansi.reading.components
  (:require [reagent-material-ui.core
    :refer [Avatar Card CardActions CardHeader CardTitle CardText FlatButton Toggle]]))

;; Tags

(defn Chip [& args]
  (conj [:div {:style {:color "blue"}}] args))

(defn Tags [p]
  (let [tags (clojure.string/split (:tags p) #" ")]
    [:div.tags tags]
  )
  )

;; Cards
(defn card [p]
  [Card {:key (:hash p)}
    [CardHeader {:title "card" :subtitle "information"}]
    [Tags p]
  ])
