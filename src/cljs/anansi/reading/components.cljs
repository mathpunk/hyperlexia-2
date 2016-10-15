(ns anansi.reading.components
  (:require [reagent-material-ui.core
    :refer [Avatar Card CardActions CardHeader CardTitle CardText FlatButton Toggle]]))

;; Tags
(defn tags [p]
  (if (empty? (:tags p))
  ""
  (clojure.string/split (:tags p) #" ") ) )

(defn Chip [& args]
  (conj [:div {:style {:color "blue"}}] args))

;; Cards
(defn card [p]
  [Card {:key (:hash p)}
    [CardHeader {:title (:description p)
                 :subtitle (:href p)}]
    [CardText (str "tags: " (clojure.string/join ", " (tags p) ))]
  ])
