(ns anansi.reading.components
  (:require [reagent-material-ui.core
    :refer [Avatar Card CardHeader CardTitle CardText FlatButton Toggle]]))

;; Item Cards
(defn card [p]
  [Card [CardText "This is a card."]])
